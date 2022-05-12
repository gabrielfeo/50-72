import kotlinx.browser.document
import kotlinx.browser.window

fun main() {
    main(
        window,
        document,
        checkNotNull(document.body),
        console,
        ::formatBody,
    )
}
