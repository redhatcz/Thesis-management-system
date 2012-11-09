<%@ page import="com.redhat.theses.ajax.JsonController" %>
<richg:dynamicField id="supervision-list" for="${membershipCommand?.memberships}" var="membership" index="i">
    <g:select id="supervisions.memberships[${i}].organization"
              name="supervisions.memberships[${i}].organization.id"
              from="${universities}"
              optionKey="id"
              required=""
              value="${membership?.organization?.id}" class="many-to-one"/>

    <g:hiddenField name="supervisions.memberships[${i}].id" value="${membership?.user?.id}"/>
    <a4g:textField name="supervisions.memberships[${i}].user.fullName" value="${membership?.user?.fullName}"
                   autocomplete-url="${createLink(controller: 'json', action: 'listUsersFromUniversityByName')}"
                   autocomplete-target="supervisions.memberships[${i}].id"
                   autocomplete-opts="supervisions.memberships[${i}].organization@organizationId"/>
</richg:dynamicField>