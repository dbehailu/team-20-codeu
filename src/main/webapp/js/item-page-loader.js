/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Get ?user=XYZ parameter value
const urlParams = new URLSearchParams(window.location.search);
const parameterMessage = urlParams.get('message');

// URL must include ?user=XYZ parameter. If not, redirect to homepage.
if (!parameterMessage) {
  window.location.replace('/');
}

/** Sets the page title based on the URL parameter username. */
function setPageTitle() {
  document.getElementById('page-title').innerText = parameterMessage;
  document.title = parameterMessage + ' - Item Page';
}


function fetchTitle(){
  const url = '/title?message=' + parameterMessage;
  fetch(url).then((response) => {
    return response.text();
  }).then((title) => {
    const titleContainer = document.getElementById('title-container');
    if(title == ''){
      title = 'This user has not entered any information yet.';
    }

    titleContainer.innerHTML = title;

  });
}

function fetchDescription(){
  const url = '/description?message=' + parameterMessage;
  fetch(url).then((response) => {
    return response.text();
  }).then((description) => {
    const descriptionContainer = document.getElementById('description-container');
    if(description == ''){
      description = 'This user has not entered any information yet.';
    }

    descriptionContainer.innerHTML = description;

  });
}

function fetchLocation(){
  const url = '/location?message=' + parameterMessage;
  fetch(url).then((response) => {
    return response.text();
  }).then((location) => {
    const locationContainer = document.getElementById('location-container');
    if(location == ''){
      location = 'This user has not entered any information yet.';
    }

    locationContainer.innerHTML = location;

  });
}
function fetchLostOrFound(){
  const url = '/lostOrFound?message=' + parameterMessage;
  fetch(url).then((response) => {
    return response.text();
  }).then((lostOrFound) => {
    const lostOrFoundContainer = document.getElementById('lostOrFound-container');
    if(lostOrFound == ''){
      lostOrFound = 'This user has not entered any information yet.';
    }

    lostOrFoundContainer.innerHTML = lostOrFound;

  });
}

/** Fetches data and populates the UI of the page. */
function buildUI() {

  setPageTitle();
  showMessageFormIfViewingSelf();
  fetchMessages();
  fetchTitle();
  fetchDescription();
  fetchLocation();
  fetchLostOrFound();
  /**const config = {removePlugins: [ 'List', 'Table'  ]};*/
  ClassicEditor.create(document.getElementById('message-input'));
}
