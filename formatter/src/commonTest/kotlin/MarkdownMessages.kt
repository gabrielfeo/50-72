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
- List item C over 72 foo foo foo foo foo foo foo foo foo foo foo foo
foo foo foo foo
- List item D
- List item E

foo foo

1. List item A
2. List item B
3. List item C over 72 foo foo foo foo foo foo foo foo foo foo foo foo
foo foo foo foo
4. List item D
5. List item E

foo

- A
  - Nested
  - Nested over 72 foo foo foo foo foo foo foo foo foo foo foo foo foo foo
  - Nested

1. B
  a. Nested
  b. Nested over 72 foo foo foo foo foo foo foo foo foo foo foo foo foo foo
  c. Nested

## H2

foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo foo foo"""

const val MD_BODY_AT_72_WITH_REDUNDANT_WHITESPACE_BETWEEEN_PARAGRAPHS = """# H1

foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo
            
foo
	
foo
  
- A
  - Nested

 
 


1. B
  a. Nested

1. C
  a. Nested"""

const val MD_BODY_AT_72_WITH_REDUNDANT_WHITESPACE_TRIMMED = """# H1

foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo

foo

foo

- A
  - Nested

1. B
  a. Nested

1. C
  a. Nested"""

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

const val MD_MSG_WITH_COMMENT_CHAR_HASH = """Subject

# H1

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo

foo foo

## H2

#Comment

foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo foo foo
# Comment"""

const val MD_MSG_WITH_COMMENT_CHAR_HASH_STRIPPED = """Subject

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo

foo foo

foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo foo foo"""

const val MD_MSG_WITH_COMMENT_CHAR_SEMICOLON = """Subject

# H1

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo

foo foo

## H2

;Comment

foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo foo foo
; Comment"""

const val MD_MSG_WITH_COMMENT_CHAR_SEMICOLON_STRIPPED = """Subject

# H1

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo

foo foo

## H2

foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo foo foo"""

const val MD_BODY_WITH_MISC_PUNCTUATION = """[JIRA-1] (subject): bla bla. Bla (bla...) or 'bla'

bla. 1bla `bla`; bla\bla bla/bla bla+bla bla+ bla++ --bla -bla -b. "bla"? bla@bla bla?! US${'$'} 40.00 | 2¢ {£4} ~3%

foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo a && b

foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo a || b

*alsm bla.

foo foo foo foo foo foo foo foo foo foo foo foo foo foo Closes issue #33

bla bla ( bla )

alsm bla?


"""

const val MD_BODY_WITH_MISC_PUNCTUATION_FIXED = """[JIRA-1] (subject): bla bla. Bla (bla...) or 'bla'

bla. 1bla `bla`; bla\bla bla/bla bla+bla bla+ bla++ --bla -bla -b.
"bla"? bla@bla bla?! US${'$'} 40.00 | 2¢ {£4} ~3%

foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo a
&& b

foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo a
|| b

*alsm bla.

foo foo foo foo foo foo foo foo foo foo foo foo foo foo Closes issue
#33

bla bla ( bla )

alsm bla?"""
