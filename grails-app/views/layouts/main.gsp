<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <g:render template="/layouts/head" />
</head>
<body>

    <g:render template="/layouts/navbar" />

    <div class="container">
        <div class="span12">
            <g:render template="/layouts/navigation" />
            <g:render template="/layouts/messages" />
        </div>
        <g:layoutBody />
    </div>

    <g:render template="/layouts/footer" />
    <g:javascript library="application"/>
    <r:layoutResources />

</body>
</html>
