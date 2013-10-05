<%@ page import="com.redhat.theses.auth.User; com.redhat.theses.Notification" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Grails"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}?=v2" type="image/x-icon">
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
    <r:require module="bootstrap-js"/>
    <r:require module="bootstrap-less"/>
    <r:require module="application"/>
    <g:layoutHead/>
    <r:layoutResources/>
</head>
<body style="background-image: none;">
    <g:layoutBody/>
    <r:layoutResources/>
</body>
</html>
