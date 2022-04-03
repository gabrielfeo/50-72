/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

import kotlin.text.RegexOption.MULTILINE

internal const val HEADING_OVER_50_MESSAGE = "Heading line must not be over 50 columns. Please re-format the heading manually."
internal const val NO_SUBJECT_BODY_SEPARATOR_MESSAGE = "There must be a blank line between subject and body. Please add the blank line!"


fun formatFullMessage(messageText: String, isMarkdown: Boolean = false): String {
    val message = CommitMessage(messageText)
    require(message.subjectIsUpTo50Columns) { HEADING_OVER_50_MESSAGE }
    if (!message.hasBody) {
        return message.fullText
    }
    require(message.hasSubjectBodySeparator) { NO_SUBJECT_BODY_SEPARATOR_MESSAGE }
    return buildMessage {
        appendSubject(message.subject())
        when {
            isMarkdown -> appendBody(message.body(), stripComments = false, hashMeansHeadingParagraph = true)
            else -> appendBody(message.body(), stripComments = true, hashMeansHeadingParagraph = false)
        }
    }
}

fun formatBody(bodyText: String, isMarkdown: Boolean = false): String {
    return buildMessage {
        when {
            isMarkdown -> appendBody(bodyText, stripComments = false, hashMeansHeadingParagraph = true)
            else -> appendBody(bodyText, stripComments = true, hashMeansHeadingParagraph = false)
        }
    }
}


private class CommitMessage(fullMessage: String) {
    val fullText = fullMessage.trim()

    private val indexOfFirstMessageChar: Int = run {
        val match = Regex("^[^#\n]", MULTILINE).find(fullText)
        checkNotNull(match?.range?.first)
    }
    private val indexOfFirstMessageNewline =
        fullText.indexOf('\n', startIndex = indexOfFirstMessageChar)

    val hasBody = indexOfFirstMessageNewline != -1
    val subjectIsUpTo50Columns: Boolean = run {
            val firstMessageChar = indexOfFirstMessageChar
            val hasBodyAndUpTo50 = hasBody
                && indexOfFirstMessageNewline in firstMessageChar..firstMessageChar + 50
            val noBodyAndUpTo50 = fullText.length <= firstMessageChar + 50
            hasBodyAndUpTo50 || noBodyAndUpTo50
        }
    val hasSubjectBodySeparator = hasBody && fullText[indexOfFirstMessageNewline + 1] == '\n'

    fun subject() = fullText.slice(indexOfFirstMessageChar until indexOfFirstMessageNewline).trim()
    fun body() = fullText.slice(indexOfFirstMessageNewline + 2..fullText.lastIndex).trim()
}

private inline fun buildMessage(block: MessageBuilder.() -> Unit): String =
    MessageBuilder().apply(block).toString()

private class MessageBuilder : CharSequence {

    private val string = StringBuilder()
    private var currentLineLength: Int = 0
    private var hasPendingParagraphBreak = false

    fun appendSubject(subject: String) {
        appendRaw(subject)
        breakParagraph()
    }

    fun appendBody(
        body: String,
        stripComments: Boolean,
        hashMeansHeadingParagraph: Boolean,
    ) {
        body.lineSequence()
            .map { it.trim(' ') }
            .forEach {
                when {
                    it.isNotEmpty() && it.isNotComment() -> append(it)
                    it.isEmpty() -> breakParagraph()
                    it.isComment() -> when {
                        stripComments -> return@forEach
                        hashMeansHeadingParagraph -> append(it)
                        else -> appendRaw(it)
                    }
                    else -> error("Unpredicted case: line '$it'")
                }
            }
    }

    /**
     * Append a paragraph break to the message. The actual break is delayed until more content is
     * added. Will be discarded if
     * - no more content is appended to the message
     * - another break was added just before. It'd be a "double" paragraph break, which git would
     *   discard anyway.
     */
    private fun breakParagraph() {
        // Only break if more content is appended
        hasPendingParagraphBreak = true
    }

    /*
    * Note to self:
    *   1. Regexp matching words and md literals (not as separators but as matches)
    *   2. If it gets unwieldy, use a simple startsWith on each word to check for the
    *      start markup of a literal. If it is, take words until the corresponding end
    *      markup comes up
    *
    * It did. '\*\*[\w \n]+\*\*|_[\w \n]+_|~[\w \n]+~|<[\w=" \n]+\/>|\w+' not even
    * matching punctuation and apostrophes yet. Countless corner cases.
    * */

    /**
     * Append content to the message breaking lines to respect the 72-column limit and trimming
     * redundant spaces.
     */
    private fun append(value: CharSequence) {
        doPendingParagraphBreakOrReturn()
        for (word in value.split(Regex(" +"))) {
            val wordIsUpTo72 = word.length <= 72
            when {
                currentLineIsEmpty() -> appendRaw(word)
                wordIsUpTo72 && wouldLineStayUpTo72(word.length + 1) -> appendRaw(" $word")
                else -> appendRaw("\n$word")
            }
        }
    }

    /**
     * Append content to the message as-is, unlike [append].
     */
    private fun appendRaw(value: CharSequence) {
        doPendingParagraphBreakOrReturn()
        string.append(value)
        val indexOfLastLineBreakInValue = value.lastIndexOf('\n')
        val hasLineBreak = indexOfLastLineBreakInValue != -1
        if (hasLineBreak) {
            val lengthAfterLineBreak = value.length - indexOfLastLineBreakInValue - 1
            currentLineLength = lengthAfterLineBreak
        } else {
            currentLineLength += value.length
        }
    }

    private fun doPendingParagraphBreakOrReturn() {
        if (!hasPendingParagraphBreak || wouldNewParagraphBreakBeDoubleBreak()) {
            return
        }
        string.append('\n')
        string.append('\n')
        currentLineLength = 0
        hasPendingParagraphBreak = false
    }

    private fun wouldNewParagraphBreakBeDoubleBreak(): Boolean {
        return currentLineIsEmpty() && string.takeLast(2) == "\n\n"
    }

    private fun wouldLineStayUpTo72(addedLength: Int) = currentLineLength + addedLength < 72
    private fun currentLineIsEmpty() = currentLineLength == 0

    private fun String.isComment() = startsWith("#")
    private fun String.isNotComment() = !isComment()

    override val length = string.length
    override fun get(index: Int) = string[index]
    override fun subSequence(startIndex: Int, endIndex: Int) = string.subSequence(startIndex, endIndex)

    override fun equals(other: Any?) = other is MessageBuilder && other.string == this.string
    override fun hashCode() = string.hashCode()
    override fun toString() = string.toString()
}
