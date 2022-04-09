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
            when {
                currentLineIsEmpty() -> appendRaw(word)
                word.isUpTo72() && wouldLineStayUpTo72Adding(word.length + 1) -> appendRaw(" $word")
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
        updateCurrentLineLength(value)
    }

    private fun updateCurrentLineLength(addedValue: CharSequence) {
        val indexOfLastLineBreak = addedValue.lastIndexOf('\n')
        val hasLineBreak = indexOfLastLineBreak != -1
        if (hasLineBreak) {
            val lengthAfterLineBreak = addedValue.length - indexOfLastLineBreak - 1
            currentLineLength = lengthAfterLineBreak
        } else {
            currentLineLength += addedValue.length
        }
    }

    private fun doPendingParagraphBreakOrReturn() {
        if (!hasPendingParagraphBreak || currentLineIsEmpty() || wouldNewParagraphBreakBeDoubleBreak()) {
            hasPendingParagraphBreak = false
            return
        }
        builder.append("\n\n")
        currentLineLength = 0
        hasPendingParagraphBreak = false
    }

    private fun wouldNewParagraphBreakBeDoubleBreak(): Boolean {
        return builder.takeLast(2) == "\n\n"
    }

    private fun String.isUpTo72() = this.length <= 72
    private fun wouldLineStayUpTo72Adding(addedLength: Int) = currentLineLength + addedLength < 72
    private fun currentLineIsEmpty() = currentLineLength == 0
}
