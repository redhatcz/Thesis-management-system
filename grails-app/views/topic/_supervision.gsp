<%@ page import="com.redhat.theses.ajax.JsonController" %>
<richg:dynamicField id="supervision-list" for="${supervisionCommand?.supervisions}" var="supervision" index="i">
    <g:select id="supervisionCommand.supervisions[${i}].university"
              name="supervisionCommand.supervisions[${i}].university.id"
              from="${universities}"
              optionKey="id"
              required=""
              value="${supervision?.universityId}" class="many-to-one"/>

    <g:hiddenField name="supervisionCommand.supervisions[${i}].supervisor.id" value="${supervision?.supervisor?.id}"/>
    <a4g:textField name="supervisionCommand.supervisions[${i}].supervisor.fullName" value="${supervision?.supervisor?.fullName}"
                   autocomplete-url="${createLink(controller: 'json', action: 'listSupervisorsByName')}"
                   autocomplete-target="supervisionCommand.supervisions[${i}].supervisor.id"/>
</richg:dynamicField>