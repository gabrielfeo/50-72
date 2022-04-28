/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

 package validation

import HEADING_OVER_50_MESSAGE
import NO_SUBJECT_BODY_SEPARATOR_MESSAGE
import info.CommitMessageInfo

internal fun interface CommitMessageValidator {
    fun validate(info: CommitMessageInfo)
}

internal class RealCommitMessageValidator : CommitMessageValidator {

    override fun validate(info: CommitMessageInfo) {
        with(info) {
            require(subjectIsUpTo50Columns) { HEADING_OVER_50_MESSAGE }
            require(hasSubjectBodySeparator || !hasBody) { NO_SUBJECT_BODY_SEPARATOR_MESSAGE }
        }
    }
}
