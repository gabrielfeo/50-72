/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

package cli.commons

import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertTrue

const val EXAMPLE_ERRNO = 129

class ChmodFilePermissionSetterTest {

    private var chmodFails = false
    private var chmodCalled = false
    private val setter = ChmodFilePermissionSetter(
        chmod = { _, _ ->
            chmodCalled = true
            if (chmodFails) 1 else 0
        },
        errno = { EXAMPLE_ERRNO },
    )

    @Test
    fun callsPlatformChmod() {
        setter.set("anypath", PermissionSet.`755`)
        assertTrue(chmodCalled)
    }

    @Test
    fun givenChmodSucceeds_whenSet_thenReturn() {
        setter.set("anypath", PermissionSet.`755`)
    }

    @Test
    fun givenChmodFails_whenSet_thenThrows() {
        chmodFails = true
        val error = assertFails {
            setter.set("anypath", PermissionSet.`755`)
        }
        assertTrue(EXAMPLE_ERRNO.toString() in error.message.orEmpty(), "Did not mention errno")
    }
}