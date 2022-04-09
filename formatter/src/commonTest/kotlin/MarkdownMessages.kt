/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

const val MD_BODY_71 = """# H1

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo fo

foo foo foo

## H2

foo foo foo"""

const val MD_BODY_72 = """# H1

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

## H2

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo"""

const val MD_BODY_OVER_72 = """# H1

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

foo foo

## H2

foo foo
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo"""

const val MD_BODY_OVER_72_FIXED = """# H1

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo

foo foo

## H2

foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo foo foo"""

const val MD_BODY_72_WITH_SNIPPET = """# H1

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

```kotlin
println("snippet")
println("snippet")
```"""

const val MD_BODY_72_WITH_SNIPPET_FORMATTED_AS_PLAIN_TEXT = """01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

```kotlin println("snippet") println("snippet") ```"""

const val MD_FULL_MSG_72_WITH_SNIPPET = """$SINGLE_LINE_50

$MD_BODY_72_WITH_SNIPPET"""

const val MD_FULL_MSG_72_WITH_SNIPPET_FORMATTED_AS_PLAIN_TEXT = """$SINGLE_LINE_50

$MD_BODY_72_WITH_SNIPPET_FORMATTED_AS_PLAIN_TEXT"""

const val MD_BODY_OVER_72_WITH_MORE_MD_FEATURES = """# H1

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

```
snippety snip
```

foo foo

## H2

foo foo
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

**Paragraph starting** with bold. Here's _italics_ and ~strikethrough~.

<img src="foo" />

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

![Screenshot](/uploads/foo.png)

```
pseudo code
```

```kotlin
println("snippet")
println("snippet")
```

| Before | After |
|--------|-------|
| Foo    | Bar   |"""

const val MD_BODY_OVER_72_WITH_MORE_MD_FEATURES_FIXED = """# H1

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo

```
snippety snip
```

foo foo

## H2

foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo foo foo

**Paragraph starting** with bold. Here's _italics_ and ~strikethrough~.

<img src="foo" />

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo

![Screenshot](/uploads/foo.png)

```
pseudo code
```

```kotlin
println("snippet")
println("snippet")
```

| Before | After |
|--------|-------|
| Foo    | Bar   |"""
