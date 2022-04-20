/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import builder.buildMessage
import info.CommitMessageInfo
import info.DEFAULT_GIT_COMMENT_CHAR

internal const val HEADING_OVER_50_MESSAGE = "Heading line must not be over 50 columns. Please re-format the heading manually."
internal const val NO_SUBJECT_BODY_SEPARATOR_MESSAGE = "There must be a blank line between subject and body. Please add the blank line!"


fun formatFullMessage(
    messageText: String,
    commentChar: Char = DEFAULT_GIT_COMMENT_CHAR,
    isMarkdown: Boolean = false,
): String {
    val message = CommitMessageInfo(messageText, commentChar)
    require(message.subjectIsUpTo50Columns) { HEADING_OVER_50_MESSAGE }
    if (!message.hasBody) {
        return message.fullText
    }
    require(message.hasSubjectBodySeparator) { NO_SUBJECT_BODY_SEPARATOR_MESSAGE }
    return buildMessage(commentChar, isMarkdown) {
        appendSubject(message.subject())
        appendBody(message.body())
    }
}

fun formatBody(
    bodyText: String,
    commentChar: Char = DEFAULT_GIT_COMMENT_CHAR,
    isMarkdown: Boolean = false,
): String {
    return buildMessage(commentChar, isMarkdown) {
        appendBody(bodyText)
    }
}
