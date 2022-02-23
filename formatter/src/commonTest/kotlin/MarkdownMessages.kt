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

const val MD_BODY_OVER_72_WITH_MORE_MD_FEATURES = """# H1

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

foo foo

## H2

foo foo
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

**Paragraph starting** with bold. Here's _italics_ and ~strikethrough~.

<img src="foo" />

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

![Screenshot](/uploads/foo.png)

| Before | After |
|--------|-------|
| Foo    | Bar   |"""

const val MD_BODY_OVER_72_WITH_MORE_MD_FEATURES_FIXED = """# H1

01234567890123456789012345678901234567890123456789012345678901234567890
foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
foo

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

| Before | After |
|--------|-------|
| Foo    | Bar   |"""
