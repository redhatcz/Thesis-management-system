<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<html>
<head>
    <g:render template="/layouts/head" />
    <g:layoutHead/>
    <r:layoutResources />
</head>
<body>

    <g:render template="/layouts/navbar" />

    <div class="container">
        <g:if test="${flash.message}">
            <div class="span12">
                <div class="alert alert-info" role="status">
                    ${flash.message}
                </div>
            </div>
        </g:if>

        <g:layoutBody />
    </div>

    <g:render template="/layouts/footer" />
    <g:javascript library="application"/>
    <r:layoutResources />

</body>
</html>