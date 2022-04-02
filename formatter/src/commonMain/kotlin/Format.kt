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

private const val WORD_SPACING = ' '
private const val WORD_SPACING_SIZE = 1
private const val SUBJECT_BODY_SEPARATOR = "\n\n"


fun formatFullMessage(messageText: String, isMarkdown: Boolean = false): String {
    val message = CommitMessage(messageText)
    require(message.subjectIsUpTo50Columns) { HEADING_OVER_50_MESSAGE }
    if (!message.hasBody) {
        return message.fullText
    }
    require(message.hasSubjectBodySeparator) { NO_SUBJECT_BODY_SEPARATOR_MESSAGE }
    return buildString {
        append(message.subject())
        append(SUBJECT_BODY_SEPARATOR)
        when {
            isMarkdown -> appendReformattedMarkdownBody(message.body())
            else -> appendReformattedBody(message.body(), stripComments = true)
        }
    }
}

fun formatBody(bodyText: String, isMarkdown: Boolean = false): String {
    return buildString {
        when {
            isMarkdown -> appendReformattedMarkdownBody(bodyText)
            else -> appendReformattedBody(bodyText, stripComments = true)
        }
    }
}

@Deprecated("Use formatBody with parameter")
fun formatMarkdownBody(bodyText: String): String {
    return buildString {
        appendReformattedMarkdownBody(bodyText)
    }
}

private fun StringBuilder.appendReformattedMarkdownBody(bodyText: String) {
    val body = parseMarkdownBody(bodyText)
    for (section in body.sections) {
        if (this.isNotEmpty()) {
            append("\n\n")
        }
        when (section) {
            is Paragraph -> appendReformattedBody(section.content, stripComments = false)
            is Other -> append(section.content)
        }
    }
}


private fun StringBuilder.appendReformattedBody(body: String, stripComments: Boolean) {
    val bodyContent = when {
        !stripComments -> body
        else -> body.lines()
            .filterNot { it.startsWith('#') }
            .joinToString("\n")
    }
    val words = bodyContent.split(Regex("(?<=\\S)$WORD_SPACING*\\n(?!\\n)|$WORD_SPACING+"))
    var currentLineColumns = 0
    fun append(str: String) {
        this.append(str)
        if ('\n' in str) currentLineColumns = str.substringAfterLast('\n').count { it != '\n' }
        else currentLineColumns += str.length
    }
    for (word in words) {
        when {
            currentLineColumns == 0 -> append(word)
            currentLineColumns + word.length + WORD_SPACING_SIZE <= 72 -> append("$WORD_SPACING$word")
            else -> append("\n$word")
        }
    }
}

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
