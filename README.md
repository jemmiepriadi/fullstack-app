# fullstack-app-be

Here, we will use MariaDB/MySQL using XAMPP. To run this project, the JDK that is used is openjdk19, and configure the run with spring-boot:run. 
First before running the service, highly advised to create a database named fullstack-app so that the backend can migrate the entities. And then it is advised to create a user account via:
http://localhost:8080/api/v1/register with request body email and password. See following example of the request body:
<img width="875" alt="Screenshot 2023-04-07 at 17 23 53" src="https://user-images.githubusercontent.com/73422524/230592783-1aabbaa2-cc42-4ce7-aaf1-f59c027c3b39.png">
After that the user need to login in order to access and request all other services, for they will need jwt token to access. After logged In via http://localhost:8080/api/v1/login with similiar request body, the user can request other services:
http://localhost:8080/api/v1/joblist
http://localhost:8080/api/v1/jobList?description=stuff&location=stuff (and other request params)
http://localhost:8080/api/v1/jobList/{id} -> detail of job
