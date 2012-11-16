<div class="span8">
    <div class="table-base">
        <g:if test="${topicInstanceList.isEmpty()}">
            <p class="center muted">No topics were found.</p>
        </g:if>
        <g:else>
            <g:each var="topic" in="${topicInstanceList}">
                <div class="table-layout">
                    <h4>
                        <g:link action="show" id="${topic.id}">
                            <g:fieldValue bean="${topic}" field="title"/>
                        </g:link>
                    </h4>
                    <g:fieldValue bean="${topic}" field="lead"/>
                </div>
            </g:each>
        </g:else>
    </div>
    <g:paginate total="${topicInstanceTotal}" class="pagination-centered"/>
</div>

<div class="span4">
    <div class="thesis">
        <h4>Tags</h4>
        <g:render template="tagList" />
    </div>
    <div class="thesis">
        <h4>Manage Topics</h4>
        <g:link class="btn btn-info" action="create">
            <g:message code="default.new.label" args="[entityName]" />
        </g:link>
    </div>
</div>