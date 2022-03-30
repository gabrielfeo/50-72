#!/usr/bin/env bash

# Copyright (c) 2022 Gabriel Feo
#
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at https://mozilla.org/MPL/2.0/.

set -euo pipefail

tag_name=${1:?Tag name expected}

echo "TAG_NAME=$tag_name"
echo "TAG_SUBJECT=$(git tag --list --format='%(contents:subject)' $tag_name)"
echo "TAG_BODY=$(git tag --list --format='%(contents:body)' $tag_name)"
