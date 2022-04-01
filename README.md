# 50-72

`50-72` is an utility to format your git commit messages to the [50/72 rule][rule-about]
_automatically_.

ℹ️ It's currently in a pre-release state but you can build from source (see below) ℹ️

📣 A browser extension for formatting merge commits on GitHub and GitLab is coming soon 📣

Install it in a local repository and it'll work from command-line `git` and any Git GUI or IDE you use Git with:

```shell
# From the root dir of a repository:

$ 50-72 hook --install
$ git commit -m "Freestyle message
> 
> You don't have to worry about breaking lines yourself anymore. The quick brown fox jumps over the lazy dog. The quick
> brown fox jumps over the lazy dog. The quick brown fox jumps over the lazy dog. The quick brown"
#  |
#  |  Gets committed as 
#  V
#
# Freestyle message
# 
# You don't have to worry about breaking lines yourself anymore. The quick
# brown fox jumps over the lazy dog. The quick brown fox jumps over the
# lazy dog. The quick brown fox jumps over the lazy dog. The quick brown
```

The hook must be installed per repository.

## Installing

The easiest way to install on both macOS and Linux is with [Homebrew][brew]:

```shell
brew install gabrielfeo/50-72/cli
```

This installs a pre-built binary from the [latest stable release][releases]. See below for instructions on how to build
from source.

## FAQ

<details>
  <summary>
    Are there any other features? Can I use it in my own scripts?
  </summary>
  A manual formatting command is available so you can use it as part of your own scripts (in CI,
  for example). Here's the full help text:

```
Usage: 50-72 [OPTIONS] COMMAND [ARGS]...

Format commit messages to the 50/72 rule automatically.

It's recommended to install it in the git hooks of each repository:

50-72 hook --install

Otherwise, manual usage is:

50-72 format MESSAGE (to format a message string)
50-72 format-file (to format the git commit message file)

See --help of each subcommand for more.

Options:
-h, --help  Show this message and exit

Commands:
format       Format a message string.
format-file  Format the git commit message file (or another file)
hook         Install the 50-72 git hook in the current repository.
```

</details>

<details>
  <summary>
    How does it work?
  </summary>
  <code>50-72 hook --install</code> simply adds to the
  <a href="https://git-scm.com/docs/githooks#_prepare_commit_msg"><code>prepare-commit-msg</code> git hook</a>
  of the repository you run it from. If you already have such a hook in that repo, it will append to it,
  otherwise it will create one. 
</details>

<details>
  <summary>
    Is it compatible with GitKraken/SourceTree/VS Code/IntelliJ/(...) git client?
  </summary>
  It should work with any git client because they all <i>should</i> support git hooks. I have not tested
  it with every client though. If you run into any problems, please
  <a href="https://github.com/gabrielfeo/50-72/issues/new">submit a new issue</a>.

Known to work out-of-the-box:

- VS Code
- Let me know where else it works for you :)

</details>

<details>
  <summary>
    Are there any messages that aren't supported?
  </summary>
  The <a href="https://github.com/gabrielfeo/50-72/issues">Issues</a> page is the most up-to-date
  source to see known issues.
</details>

<details>
  <summary>
    Are markdown messages supported?
  </summary>
  Markdown support is available with <code>50-72 format --markdown [message]</code> but still needs work. Currently,
  the hook can't be installed with markdown enabled. Markdown will be fully supported in the browser extension.
</details>

<details>
  <summary>
    Do I have to re-run `--install` when a new `50-72` version comes out?
  </summary>
  No. The git hook calls `50-72` itself, so it'll always get the latest installed version on your
  system (technically, the first one from your `PATH`).
</details>

<details>
  <summary>
    How can I uninstall the hook from a repository?
  </summary>
  From the repository root dir:

```shell
50-72 hook --uninstall
```

</details>

<details>
  <summary>
    How can I uninstall command itself from my machine?
  </summary>

```shell
brew uninstall fifty-seventy-two-cli
brew untap gabrielfeo/50-72
```

</details>

## Building from source

If you'd like to build from source, you'll need:

- Linux or macOS*
- JDK 11 (with `JAVA_HOME` set)

Then just clone this repository and run from its root directory:

```shell
./gradlew :cli:packageRelease
```

A zip file with the `50-72` executable is generated in `cli/build/release`. Unzip in a directory and add this directory
to your shell `PATH` variable.

*You need Xcode developer tools installed, but you probably have it already if you ever compiled anything on macOS

## Contributing

Usage and bug descriptions are great first contributions. I'll treat bug reports as a gift.

PRs are much welcome and I'll be happy to help you get started if you need. If you want to contribute code, please take
a look at existing commits to follow current project patterns, and of course, follow the 50/72 rule! You can install
the `50-72` hook in the `50-72` repo itself.

[rule-about]: https://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html
[brew]: https://brew.sh/
[releases]: https://github.com/gabrielfeo/50-72/releases
