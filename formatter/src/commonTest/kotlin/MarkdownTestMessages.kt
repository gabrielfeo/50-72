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
