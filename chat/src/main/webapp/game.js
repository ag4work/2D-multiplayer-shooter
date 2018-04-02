/**
 * Created by agordeev on 02.04.2018.
 */
var Game = {};
Game.init = function(canvasId) {
    var direction;
    var leftPressed = 0;
    var rightPressed = 0;
    function myrect(ctx, x, y, width, height) {
        ctx.beginPath();
        ctx.fillStyle = null;
        ctx.rect(x, y, width, height);
        ctx.fill();
        ctx.closePath();
    }

    function keyUpHandler(e) {
        if (e.keyCode == '37') {
            console.log("left key up");
            leftPressed = 0;
        }
        else if (e.keyCode == '39') {
            console.log("right key up");
            rightPressed = 0;
        }
    }

    function keyDownHandler(e) {
        if (e.keyCode == '37' && !leftPressed) {
            console.log("left key down");
            Game.sendMessage("left");
            leftPressed = 1;
        }
        else if (e.keyCode == '39' && !rightPressed) {
            console.log("right key down");
            Game.sendMessage("right");
            rightPressed = 1;
        }
    }


    function addKeyboardListners() {
        window.addEventListener('keydown', keyDownHandler, false);
        window.addEventListener('keyup', keyUpHandler, false);
    }

    Game.socket = {};
    Game.connect = function(host) {
        Game.socket = new WebSocket(host);
        Game.socket.onopen = function() {
            console.log('Info: WebSocket connection opened.');
            Game.sendMessage("Hello from frontend");
        };
        Game.socket.onmessage = function (message) {
            console.log(message.data);
        };
        Game.socket.onclose = function () {
            console.log('Info: WebSocket closed.');
        };


    };
    Game.sendMessage = function(message) {
        console.log("senging message:" + message);
        Game.socket.send(message);
    };


    var canvas = document.getElementById(canvasId);
    var ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    myrect(ctx,0, 0, canvas.width, canvas.height);
    Game.connect('ws://' + window.location.host + window.location.pathname + '/websocket/endpoint');
    addKeyboardListners();
};


