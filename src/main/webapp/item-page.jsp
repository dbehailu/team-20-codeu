<%@page import="com.google.codeu.data.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<% BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
String uploadUrl = blobstoreService.createUploadUrl("/my-item-handler"); %>


<!DOCTYPE html>
<html>
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>Item Page</title>
  </head>
  <body>
  <%Message m = (Message) request.getAttribute("message");%>
      <h1>User</h1>
            <h2><%=m.getUser()%></h2>
      <h1>Text</h1>
            <h2><%=m.getText()%></h2>
      <h1>Description</h1>
            <h2><%=m.getDescription()%></h2>
      <h1>Location</h1>
            <h2><%=m.getLocation()%></h2>
      <h1>lostOrFound</h1>
            <h2><%=m.getLostOrFound()%></h2>
    </body>
</html>

