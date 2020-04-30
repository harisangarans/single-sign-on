# single-sign-on with single logout
A single sign on project using Spring boot,Spring Security and Spring LDAP
This is a Java Maven multimodule application containing: 

* An **authorization server** module that acts also as a **resource server**
* A  **client application-1** module that uses the authorization server for authentication, also for getting access to a resource on the resource server.This application list of all users in the embedded-ldap
* A  **client application-2** module that uses the authorization server for authentication, also for getting access to a resource on the resource server.This application contains a form that creates new users which can be used to log in into the application
* **Spring embedded ldap** is used to save user detsails
## How to test
* Authurization server runs at http://localhost:8080/auth/ -

* Using a browser go to: http://localhost:8081/client/ - This will authenticate the current user against
the authorization server. The username/password is hari/1.

* Using a browser go to: http://localhost:8082/client/ - This will authenticate the current user against
the authorization server. The username/password is hari/1.




