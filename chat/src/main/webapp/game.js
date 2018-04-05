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
//            console.log("left key up");
            leftPressed = 0;
        }
        else if (e.keyCode == '39') {
//            console.log("right key up");
            rightPressed = 0;
        }
    }

    function keyDownHandler(e) {
        if (e.keyCode == '37' && !leftPressed) {
//            console.log("left key down");
            Game.sendMessage("left");
            leftPressed = 1;
        }
        else if (e.keyCode == '39' && !rightPressed) {
//            console.log("right key down");
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
            Console.log('Info: WebSocket connection opened.');
            Game.sendMessage("Hello from frontend");
        };
        Game.socket.onmessage = function (message) {
            Console.log(message.data);
        };
        Game.socket.onclose = function () {
            Console.log('Info: WebSocket closed.');
        };
    };
    Game.sendMessage = function(message) {
        Console.log("senging message:" + message);
        Game.socket.send(message);
    };

    var Console = {};
    Console.log = (function(message) {
        var console = document.getElementById('console');
        var p = document.createElement('p');
        p.style.wordWrap = 'break-word';
        p.innerHTML = message;
        console.appendChild(p);
        while (console.childNodes.length > 25) {
            console.removeChild(console.firstChild);
        }
        console.scrollTop = console.scrollHeight;
    });


    var canvas = document.getElementById(canvasId);
    var ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    myrect(ctx,0, 0, canvas.width, canvas.height);
    Game.connect('ws://' + window.location.host + window.location.pathname + '/websocket/endpoint');
    addKeyboardListners();
};


