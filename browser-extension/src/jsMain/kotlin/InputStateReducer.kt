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

sealed interface InputState {
    data class ShowingSubmitButton(val text: String) : InputState
    object ShowingFormatButton : InputState
    object ShowingFormatError : InputState
}

sealed interface InputEvent {
    object BodyInputChange : InputEvent
    data class FormatButtonClick(val bodyText: String) : InputEvent
}

class InputStateReducer(
    private val format: (text: String, isMarkdown: Boolean) -> String,
) : Reducer<InputState, InputEvent> {

    override fun reduce(
        state: InputState,
        event: InputEvent,
    ) = when (event) {
        BodyInputChange -> ShowingFormatButton
        is FormatButtonClick -> {
            try {
                val newText = format(event.bodyText, true)
                ShowingSubmitButton(newText)
            } catch (e: IllegalArgumentException) {
                ShowingFormatError
            }
        }
    }
}
