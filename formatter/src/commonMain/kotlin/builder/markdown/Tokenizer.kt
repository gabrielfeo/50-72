/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package builder.markdown

import builder.markdown.TokenType.GitComment
import builder.markdown.TokenType.MarkdownToken

internal data class Tokenizer(
    private val commentChar: Char,
) {

    private val tokenTypes = arrayOf(
        GitComment(commentChar),
        *MarkdownToken.values(),
    )

    fun tokenizePosition(
        currentPosition: Int,
        body: String,
    ): Token {
        for (type in tokenTypes) {
            val match = type.matchAt(body, currentPosition) ?: continue
            return Token(type, match)
        }
        val currentPositionSnippet = body.substring(currentPosition, currentPosition + 10)
        error("No match for position $currentPosition: $currentPositionSnippet...")
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun TokenType.matchAt(
        string: String,
        index: Int,
    ): String? {
        val match = Regex(pattern).matchAt(string, index)
        return match?.value
    }
}
