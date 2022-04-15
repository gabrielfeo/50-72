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

const val MD_BODY_AT_72_WITH_LIST_ITEMS = """# H1

foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo

- List item A
- List item B

- List item A
- List item B

foo foo

1. List item A
2. List item B

foo

- A
  - Nested

1. B
  a. Nested

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

>Quote

>Multi-line quote
>Multi-line quote

## H2

foo foo
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

### H3

**Paragraph starting** with bold. Here's _italics_ and ~strikethrough~.

#### H4

<img src="foo" />

##### H5

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

![Screenshot](/uploads/foo.png)

###### H6

```
pseudo code
```

```kotlin
println("snippet")
println("snippet")
```

<details>
<summary>
Foo
</summary>

Bar

</details>

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

>Quote

>Multi-line quote
>Multi-line quote

## H2

foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo foo foo

### H3

**Paragraph starting** with bold. Here's _italics_ and ~strikethrough~.

#### H4

<img src="foo" />

##### H5

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo

![Screenshot](/uploads/foo.png)

###### H6

```
pseudo code
```

```kotlin
println("snippet")
println("snippet")
```

<details>
<summary>
Foo
</summary>

Bar

</details>

| Before | After |
|--------|-------|
| Foo    | Bar   |"""
