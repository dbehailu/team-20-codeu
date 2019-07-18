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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" rel="stylesheet"> 
      <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDYWpnhyR1LEDFMgQj_WFiaVoUkzjKnqWs"></script>
  <script>
    let map;
    /* Editable marker that displays when a user clicks in the map. */
    let editMarker;
    function createMap(){
      map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 38.5949, lng: -94.8923},
        zoom: 4
      });
      // When the user clicks in the map, show a marker with a text box the user can edit.
      map.addListener('click', (event) => {
        createMarkerForEdit(event.latLng.lat(), event.latLng.lng());
      });
      fetchMarkers();
    }
    /** Fetches markers from the backend and adds them to the map. */
    function fetchMarkers(){
      fetch('/markers').then((response) => {
        return response.json();
      }).then((markers) => {
        markers.forEach((marker) => {
         createMarkerForDisplay(marker.lat, marker.lng, marker.content)
        });
      });
    }
    /** Creates a marker that shows a read-only info window when clicked. */
    function createMarkerForDisplay(lat, lng, content){
      const marker = new google.maps.Marker({
        position: {lat: lat, lng: lng},
        map: map
      });
      var infoWindow = new google.maps.InfoWindow({
        content: content
      });
      marker.addListener('click', () => {
        infoWindow.open(map, marker);
      });
    }
    /** Sends a marker to the backend for saving. */
    function postMarker(lat, lng, content){
      const params = new URLSearchParams();
      params.append('lat', lat);
      params.append('lng', lng);
      params.append('content', content);
      fetch('/markers', {
        method: 'POST',
        body: params
      });
    }
    /** Creates a marker that shows a textbox the user can edit. */
    function createMarkerForEdit(lat, lng){
      // If we're already showing an editable marker, then remove it.
      if(editMarker){
       editMarker.setMap(null);
      }
      editMarker = new google.maps.Marker({
        position: {lat: lat, lng: lng},
        map: map
      });
      const infoWindow = new google.maps.InfoWindow({
        content: buildInfoWindowInput(lat, lng)
      });
      // When the user closes the editable info window, remove the marker.
      google.maps.event.addListener(infoWindow, 'closeclick', () => {
        editMarker.setMap(null);
      });
      infoWindow.open(map, editMarker);
    }
    /** Builds and returns HTML elements that show an editable textbox and a submit button. */
    function buildInfoWindowInput(lat, lng){
      const textBox = document.createElement('textarea');
      const button = document.createElement('button');
      button.appendChild(document.createTextNode('Submit'));
      button.onclick = () => {
        postMarker(lat, lng, textBox.value);
        createMarkerForDisplay(lat, lng, textBox.value);
        editMarker.setMap(null);
      };
      const containerDiv = document.createElement('div');
      containerDiv.appendChild(textBox);
      containerDiv.appendChild(document.createElement('br'));
      containerDiv.appendChild(button);
      return containerDiv;
    }
  </script>
  </head>
  <body onload= "createMap()">
    <div id="content">
    <nav>
      <ul id="navigation" class="down">
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
      <h1>Marker Storage</h1>
        <div id="map"></div>
        <p>Click in the map to create a new marker.</p>
      <button>Submit</button>
    </form>

    

    <div id="message-container" class="message-container">Loading...</div>

    <script>
      buildUI();
  </script>
  </div>


  </body>
</html>
