#!/usr/bin/env bash

# Copyright (c) 2022 Gabriel Feo
#
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at https://mozilla.org/MPL/2.0/.

set -euo pipefail

tag_name=${1:?Tag name expected}

git update-ref -d "refs/heads/$tag_name" || true
git fetch -f origin "refs/tags/$tag_name:refs/tags/$tag_name"
