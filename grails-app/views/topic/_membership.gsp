<richg:dynamicField id="supervision-list" for="${membershipCommand?.memberships}" var="membership" index="i">
    <g:select id="supervisions.memberships[${i}].organization"
              name="supervisions.memberships[${i}].organization.id"
              from="${universities}"
              optionKey="id"
              required=""
              value="${membership?.organization?.id}" class="many-to-one"/>

    <a4g:autocomplete remoteUrl="${createLink(action: 'listUsersFromUniversityByName')}"
                      optElements="supervisions.memberships[${i}].organization@organizationId">
        <g:hiddenField name="supervisions.memberships[${i}].id" value="${membership?.user?.id}"/>
        <g:textField name="supervisions.memberships[${i}].user.fullName" value="${membership?.user?.fullName}"/>
    </a4g:autocomplete>
</richg:dynamicField>