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

fun main() {
    addFormatButton()
}

private fun addFormatButton() {
    if (!tryAdd()) {
        MutationObserver { _, observer ->
            if (tryAdd()) {
                observer.disconnect()
            }
        }.startObserving()
    }
}

private fun MutationObserver.startObserving() {
    observe(
        // TODO Observe just the PR description node
        target = checkNotNull(document.body),
        options = MutationObserverInit(
            childList = true,
            subtree = true,
        )
    )
}

private fun tryAdd(): Boolean {
    val button = document.findGitHubSubmitButton()
    if (button == null) {
        console.log("Button not found")
        return false
    }
    if (button.innerText == FORMAT_TO_50_72) {
        return true
    }
    button.apply {
        val originalText = innerText.trim()
        val originalOnclick = onclick
        innerText = FORMAT_TO_50_72
        onclick = {
            format()
            innerText = originalText
            onclick = originalOnclick
            false
        }
    }
    return true
}

private fun Document.findGitHubSubmitButton(): HTMLButtonElement? {
    return querySelector(".comment-form-actions")
        ?.querySelector("[type='submit']")
        as? HTMLButtonElement
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
