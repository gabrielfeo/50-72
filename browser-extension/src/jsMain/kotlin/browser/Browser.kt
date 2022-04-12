/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

package browser

@JsName("internalBrowser")
val browser: dynamic get() = when {
    jsTypeOf(js("browser")) != "undefined" -> js("browser")
    jsTypeOf(js("chrome")) != "undefined" -> js("chrome")
    else -> error("Unsupported browser")
}
