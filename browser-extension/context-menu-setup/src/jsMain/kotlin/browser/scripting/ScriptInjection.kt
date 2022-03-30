/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

package browser.scripting

class ScriptInjection(
    val target: InjectionTarget,
    val func: (() -> Unit)? = null,
    val files: Array<String>? = null,
) {
    class InjectionTarget(val tabId: String)
}