const url = 'http://localhost:8080';
let stompClient;
let selectedUser;
let newMessages = new Map();
let $chatHistory;
let $button;
let $textarea;
let $chatHistoryList;

//This will be removed
function registration() {
    let userName = document.getElementById("userName").value;
    if(userName.length != 0 ){
        var button = document.getElementById('RegBTN');
        hideButton(button);
        $.get(url + "/registration/" + userName, function (response) {
            connectToChat(userName);
        }).fail(function (error) {
            if (error.status === 400) {
                alert("Login is already busy!")
            }
        })
    }
}
//This will be removed
function hideButton(x)
 {
  x.style.display = 'none';
 }
//This will be removed
function fetchAll() {
    $.get(url + "/fetchAllUsers", function (response) {
        let users = response;
        let usersTemplateHTML = "";
        for (let i = 0; i < users.length; i++) {
            usersTemplateHTML = usersTemplateHTML + '<a href="#" onclick="selectUser(\'' + users[i] + '\')"><li class="clearfix">\n' +
                '                <div class="about">\n' +
                '                    <div id="userNameAppender_' + users[i] + '" class="name">' + users[i] + '</div>\n' +
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
                $('#userNameAppender_' + data.fromLogin).append('<span id="newMessage_' + data.fromLogin + '" style="color: red">+1</span>');
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
    let isNew = document.getElementById("newMessage_" + userName) !== null;
    if (isNew) {
        let element = document.getElementById("newMessage_" + userName);
        element.parentNode.removeChild(element);
        render(newMessages.get(userName), userName);
    }
    $('#selectedUserId').html('');
    $('#selectedUserId').append('Chat with ' + userName);
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

        //the autoResponse
        setTimeout(autoResponses, 1500);
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
function autoResponses(){
var template = Handlebars.compile($("#message-response-template").html());
var contextResponse = {
    response: getRandomItem(messageResponses),
    time: getCurrentTime(),
    userName: 'Doctor Smith'
    };

    $chatHistoryList.append(template(contextResponse));
    scrollToBottom();
    $textarea.val('');
}
const messageResponses = [
      'Hello this is doctor smith.',
      'How can I help you?'
    ];
var count = 0;
function getRandomItem(arr) {
    if(count >= arr.length){
        count = 0;
        count++;
        return arr[count-1];
    }else{
        count++;
        return arr[count-1];
        }
    }
init();