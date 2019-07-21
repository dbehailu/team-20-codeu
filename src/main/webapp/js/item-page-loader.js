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

// Get ?item=XYZ parameter value
const urlParams = new URLSearchParams(window.location.search);
const parameterItem = urlParams.get('id');

// URL must include ?item=XYZ parameter. If not, redirect to homepage.
if (!parameterItem) {
  window.location.replace('/');
}

/** Sets the page title based on the URL parameter username. */
function setPageTitle() {
  document.getElementById('page-title').innerText = parameterItem;
  document.title = parameterItem + ' - Item Page';
}


function fetchItem(){
  const url = '/items?id=' + parameterItem;
  fetch(url).then((response) => {
    return response.json();
  }).then((item) => {
    const itemContainer = document.getElementById('item-container');
    if(id == ''){
      itemContainer.innerHTML = 'This item no longer exists.';
    }

    const itemDiv = buildItemDiv(item);
    itemContainer.appendChild(itemDiv);
  });
}


function buildItemDiv(item) {
    const headerDiv = document.createElement('div');
    headerDiv.classList.add('message-header');
    headerDiv.classList.add('padded');

    headerDiv.appendChild(document.createTextNode(
      item.user + ' - ' + formatDate(message.timestamp)));

    const titleDiv = document.createElement('div');
    titleDiv.classList.add('message-body');
    titleDiv.classList.add('padded');
    titleDiv.innerHTML = 'Title: ' + item.title;

    const descriptionDiv = document.createElement('div');
    descriptionDiv.classList.add('message-body');
    descriptionDiv.classList.add('padded');
    descriptionDiv.innerHTML = 'Description: ' + item.description;

    const locationDiv = document.createElement('div');
    locationDiv.classList.add('message-body');
    locationDiv.classList.add('padded');
    locationDiv.innerHTML = 'Location: ' + item.location;

    const lostOrFoundDiv = document.createElement('div');
    lostOrFoundDiv.classList.add('message-body');
    lostOrFoundDiv.classList.add('padded');
    lostOrFoundDiv.innerHTML = 'Status: ' + item.lostOrFound;

    const messageDiv = document.createElement('div');
    messageDiv.classList.add('rounded');
    messageDiv.classList.add('panel');
    messageDiv.appendChild(headerDiv);
    messageDiv.appendChild(titleDiv);
    messageDiv.appendChild(descriptionDiv);
    messageDiv.appendChild(locationDiv);
    messageDiv.appendChild(lostOrFoundDiv);


    return messageDiv;
}

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


/** Fetches data and populates the UI of the page. */
function buildUI() {

  setPageTitle();
  fetchItem();
}
