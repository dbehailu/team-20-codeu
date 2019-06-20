let message = document.getElementById("message-form");

if (message === "") {
 // Do nothing; consider showing a simple error to the user.
}

try {
 let resp = await fetch("/a11y/tts", {
   method: "POST",
   body: message, // The message from the form
   headers: {
     "Content-Type": "text/plain"
   },
 })
 
 // Our audio is binary data - often called a "blob" - and
 // not text data.
 // See https://developer.mozilla.org/en-US/docs/Web/API/Body/blob for another
 // example.
 let audio = await resp.blob()
 
 // TODO(you): Pass your "audio" content to the Web Audio API and
 // trigger playback.
 // Refer to https://developer.mozilla.org/en-US/docs/Web/HTML/Element/audio
 // e.g. <audio id=”audio-11”>
 // Think about how you would have an empty audio tag for each message, ready
 // to go - e.g <audio id=”something” src=””>
 let elem = document.getElementById(“audio-”)
 
} catch (err) {
 throw new Error(`Unable to call the Text to Speech API: {e}`)
}