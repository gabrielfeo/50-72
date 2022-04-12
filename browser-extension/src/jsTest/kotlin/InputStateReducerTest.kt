/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import InputEvent.BodyInputChange
import InputEvent.FormatButtonClick
import InputState.*
import kotlin.test.Test
import kotlin.test.assertEquals

class InputStateReducerTest {

    private var formatThrows = false
    private var formatCalled = false
    private val format: (String, Boolean) -> String = { text, _ ->
        formatCalled = true
        if (formatThrows) throw IllegalArgumentException()
        else "formatted $text"
    }

    private val reduce = InputStateReducer(format)::reduce

    @Test
    fun formatsAndShowsSubmitOnFormatClick() {
        val state = reduce(ShowingFormatButton, FormatButtonClick("any"))
        assertEquals(ShowingSubmitButton("formatted any"), state)
    }

    @Test
    fun formatsAndShowsSubmitOnFormatClickAfterError() {
        val state = reduce(ShowingFormatError, FormatButtonClick("any"))
        assertEquals(ShowingSubmitButton("formatted any"), state)
    }

    @Test
    fun showsFormatOnInputChange() {
        val state = reduce(ShowingSubmitButton("any"), BodyInputChange)
        assertEquals(ShowingFormatButton, state)
    }

    @Test
    fun keepsShowingFormatOnFurtherInputChanges() {
        val state = reduce(ShowingFormatButton, BodyInputChange)
        assertEquals(ShowingFormatButton, state)
    }

    @Test
    fun keepsShowingFormatOnInputChangeAfterError() {
        val state = reduce(ShowingFormatError, BodyInputChange)
        assertEquals(ShowingFormatButton, state)
    }

    @Test
    fun showsFormatError() {
        formatThrows = true
        val state = reduce(ShowingFormatButton, FormatButtonClick("any"))
        assertEquals(ShowingFormatError, state)
    }

    @Test
    fun invalidCases() {
        // Format clicked when not shown
        val state = reduce(ShowingSubmitButton("a"), FormatButtonClick("b"))
        assertEquals(ShowingSubmitButton("formatted b"), state)
    }
}
