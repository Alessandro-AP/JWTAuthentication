<div align="center">
  <h2>JWTAuthentication</h2>
  <p>
    JWTAuthenticatio Project is a school practical project that aims to extend our skills on web app development and team work.
  </p>
</div>


<!-- TABLE OF CONTENTS -->

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#license">License</a></li>
  </ol>
</details>


<!-- ABOUT THE PROJECT -->

## About The Project

This project is an API Rest that provides an authentication service using JWT tokens.


### Built With

* [Spring](https://spring.io/)
* [MySQL](https://www.mysql.com/)

<!-- GETTING STARTED -->

## Getting Started
### Prerequisites

You need the following components to use the projet, please make sure you have installed them :

* [Java 11](https://jdk.java.net/11/)
* [Maven 3.6.0](https://maven.apache.org/install.html)
* [Docker](https://docs.docker.com/get-docker/)

### Installation
1. Clone the repo.

```sh
git clone https://github.com/Alessandro-AP/JWTAuthentication.git
```

2. Open a terminal in the same location as the `pom.xml` and enter the command:
```sh
mvn clean install
```
This command will clean up the `target/` folder and perform the `install` cycle (validate, compile, test, package, verify) in order to create a .jar artefact in the `target/` folder.

3. In the same directory as before, enter the command: 
```sh
docker-compose up -d
```
The `docker-compose.yml` lets you bring up a complete docker development environment, it will install two main services:

**springboot-auth** : Spring Boot REST API which runs on port 8084.<br>
**mysql-auth** : MySQL DB which runs on port 3306

(Warning: make sure these ports are free, otherwise change them in the docker-compose.yml)
## Usage
Documentation swagger https://app.swaggerhub.com/apis-docs/gaetan.zwick/Authentication-MIcroservice/v1 

Once the application has been launched, 2 actions are possible:
**Register**: A user can register at the endpoint `/register` by sending an HTTP POST request containing a JSON body with username and password (as in the example)

![register](reamde_images/register.png)

**Login** : A user can login to the `/login` endpoint by sending an HTTP POST request containing a JSON body with username and password (as in the example)

![login](reamde_images/login.png)



<!-- CONTACT -->
## Contact
[Alessandro Parrino](https://github.com/Alessandro-AP) <br>
[Daniel Sciarra](https://github.com/DS-Daniel) <br>
[Marco Maziero](https://github.com/MazieroMarco) <br>
[Gaétan Zwick](https://github.com/Ga-3tan) <br>
[Anh Mai Hoang](https://github.com/MaIT-HgA) <br>

Project Link: [https://github.com/Alessandro-AP/JWTAuthentication](https://github.com/Alessandro-AP/JWTAuthentication)

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>
