<richg:dynamicField id="supervision-list" for="${membershipCommand?.memberships}" var="membership" index="i">
    <g:select id="memberships[${i}].university"
              name="memberships[${i}].university.id"
              from="${com.redhat.theses.University.list()}"
              optionKey="id"
              required=""
              value="${membership?.university?.id}" class="many-to-one"/>

    <a4g:autocomplete remoteUrl="${createLink(action: 'listUsersFromUniversityByName')}"
                      optElements="memberships[${i}].university@universityId">
        <g:hiddenField name="memberships[${i}].id" value="${membership?.user?.id}"/>
        <g:textField name="memberships[${i}].user.fullName" value="${user?.fullName}"/>
    </a4g:autocomplete>
</richg:dynamicField>