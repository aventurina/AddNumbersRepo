# AddNumbersService
This repository contains a Spring Boot project that allows a user to make API calls to the server to either request a question or send an original question sent by the server along with the answer to validate if the answer is correct. 


How to use the APIs

The two API endpoints are described below:

1. To retrieve a new question from the server, make a GET request to this url: http://<HOSTNAME>:9000/question. Ensure that the HOSTNAME is replaced with the correct hostname or IP address. 
  
2. To answer the question sent by the server, make a POST request to this url: http://<HOSTNAME>:9000/answer. A JSON data with the original question sent by the server and the user's answer will need to be sent with the POST request. The JSON data expected by the server looks like similar to this:
  
{
  "question" : "Please sum the numbers 7,3,3",
	"answer" : 13
}
  
