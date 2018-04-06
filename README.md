# AddNumbersService
This repository contains a Spring Boot project that allows a user to make API calls to the server to either request a question or send an original question sent by the server along with the answer to validate if the answer is correct. 

The default port that the web server is running from is port 9000. However, this can be modified. To modify the port number, go to the application.properties file and change the server.port value to another port number. For example: server.port=8080

To run the jar file, there is a script called runServices.bat under the /extra-resources/scripts directory that can be executed. Ensure that the add-numbers-service.jar file and the application properties are in the same location as the batch file.

To use the APIs, there are two endpoints that can be accessed by making HTTP requests. The two endpoints are described below:

1. To retrieve a new question from the server, make a GET request to this url: http://<HOSTNAME>:9000/question. Ensure that the HOSTNAME is replaced with the correct hostname or IP address. 
  
2. To answer the question sent by the server, make a POST request to this url: http://<HOSTNAME>:9000/answer. A JSON data with the original question sent by the server and the user's answer will need to be sent with the POST request. The JSON data expected by the server looks similar to this: 
	
	{ "question" : "Please sum the numbers 7,3,3", "answer" : 13 }
	
If the answer is correct, the server sends a response status code of 200 and the user receives a confirmation that the answer is correct. A message similar to below is sent by the server if the answer sent is correct:

	Question: Please sum the numbers 7,3,3
	Your answer: 13
	Result: Your answer is correct!
	
If the answer is incorrect, the server sends a response status code of 400 along with a message notifying the user that the answer is incorrect and to try again.

	Question: Please sum the numbers 7,3,3
	Your answer: 15
	Result: Your answer is incorrect. Please try again.
	
Any other invalid requests, such as incorrectly formatted JSON data, will result in a server response status code of 400 and a message similar to the one below:

	Invalid request. Please try again.
  
