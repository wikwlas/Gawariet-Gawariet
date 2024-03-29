<h1>Java Chat Application with Authentication and Messaging</h1>

This project entails the development of a Java-based chat application, encompassing two primary components: a server and a client. The objective is to facilitate seamless communication between users, necessitating authentication for access.
<h2>Components:</h2>
<h3>Server:</h3>
Responsible for managing user authentication and message routing.
<h3>Client:</h3>
Allows users to connect to the server using their login credentials and engage in conversations.
<h3>User Authentication:</h3>
Each user is required to have a unique login and password to access the system.

To initiate a conversation, the client must establish a connection to the server by providing their login and password for authentication.

The server validates the provided credentials before permitting message transmission.
<h3>Messaging:</h3>
Users can send a package of data containing the recipient's login and the message's content.

The server then relays the message to the intended recipient, allowing for replies.

If the intended recipient is not logged in, the server informs the sender accordingly.
<h2>Additional Features:</h2>
<h3>Friend Functionality:</h3>

Prior to engaging in a conversation, users must establish a friendship.\
For example, Adam must establish a friendship with Beata to initiate a chat.
<h3>Friend List:</h3>

The client can retrieve a list of friends, along with their online status, to facilitate streamlined communication.
