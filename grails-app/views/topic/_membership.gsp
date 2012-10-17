    <g:select id="membership.university"
              name="membership.university.id"
              from="${com.redhat.theses.University.list()}"
              optionKey="id"
              required=""
              value="${membershipInstance?.university?.id}" class="many-to-one"/>

    <a4g:autocomplete remoteUrl="${createLink(action: 'listUsersFromUniversityByName')}"
                      optElements="membership.university">
        <g:hiddenField name="membership.id" value="${membershipInstance?.user?.id}"/>
        <g:textField name="membership.user.fullName" value="${user?.fullName}"/>
    </a4g:autocomplete>