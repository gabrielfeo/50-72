/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

import CommitMessage.MarkdownBody
import CommitMessage.MarkdownBody.Section.Other
import CommitMessage.MarkdownBody.Section.Paragraph
import kotlin.jvm.JvmInline
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
        appendRaw(message.subject())
        breakParagraph()
        when {
            isMarkdown -> appendReformattedMarkdownBody(message.body())
            else -> appendReformattedBody(message.body(), stripComments = true)
        }
    }
}

fun formatBody(bodyText: String, isMarkdown: Boolean = false): String {
    return buildMessage {
        when {
            isMarkdown -> appendReformattedMarkdownBody(bodyText)
            else -> appendReformattedBody(bodyText, stripComments = true)
        }
    }
}

@Deprecated("Use formatBody with parameter")
fun formatMarkdownBody(bodyText: String): String {
    return buildMessage {
        appendReformattedMarkdownBody(bodyText)
    }
}

private fun MessageBuilder.appendReformattedMarkdownBody(bodyText: String) {
    val body = parseMarkdownBody(bodyText)
    for (section in body.sections) {
        if (this.isNotEmpty()) {
            breakParagraph()
        }
        when (section) {
            is Paragraph -> appendReformattedBody(section.content, stripComments = false)
            is Other -> append(section.content)
        }
    }
}



private fun MessageBuilder.appendReformattedBody(body: String, stripComments: Boolean) {
    body.lineSequence()
        .map { it.trim(' ') }
        .forEach {
            when {
                it.isNotEmpty() && it.isNotComment() -> append(it)
                it.isEmpty() -> breakParagraph()
                stripComments && it.isComment() -> return@forEach
                it.isComment() -> appendRaw(it)
                else -> error("Unpredicted case: line '$it'")
            }
        }
}

private fun String.isComment() = startsWith("#")
private fun String.isNotComment() = !isComment()

@Suppress("RemoveExplicitTypeArguments")
private fun parseMarkdownBody(body: String): MarkdownBody {
    val sectionsRegex = Regex("""(?:[^\n]+\n?)+""", MULTILINE)
    val sections = sectionsRegex.findAll(body)
        .map { it.value }
        .map {
            if (it.endsWith('\n')) it.substring(0 until it.lastIndex)
            else it
        }
        .map {
            if (it.first().isLetterOrDigit()) Paragraph(it)
            else Other(it)
        }
    return MarkdownBody(sections)
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

    @JvmInline
    value class MarkdownBody(val sections: Sequence<Section>) {

        sealed interface Section {

            @JvmInline
            value class Paragraph(val content: String) : Section

            @JvmInline
            value class Other(val content: String) : Section
        }
    }
}

private inline fun buildMessage(block: MessageBuilder.() -> Unit): String =
    MessageBuilder().apply(block).toString()

/*
 * Note to self: Current separation of concerns is to keep MessageBuilder with the line-breaking logic onlu. Line and
 * section splitting is done outside the class. Only consider refactoring this further once all test are passing
 * including Markdown tests using the new implementation.
 */
private class MessageBuilder : CharSequence {

    private var currentLineLength: Int = 0

    private val string = StringBuilder()

    private var hasPendingParagraphBreak = false

    fun breakParagraph() {
        // Only break if more content is appended
        hasPendingParagraphBreak = true
    }

    fun appendRaw(value: CharSequence) {
        doPendingParagraphBreakOrReturn()
        string.append(value)
        val indexOfLastNewline = value.lastIndexOf('\n')
        if (indexOfLastNewline == -1) {
            currentLineLength += value.length
        } else {
            val lengthAfterNewline = value.length - indexOfLastNewline - 1
            currentLineLength = lengthAfterNewline
        }
    }

    fun append(value: CharSequence) {
        doPendingParagraphBreakOrReturn()
        for (word in value.split(Regex(" +"))) {
            val wordIs72OrMore = word.length >= 72
            if (wordIs72OrMore) {
                if (currentLineLength == 0) {
                    string.append("$word\n")
                    currentLineLength = 0
                } else {
                    string.append("\n$word\n")
                }
                currentLineLength = 0
            } else {
                val mustPrependSpace = currentLineLength != 0
                val addedLength = when {
                    mustPrependSpace -> word.length + 1
                    else -> word.length
                }
                val lineWontPass72 = currentLineLength + addedLength <= 72
                if (lineWontPass72) {
                    if (mustPrependSpace) {
                        string.append(' ')
                    }
                    string.append(word)
                    currentLineLength += addedLength
                } else {
                    string.append("\n$word")
                    currentLineLength = addedLength
                }
            }
        }
    }

    // TODO Remove?
    private fun append(value: Char) {
        if (value == '\n') {
            currentLineLength = 0
        } else {
            val lineWouldPass72 = currentLineLength + 1 > 72
            if (lineWouldPass72) {
                string.append('\n')
                currentLineLength = 1
            }
        }
        string.append(value)
    }

    private fun doPendingParagraphBreakOrReturn() {
        if (!hasPendingParagraphBreak || shouldIgnoreParagraphBreak()) {
            return
        }
        string.append('\n')
        string.append('\n')
        currentLineLength = 0
        hasPendingParagraphBreak = false
    }

    private fun shouldIgnoreParagraphBreak(): Boolean {
        // Git ignores any line-breaks beyond the second one, so should we
        return currentLineLength == 0 && string.takeLast(2) == "\n\n"
    }

    override val length = string.length
    override fun get(index: Int) = string[index]
    override fun subSequence(startIndex: Int, endIndex: Int) = string.subSequence(startIndex, endIndex)

    override fun equals(other: Any?) = other is MessageBuilder && other.string == this.string
    override fun hashCode() = string.hashCode()
    override fun toString() = string.toString()
}
