package fixture

@Suppress("TestFunctionName")
fun GitHubEditingPrBody(bodyText: String) = """
    <div class="js-previewable-comment-form previewable-comment-form write-selected" data-preview-url="/preview?markdown_unsupported=false&amp;pull_request=1218112325&amp;repository=460434583&amp;subject_type=PullRequest">
      <input type="hidden" value="CVP0MJ2sv3nyXQqNniKf5uvrenQN680E2mJDzpTo7qJ8r3rocFtNgjLywptnx_HWCwtlFff3DLBP7zN1AQZ2nQ" data-csrf="true" class="js-data-preview-url-csrf">
      
    <div class="comment-form-head tabnav d-flex flex-justify-between mb-2 ">
      <nav class="tabnav-tabs " role="tablist" aria-label="Preview">
        <button type="button" class="btn-link tabnav-tab write-tab js-write-tab selected" role="tab" aria-selected="true">Write</button>
        <button type="button" class="btn-link tabnav-tab preview-tab js-preview-tab " role="tab">Preview</button>
      </nav>
    
        
        <markdown-toolbar role="toolbar" aria-label="Composition" for="issue-1218112325-body" data-view-component="true" class="js-details-container Details toolbar-commenting no-wrap d-flex px-2 flex-items-start flex-wrap" tabindex="0">
    
      
      <div class="flex-nowrap d-inline-block mr-3">
        <md-header aria-label="Heading" id="md-heading-issue-1218112325-body-1" role="button" tabindex="-1" class="toolbar-item btn-octicon mx-1" data-ga-click="Markdown Toolbar, click, header" aria-describedby="tooltip-1652316716655-3323">
          <svg aria-hidden="true" height="16" viewBox="0 0 16 16" version="1.1" width="16" data-view-component="true" class="octicon octicon-heading">
        <path fill-rule="evenodd" d="M3.75 2a.75.75 0 01.75.75V7h7V2.75a.75.75 0 011.5 0v10.5a.75.75 0 01-1.5 0V8.5h-7v4.75a.75.75 0 01-1.5 0V2.75A.75.75 0 013.75 2z"></path>
    </svg>
        </md-header>
        <tool-tip for="md-heading-issue-1218112325-body-1" data-direction="sw" data-type="description" data-view-component="true" class="js-modifier-label-key" id="tooltip-1652316716655-3323" role="tooltip" hidden="">Add heading text</tool-tip>
    
        <md-bold aria-label="Bold" id="md-bold-issue-1218112325-body-1" role="button" tabindex="-1" class="toolbar-item btn-octicon mx-1" data-hotkey-scope="issue-1218112325-body" data-hotkey="Meta+b" data-ga-click="Markdown Toolbar, click, bold" aria-describedby="tooltip-1652316716655-6174">
          <svg aria-hidden="true" height="16" viewBox="0 0 16 16" version="1.1" width="16" data-view-component="true" class="octicon octicon-bold">
        <path fill-rule="evenodd" d="M4 2a1 1 0 00-1 1v10a1 1 0 001 1h5.5a3.5 3.5 0 001.852-6.47A3.5 3.5 0 008.5 2H4zm4.5 5a1.5 1.5 0 100-3H5v3h3.5zM5 9v3h4.5a1.5 1.5 0 000-3H5z"></path>
    </svg>
        </md-bold>
        <tool-tip for="md-bold-issue-1218112325-body-1" data-direction="sw" data-type="description" data-view-component="true" class="js-modifier-label-key" id="tooltip-1652316716655-6174" role="tooltip" hidden="">Add bold text, &lt;Cmd+b&gt;</tool-tip>
    
        <md-italic aria-label="Italic" id="md-italic-issue-1218112325-body-1" role="button" tabindex="-1" class="toolbar-item btn-octicon mx-1" data-hotkey-scope="issue-1218112325-body" data-hotkey="Meta+i" data-ga-click="Markdown Toolbar, click, italic" aria-describedby="tooltip-1652316716655-2962">
          <svg aria-hidden="true" height="16" viewBox="0 0 16 16" version="1.1" width="16" data-view-component="true" class="octicon octicon-italic">
        <path fill-rule="evenodd" d="M6 2.75A.75.75 0 016.75 2h6.5a.75.75 0 010 1.5h-2.505l-3.858 9H9.25a.75.75 0 010 1.5h-6.5a.75.75 0 010-1.5h2.505l3.858-9H6.75A.75.75 0 016 2.75z"></path>
    </svg>
        </md-italic>
        <tool-tip for="md-italic-issue-1218112325-body-1" data-direction="sw" data-type="description" data-view-component="true" class="js-modifier-label-key" id="tooltip-1652316716655-2962" role="tooltip" hidden="">Add italic text, &lt;Cmd+i&gt;</tool-tip>
      </div>
    
      <div class="d-inline-block mr-3">
        <md-quote aria-label="Quote" id="md-quote-issue-1218112325-body-1" role="button" tabindex="-1" class="toolbar-item btn-octicon p-1 mx-1" data-hotkey-scope="issue-1218112325-body" data-hotkey="Meta+Shift+." data-ga-click="Markdown Toolbar, click, quote" aria-describedby="tooltip-1652316716655-1948">
          <svg aria-hidden="true" height="16" viewBox="0 0 16 16" version="1.1" width="16" data-view-component="true" class="octicon octicon-quote">
        <path fill-rule="evenodd" d="M1.75 2.5a.75.75 0 000 1.5h10.5a.75.75 0 000-1.5H1.75zm4 5a.75.75 0 000 1.5h8.5a.75.75 0 000-1.5h-8.5zm0 5a.75.75 0 000 1.5h8.5a.75.75 0 000-1.5h-8.5zM2.5 7.75a.75.75 0 00-1.5 0v6a.75.75 0 001.5 0v-6z"></path>
    </svg>
        </md-quote>
        <tool-tip for="md-quote-issue-1218112325-body-1" data-direction="sw" data-type="description" data-view-component="true" class="js-modifier-label-key" id="tooltip-1652316716655-1948" role="tooltip" hidden="">Add a quote, &lt;Cmd+Shift+.&gt;</tool-tip>
    
        <md-code aria-label="Code" id="md-code-issue-1218112325-body-1" role="button" tabindex="-1" class="toolbar-item btn-octicon p-1 mx-1" data-hotkey-scope="issue-1218112325-body" data-hotkey="Meta+e" data-ga-click="Markdown Toolbar, click, code" aria-describedby="tooltip-1652316716655-6771">
          <svg aria-hidden="true" height="16" viewBox="0 0 16 16" version="1.1" width="16" data-view-component="true" class="octicon octicon-code">
        <path fill-rule="evenodd" d="M4.72 3.22a.75.75 0 011.06 1.06L2.06 8l3.72 3.72a.75.75 0 11-1.06 1.06L.47 8.53a.75.75 0 010-1.06l4.25-4.25zm6.56 0a.75.75 0 10-1.06 1.06L13.94 8l-3.72 3.72a.75.75 0 101.06 1.06l4.25-4.25a.75.75 0 000-1.06l-4.25-4.25z"></path>
    </svg>
        </md-code>
        <tool-tip for="md-code-issue-1218112325-body-1" data-direction="sw" data-type="description" data-view-component="true" class="js-modifier-label-key" id="tooltip-1652316716655-6771" role="tooltip" hidden="">Add code, &lt;Cmd+e&gt;</tool-tip>
    
    
    
        <md-link aria-label="Link" id="md-link-issue-1218112325-body-2" role="button" tabindex="-1" class="toolbar-item btn-octicon p-1 d-inline-block mx-1" data-hotkey-scope="issue-1218112325-body" data-hotkey="Meta+k" data-ga-click="Markdown Toolbar, click, link" aria-describedby="tooltip-1652316716656-4430">
          <svg aria-hidden="true" height="16" viewBox="0 0 16 16" version="1.1" width="16" data-view-component="true" class="octicon octicon-link">
        <path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path>
    </svg>
        </md-link>
        <tool-tip for="md-link-issue-1218112325-body-2" data-direction="sw" data-type="description" data-view-component="true" class="js-modifier-label-key" id="tooltip-1652316716656-4430" role="tooltip" hidden="">Add a link, &lt;Cmd+k&gt;</tool-tip>
      </div>
    
      <div class="d-inline-block mr-3">
        <md-unordered-list aria-label="Unordered list" id="md-unordered_list-issue-1218112325-body-1" role="button" tabindex="-1" class="toolbar-item btn-octicon mx-1" data-hotkey-scope="issue-1218112325-body" data-hotkey="Meta+Shift+8" data-ga-click="Markdown Toolbar, click, unordered list" aria-describedby="tooltip-1652316716656-7648">
          <svg aria-hidden="true" height="16" viewBox="0 0 16 16" version="1.1" width="16" data-view-component="true" class="octicon octicon-list-unordered">
        <path fill-rule="evenodd" d="M2 4a1 1 0 100-2 1 1 0 000 2zm3.75-1.5a.75.75 0 000 1.5h8.5a.75.75 0 000-1.5h-8.5zm0 5a.75.75 0 000 1.5h8.5a.75.75 0 000-1.5h-8.5zm0 5a.75.75 0 000 1.5h8.5a.75.75 0 000-1.5h-8.5zM3 8a1 1 0 11-2 0 1 1 0 012 0zm-1 6a1 1 0 100-2 1 1 0 000 2z"></path>
    </svg>
        </md-unordered-list>
        <tool-tip for="md-unordered_list-issue-1218112325-body-1" data-direction="sw" data-type="description" data-view-component="true" class="js-modifier-label-key" id="tooltip-1652316716656-7648" role="tooltip" hidden="">Add a bulleted list, &lt;Cmd+Shift+8&gt;</tool-tip>
    
        <md-ordered-list aria-label="Numbered list" id="md-ordered_list-issue-1218112325-body-1" role="button" tabindex="-1" class="toolbar-item btn-octicon mx-1" data-hotkey-scope="issue-1218112325-body" data-hotkey="Meta+Shift+7" data-ga-click="Markdown Toolbar, click, ordered list" aria-describedby="tooltip-1652316716656-2451">
          <svg aria-hidden="true" height="16" viewBox="0 0 16 16" version="1.1" width="16" data-view-component="true" class="octicon octicon-list-ordered">
        <path fill-rule="evenodd" d="M2.003 2.5a.5.5 0 00-.723-.447l-1.003.5a.5.5 0 00.446.895l.28-.14V6H.5a.5.5 0 000 1h2.006a.5.5 0 100-1h-.503V2.5zM5 3.25a.75.75 0 01.75-.75h8.5a.75.75 0 010 1.5h-8.5A.75.75 0 015 3.25zm0 5a.75.75 0 01.75-.75h8.5a.75.75 0 010 1.5h-8.5A.75.75 0 015 8.25zm0 5a.75.75 0 01.75-.75h8.5a.75.75 0 010 1.5h-8.5a.75.75 0 01-.75-.75zM.924 10.32l.003-.004a.851.851 0 01.144-.153A.66.66 0 011.5 10c.195 0 .306.068.374.146a.57.57 0 01.128.376c0 .453-.269.682-.8 1.078l-.035.025C.692 11.98 0 12.495 0 13.5a.5.5 0 00.5.5h2.003a.5.5 0 000-1H1.146c.132-.197.351-.372.654-.597l.047-.035c.47-.35 1.156-.858 1.156-1.845 0-.365-.118-.744-.377-1.038-.268-.303-.658-.484-1.126-.484-.48 0-.84.202-1.068.392a1.858 1.858 0 00-.348.384l-.007.011-.002.004-.001.002-.001.001a.5.5 0 00.851.525zM.5 10.055l-.427-.26.427.26z"></path>
    </svg>
        </md-ordered-list>
        <tool-tip for="md-ordered_list-issue-1218112325-body-1" data-direction="sw" data-type="description" data-view-component="true" class="js-modifier-label-key" id="tooltip-1652316716656-2451" role="tooltip" hidden="">Add a numbered list, &lt;Cmd+Shift+7&gt;</tool-tip>
    
        <md-task-list aria-label="Task list" id="md-task_list-issue-1218112325-body-1" role="button" tabindex="-1" class="toolbar-item btn-octicon mx-1" data-hotkey-scope="issue-1218112325-body" data-hotkey="Meta+Shift+l" data-ga-click="Markdown Toolbar, click, task list" aria-describedby="tooltip-1652316716656-6461">
          <svg aria-hidden="true" height="16" viewBox="0 0 16 16" version="1.1" width="16" data-view-component="true" class="octicon octicon-tasklist">
        <path fill-rule="evenodd" d="M2.5 2.75a.25.25 0 01.25-.25h10.5a.25.25 0 01.25.25v10.5a.25.25 0 01-.25.25H2.75a.25.25 0 01-.25-.25V2.75zM2.75 1A1.75 1.75 0 001 2.75v10.5c0 .966.784 1.75 1.75 1.75h10.5A1.75 1.75 0 0015 13.25V2.75A1.75 1.75 0 0013.25 1H2.75zm9.03 5.28a.75.75 0 00-1.06-1.06L6.75 9.19 5.28 7.72a.75.75 0 00-1.06 1.06l2 2a.75.75 0 001.06 0l4.5-4.5z"></path>
    </svg>
        </md-task-list>
        <tool-tip for="md-task_list-issue-1218112325-body-1" data-direction="sw" data-type="description" data-view-component="true" class="js-modifier-label-key" id="tooltip-1652316716656-6461" role="tooltip" hidden="">Add a task list, &lt;Cmd+Shift+l&gt;</tool-tip>
      </div>
    
            <div class="d-inline-block">
    
              <md-mention aria-label="Mention" role="button" id="md-mention-issue-1218112325-body" tabindex="-1" class="flex-auto text-center toolbar-item btn-octicon p-1 mx-1" data-ga-click="Markdown Toolbar, click, mention" aria-describedby="tooltip-1652316716656-2084">
                <svg aria-hidden="true" height="16" viewBox="0 0 16 16" version="1.1" width="16" data-view-component="true" class="octicon octicon-mention">
        <path fill-rule="evenodd" d="M4.75 2.37a6.5 6.5 0 006.5 11.26.75.75 0 01.75 1.298 8 8 0 113.994-7.273.754.754 0 01.006.095v1.5a2.75 2.75 0 01-5.072 1.475A4 4 0 1112 8v1.25a1.25 1.25 0 002.5 0V7.867a6.5 6.5 0 00-9.75-5.496V2.37zM10.5 8a2.5 2.5 0 10-5 0 2.5 2.5 0 005 0z"></path>
    </svg>
              </md-mention>
              <tool-tip for="md-mention-issue-1218112325-body" data-direction="sw" data-type="description" data-view-component="true" id="tooltip-1652316716656-2084" role="tooltip" hidden="">Directly mention a user or team</tool-tip>
    
    
              <md-ref role="button" id="md-ref-issue-1218112325-body" aria-label="Issue or pull request reference" tabindex="-1" class="flex-auto text-center toolbar-item btn-octicon p-1 mx-1" data-ga-click="Markdown Toolbar, click, reference" aria-describedby="tooltip-1652316716656-8265">
                <svg aria-hidden="true" height="16" viewBox="0 0 16 16" version="1.1" width="16" data-view-component="true" class="octicon octicon-cross-reference">
        <path fill-rule="evenodd" d="M16 1.25v4.146a.25.25 0 01-.427.177L14.03 4.03l-3.75 3.75a.75.75 0 11-1.06-1.06l3.75-3.75-1.543-1.543A.25.25 0 0111.604 1h4.146a.25.25 0 01.25.25zM2.75 3.5a.25.25 0 00-.25.25v7.5c0 .138.112.25.25.25h2a.75.75 0 01.75.75v2.19l2.72-2.72a.75.75 0 01.53-.22h4.5a.25.25 0 00.25-.25v-2.5a.75.75 0 111.5 0v2.5A1.75 1.75 0 0113.25 13H9.06l-2.573 2.573A1.457 1.457 0 014 14.543V13H2.75A1.75 1.75 0 011 11.25v-7.5C1 2.784 1.784 2 2.75 2h5.5a.75.75 0 010 1.5h-5.5z"></path>
    </svg>
              </md-ref>
              <tool-tip for="md-ref-issue-1218112325-body" data-direction="sw" data-type="description" data-view-component="true" id="tooltip-1652316716656-8265" role="tooltip" hidden="">Reference an issue or pull request</tool-tip>
    
              <details class="details-reset details-overlay flex-auto toolbar-item btn-octicon select-menu select-menu-modal-right js-saved-reply-container " tabindex="-1">
      <summary id="saved-reply-issue-1218112325-body" data-md-button="" tabindex="-1" class="text-center p-1 ml-1" aria-label="Reply" data-ga-click="Markdown Toolbar, click, saved reply" aria-haspopup="menu" role="button" aria-describedby="tooltip-1652316716656-6286">
        <svg aria-hidden="true" height="16" viewBox="0 0 16 16" version="1.1" width="16" data-view-component="true" class="octicon octicon-reply">
        <path fill-rule="evenodd" d="M6.78 1.97a.75.75 0 010 1.06L3.81 6h6.44A4.75 4.75 0 0115 10.75v2.5a.75.75 0 01-1.5 0v-2.5a3.25 3.25 0 00-3.25-3.25H3.81l2.97 2.97a.75.75 0 11-1.06 1.06L1.47 7.28a.75.75 0 010-1.06l4.25-4.25a.75.75 0 011.06 0z"></path>
    </svg>
        <span class="dropdown-caret "></span>
      </summary>
    
      <details-menu style="z-index: 99;" class="select-menu-modal position-absolute right-0 js-saved-reply-menu " data-menu-input="issue-1218112325-body_saved_reply_id" src="/settings/replies?context=none" preload="" role="menu">
        <div class="select-menu-header d-flex">
          <span class="select-menu-title flex-auto">Select a reply</span>
          <code><span class="border rounded p-1 mr-2">ctrl .</span></code>
        </div>
    
        <include-fragment role="menuitem" aria-label="Loading">
          <svg style="box-sizing: content-box; color: var(--color-icon-primary);" width="32" height="32" viewBox="0 0 16 16" fill="none" data-view-component="true" class="my-6 mx-auto d-block anim-rotate">
      <circle cx="8" cy="8" r="7" stroke="currentColor" stroke-opacity="0.25" stroke-width="2" vector-effect="non-scaling-stroke"></circle>
      <path d="M15 8a7.002 7.002 0 00-7-7" stroke="currentColor" stroke-width="2" stroke-linecap="round" vector-effect="non-scaling-stroke"></path>
    </svg>
        </include-fragment>
    
      </details-menu>
    </details>
    <tool-tip for="saved-reply-issue-1218112325-body" data-direction="sw" data-type="description" data-view-component="true" class="js-modifier-label-key" id="tooltip-1652316716656-6286" role="tooltip" hidden="">Add saved reply</tool-tip>
    
    
          </div>
    
    
    </markdown-toolbar>
    </div>
    
    
      <div class="clearfix"></div>
    
      <p class="comment-form-error comment-show-stale">
      <svg aria-hidden="true" height="16" viewBox="0 0 16 16" version="1.1" width="16" data-view-component="true" class="octicon octicon-alert">
        <path fill-rule="evenodd" d="M8.22 1.754a.25.25 0 00-.44 0L1.698 13.132a.25.25 0 00.22.368h12.164a.25.25 0 00.22-.368L8.22 1.754zm-1.763-.707c.659-1.234 2.427-1.234 3.086 0l6.082 11.378A1.75 1.75 0 0114.082 15H1.918a1.75 1.75 0 01-1.543-2.575L6.457 1.047zM9 11a1 1 0 11-2 0 1 1 0 012 0zm-.25-5.25a.75.75 0 00-1.5 0v2.5a.75.75 0 001.5 0v-2.5z"></path>
    </svg> The content you are editing has changed.
      Please copy your edits and refresh the page.
    </p>
    
    
      <file-attachment class="js-upload-markdown-image is-default" input="fc-issue-1218112325-body" data-upload-policy-url="/upload/policies/assets"><input type="hidden" value="bWeP-sYCjLukmQIr364aHkviLN7NXaYT51nglQxecbpB4ITJx9wMTI8avls7HrFwse9XffDJqd5qIt_cDcPGGg" data-csrf="true" class="js-data-upload-policy-url-csrf">
      <div class="write-content js-write-bucket upload-enabled">
        <input type="hidden" name="context" value="">
        <input type="text" name="required_field_6e70" class="form-control" hidden="hidden"><input type="hidden" name="timestamp" value="1652316716687" autocomplete="off" class="form-control"><input type="hidden" name="timestamp_secret" value="ea0da0fd6771177350d1da9d3c61435a457aa2546dfc0e9c77329e7640558dc2" autocomplete="off" class="form-control">
    
        <input type="hidden" name="pull_request[id]" value="1218112325">
        <input type="hidden" name="pull_request[bodyVersion]" class="js-body-version" value="32854fee782ede78b90afb3b0b67da66779c3cd53d7cdf9b2aa89d6ca92ea4f8">
    
        <text-expander keys=": @ #" data-emoji-url="/autocomplete/emoji" data-issue-url="/suggestions?issue_suggester=1&amp;repository=50-72&amp;user_id=gabrielfeo" data-mention-url="/suggestions?mention_suggester=1&amp;repository=50-72&amp;user_id=gabrielfeo" multiword="#">
            
            <textarea name="pull_request[body]" id="issue-1218112325-body" placeholder="Leave a comment" aria-label="Comment body" class="form-control input-contrast comment-form-textarea js-comment-field js-paste-markdown js-task-list-field js-quick-submit js-size-to-fit js-session-resumable js-saved-reply-shortcut-comment-field ">$bodyText</textarea>
    
        </text-expander>
    
          <label class="text-normal drag-and-drop hx_drag-and-drop position-relative d-flex flex-justify-between">
        <input accept=".gif,.jpeg,.jpg,.mov,.mp4,.png,.svg,.csv,.docx,.fodg,.fodp,.fods,.fodt,.gz,.log,.md,.odf,.odg,.odp,.ods,.odt,.pdf,.pptx,.txt,.xls,.xlsx,.zip" type="file" multiple="" class="manual-file-chooser manual-file-chooser-transparent top-0 right-0 bottom-0 left-0 width-full ml-0 form-control rounded-top-0" id="fc-issue-1218112325-body">
        <span class="color-bg-subtle position-absolute top-0 left-0 rounded-bottom-2 width-full height-full" style="pointer-events: none;"></span>
        <span class="position-relative pr-2" style="pointer-events: none;">
          <span class="default">
            Attach files by dragging &amp; dropping, selecting or pasting them.
          </span>
          <span class="loading">
            <svg style="box-sizing: content-box; color: var(--color-icon-primary);" width="16" height="16" viewBox="0 0 16 16" fill="none" data-view-component="true" class="v-align-text-bottom mr-1 anim-rotate">
      <circle cx="8" cy="8" r="7" stroke="currentColor" stroke-opacity="0.25" stroke-width="2" vector-effect="non-scaling-stroke"></circle>
      <path d="M15 8a7.002 7.002 0 00-7-7" stroke="currentColor" stroke-width="2" stroke-linecap="round" vector-effect="non-scaling-stroke"></path>
    </svg>
              <span class="js-file-upload-loading-text" data-file-upload-message="Uploading your files…">
                Uploading your files…
              </span>
          </span>
          <span class="error bad-file">
            We don’t support that file type.
            <span class="drag-and-drop-error-info">
              <span class="btn-link">Try again</span> with a
              GIF, JPEG, JPG, MOV, MP4, PNG, SVG, CSV, DOCX, FODG, FODP, FODS, FODT, GZ, LOG, MD, ODF, ODG, ODP, ODS, ODT, PDF, PPTX, TXT, XLS, XLSX or ZIP.
            </span>
          </span>
          <span class="error bad-permissions">
            Attaching documents requires write permission to this repository.
            <span class="drag-and-drop-error-info">
              <span class="btn-link">Try again</span> with a GIF, JPEG, JPG, MOV, MP4, PNG, SVG, CSV, DOCX, FODG, FODP, FODS, FODT, GZ, LOG, MD, ODF, ODG, ODP, ODS, ODT, PDF, PPTX, TXT, XLS, XLSX or ZIP.
            </span>
          </span>
          <span class="error repository-required">
            We don’t support that file type.
            <span class="drag-and-drop-error-info">
              <span class="btn-link">Try again</span> with a GIF, JPEG, JPG, MOV, MP4, PNG, SVG, CSV, DOCX, FODG, FODP, FODS, FODT, GZ, LOG, MD, ODF, ODG, ODP, ODS, ODT, PDF, PPTX, TXT, XLS, XLSX or ZIP.
            </span>
          </span>
          <span class="error too-big js-upload-too-big">
          </span>
          <span class="error empty">
            This file is empty.
            <span class="drag-and-drop-error-info">
              <span class="btn-link">Try again</span> with a file that’s not empty.
            </span>
          </span>
          <span class="error hidden-file">
            This file is hidden.
            <span class="drag-and-drop-error-info">
              <span class="btn-link">Try again</span> with another file.
            </span>
          </span>
          <span class="error failed-request">
            Something went really wrong, and we can’t process that file.
            <span class="drag-and-drop-error-info">
              <span class="btn-link">Try again.</span>
            </span>
          </span>
        </span>
        <a class="Link--muted position-relative d-inline" href="https://docs.github.com/github/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax" target="_blank" data-ga-click="Markdown Toolbar, click, help" id="dd_fc-issue-1218112325-body_md_link" aria-labelledby="tooltip-1652316716656-4460">
          <svg aria-hidden="true" height="16" viewBox="0 0 16 16" version="1.1" width="16" data-view-component="true" class="octicon octicon-markdown v-align-bottom">
        <path fill-rule="evenodd" d="M14.85 3H1.15C.52 3 0 3.52 0 4.15v7.69C0 12.48.52 13 1.15 13h13.69c.64 0 1.15-.52 1.15-1.15v-7.7C16 3.52 15.48 3 14.85 3zM9 11H7V8L5.5 9.92 4 8v3H2V5h2l1.5 2L7 5h2v6zm2.99.5L9.5 8H11V5h2v3h1.5l-2.51 3.5z"></path>
    </svg>
        </a>
        <tool-tip for="dd_fc-issue-1218112325-body_md_link" data-direction="nw" data-type="label" data-view-component="true" id="tooltip-1652316716656-4460" role="tooltip" hidden="">Styling with Markdown is supported</tool-tip>
      </label>
    
      </div>
    </file-attachment>
    
      <div class="preview-content">
        <div class="comment js-suggested-changes-container" data-thread-side="">
      <div class="comment-body markdown-body js-preview-body">
        <p>Nothing to preview</p>
      </div>
    </div>
    
      </div>
    
      <div class="clearfix">
    
        <input type="hidden" name="comment_id" value="1218112325" class="js-comment-id">
        <div class="form-actions comment-form-actions">
          <button data-disable-with="" type="submit" data-view-component="true" class="btn-primary btn">  Update comment
      
    </button>
          <button data-confirm-text="Are you sure you want to discard your unsaved changes?" type="button" data-view-component="true" class="js-comment-cancel-button btn-danger btn">  Cancel
      
    </button>    </div>
      </div>
    
      <div class="comment-form-error mb-2 js-comment-update-error" hidden=""></div>
    </div>
""".trimIndent()