<!--
Copyright 2019 Google Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<% BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
String uploadUrl = blobstoreService.createUploadUrl("/my-form-handler"); %>

<!DOCTYPE html>
<html>
  <head>
    <title>User Page</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/main.css">
    <link rel="stylesheet" href="/css/user-page.css">
    <script src="/js/user-page-loader.js"></script>
    <script src="https://cdn.ckeditor.com/ckeditor5/11.2.0/classic/ckeditor.js"></script>
  </head>
  <body>
    <div id="content">
    <nav>
      <ul id="navigation">
        <li><a href="/">Home</a></li>
        <li><a href="/aboutus.html">About Our Team</a></li>
      </ul>
    </nav>
    <h1 id="titleHeader">User Page</h1>

    <div id="title-container">Loading...</div>
  <div id="title-form" class="hidden">
  <form action="/title" method="POST">
    <textarea name="title" placeholder="title" rows=2 required></textarea>
    <br/>
    <input type="submit" value="Submit">
  </form>
</div> 

<div id="description-container">Loading...</div>
  <div id="description-form" class="hidden">
  <form action="/description" method="POST">
    <textarea name="description" placeholder="description" rows=4 required></textarea>
    <br/>
    <input type="submit" value="Submit">
  </form>
</div>

<div id="location-container">Loading...</div>
  <div id="location-form" class="hidden">
  <form action="/location" method="POST">
    <textarea name="location" placeholder="location" rows=4 required></textarea>
    <br/>
    <input type="submit" value="Submit">
  </form>
</div>

<div id="lostOrFound-container">Loading...</div>
  <div id="lostOrFound-form" class="hidden">
  <form action="/lostOrFound" method="POST">
    <textarea name="lostOrFound" placeholder="lostOrFound" rows=1 required></textarea>
    <br/>
    <input type="submit" value="Submit">
  </form>
</div> 

  <!-- <form id="message-form" action="/messages" method="POST" class="hidden">
    Enter a new message:
    <br/>
    <textarea name="text" id="message-input"></textarea>
    <br/>
    <input type="submit" value="Submit">
  </form> -->
  <hr/>

  <!-- <form id="message-form" action="/messages" method="POST" class="hidden"> -->

    <form id="message-form" action="<%= uploadUrl %>" method="POST" enctype="multipart/form-data">
      Enter a new message:
      <br/>
      <textarea name="text" id="message-input"></textarea>
      <br/>
      <p>Upload an image:</p>
      <input type="file" name="image">
      <br/><br/>
      <button>Submit</button>
    </form>

    

    <div id="message-container" class="message-container">Loading...</div>

    <script>
      buildUI();
  </script>
  </div>


  </body>
</html>
