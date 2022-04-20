/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

const val SINGLE_LINE_40 = "0123456789012345678901234567890123456789"
const val SINGLE_LINE_50 = "01234567890123456789012345678901234567890123456789"
const val SINGLE_LINE_51 = "012345678901234567890123456789012345678901234567890"


const val BODY_71 = """01234567890123456789012345678901234567890123456789012345678901234567890"""

const val BODY_72 = """012345678901234567890123456789012345678901234567890123456789012345678901
012345678901234567890123456789012345678901234567890123456789012345678901"""

const val BODY_73 = """012345678901234567890123456789012345678901234567890123456789012345678901
lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum"""

const val BODY_73_FIXED = """012345678901234567890123456789012345678901234567890123456789012345678901
lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
ipsum ipsum"""

const val BODY_73_TWO_PARAGRAPHS = """012345678901234567890123456789012345678901234567890123456789012345678901
lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem

lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
ipsum ipsum"""

const val BODY_73_TWO_PARAGRAPHS_FIXED = """012345678901234567890123456789012345678901234567890123456789012345678901
lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem

lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
ipsum ipsum ipsum ipsum"""

const val BODY_73_TWO_PARAGRAPHS_DOUBLE_NEWLINE = """012345678901234567890123456789012345678901234567890123456789012345678901
lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem


lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
ipsum ipsum"""

const val BODY_73_TWO_PARAGRAPHS_DOUBLE_NEWLINE_FIXED = """012345678901234567890123456789012345678901234567890123456789012345678901
lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem

lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
ipsum ipsum ipsum ipsum"""

const val MESSAGE_73_WITH_COMMENT_CHAR_HASH = """Lorem ipsum

lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem
#Useless comment

lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
# Another one
lorem lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
ipsum ipsum ipsum ipsum
# Please enter the commit message for your changes. Lines starting
# with '#' will be ignored, and an empty message aborts the commit."""

const val MESSAGE_73_WITH_COMMENT_CHAR_HASH_FIXED = """Lorem ipsum

lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem

lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
ipsum ipsum ipsum ipsum"""

const val MESSAGE_73_WITH_COMMENT_CHAR_SEMICOLON = """Lorem ipsum

lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem
;Useless comment

lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
; Another one
lorem lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
ipsum ipsum ipsum ipsum
; Please enter the commit message for your changes. Lines starting
; with '#' will be ignored, and an empty message aborts the commit."""

const val MESSAGE_73_WITH_COMMENT_CHAR_SEMICOLON_FIXED = """Lorem ipsum

lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem

lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
ipsum ipsum ipsum ipsum"""


const val SUBJECT_51_BODY_72 = """$SINGLE_LINE_51

$BODY_72"""

const val SUBJECT_50_BODY_72 = """$SINGLE_LINE_50

$BODY_72"""

const val SUBJECT_50_BODY_71 = """$SINGLE_LINE_50

$BODY_71"""

const val SUBJECT_40_BODY_72 = """$SINGLE_LINE_40

$BODY_72"""

const val SUBJECT_50_BODY_73 = """$SINGLE_LINE_50

$BODY_73"""

const val SUBJECT_50_BODY_73_FIXED = """$SINGLE_LINE_50

$BODY_73_FIXED"""

const val SUBJECT_50_BODY_73_TWO_PARAGRAPHS = """$SINGLE_LINE_50

$BODY_73_TWO_PARAGRAPHS"""

const val SUBJECT_50_BODY_73_TWO_PARAGRAPHS_FIXED = """$SINGLE_LINE_50

$BODY_73_TWO_PARAGRAPHS_FIXED"""

const val SUBJECT_50_BODY_73_TWO_PARAGRAPHS_TRIPLE_NEWLINE = """$SINGLE_LINE_50

$BODY_73_TWO_PARAGRAPHS_DOUBLE_NEWLINE"""

const val SUBJECT_50_BODY_73_TWO_PARAGRAPHS_TRIPLE_NEWLINE_FIXED = """$SINGLE_LINE_50

$BODY_73_TWO_PARAGRAPHS_DOUBLE_NEWLINE_FIXED"""

const val SQUASH_MESSAGE_SUBJECT_50_BODY_72 = """# This is a combination of 2 commits.
# This is the 1st commit message:

a

# This is the commit message #2:

b

# Please enter the commit message for your changes. Lines starting
# with '#' will be ignored, and an empty message aborts the commit.
#
# Date:      Fri Apr 1 11:21:10 2022 +0100
#
# interactive rebase in progress; onto 11e86f9
# Last commands done (2 commands done):
#    pick 019360b a
#    squash c442edd b
# No commands remaining.
# You are currently rebasing branch 'fix/error-on-squash-messages' on '11e86f9'.
#
# Changes to be committed:
#	new file:   a
#	new file:   b
#
"""

const val SQUASH_MESSAGE_SUBJECT_50_BODY_72_STRIPPED = """a

b"""

val miscMessages = mapOf(
    //0------------------------------------------------------------------
    """
    $SINGLE_LINE_50
    
    012345678901234567890123456789012345678901234567890123456789012345678901 foo
    foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo 
    ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum 
    """.trimIndent() to """
        $SINGLE_LINE_50
        
        012345678901234567890123456789012345678901234567890123456789012345678901
        foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
        foo foo foo foo ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
        ipsum ipsum ipsum ipsum ipsum ipsum
        """.trimIndent(),
    //1------------------------------------------------------------------
    """
    $SINGLE_LINE_50
    
         foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
    foo foo foo foo bar foo foo foo foo    foo foo foo foo bar foo foo foo foo foo foo foo 
    ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum 
    """.trimIndent() to """
        $SINGLE_LINE_50
        
        foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
        foo foo foo foo foo foo foo foo bar foo foo foo foo foo foo foo foo bar
        foo foo foo foo foo foo foo ipsum ipsum ipsum ipsum ipsum ipsum ipsum
        ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
        """.trimIndent(),
    //2------------------------------------------------------------------
    """
    $SINGLE_LINE_50
    
    
    foo 
    """.trimIndent() to """
        $SINGLE_LINE_50
        
        foo
        """.trimIndent(),
    //3------------------------------------------------------------------
    """
    $SINGLE_LINE_50
    
    foo
    
    """.trimIndent() to """
        $SINGLE_LINE_50
        
        foo
        """.trimIndent(),
    //4------------------------------------------------------------------
    """
    # This is a combination of 2 commits.
    # This is the 1st commit message:
    
    a
    
    # This is the commit message #2:
    
    $SINGLE_LINE_51
    
    # Please enter the commit message for your changes. Lines starting
    # with '#' will be ignored, and an empty message aborts the commit.
    #
    # Date:      Fri Apr 1 11:21:10 2022 +0100
    #
    # interactive rebase in progress; onto 11e86f9
    # Last commands done (2 commands done):
    #    pick 019360b a
    #    squash c442edd b
    # No commands remaining.
    # You are currently rebasing branch 'fix/error-on-squash-messages' on '11e86f9'.
    #
    # Changes to be committed:
    #	new file:   a
    #	new file:   b
    #
    """.trimIndent() to """
        a
        
        $SINGLE_LINE_51
        """.trimIndent(),
    //5------------------------------------------------------------------
    """
    |# This is a combination of 2 commits.
    |# This is the 1st commit message:
    |
    |a
    |
    |$SINGLE_LINE_51
    |
    |# This is the commit message #2:
    |
    |${BODY_73.lines().joinToString("\n|")}
    |
    |# Please enter the commit message for your changes. Lines starting
    |# with '#' will be ignored, and an empty message aborts the commit.
    |#
    |# Date:      Fri Apr 1 11:21:10 2022 +0100
    |#
    |# interactive rebase in progress; onto 11e86f9
    |# Last commands done (2 commands done):
    |#    pick 019360b a
    |#    squash c442edd b
    |# No commands remaining.
    |# You are currently rebasing branch 'fix/error-on-squash-messages' on '11e86f9'.
    |#
    |# Changes to be committed:
    |#	new file:   a
    |#	new file:   b
    |#
    """.trimMargin() to """
        |a
        |
        |$SINGLE_LINE_51
        |
        |${BODY_73_FIXED.lines().joinToString("\n|")}
        """.trimMargin(),
    //6------------------------------------------------------------------
    """
    $SINGLE_LINE_50
    
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long long
    """.trimIndent() to """
        $SINGLE_LINE_50
    
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long
        """.trimIndent(),
    //7------------------------------------------------------------------
    """
    $SINGLE_LINE_50
    
    long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long
    long long long long long long long long long long long long long long long
    """.trimIndent() to """
        $SINGLE_LINE_50
    
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long long long long long long long long long long long long long long
        long
        """.trimIndent(),
)

val miscInvalidMessages = mapOf(
    //1------------------------------------------------------------------
    """
    # This is a combination of 2 commits.
    # This is the 1st commit message:
    
    $SINGLE_LINE_51
    
    # This is the commit message #2:
    
    b
    
    # Please enter the commit message for your changes. Lines starting
    # with '#' will be ignored, and an empty message aborts the commit.
    #
    # Date:      Fri Apr 1 11:21:10 2022 +0100
    #
    # interactive rebase in progress; onto 11e86f9
    # Last commands done (2 commands done):
    #    pick 019360b a
    #    squash c442edd b
    # No commands remaining.
    # You are currently rebasing branch 'fix/error-on-squash-messages' on '11e86f9'.
    #
    # Changes to be committed:
    #	new file:   a
    #	new file:   b
    #
    """.trimIndent() to
        HEADING_OVER_50_MESSAGE
)
