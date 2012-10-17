    <g:select id="membership.university"
              name="membership.university.id"
              from="${com.redhat.theses.University.list()}"
              optionKey="id"
              required=""
              value="${membershipInstance?.university?.id}" class="many-to-one"/>


    <g:hiddenField name="membership.id" value="${membershipInstance?.user?.id}"/>
    <a4g:autocomplete name="membership.user.fullName" value="${user?.fullName}"
                            hiddenFieldId="membership.id"
                            remoteUrl="${createLink(action: 'listUsersFromUniversityByName')}"
                            optElements="membership.university"/>