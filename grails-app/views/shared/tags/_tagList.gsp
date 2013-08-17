<%@ page import="com.redhat.theses.util.Util" %>
<div role="navigation">
    <g:if test="${tagListWithUsage && tagListWithUsage.size() != 0}">
    <ul class="unstyled tags-panel">
        <g:each in="${tagListWithUsage}" var="tagEntry">
        <li>
            <richg:link class="${tagEntry.key == currentTag ? 'tag-selected' : 'tag'}" action="list"
                    params="['filter.tags.title': tagEntry.key.title]" removeParams="['max', 'offset']"
            ><g:fieldValue bean="${tagEntry.key}" field="title"/></richg:link>
            &times; ${tagEntry.value}
        </li>
        </g:each>
        <li class="more">
            <i class="icon-tags"></i>
            <g:link controller="tag" action="list" params="[tagsOf: params.controller]">
                <g:message code="more.button"/>
            </g:link>
        </li>
    </ul>
    </g:if>
    <g:else>
        <p class="muted"><g:message code="tag.no.tags.found"/></p>
    </g:else>
</div>
