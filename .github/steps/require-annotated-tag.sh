#!/usr/bin/env bash

# Copyright (c) 2022 Gabriel Feo
#
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at https://mozilla.org/MPL/2.0/.

set -euo pipefail

tag_name=${1:?Tag name expected}

tag_ref="$(git cat-file -t "$tag_name")"
if [[ "$tag_ref" != "tag" ]]; then
  echo "Tag $tag_name isn't annotated: $tag_ref"
  exit 1
fi
