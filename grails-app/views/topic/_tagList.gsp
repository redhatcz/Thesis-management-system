<div role="navigation">
    <ul class="unstyled tags-panel">
        <g:each in="${tagListWithUsage}" var="tagEntry">
        <li>
            <g:link class="${tagEntry.key == currentTag ? 'tag-selected' : 'tag'}" action="list"
                    params="${[tagTitle: tagEntry.key.title] + (params.categoryId ? [categoryId: params.categoryId] : [])}">${fieldValue(bean: tagEntry.key, field: "title")}</g:link>
            &times; ${tagEntry.value}
        </li>
        </g:each>
    </ul>
</div>
