## Installation Guide

### For Backend

#### (1). Installing the database

    1. Prepare an operating system - Ubuntu 18

    2. Using command:  sudo apt update; sudo apt install mysql-server

    3. Using command:  mysql -u root;

    4. Running command in mysql:  create user onpro@'%' on onpro. * identified by "root9900";

    5. Running command in mysql:  grant all privilege to onpro@'%';

    6. Login MySQL with user onpro, running sql script "create_db.sql"

    7. Login MySQL with user onpro, running sql script "create_table.sql"


#### (2) Running the backend

    1. In the backend project folder (which contains pom.xml) using console command:  man clean package, it will generate a package:  backend-0.0.1-snapshot.war

    2. Prepare an operating system - Ubuntu 18

    3. Using Linux command: sudo install default-jre

    4. Download tomcat8 from below link and unpack it to directory: /home/ubuntu/tomcat/apache-tomcat
    Link:  https://tomcat.apache.org/download-80.cgi

    5. Using scp software to upload backend-0.0.1-snapshot.war to Ubuntu path:     /home/ubuntu/tomcat

    6. Using command in Linux:  mv /home/ubuntu/tomcat/backend-0.0.1-snap.war
       /home/ubuntu/tomcat/apache-tomcat/webapps/api.war

    7. Go to directory:  /home/ubuntu/tomcat/apache-tomcat/bin,  running script ". /startup.sh"

### For Frontend

#### (1)	Running the frontend in development environment

    1. Download node v14.13.1 from below link and install
       Link: https://nodejs.org/en/download/current/
    
    2. Install Angular by below command:
    npm install -g @angular/cli
    
    3. Open command/terminal, go to folder OLP/frontend and type "npm install"
    
    4. Open it by Java IDE Intellij or Eclipse - The project root folder is OLP
    
    5. Install resource web server by downloading Apache Tomcat from the following link 
    
    https://tomcat.apache.org/download-90.cgi
    
    6. Create a resources folder if it doesn’t exist in Tomcat installation folder “./apache-tomcat-9.0.39/webapps”
    
    7. Update the environment.ts if it points to different backend server instead of the localhost

    baseEndpoint: 
    It defines the API backend endpoint. By default is http://localhost:8080/api.
    
    baseResourceEndpoint: 
    It defines the resource web server which stores document and photo. It is set to http://localhost:8080 by default.
    
    8. To start frontend, go to OLP/frontend folder in command/terminal and type "ng serve"
    
    9. Once started, the URL http://localhost:4200/ would be accessible
    
    10. Backend can be started in IDE as spring boot application as usual
    
#### (2) Running the frontend in production environment
    
    1. Setup a development environment by above steps 1-3
    
    2. Update the environment.prod.ts to the production backend server

    baseEndpoint: 
    It defines the API backend endpoint. By default it is http://localhost:8080/api.
    
    baseResourceEndpoint: 
    It defines the resource web server which stores document and photo. 
    It is set to http://localhost:8080 by default.
    
    3. Go to folder OLP/frontend and run the following command to compile the front end
    ng build --prod --base-href /olp/
    
    4. Create “olp” folder under ./apache-tomcat-9.0.39/webapps in prod server
    
    5. Copy the files from ./OLP/frontend/dist/frontend to the “olp” folder created in step 4
    
    6. Start up the tomcat in production server by running command ./catalina.sh start
    
    7. Once started, the URL http://localhost:8080/olp would be accessible in production server

