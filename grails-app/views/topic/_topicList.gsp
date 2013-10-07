<%@ page import="com.redhat.theses.util.Util" %>
<div class="span8 content">
<g:if test="${topicInstanceList && !topicInstanceList.empty}">
    <g:each var="topic" in="${topicInstanceList}">
        <div class="table-layout ${!topic?.enabled ? 'disabled' : ''}">
            <h3>
                <g:link action="show" id="${topic.id}" params="[headline: Util.hyphenize(topic?.title)]">
                    <g:fieldValue bean="${topic}" field="title"/>
                </g:link>

                <g:if test="${!topic?.enabled}">
                    <span class="disabled-title">(disabled)</span>
                </g:if>
            </h3>
            <ul class="inline">
                <li>
                    <i class="icon-user" title="${message(code:'role.owner.label')}"></i>
                    <g:link controller="user" action="show" id="${topic?.ownerId}">${topic?.owner?.fullName}</g:link>
                </li>
                <li>
                    <i class="icon-time" title="${message(code:'topic.dateCreated.label')}"></i>
                    <g:formatDate date="${topic?.dateCreated}"
                                  dateStyle="LONG"
                                  type="date" />
                </li>
                <li>
                    <i class="icon-comment" title="${message(code:'comment.title')}"></i>
                    <g:link action="show" id="${topic.id}" fragment="comments">${commentCounts[topic] ?: 0} comments</g:link>
                </li>
            </ul>
            <div class="table-text">
                <g:fieldValue bean="${topic}" field="lead"/>
                <div class="read-more">
                    <g:link class="gray-link" action="show"
                            id="${topic.id}" params="[headline: Util.hyphenize(topic?.title)]"
                    ><g:message code="topic.read.more.label"/>&hellip;</g:link>
                </div>
            </div>
        </div>
    </g:each>
</g:if>

<g:else>
    <p class="center muted"><g:message code="topic.no.topics.found"/></p>
</g:else>

<g:if test="${Util.isPaginationVisible(topicInstanceTotal, params.max)}">
    <g:paginate total="${topicInstanceTotal}" class="pagination-centered"
                params="${Util.formatParams(request)}"/>
</g:if>
</div>

<div class="span4 sidebar">
    <div class="panel right">
        <sec:ifAnyGranted roles="ROLE_OWNER,ROLE_SUPERVISOR,ROLE_ADMIN">
            <h4><g:message code="topic.list.manage.label"/></h4>
            <div class="panel-content">
                <div class="panel-buttons">
                    <sec:ifAnyGranted roles="ROLE_OWNER">
                    <g:if test="${currentCategory}">
                        <g:link class="tms-link btn-link" action="create" params="[categoryId: currentCategory?.id]">
                            <i class="icon-plus"></i>
                            <g:message code="topic.create.button" />
                        </g:link>
                    </g:if>
                    <g:else>
                        <g:link class="tms-link btn-link" action="create">
                            <i class="icon-plus"></i>
                            <g:message code="topic.create.button"/>
                        </g:link>
                    </g:else>
                    </sec:ifAnyGranted>
                    <g:link class="tms-link btn-link" action="printableList" target="_blank"
                            params="${Util.formatParams(request, [:], ['max', 'offset'])}">
                        <i class="icon-print"></i>
                        <g:message code="topic.show.printable.button"/>
                    </g:link>
                </div>
            </div>
        </sec:ifAnyGranted>
        <g:if test="${currentCategory}">
            <h4><g:message code="category.label"/>: ${currentCategory?.title}</h4>
        </g:if>
        <g:else>
            <h4><g:message code="topic.categories.label"/></h4>
        </g:else>
        <div class="panel-content">
            <g:render template="categoryList" />
        </div>

        <h4>
            <div class="small-msg pull-right">
                <i class="icon-info-sign icon-large"
                   title="${message(code:'info.filter.topic')}"></i>
            </div>
            <g:message code="filters.label"/>
        </h4>
        <div class="panel-content">
            <g:form method="get" class="filter">
                <g:hiddenField name="filtering" value="true"/>
                <g:hiddenField name="filter.categories.id" value="${params?.filter?.categories?.id}"/>
                <g:hiddenField name="filter.tags.title" value="${params?.filter?.tags?.title}"/>
                <g:textField value="${params?.filter?.title}" class="wide"
                             name="filter.title" placeholder="${message(code: 'topic.title.label')}"/>
                <g:hiddenField name="type.title" value="ilike"/>
                <g:textField value="${params?.filter?.owner?.fullName}" class="wide"
                             name="filter.owner.fullName" placeholder="${message(code: 'role.owner.label')}"/>
                <g:hiddenField name="type.owner.fullName" value="ilike"/>
                <g:textField value="${params?.filter?.supervisions?.supervisor?.fullName}" class="wide"
                             name="filter.supervisions.supervisor.fullName" placeholder="${message(code: 'role.supervisor.label')}"/>
                <g:hiddenField name="type.supervisions.supervisor.fullName" value="ilike"/>
                <g:select name="filter.universities.id" from="${universities}"
                          noSelection="['':message(code:'topic.university.select.label')]"
                          optionKey="id" value="${params?.filter?.universities?.id}"
                          class="many-to-one"/>
                <g:submitButton class="tms-btn pull-right" name="filter-button"
                                value="${message(code: 'filter.button')}"/>
                <label>
                    <g:checkBox name="filter.onlyEnabled" value="${params?.filter?.onlyEnabled}"/> <g:message code="topic.show.only.enabled.label"/>
                </label>
            </g:form>
        </div>

        <h4><g:message code="topic.tags.label"/></h4>
        <div class="panel-content">
            <g:render template="/shared/tags/tagList" />
        </div>
    </div>
</div>
