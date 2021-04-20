Here is an experiments with websockets.

This is supposed to be a base for online realtime multi-player game.
The current logic is the following.
You connect to server:
Server tells you that another player is needed.
After the second player connected the "game" is started.

Each of two player move left and right (arrow keys) and shoot (spacebar).
These commands change player's state (it's X-coord). After that new game state (players coords) are
sent to each players frontend.

There is currently third players (AI) included. It moves only, does not shoot yet.

Application configured with embedded Tomcat so you can run it simply:
1. Built:
mvn clean install

2. Run application:
mvn tomcat7:run

3. Open in browser a couple of tabs with this address:
http://localhost:9090/
