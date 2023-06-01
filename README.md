# Gawariet-Gawariet
A project in Java that allows chatting between users. \

The system consists of two components: server and client. \
Each user has his own login and password. In order to send a message to another user, the client should:\

Connect to the server by sending login and password. \
The server checks this password...\
Send a data package containing the login of the user to whom you want to send the message and the content of the message.\
The server now sends a message to the second user (who can reply) if the second user is not logged in first he is informed.\
Additionally:\

There is a friend functionality - for Adam to be able to talk to Beata, they first need to become friends.\
The client can download a list of his friends and information about whether they are currently logged in from the server.
