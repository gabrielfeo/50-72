import browser.dom.*

/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

const val SUPPORT_ADDRESS = "gabriel@gabrielfeo.com"

fun main(
    window: Window,
    document: Document,
    root: Element,
    console: Console,
    format: (String, Char, Boolean) -> String = ::formatBody,
) {
    root.appendElement("button") {
        innerHTML = "Format"
        addEventListener("click", { onFormatButtonClicked(window, document, console, format) })
        setAttribute(
            "style",
            "position: absolute; bottom: 0; right: 0; z-index: 999999; font-size: large;",
        )
    }
}

fun onFormatButtonClicked(
    window: Window,
    document: Document,
    console: Console,
    format: (String, Char, Boolean) -> String,
) {
    val bodyArea = findCommitMessageBodyTextArea(document)
    val description = bodyArea?.value
    if (description == null || description.isBlank() || !replaceBody(document, bodyArea, format)) {
        window.alertFailedToFindBodyArea()
        console.logFailedToFindBodyArea(bodyArea)
    }
}

private fun findCommitMessageBodyTextArea(document: Document): HTMLTextAreaElement? {
    val element = document.run { gitHubBodyArea ?: gitLabBodyArea }
    return element as? HTMLTextAreaElement
}

private val Document.gitLabBodyArea
    get() = querySelector("#merge_request_description")

private val Document.gitHubBodyArea
    get() = querySelector("#pull_request_body")
        ?: querySelector("[name='pull_request[body]']")

private fun Window.alertFailedToFindBodyArea() {
    alert("""
        Couldn't find an PR/MR description area in this page :(
        
        Please report this to $SUPPORT_ADDRESS with:
          • the current page URL (please remove any sensitive data)
          • a screenshot of the page's Console (only the log entry starting with [50-72])
    """.trimIndent())
}

private fun Console.logFailedToFindBodyArea(bodyArea: HTMLTextAreaElement?) {
    log(
        """
            [50-72] Failed to format description.
              bodyArea=$bodyArea
        """.trimIndent()
    )
}

private fun replaceBody(document: Document, bodyArea: HTMLTextAreaElement, format: (String, Char, Boolean) -> String): Boolean {
    val previouslyFocused = document.activeElement as HTMLElement?
    try {
        val formattedBody = format(bodyArea.value, '#', true)
        bodyArea.run { focus(); select() }
        return document.execCommand("insertText", showUI = false, value = formattedBody)
    } finally {
        previouslyFocused?.focus()
    }
}
