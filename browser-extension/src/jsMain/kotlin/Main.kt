/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.dom.appendElement
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLTextAreaElement

const val SUPPORT_ADDRESS = "gabriel@gabrielfeo.com"

fun main(
    root: Element = checkNotNull(document.body),
    format: (String, Char, Boolean) -> String = ::formatBody,
) {
    root.appendElement("button") {
        innerHTML = "Format"
        addEventListener("click", { onFormatButtonClicked(root, format) })
        setAttribute(
            "style",
            "position: absolute; bottom: 0; right: 0; z-index: 999999; font-size: large;",
        )
    }
}

fun onFormatButtonClicked(root: Element, format: (String, Char, Boolean) -> String) {
    val bodyArea = RealMessageBodyFinder.find(root)
    val description = bodyArea?.value
    if (description == null || description.isBlank() || !replaceBody(bodyArea, format)) {
        alertFailedToFindBodyArea()
        logFailedToFindBodyArea(bodyArea)
    }
}

private fun alertFailedToFindBodyArea() {
    window.alert("""
        Couldn't find an PR/MR description area in this page :(
        
        Please report this to $SUPPORT_ADDRESS with:
          • the current page URL (please remove any sensitive data)
          • a screenshot of the page's Console (only the log entry starting with [50-72])
    """.trimIndent())
}

private fun logFailedToFindBodyArea(bodyArea: HTMLTextAreaElement?) {
    console.log(
        """
            [50-72] Failed to format description.
              bodyArea=$bodyArea
                bodyArea.textContent=${bodyArea?.textContent} 
                bodyArea.value=${bodyArea?.value} 
        """.trimIndent()
    )
}

private fun replaceBody(bodyArea: HTMLTextAreaElement, format: (String, Char, Boolean) -> String): Boolean {
    val previouslyFocused = document.activeElement as? HTMLElement
    try {
        val formattedBody = format(bodyArea.value, '#', true)
        bodyArea.run { focus(); select() }
        return document.execCommand("insertText", showUI = false, value = formattedBody)
    } finally {
        previouslyFocused?.focus()
    }
}
