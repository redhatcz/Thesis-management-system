<%@ page import="com.redhat.theses.util.Util" %>
<div role="navigation">
    <ul class="unstyled tags-panel">
        <g:each in="${tagListWithUsage}" var="tagEntry">
        <li>
            <richg:link class="${tagEntry.key == currentTag ? 'tag-selected' : 'tag'}" action="list"
                    params="['filter.tags.title': tagEntry.key.title]" removeParams="['max', 'offset']"
            ><g:fieldValue bean="${tagEntry.key}" field="title"/></richg:link>
            &times; ${tagEntry.value}
        </li>
        </g:each>
    </ul>
</div>
