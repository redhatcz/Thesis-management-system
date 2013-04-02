<div class="table-layout">
    <div class="search-result">
        <div class="pull-left">
            <richg:avatar user="${user}" small="true"
                          class="img-circle avatar-mini" height="36" width="36"/>
        </div>
        <div class="result-content">
            <h4>
                <g:link controller="user" action="show" id="${user?.id}">
                    <g:fieldValue bean="${user}" field="fullName"/>
                </g:link>
            </h4>
        </div>
    </div>
</div>