const val SINGLE_LINE_40 = "0123456789012345678901234567890123456789"
const val SINGLE_LINE_50 = "01234567890123456789012345678901234567890123456789"
const val SINGLE_LINE_51 = "012345678901234567890123456789012345678901234567890"

const val SUBJECT_51_BODY_72 = """$SINGLE_LINE_51

012345678901234567890123456789012345678901234567890123456789012345678901
012345678901234567890123456789012345678901234567890123456789012345678901"""

const val SUBJECT_50_BODY_72 = """$SINGLE_LINE_50

012345678901234567890123456789012345678901234567890123456789012345678901
012345678901234567890123456789012345678901234567890123456789012345678901"""

const val SUBJECT_50_BODY_71 = """$SINGLE_LINE_50

01234567890123456789012345678901234567890123456789012345678901234567890"""

const val SUBJECT_40_BODY_72 = """$SINGLE_LINE_40

012345678901234567890123456789012345678901234567890123456789012345678901
012345678901234567890123456789012345678901234567890123456789012345678901"""

const val SUBJECT_50_BODY_73 = """$SINGLE_LINE_50

012345678901234567890123456789012345678901234567890123456789012345678901
lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum"""

const val SUBJECT_50_BODY_73_FIXED = """$SINGLE_LINE_50

012345678901234567890123456789012345678901234567890123456789012345678901
lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
ipsum ipsum"""

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
