async function play(){
    let message = document.getElementById("myText").value;

    if (message === "") {
     // Do nothing; consider showing a simple error to the user.
    }

    try {

     let resp = await fetch("/a11y/tts?text=" + encodeURI(message))

     // Our audio is binary data - often called a "blob" - and
     // not text data.
     // See https://developer.mozilla.org/en-US/docs/Web/API/Body/blob for another
     // example.

     // TODO(you): Pass your "audio" content to the Web Audio API and
     // trigger playback.
     // Refer to https://developer.mozilla.org/en-US/docs/Web/HTML/Element/audio
     // e.g. <audio id=”audio-11”>
     // Think about how you would have an empty audio tag for each message, ready
     // to go - e.g <audio id=”something” src=””>


    let audio = await resp.blob();
    var audioURL = URL.createObjectURL(audio);
    let elem = document.getElementById("audio-");
    elem.src = audioURL;
    elem.play();

    } catch (err) {
        throw new Error(`Unable to call the Text to Speech API: {e}`)
    }

}

function doTTS() {
    (async function() {
      await play();
    })();
}





