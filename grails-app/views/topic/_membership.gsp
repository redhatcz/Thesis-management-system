<richg:dynamicField id="supervision-list" for="${membershipCommand?.memberships}" var="membership" index="i">
    <g:select id="supervisions.memberships[${i}].university"
              name="supervisions.memberships[${i}].university.id"
              from="${com.redhat.theses.University.list()}"
              optionKey="id"
              required=""
              value="${membership?.university?.id}" class="many-to-one"/>

    <a4g:autocomplete remoteUrl="${createLink(action: 'listUsersFromUniversityByName')}"
                      optElements="supervisions.memberships[${i}].university@universityId">
        <g:hiddenField name="supervisions.memberships[${i}].id" value="${membership?.user?.id}"/>
        <g:textField name="supervisions.memberships[${i}].user.fullName" value="${membership?.user?.fullName}"/>
    </a4g:autocomplete>
</richg:dynamicField>