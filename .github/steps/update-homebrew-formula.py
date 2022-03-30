#!/usr/bin/env python3

# Copyright (c) 2022 Gabriel Feo
#
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at https://mozilla.org/MPL/2.0/.        

import argparse
from collections import namedtuple
import hashlib
import os
import requests
import sys

TAP_REPOSITORY = 'gabrielfeo/homebrew-50-72'

parser = argparse.ArgumentParser()
parser.add_argument('--version', required=True)
parser.add_argument('--trigger-source', required=True)
parser.add_argument('--assets', nargs='+', required=True)
args = parser.parse_args()

github_username = os.environ["GITHUB_USERNAME"]
github_token = os.environ["GITHUB_TOKEN"]

Asset = namedtuple('Asset', ('path', 'sha'))


def sha256(path) -> str:
    sha = hashlib.sha256()
    with open(path, 'rb') as f:
        for bytes in iter(lambda: f.read(4096), b''):
            sha.update(bytes)
        return sha.hexdigest()


assert len(args.assets) == 2
for path in args.assets:
    asset = Asset(path, sha256(path))
    if 'linux' in path:
        linux = asset
    elif 'macos' in path:
        macos = asset
    print(asset)

response = requests.post(
    f"https://api.github.com/repos/{TAP_REPOSITORY}/dispatches",
    auth=(github_username, github_token),
    headers={
        'Accept': 'application/vnd.github.v3+json',
    },
    json={
        'event_type': 'update-formula',
        'client_payload': {
            'version': args.version,
            'linux_sha': linux.sha,
            'macos_sha': macos.sha,
            'trigger_source_url': args.trigger_source
        }
    }
)

if response.status_code in range(200, 299):
    print(f"Trigger successful: {response.status_code}")
else:
    print(f"Request failed: {response.status_code}\n{response.content}", file=sys.stderr)
    exit(1)
