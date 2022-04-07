/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import builder.buildMarkdownMessage
import builder.buildPlainTextMessage

internal const val HEADING_OVER_50_MESSAGE = "Heading line must not be over 50 columns. Please re-format the heading manually."
internal const val NO_SUBJECT_BODY_SEPARATOR_MESSAGE = "There must be a blank line between subject and body. Please add the blank line!"


fun formatFullMessage(messageText: String, isMarkdown: Boolean = false): String {
    val message = CommitMessageInfo(messageText)
    require(message.subjectIsUpTo50Columns) { HEADING_OVER_50_MESSAGE }
    if (!message.hasBody) {
        return message.fullText
    }
    require(message.hasSubjectBodySeparator) { NO_SUBJECT_BODY_SEPARATOR_MESSAGE }
    return buildPlainTextMessage {
        appendSubject(message.subject())
        appendBody(message.body(), stripComments = true)
    }
}

fun formatBody(bodyText: String, isMarkdown: Boolean = false): String {
    return buildMarkdownMessage {
        appendBody(bodyText, stripComments = false)
    }
}
