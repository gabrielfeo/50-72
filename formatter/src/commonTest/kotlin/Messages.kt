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

const val MESSAGE_73_WITH_COMMENTS = """Lorem ipsum

lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem
# Useless comment

lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
# Another one
lorem lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
ipsum ipsum ipsum ipsum
# Please enter the commit message for your changes. Lines starting
# with '#' will be ignored, and an empty message aborts the commit."""

const val MESSAGE_73_WITH_COMMENTS_FIXED = """Lorem ipsum

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

const val SUBJECT_50_BODY_73_TWO_PARAGRAPHS_DOUBLE_NEWLINE = """$SINGLE_LINE_50

$BODY_73_TWO_PARAGRAPHS_DOUBLE_NEWLINE"""

const val SUBJECT_50_BODY_73_TWO_PARAGRAPHS_DOUBLE_NEWLINE_FIXED = """$SINGLE_LINE_50

$BODY_73_TWO_PARAGRAPHS_DOUBLE_NEWLINE_FIXED"""


val miscMessages = mapOf(
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
    //------------------------------------------------------------------
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
    //------------------------------------------------------------------
    """
    $SINGLE_LINE_50
    
    
    foo 
    """.trimIndent() to """
        $SINGLE_LINE_50
        
        foo
        """.trimIndent(),
    //------------------------------------------------------------------
    """
    $SINGLE_LINE_50
    
    foo
    
    """.trimIndent() to """
        $SINGLE_LINE_50
        
        foo
        """.trimIndent(),
    //------------------------------------------------------------------
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
    //------------------------------------------------------------------
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
