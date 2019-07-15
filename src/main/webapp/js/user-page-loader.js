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
        var strTime = hours + ':' + minutes + ' ' + ampm;
        return date.getMonth()+1 + "/" + date.getDate() + "/" + date.getFullYear() + "  " + strTime;
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
  document.getElementById('titleHeader').innerText = parameterUsername;
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
        }
      });
      document.getElementById('title-form').classList.remove('hidden');
      document.getElementById('description-form').classList.remove('hidden');
      document.getElementById('location-form').classList.remove('hidden');
      document.getElementById('lostOrFound-form').classList.remove('hidden');
}

/** Fetches messages and add them to the page. */
function fetchMessages() {
  // const url = '/messages?user=' + parameterUsername;
  // fetch(url)
  //     .then((response) => {
  //       return response.json();
  //     })
  //     .then((messages) => {
  //       const messagesContainer = document.getElementById('message-container');
  //       if (messages.length == 0) {
  //         messagesContainer.innerHTML = '<p>This user has no posts yet.</p>';
  //       } else {
  //         messagesContainer.innerHTML = '';
  //       }
  //       messages.forEach((message) => {
  //         const messageDiv = buildMessageDiv(message);
  //         messagesContainer.appendChild(messageDiv);
  //       });
  //     });
  //const div = document.getElementById('lostDiv');
        const div = document.getElementById('message-container');
        while(div.firstChild){
            div.removeChild(div.firstChild);
        }
        // var addDiv = document.createElement('div');
        // addDiv.setAttribute('id', `message-container-0`);
        // document.getElementById('lostDiv').appendChild(addDiv);
         messageContainer = document.getElementById(`message-container`);
        // messageContainer.classList.add('message-container');

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
          //var counter = 0;
          messages.forEach((message) => {  
              const messageDiv = buildSummaryDiv(message);
              // if (counter % 3 == 0 && counter != 0) {
              //   divCount++;
              //   var addDiv = document.createElement('div');
              //   addDiv.setAttribute('id', `message-container-${divCount}`);
              //   document.getElementById('lostDiv').appendChild(addDiv);
                
              // }
              messageContainer = document.getElementById(`message-container`);
              //messageContainer.classList.add('message-container');
              messageContainer.appendChild(messageDiv);
              //counter++;
              
           
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

        // const back = document.createElement('div');
        // back.classList.add("back");
        // cardInfo.insertAdjacentHTML('beforeend', message.image);

         cardWrap.appendChild(card);
         // cardWrap.appendChild(back);

         return cardWrap;
      }


function fetchTitle(){
  const url = '/title?user=' + parameterUsername;
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
  const url = '/description?user=' + parameterUsername;
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
  const url = '/location?user=' + parameterUsername;
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
  const url = '/lostOrFound?user=' + parameterUsername;
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
