/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import kotlin.test.Test

class FormatMessageFileTest {

    @Test
    fun givenMessageFileWithNewlineAtEofThenDoesNotFail() {
        formatFullMessage("0123456789012345678901234567890123456789\n")
    }
}
