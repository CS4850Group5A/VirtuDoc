const audioButton = document.getElementById('audioButton');
const videoButton = document.getElementById('videoButton');
const AddButton = document.getElementById('Add');
const btn1 = document.getElementById('btn1');
const btn2 = document.getElementById('btn2');
const btnVid = document.getElementById('btnVid');

const urlbox = document.getElementById('url');
const addVid = document.getElementById('addVid');

audioButton.addEventListener('click', mute);
videoButton.addEventListener('click', videoOnOff);

btn1.addEventListener('click', video1);
btn2.addEventListener('click', video2);
AddButton.addEventListener('click', addVideo);

// Generate random room name if needed
if (!location.hash) {
  location.hash = "Virtudoc-conf"
}
const roomHash = location.hash.substring(1);


const drone = new ScaleDrone('2xmbUiTsqTzukyf7');
// Room name needs to be prefixed with 'observable-'
const roomName = 'observable-' + roomHash;
const configuration = {
  iceServers: [{
    urls: 'stun:stun.l.google.com:19302'
  }]
};
let room;
let pc;


function onSuccess() {};
function onError(error) {
  console.error(error);
};

drone.on('open', error => {
  if (error) {
    return console.error(error);
  }
  room = drone.subscribe(roomName);
  room.on('open', error => {
    if (error) {
      onError(error);
    }
  });
  // We're connected to the room and received an array of 'members'
  // connected to the room (including us). Signaling server is ready.
  room.on('members', members => {
    console.log('MEMBERS', members);
    // If we are the second user to connect to the room we will be creating the offer
    const isOfferer = members.length === 2;
    startWebRTC(isOfferer);
  });
});

// Send signaling data via Scaledrone
function sendMessage(message) {
  drone.publish({
    room: roomName,
    message
  });
}

function startWebRTC(isOfferer) {
  pc = new RTCPeerConnection(configuration);

  // 'onicecandidate' notifies us whenever an ICE agent needs to deliver a
  // message to the other peer through the signaling server
  pc.onicecandidate = event => {
    if (event.candidate) {
      sendMessage({'candidate': event.candidate});
    }
  };

  // If user is offerer let the 'negotiationneeded' event create the offer
  if (isOfferer) {
    pc.onnegotiationneeded = () => {
      pc.createOffer().then(localDescCreated).catch(onError);
    }
  }

  // When a remote stream arrives display it in the #remoteVideo element
  pc.onaddstream = event => {
    remoteVideo.srcObject = event.stream;
  };

  navigator.mediaDevices.getUserMedia({
    audio: true,
    video: true,
  }).then(stream => {
    // Display your local video in #localVideo element
    localVideo.srcObject = stream;
    // Add your stream to be sent to the conneting peer
    pc.addStream(stream);
  }, onError);

  // Listen to signaling data from Scaledrone
  room.on('data', (message, client) => {
    // Message was sent by us
    if (client.id === drone.clientId) {
      return;
    }

    if (message.sdp) {
      // This is called after receiving an offer or answer from another peer
      pc.setRemoteDescription(new RTCSessionDescription(message.sdp), () => {
        // When receiving an offer lets answer it
        if (pc.remoteDescription.type === 'offer') {
          pc.createAnswer().then(localDescCreated).catch(onError);
        }
      }, onError);
    } else if (message.candidate) {
      // Add the new ICE candidate to our connections remote description
      pc.addIceCandidate(
        new RTCIceCandidate(message.candidate), onSuccess, onError
      );
    }
  });
}

function localDescCreated(desc) {
  pc.setLocalDescription(
    desc,
    () => sendMessage({'sdp': pc.localDescription}),
    onError
  );
}
function mute(){
localVideo.muted = !localVideo.muted;
}

function videoOnOff(){
if (localVideo.style.display === "none") {
    localVideo.style.display = "block";
  } else {
    localVideo.style.display = "none";
  }
}

function video1(){
btnVid.src = "https://www.youtube.com/embed/7z0kzYpuqhw";
}

function video2(){
btnVid.src = "https://www.youtube.com/embed/geQP_zYS-6s";
}
var count = 2;
function addVideo(){
 count++;
 var x = document.createElement("button");
 var t = document.createTextNode("Play Video "+count);

 urlbox.style.display = "inline";
 addVid.style.display = "inline";

 var URL = "https://www.youtube.com/watch?v=-txW8dPStfs";

 x.appendChild(t);
 x.style.cssText ='margin: 1px;width: 300px;height: 40px;'

 x.setAttribute('onclick','myFunction('+"'"+URL+"'"+')');

 var div = document.getElementById('something');
 div.appendChild(x);
}
 function myFunction(url) {
       btnVid.src = url;
 }
//"https://www.youtube.com/embed/-txW8dPStfs" <-- GOOD
//"https://www.youtube.com/watch?v=-txW8dPStfs"; <--BAD