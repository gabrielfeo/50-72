# 50-72

![CLI version shield][cli-version-shield]

`50-72` is an utility to format your git commit messages to the [50/72 rule][rule-about]
_automatically_:

```shell
$ git commit -m "Freestyle message
> 
> The quick brown fox jumps over the lazy dog. The quick brown fox jumps over the lazy dog. The quick
> brown fox jumps over the lazy dog. The quick brown fox jumps over the lazy dog. The quick brown fox jumps over the lazy dog."
#  |
#  |
#  |  Gets committed as
#  V
#
# Freestyle message
# 
# The quick brown fox jumps over the lazy dog. The quick brown fox jumps
# over the lazy dog. The quick brown fox jumps over the lazy dog. The
# quick brown fox jumps over the lazy dog. The quick brown fox jumps over
# the lazy dog.
```

📣 A browser extension for formatting merge commits on GitHub and GitLab is coming soon 📣

## Installing

ℹ️ It's stable, but still pre-release. File an [issue][new-issue] if you run into any problems

On both macOS and Linux using [Homebrew][brew]:

```shell
brew install gabrielfeo/50-72/cli
```

Now that the 50-72 command is available, install it in a git repository:

```shell
$ 50-72 hook --install
# Or, if you write messages in Markdown:
$ 50-72 hook --install --markdown
```

This adds formatting to that repository's [git hooks][man-githook]. Once installed,
it'll work from command-line `git` as well as any Git client you use with that repo.

## FAQ

<details>
  <summary>
    Are there any other features? Can I use it in my own scripts?
  </summary>

  A manual `format` command is available so you can use it as part of your own scripts (in CI,
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
    Are existing git hooks deleted when I run <code>hook --install</code>?
  </summary>

  No. If you already have a [`commit-msg` git hook][man-githook] in the repo, `50-72 hook --install`
  simply appends to it, preserving your existing commands. If there is no `commit-msg` hook, it
  will create one.

</details>

<details>
  <summary>
    Is it compatible with git GUIs like GitKraken, SourceTree, VS Code, IntelliJ?
  </summary>

  It should work with any git client because they all _should_ support git hooks. I have not tested
  it with every client though. If you run into any problems, please
  [submit a new issue][new-issue].

Known to work out-of-the-box:

- VS Code
- Let me know where else it works for you :)

</details>

<details>
  <summary>
    Are there any messages that aren't supported?
  </summary>

  The [Issues][issues] page is the most up-to-date source to see known issues.

</details>

<details>
  <summary>
    Are markdown messages supported?
  </summary>

  Markdown is fully supported in the CLI and browser extension.

</details>

<details>
  <summary>
    Do I have to re-run <code>--install</code> when a new <code>50-72</code> version comes out?
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

  ⚠️ Make sure to run `50-72 hook --uninstall` in every repo before uninstalling the
  command itself.

```shell
brew uninstall fifty-seventy-two-cli
```

  If you forget to uninstall the hook in some repository, you'll get a "command not found" error
  when you try to commit because in that repo the hook still tries to format the message using `50-72`.
  You can remove this leftover like this, from the root dir of the repo:

```shell
sed -I bak 's/^50-72 .*//' .git/hooks/commit-msg
```

</details>

## Building from source

If you'd like to build from source, you'll need:

- Linux or macOS (with Xcode developer tools)
- JDK 11 (with `JAVA_HOME` set)

Then just clone this repository and run from its root directory:

```shell
./gradlew :cli:buildLatestRelease
```

A symlink to the generated native executable will be created in `cli/build/release/latest/<platform>/bin` where
`<platform>` depends on your host OS and processor architecture. Add the desired directory to your `PATH` variable.

## Contributing

Usage and bug descriptions are great first contributions. PRs are also much welcome. Don't be afraid to ask for
help.

### Contributing code

Before committing, please take a look at existing commits to follow current project patterns, and of course,
follow the 50/72 rule! You can install the `50-72` hook in the `50-72` repo itself.

The project is written in Kotlin Multiplatform compiling to native code (for the CLI) and JavaScript (for the
browser extension). The build system is Gradle.

- To run all tests, `./gradlew check`. Report is generated in `build/reports/` of each project. 
- To build a CLI binary, see the "Building from source" section.
- To build the CLI with debug symbols, `./gradlew :cli:linkDebugExecutable`. Outputs to `cli/build/bin/`.
- To build the browser extension, `./gradlew :browser-extension:assemble`. Outputs to
  `browser-extension/distributions/`. [Load it unpacked][load-unpacked] in Chrome to test it.

[rule-about]: https://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html
[brew]: https://brew.sh/
[releases]: https://github.com/gabrielfeo/50-72/releases
[man-githook]: https://git-scm.com/docs/githooks#_commit_msg
[issues]: https://github.com/gabrielfeo/50-72/issues
[new-issue]: https://github.com/gabrielfeo/50-72/issues/new
[load-unpacked]: https://developer.chrome.com/docs/extensions/mv3/getstarted/#unpacked
[cli-version-shield]: https://img.shields.io/badge/dynamic/json?label=CLI&query=%24%5B%3F%28%2Fcli%5C%2F.%2A%2F.test%28%40.name%29%29%5D.name&url=https%3A%2F%2Fapi.github.com%2Frepos%2Fgabrielfeo%2F50-72%2Ftags
