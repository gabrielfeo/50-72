/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class Presenter<T, R>(
    private val initialState: T,
    private val reducer: Reducer<T, R>,
) {

    fun subscribe(
        events: Flow<R>,
        scope: CoroutineScope,
    ): Flow<T> {
        val state = MutableStateFlow(initialState)
        scope.launch {
            events.collect { event ->
                state.value = reducer.reduce(state.value, event)
            }
        }
        return state.asStateFlow()
    }
}
