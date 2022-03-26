import kotlin.test.Test
import kotlin.test.assertEquals

class FormatMarkdownBodyTest {

    @Test
    fun reformatsMarkdownBodyGivenParagraphLineOver72() {
        val reformatted = formatBody(MD_BODY_OVER_72, isMarkdown = true)
        assertEquals(MD_BODY_OVER_72_FIXED, reformatted)
    }

    @Test
    fun returnsSameMessageGivenAllParagraphLinesAt72() {
        val reformatted = formatBody(MD_BODY_72, isMarkdown = true)
        assertEquals(MD_BODY_72, reformatted)
    }

    @Test
    fun returnsSameMessageGivenAllParagraphLinesUpTo72() {
        val reformatted = formatBody(MD_BODY_71, isMarkdown = true)
        assertEquals(MD_BODY_71, reformatted)
    }

    @Test
    fun supportsMiscMarkdownFeatures() {
        val reformatted = formatBody(MD_BODY_OVER_72_WITH_MORE_MD_FEATURES, isMarkdown = true)
        assertEquals(MD_BODY_OVER_72_WITH_MORE_MD_FEATURES_FIXED, reformatted)
    }
}