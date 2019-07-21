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
    <link rel="icon" type="image/png" href="icon.png">
    <script src="https://cdn.ckeditor.com/ckeditor5/11.2.0/classic/ckeditor.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" rel="stylesheet"> 
  </head>
  <body>
    <h1 id="titleHeader"><a href="/">WALDO</a></h1>
    <nav>
      <ul id="navigation">
        <li><a href="/">Home</a></li>
        <li><a href="/aboutus.html">About</a></li>
        <li><a href="/contributions.html">Contributions</a></li>
      </ul>
    </nav>
    <div id="content">
      
    <h2 id = "aboutmeh2" class="userpage">User Page</h2> 
    <hr/>

<form action="/description" method="POST">
    <div id="title-container">This user has not entered any information yet.</div>
  <div id="title-form" class="hidden">

    <textarea name="title" placeholder="title" rows=2 required></textarea>
    <br/>
  </div>

<div id="description-container">This user has not entered any information yet.</div>
  <div id="description-form" class="hidden">
    <textarea name="description" placeholder="description" rows=4 required></textarea>
    <br/>

  </div>

<div id="location-container">This user has not entered any information yet.</div>
  <div id="location-form" class="hidden">
    <textarea name="location" placeholder="location" rows=4 required></textarea>
    <br/>

</div>

<div id="lostOrFound-container">This user has not entered any information yet.</div>
  <div id="lostOrFound-form" class="hidden">
    <textarea name="lostOrFound" placeholder="lostOrFound" rows=1 required></textarea>
    <br/>


</div>
<input type="submit" value="Submit">
</form>

  <!-- <form id="message-form" action="/messages" method="POST" class="hidden">
    Enter a new message:
    <br/>
    <textarea name="text" id="message-input"></textarea>
    <br/>
    <input type="submit" value="Submit">
  </form> -->
  <hr/>

  <!-- <form id="message-form" action="/messages" method="POST" class="hidden"> -->

    <form id="message-form" action="<%= uploadUrl %>" method="POST" enctype="multipart/form-data" class="hidden">
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
