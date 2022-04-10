/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.*

const val SUPPORT_ADDRESS = "gabriel@gabrielfeo.com"
private const val FORMAT_TO_50_72 = "Format to 50/72"

typealias MutationObserverCallback = (Array<MutationRecord>, MutationObserver) -> Unit

val gitHubBodySection = document.querySelector("[data-url*='/partials/body']")

fun main() {
    observe(gitHubBodySection ?: TODO(), documentBodyObserverConfig) { mutations, observer ->
        console.log(mutations)
        console.log("Document body observer called")
        for (mutation in mutations) {
            val textArea = mutation.target as? HTMLTextAreaElement ?: continue
            if (textArea.name == "pull_request[body]") {
                observer.disconnect()
                observePrBodyChanges(textArea)
                break
            }
        }
    }
}

private fun observePrBodyChanges(prBody: HTMLElement) {
    val submitButton = prBody.findParentForm()
        ?.querySelector("[type='submit']")
        as? HTMLButtonElement
    if (submitButton == null) {
        console.log("Button not found")
        return
    }
    prBody.addEventListener("input", {
        console.log("PR body changed")
        submitButton.maybeReplaceWithFormat()
    })
}

private fun HTMLElement.findParentForm(): HTMLFormElement? {
    return parentElement?.let {
        it as? HTMLFormElement ?: findParentForm()
    }
}

private fun observe(node: Node, config: MutationObserverInit, callback: MutationObserverCallback) {
    MutationObserver(callback).observe(node, config)
}

private val documentBodyObserverConfig = MutationObserverInit(
    childList = true,
    subtree = true,
    attributes = true,
)

private fun HTMLButtonElement.maybeReplaceWithFormat(): Boolean {
    console.log("Will try to replace button")
    if (innerText == FORMAT_TO_50_72) {
        console.log("Button replaced already")
        return false
    }
    val originalText = innerHTML.trim()
    val originalOnclick = onclick
    innerHTML = FORMAT_TO_50_72
    onclick = {
        format()
        innerHTML = originalText
        onclick = originalOnclick
        false
    }
    return true
}

private fun format() {
    val bodyArea = findCommitMessageBodyTextArea()
    val description = bodyArea?.value
    if (description == null || description.isBlank() || !replaceBody(bodyArea)) {
        alertFailedToFindBodyArea()
        logFailedToFindBodyArea(bodyArea)
    }
}

private fun findCommitMessageBodyTextArea(): HTMLTextAreaElement? {
    val element = document.run { gitHubBodyArea ?: gitLabBodyArea }
    return element as? HTMLTextAreaElement
}

private val Document.gitLabBodyArea
    get() = querySelector("#merge_request_description")

private val Document.gitHubBodyArea
    get() = querySelector("#pull_request_body")
        ?: querySelector("[name='pull_request[body]']")

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

private fun replaceBody(bodyArea: HTMLTextAreaElement): Boolean {
    val previouslyFocused = document.activeElement as? HTMLElement
    try {
        val formattedBody = formatBody(bodyArea.value, isMarkdown = true)
        bodyArea.run { focus(); select() }
        return document.execCommand("insertText", showUI = false, value = formattedBody)
    } finally {
        previouslyFocused?.focus()
    }
}
