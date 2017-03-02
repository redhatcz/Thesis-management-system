<%@ page import="com.redhat.theses.auth.User; com.redhat.theses.Notification" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Grails"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}?=v2" type="image/x-icon">
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
    <r:require module="bootstrap-js"/>
    <r:require module="bootstrap-less"/>
    <r:require module="application"/>
    <r:require module="jquery"/>
    <r:require module="jquery-ui"/>
    <g:layoutHead/>
    <r:layoutResources/>
</head>
<body style="background-image: none;">
    <div id="options" class="no-print">
        <div class="fixed-options">
            <i class="icon-cog"></i>
            <a href="#options-modal" class="no-print" data-toggle="modal">
                <g:message code="options.button"/>
            </a>
        </div>

        <div id="options-modal" class="modal hide fade">
            <div class="modal-wrapper">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3><g:message code="options.header"/></h3>
                </div>
                <g:form method="get" class="filter" action="printable" style="margin: 0;">
                    <div class="modal-body">
                        <%--Filter--%>
                        <h4><g:message code="filter.options.header"/></h4>
                        <g:hiddenField name="filtering" value="true"/>
                        <g:pageProperty name="page.options.filter"/>

                        <%--Table options--%>
                        <h4><g:message code="printable.view.options.header"/></h4>
                        <p><b><g:message code="printable.view.options.table.width"/>:</b></p>
                        <label>
                            <g:radio value="on" name="fixedTable" checked="${params?.fixedTable != 'off' ? 'true' : ''}"
                                /> <g:message code="printable.view.options.table.fixed"/>
                        </label>
                        <label>
                            <g:radio value="off" name="fixedTable" checked="${params?.fixedTable == 'off' ? 'true' : ''}"
                                /> <g:message code="printable.view.options.table.manual"
                                />: <g:textField name="manualTableWidth" style="width: 70px; height: 20px;" value="${params?.manualTableWidth}"/> px
                        </label>
                        <g:pageProperty name="page.options.table"/>

                        <%--View options--%>
                        <p><b><g:message code="printable.view.options.fields"/>:</b></p>
                        <g:hiddenField name="viewing" value="true"/>
                        <g:pageProperty name="page.options.view"/>
                    </div>                            
                    <div class="modal-footer">
                        <g:submitButton class="tms-btn pull-right" name="filter-button"
                                        value="${message(code: 'apply.button')}"/>
                    </div>
                </g:form>        
            </div>
        </div>
    </div>

    <%--Table--%>
    <g:if test="${content}">
    <table id="printable-table-topics" class="table table-hover"
           style="${params?.fixedTable == 'off' ? "width: ${params?.manualTableWidth?.encodeAsHTML()}px;" : ''}">
        <thead>
            <tr>
                <g:pageProperty name="page.table.head"/>
            </tr>
        </thead>
        <tbody>
           <g:pageProperty name="page.table.body"/>
        </tbody>
    </table>
    </g:if>
    <g:else>
        <p><g:message code="printable.no.entries.found"/></p>
    </g:else>
                       
    <g:layoutBody/>
    <r:layoutResources/>
</body>
</html>
