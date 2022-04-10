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
    logBodySectionMutations()
    findBodyBodyAreaAndWatch()
}

private fun findBodyBodyAreaAndWatch() {
    onBodyAreaAdded {
        watchInputChanges()
        onNodeRemovedFrom(document.body ?: TODO()) {
            findBodyBodyAreaAndWatch()
        }
    }
}

// TODO When is the area element changed? Can't reproduce it
private inline fun Node.onNodeRemovedFrom(ancestor: Node, crossinline block: () -> Unit) {
    MutationObserver { mutations, observer ->
        if (!ancestor.contains(this)) {
            console.log("Node removed", this, mutations)
            observer.disconnect()
            block()
        }
    }.observe(
        ancestor,
        MutationObserverInit(childList = true, subtree = true)
    )
}

private fun NodeList.asIterable() = object : Iterable<Node> {
    override fun iterator() = object : Iterator<Node> {
        private var i = 0
        override fun hasNext() = i < length
        override fun next() = checkNotNull(get(i))
    }
}

private fun logBodySectionMutations() {
    MutationObserver { mutations, _ ->
        console.log(mutations)
    }.observe(gitHubBodySection ?: TODO(), documentBodyObserverConfig)
}

private inline fun onBodyAreaAdded(crossinline block: HTMLTextAreaElement.() -> Unit) {
    document.gitHubBodyArea?.block()?.let {
        return
    }
    MutationObserver { _, observer ->
        document.gitHubBodyArea?.run {
            block()
            observer.disconnect()
        }
    }.observe(gitHubBodySection ?: TODO(), documentBodyObserverConfig)
}

private fun HTMLTextAreaElement.watchInputChanges() {
    addEventListener("input", {
        console.log("PR body changed")
        maybeReplaceSubmitWithFormat()
    })
}

private val documentBodyObserverConfig = MutationObserverInit(
    childList = true,
    subtree = true,
)

private fun maybeReplaceSubmitWithFormat(): Boolean {
    console.log("Will try to replace button")
    val button = gitHubBodySection
        ?.querySelector(".comment-form-actions")
        ?.querySelector("[type='submit']")
        as? HTMLButtonElement
    if (button == null) {
        console.log("Button not found")
        return false
    }
    button.apply {
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
}

private fun format() {
    findCommitMessageBodyTextArea()?.let {
        if (it.value.isNotBlank()) {
            replaceBody(it)
        }
    } ?: alertFailedToFindBodyArea()
}

private fun findCommitMessageBodyTextArea(): HTMLTextAreaElement? {
    val element = document.run { gitHubBodyArea ?: gitLabBodyArea }
    return element as? HTMLTextAreaElement
}

private val Document.gitLabBodyArea
    get() = querySelector("#merge_request_description")

private val gitHubBodySection
    get() = document.querySelector("[data-url*='/partials/body']")

private val Document.gitHubBodyArea
    get() = querySelector("[name='pull_request[body]']") as? HTMLTextAreaElement

private fun alertFailedToFindBodyArea() {
    window.alert("""
        Couldn't find an PR/MR description area in this page :(
        
        Please report this to $SUPPORT_ADDRESS with:
          • the current page URL (please remove any sensitive data)
          • a screenshot of the page's Console (only the log entry starting with [50-72])
    """.trimIndent())
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
