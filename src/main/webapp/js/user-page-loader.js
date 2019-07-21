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


 function formatDate(date) {
        var hours = date.getHours();
        var minutes = date.getMinutes();
        if (hours < 12) {
          ampm = "am";
        } else {
          ampm = "pm";
        }
        hours = hours % 12;
        if (hours == 0) {
          hours = 12;
        }
        if (minutes < 10) {
          minutes = '0' + minutes;
        }
        var time = hours + ':' + minutes + ' ' + ampm;
        return date.getMonth()+1 + "/" + date.getDate() + "/" + date.getFullYear() + "  " + time;
      }

// Get ?user=XYZ parameter value
const urlParams = new URLSearchParams(window.location.search);
const parameterUsername = urlParams.get('user');

// URL must include ?user=XYZ parameter. If not, redirect to homepage.
if (!parameterUsername) {
  window.location.replace('/');
}

/** Sets the page title based on the URL parameter username. */
function setPageTitle() {
  document.getElementsByClassName('userpage')[0].innerText = parameterUsername;
  document.title = parameterUsername + ' - User Page';
}

/**
 * Shows the message form if the user is logged in and viewing their own page.
 */
function showMessageFormIfViewingSelf() {
  fetch('/login-status')
      .then((response) => {
        return response.json();
      })
      .then((loginStatus) => {
        if (loginStatus.isLoggedIn &&
            loginStatus.username == parameterUsername) {
          const messageForm = document.getElementById('message-form');
          messageForm.classList.remove('hidden');
          document.getElementById('title-form').classList.remove('hidden');
          document.getElementById('description-form').classList.remove('hidden');
          document.getElementById('location-form').classList.remove('hidden');
          document.getElementById('lostOrFound-form').classList.remove('hidden');
        }
      });
    
}

/** Fetches messages and add them to the page. */
function fetchMessages() {
        const div = document.getElementById('message-container');
        while(div.firstChild){
            div.removeChild(div.firstChild);
        }
         messageContainer = document.getElementById(`message-container`);

        const url = '/messages?user=' + parameterUsername;
        fetch(url).then((response) => {
          return response.json();
        }).then((messages) => {
          // var counter = 0;
          // var divCount = 0;
          if(messages.length == 0){
           messageContainer.innerHTML = '<p>There are no posts yet.</p>';
          }
          else{
           messageContainer.innerHTML = '';
          }
          messages.forEach((message) => {
              const messageDiv = buildSummaryDiv(message);
              messageContainer = document.getElementById(`message-container`);
              messageContainer.appendChild(messageDiv);
          });
        });
}

/**
 * Builds an element that displays the message.
 * @param {Message} message
 * @return {Element}
 */
function buildMessageDiv(message) {
  const headerDiv = document.createElement('div');
  headerDiv.classList.add('message-header');
  headerDiv.appendChild(document.createTextNode(
      message.user + ' - ' + new Date(message.timestamp)));

  const bodyDiv = document.createElement('div');
  bodyDiv.classList.add('message-body');
  bodyDiv.innerHTML = message.text;

  const messageDiv = document.createElement('div');
  messageDiv.classList.add('message-div');
  messageDiv.appendChild(headerDiv);
  messageDiv.appendChild(bodyDiv);

  return messageDiv;
}
      function buildSummaryDiv(message){

        const cardWrap = document.createElement('div');
        cardWrap.classList.add("card-wrap");
        cardWrap.classList.add("hover");

        const card = document.createElement('div');
         card.classList.add("card-lost");


         const timeDiv = document.createElement('div');
         timeDiv.classList.add('inner-wrapper');
         timeDiv.appendChild(document.createElement("H2").appendChild(document.createTextNode(formatDate(new Date(message.timestamp)))));
         timeDiv.appendChild(document.createElement("br"));
         timeDiv.appendChild(document.createElement("br"));
         timeDiv.appendChild(document.createElement("p").appendChild(document.createTextNode(message.user)));
         var line = document.createElement("hr");
         line.classList.add("line");
         timeDiv.appendChild(line);

         const cardInfo = document.createElement('div');
         cardInfo.insertAdjacentHTML('beforeend', message.text);

         card.appendChild(timeDiv);
         card.appendChild(cardInfo);
         cardWrap.appendChild(card);

         return cardWrap;
      }


function fetchTitle(){
  const url = '/description?user=' + parameterUsername;
  fetch(url).then((response) => {
    return response.text();
  }).then((user) => {
    let title = JSON.parse(user).title;
    const titleContainer = document.getElementById('title-container');
    if(title == ''){
      title = 'This user has not entered any information yet.';
    }
    titleContainer.innerHTML = title;
  });
}

function fetchDescription(){
  const url = '/description?user=' + parameterUsername;
  fetch(url).then((response) => {
    return response.text();
  }).then((user) => {
    let description = JSON.parse(user).description;
    const descriptionContainer = document.getElementById('description-container');
    if(description == ''){
      description = 'This user has not entered any information yet.';
    }
    descriptionContainer.innerHTML = description;
  });
}

function fetchLocation(){
  const url = '/description?user=' + parameterUsername;
  fetch(url).then((response) => {
    return response.text();
  }).then((user) => {
    let location = JSON.parse(user).location;
    const locationContainer = document.getElementById('location-container');
    if(location == ''){
      location = 'This user has not entered any information yet.';
    }
    locationContainer.innerHTML = location;
  });
}

function fetchLostOrFound(){
  const url = '/description?user=' + parameterUsername;
  fetch(url).then((response) => {
    return response.text();
  }).then((user) => {
    let lostOrFound = JSON.parse(user).lostOrFound;
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
  const config = {removePlugins: [ "BlockQuote", "EasyImage", "Heading", "Image", "ImageCaption", "ImageStyle", "ImageToolbar", "ImageUpload", "MediaEmbed", "PasteFromOffice", "Table", "TableToolbar" ] };
  ClassicEditor.create(document.getElementById('message-input'), config);
}
