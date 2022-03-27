#!/usr/bin/env python3

import argparse
import sys
import requests
import hashlib

parser = argparse.ArgumentParser()
parser.add_argument('--version')
parser.add_argument('--trigger-source', dest='trigger_source')
parser.add_argument('--username')
parser.add_argument('--password')
args = parser.parse_args()

linux_zip_location=f"./cli/build/release/50-72-{args.version}-linuxX64.zip"
macos_zip_location=f"./cli/build/release/50-72-{args.version}-macosX64.zip"


def sha256(path) -> str:
    sha = hashlib.sha256()
    with open(path, 'rb') as f:
        for bytes in iter(lambda: f.read(4096), b''):
            sha.update(bytes)
        return sha.hexdigest()


linux_sha = sha256(linux_zip_location)
macos_sha = sha256(macos_zip_location)
print('Artifact SHA-256 sums:')
print(f"{linux_zip_location}: {linux_sha}")
print(f"{macos_zip_location}: {macos_sha}\n")

response = requests.post(
    'https://api.github.com/repos/gabrielfeo/homebrew-50-72/dispatches',
    auth=(args.username, args.password),
    headers={
        'Accept': 'application/vnd.github.v3+json',
    },
    json={
        'event_type': 'update-formula',
        'client_payload': {
            'version': args.version,
            'linux_sha': linux_sha,
            'macos_sha': macos_sha,
            'trigger_source_url': args.trigger_source
        }
    }
)

if response.status_code not in range(200, 299):
    print(f"Request failed: {response.status_code}\n{response.content}", file=sys.stderr)
    exit(1)
