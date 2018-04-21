Here is an experiments with websockets.

This is supposed to be a base for online realtime multi-player game.
The current logic is the following.
You connect to server:
Server tells you that another player is needed.
After the second player connected the "game" is started.

Each of two player can press "left" and "right" buttons and it will be send to the server.
These commands change player's state (it's X-coord). After that new game state (players coords) are
sent to each players frontend.

There is currently third players (PC) included.