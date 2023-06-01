# Gawariet-Gawariet
A project in Java that allows chatting between users.

The system consists of two components: server and client.\
Each user has his own login and password. In order to send a message to another user, the client should connect to the server by sending login and password.\
The server checks this password...\
A user send a package of data containing the login of the user to whom he/she wants to send the message and the content of the message.\
Then the server sends a message to the second user (who can reply) and if the second user is not logged, the first one should be informed about it.\
Additionally:

There is a friend functionality - if Adam wants to chat with Beata, they first need to become friends.\
The client can download a list of friends and information about whether they are currently logged.
