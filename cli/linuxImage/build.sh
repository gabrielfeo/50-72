#!/usr/bin/env bash

# Copyright (c) 2022 Gabriel Feo
#
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at https://mozilla.org/MPL/2.0/.        

set -euo pipefail

# Always run from where the script is located
cd "$( dirname "${BASH_SOURCE[0]}" )"
root_project_dir="../.."

case "${1:-debug}" in
  debug)
    tasks=":cli:linkDebugExecutableLinuxX64"
    output="$root_project_dir/cli/build/bin/linuxX64/debugExecutable/cli.kexe"
    ;;
  release)
    tasks=":cli:linkReleaseExecutableLinuxX64"
    output="$root_project_dir/cli/build/bin/linuxX64/releaseExecutable/cli.kexe"
    ;;
esac

"$root_project_dir"/gradlew --project-dir "$root_project_dir" $tasks
cp "$output" "./50-72"
podman build . -t 50-72-linux
rm ./50-72
