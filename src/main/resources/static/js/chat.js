const url = 'https://localhost';
let stompClient;
let selectedUser;
let newMessages = new Map();
let $chatHistory;
let $button;
let $textarea;
let $chatHistoryList;
//This will be reduced and possable removed
function registration() {
    let name = document.getElementById("USER").textContent;
    let userName = document.getElementById("userName").value;
    if (name.includes(userName)) {
        var button = document.getElementById('RegBTN');
        hideButton(button);
        document.getElementById("userName").style.display = 'none'
    	connectToChat(userName);
    	fetchAll();
    } else {
    	alert("Sorry username does not match");
    }
}
//This will be removed
function hideButton(x)
 {
  x.style.display = 'none';
 }
//This will be removed and replaced with controller
function fetchAll() {
    $.get(url + "/fetchAllUsers", function (response) {
        let users = response;
        let usersTemplateHTML = "";
        for (let i = 0; i < users.length; i++) {
            usersTemplateHTML = usersTemplateHTML + '<a href="#" onclick="selectUser(\'' + users[i]+ '\')"><li class="clearfix">\n' +
                '                <div class="about">\n' +
                '                    <div id="userNameAppender_' + users[i]+ '" class="name">' + users[i]+ '</div>\n' +
                '                    <div class="status">\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </li></a>';
        }
        $('#usersList').html(usersTemplateHTML);
    });
}

function connectToChat(userName) {
    console.log("connecting to chat...")
    let socket = new SockJS(url + '/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);
        stompClient.subscribe("/topic/messages/" + userName, function (response) {
            let data = JSON.parse(response.body);
            if (selectedUser === data.fromLogin) {
                render(data.message, data.fromLogin);
            } else {
                newMessages.set(data.fromLogin, data.message);
            }
        });
    });
}


function sendMsg(from, text) {
    stompClient.send("/app/chat/" + selectedUser, {}, JSON.stringify({
        fromLogin: from,
        message: text
    }));
}

function selectUser(userName) {
    console.log("selecting users: " + userName);
    selectedUser = userName;
    if(selectedUser.includes('NO APPOINTMENTS')){
            location.href = "/appointment/show";
            }
    let isNew = document.getElementById("newMessage_" + userName) !== null;
    if (isNew) {
        let element = document.getElementById("newMessage_" + userName);
        element.parentNode.removeChild(element);
        render(newMessages.get(userName), userName);
    }
    $('#selectedUserId').html('');
    $('#selectedUserId').append('Chat with ' + userName);
    $chatHistoryList.empty();
}

function init() {
    cacheDOM();
    bindEvents();
}

function bindEvents() {
    $button.on('click', addMessage.bind(this));
    $textarea.on('keyup', addMessageEnter.bind(this));
}

function cacheDOM() {
    $chatHistory = $('.chat-history');
    $button = $('#sendBtn');
    $textarea = $('#message-to-send');
    $chatHistoryList = $chatHistory.find('ul');
}

function render(message, userName) {
    scrollToBottom();
    // responses
    var templateResponse = Handlebars.compile($("#message-response-template").html());
    var contextResponse = {
        response: message,
        time: getCurrentTime(),
        userName: userName
    };

    setTimeout(function () {
        $chatHistoryList.append(templateResponse(contextResponse));
        scrollToBottom();
    }.bind(this), 1500);
}

function sendMessage(message) {
    let username = $('#userName').val();
    console.log(username)
    sendMsg(username, message);
    scrollToBottom();
    if (message.trim() !== '') {
        var template = Handlebars.compile($("#message-template").html());
        var context = {
            messageOutput: message,
            time: getCurrentTime(),
            toUserName: selectedUser
        };
        $chatHistoryList.append(template(context));
        scrollToBottom();
        $textarea.val('');
        if(selectedUser.includes('Doctor Smith')){
        //the autoResponse
            setTimeout(autoResponsesDoc, 1500);
        }
        else if(selectedUser.includes('Patient John')){
        //the autoResponse
            setTimeout(autoResponsesPat, 1500);
        }
    }
}

function scrollToBottom() {
    $chatHistory.scrollTop($chatHistory[0].scrollHeight);
}

function getCurrentTime() {
    return new Date().toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
}

function addMessage() {
    sendMessage($textarea.val());
}

function addMessageEnter(event) {
    // enter was pressed
    if (event.keyCode === 13) {
        addMessage();
    }
}
//auto stuff for the doc
function autoResponsesDoc(){
var template = Handlebars.compile($("#message-response-template").html());
var contextResponse = {
    response: getItemDoc(DocMessageResponses),
    time: getCurrentTime(),
    userName: 'Doctor Smith'
    };

    $chatHistoryList.append(template(contextResponse));
    scrollToBottom();
    $textarea.val('');
}
const DocMessageResponses = [
      'Hello this is doctor smith. How can I help you?',
      'You can either e-mail them to me or upload them to in the records tab.',
      'Is there anything else?',
      'Your welcome.',
      'You too.'
    ];
var countDoc = 0;
function getItemDoc(arr) {
    if(countDoc >= arr.length){
        countDoc = 0;
        countDoc++;
        return arr[countDoc-1];
    }else{
        countDoc++;
        return arr[countDoc-1];
        }
    }

//auto stuff for patient
function autoResponsesPat(){
var template = Handlebars.compile($("#message-response-template").html());
var contextResponse = {
    response: getItemPat(PatMessageResponses),
    time: getCurrentTime(),
    userName: 'Patient John'
    };

    $chatHistoryList.append(template(contextResponse));
    scrollToBottom();
    $textarea.val('');
}

const PatMessageResponses = [
      'Hey doc I need help',
      'Where do I submit my documents?',
      'ok thank you.'
    ];

var countPat = 0;
function getItemPat(arr) {
    if(countPat >= arr.length){
        countPat = 0;
        countPat++;
        return arr[countPat-1];
    }else{
        countPat++;
        return arr[countPat-1];
        }
    }
init();