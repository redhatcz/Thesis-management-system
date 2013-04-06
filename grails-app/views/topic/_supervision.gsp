<%@ page import="com.redhat.theses.ajax.JsonController" %>

<richg:dynamicField id="supervision-list" for="${supervisionCommand?.supervisions}"
                    var="supervision" index="i">

    <g:select id="supervisionCommand.supervisions[${i}].university"
              name="supervisionCommand.supervisions[${i}].university.id"
              from="${universities}" optionKey="id"
              value="${supervision?.universityId}" class="many-to-one"/>

    <g:hiddenField name="supervisionCommand.supervisions[${i}].supervisor.id"
                   value="${supervision?.supervisor?.id}"/>

    <g:hiddenField name="a4g-role[${i}]" value="${com.redhat.theses.auth.Role.SUPERVISOR}"/>
    <a4g:textField name="supervisionCommand.supervisions[${i}].supervisor.fullName"
                   value="${supervision?.supervisor?.fullName}"
                   data-autocomplete-url="${createLink(controller: 'json', action: 'listUsersByNameAndRole')}"
                   data-autocomplete-target="supervisionCommand.supervisions[${i}].supervisor.id"
                   data-autocomplete-opts="a4g-role[${i}]@role"/>

</richg:dynamicField>
