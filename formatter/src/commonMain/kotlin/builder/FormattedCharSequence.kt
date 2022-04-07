/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package builder

internal data class FormattedCharSequence(
    private val builder: StringBuilder = StringBuilder(),
) : CharSequence by builder {

    private var currentLineLength: Int = 0
    private var hasPendingParagraphBreak = false

    override fun toString() = builder.toString()

    /**
     * Append a paragraph break to the message. The actual break is delayed until more content is
     * added. Will be discarded if
     * - no more content is appended to the message
     * - another break was added just before. It'd be a "double" paragraph break, which git would
     *   discard anyway.
     */
    fun breakParagraph() {
        // Only break if more content is appended
        hasPendingParagraphBreak = true
    }

    /**
     * Append content to the message breaking lines to respect the 72-column limit and trimming
     * redundant spaces.
     */
    fun append(value: CharSequence) {
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
    fun appendRaw(value: CharSequence) {
        doPendingParagraphBreakOrReturn()
        builder.append(value)
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
        builder.append('\n')
        builder.append('\n')
        currentLineLength = 0
        hasPendingParagraphBreak = false
    }

    private fun wouldNewParagraphBreakBeDoubleBreak(): Boolean {
        return currentLineIsEmpty() && builder.takeLast(2) == "\n\n"
    }

    private fun wouldLineStayUpTo72(addedLength: Int) = currentLineLength + addedLength < 72
    private fun currentLineIsEmpty() = currentLineLength == 0
}
