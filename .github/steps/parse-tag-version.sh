#!/usr/bin/env bash

# Copyright (c) 2022 Gabriel Feo
#
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at https://mozilla.org/MPL/2.0/.

set -euo pipefail

tag_name="${1:?Tag name expected}"

if [[ "$tag_name" != */* ]] || [[ "$tag_name" == */*/* ]]; then
  echo "Tag '$tag_name' must have exactly one prefix" >&2
  exit 1
fi

# 'cli/v1.0.0' -> 'v1.0.0'
echo "TAG_VERSION=${tag_name#*/}"
