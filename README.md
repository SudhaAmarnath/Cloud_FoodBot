# Food Bolt
Food Bolt is a single window platform for Table Reservation and Pre-ordering foods in our favourite restaurants.





CMPE 281 CLOUD TECHNOLOGIES

PROJECT - II



FOODBOLT




TEAM MEMBERS

MUKESH RANJAN SAHAY
MUTHU KUMAR SUKUMARAN
SUDHA AMARNATH
THIRUMALAI NAMBI DOSS 






ABSTRACT

Dining-out in our favorite restaurant with our beloved ones is one of the main recreations for many people. In order to spend some quality time with our family, friends or business partners during dining, it is necessary to be sure about the slot at the restaurant for the number of people to dine in. There are very minimal online services which offer table booking for multiple restaurants around the locality. Food Bolt application is a table reservation platform which provides seamless access for both users and restaurant owners to reserve tables. 

One-Click IoT device enhances the user experience in table reservation which gives an edge over our competitors. With extensive use of cloud resource we have optimized the cost of development and operation without compromising the quality of reservation service. With the optimized cost we are bringing the IoT device to customers at free of cost and enhancing added value to our service.
	










ARCHITECTURE DIAGRAM











TECHNOLOGIES USED

1. FrontEnd 
HTML 
JavaScript 
CSS 
BootStrap
Thymeleaf
2. Backend
Java
Spring Boot Framework
3. Database
RDS (MySQL) - Used as the DB for the application for storing the major data.
DynamoDB - For storing other data.
4. AWS Resources
Route53 - DNS to resolves the IP address for the application domain.
ELB - Elastic Load Balancer is used to handle the load and route it to different EC2 instances.
EC2 - For hosting the Web and Application server.
Auto Scaling - Used to support the highly available and scalable web application.
Lambda - Used the lambda function for handling the IOT 1-click button and sending the notification to the user for booking confirmation. Also to upload the logs to Cloud Watch.
SES - Simple Email Service is used to send email on RDS update.
SNS - Simple Notification Service is used to send email on EC2 health change.
DynamoDB
Certificate Manager - Generating the SSL certificate for implementing HTTPS.
IOT Core: For connecting the 1-click device to the cloud.
IOT 1-Click: For triggering the Lambda functions from the IOT device.
S3 - Used to store the build from Jenkins.
5. Source Code Management
GitHub
7. CI/CD
Jenkins
Code Pipeline - Used to deploy the changes to the system
Code Build: Generating the build output.
Code Commit: This is used as the version control and code repository (code is also uploaded to the GitHub)
Code Deploy: Deploys the code to the server.

8. Local configuration - Required tools to be installed
Install JAVA JDK 1.8+
Install Apache Maven

9. How to set up and run project locally?
Clone the repository from github : https://github.com/ptndoss/Cloud_FoodBot.git
Navigate to the directory where pom.xml is located
Run the command “mvn spring-boot:run”
Open the webpage http://localhost:8080













FEATURES
High Availability
	The best approach to build a highly available system is to use the Multiple Availability Zones (AZ) in Amazon Web Services by setting up a failover cluster for the database which is inherently supported by Amazon RDS Multi-AZ deployment. Thus, the Multi-AZ deployment ensures maximum availability. Therefore, the system can be avoided from a “Single Point of Failure”.

HIGH AVAILABILITY

Fault Tolerant
	A web application consists of three tiers namely web, application and database. The Web tier to resistant to fault tolerance by the AWS service of Elastic Load Balancing. Since, the Load Balancer redirects the web traffic to healthy Amazon EC2 instances for more consistent application performance. The Application tier is resistant to fault tolerance through Auto-Scaling which monitors the application enables scaling of EC2 instances based upon end user’s usage of the application. The Database tier is resistant to fault tolerance by maintaining a standby database in the other zone and a read replica in the primary zone.

WEB TIER


APPLICATION TIER



DATABASE TIER








Security
	The AWS resources are placed in a Virtual Private Cloud with additional AWS security components like Internet Gateway which allows for the communication of the AWS resources in a subnet to the Internet. The Route table contains the list of inbound and outbound rules to allow communication to specific ports. The EC2 instances are connected to the Security Group with Internet Gateway making it a Subnet whereas the Internet Gateway is not attached to the Security Group of the RDS to make it a Private Subnet.

SECURED DATABASE

Disaster Recovery
RDS has been enabled in Multiple Availability Zones in order to make the application highly available and also available during disaster. In addition to Multi-AZ deployment is enabled, the application archives the backup for one week. Also, frequent RDS snapshots are taken to rebuilt in case of any failures.
		Master and Read Replica of FoodBolt RDS

			Backup of RDS 

Multi-AZ Deployment - enabled


Cost Optimization
	With the extensive use of AWS resources we have optimized the cost  in every phase of development lifecycle. With pay-as-you go model operational cost has been reduced drastically than the traditional model. With the help of Auto-scaling we make sure we are utilizing only the resources necessary for the load our application is handling at any point of time with this approach we are reducing the cost further and providing more valuable service to our customers.

Easier Reservation
	The end user of the application can reserve the table in a restaurant with a AWS IOT through a single button click. This is possible as the registered user can set his/her preferences in the user profile section where there are fields like Preferred Restaurant, Preferred Booking Time and Preferred Number of Guests. A Lambda function is triggered on IOT button click to fetch these preferred settings from the database and make a reservation in the database. This is will be followed by the user getting a Email notification from AWS SES service.

AWS Thing




AWS IOT


AWS Core -> Monitor



Role Based Access
	The end user can access the application by two roles namely User role and Service Provider role. A user is given the access to view the list of restaurants registered under the application, book the desired restaurant, cancel the reservation and view the history of booking made under the user profile. The Service Provider has register the restaurant under the application, cancel the reservations made in the restaurant and view the panel of bookings made in the restaurant.


User Home Page -> With list of all the registered restaurants


User Profile Page


User Profile Page -> Edit

User -> Reserve Table


Service Provider -> Home Page



Connect Using Social Login
	The user can register using social logins like Facebook and Google. This allows the user to sign into a application instead of creating a new login account specifically for this application. This also serves as a way for faster registration. The Service Provider is restricted from using Social Login since the Service Provider needs to enter specific details regarding the restaurant at the time of Signup.

Security
	The application is hosted on a HTTPS protocol to provide a more secure way to send data. The SSL Certificate is generated from AWS Certificate Manager and applied to Elastic Load Balancer.





CONTINUOUS INTEGRATION & CONTINUOUS DEPLOYMENT
Continuous integration focuses on integrating work from individual developers into a main repository multiple times a day to catch integration bugs early and accelerate collaborative development. Continuous delivery and deployment is concerned with reducing friction in the deployment or release process, automating the steps required to deploy a build so that code can be released safely at any time and by automatically deploying each time a code change is made.
We followed the CI/CD process throughout our development and deployment phase in order to get the benefits of these well proven processes. We used GitHub for code control and versioning. AWS CodePipeline ispolling for any changes in the GitHub repository and whenever the code is checked-in to the GitHub, CodePipeline gets the notification and the code is copied to to the S3 bucket. The the code is then pushed to the Jenkins builder which compiles the code and pushes the build output to the S3. After that, the AWS CodePipeline deploys the code to the EC2 instances using the Code Deploy to the development environment and once the build is promoted it is deployed on the production environment..

INDIVIDUAL CONTRIBUTION
Mukesh Ranjan Sahay
Created REST API for User access of the application and tested it using Postman.
Handled the Database schema and tables for User entity in the application.
Configured AWS IOT device for making the table booking for the user based on his pre-configured preferences.
Created Lambda function to retrieve the user data from database and make a table reservation in the database.
Set up the initial configurations of Jenkins and configured it for build.
Muthu Kumar Sukumaran
Created the front end views of the User interface.
Validated the forms and made server calls for displaying dynamic data for the User part.
Generated SSL Certificate from AWS Certificate Manager for hosting the website as a HTTPS protocol.
Configured EC2 instance and created an AMI of EC2 for AutoScaling group and configured the Auto Scaling group.
Thirumalai Nambi Doss
Created REST API for Service Provider access of the application and tested it using Postman.
Handled the Database schema and tables for Service Provider entity in the application.
Configured a highly secure RDS instance in a private subnet connected it to EC2 and enabled Read Replica and Multi-AZ deployments.
Configured Amazon Lex and created lambda function for user validation
Sudha Amarnath
Created the front end views of the Service Provider part of the application.
Configured Route53 and Elastic Load Balancer.
Built CI/CD Pipeline using Code Star, Code Commit, Code Deploy and Code Pipeline.
Validated the forms and made server calls for displaying dynamic data for the Service Provider part.

PUBLIC URL
https://msoncloud.com
	The domain name is registered under Amazon Route53 for the domain name to be highly available and scalable. The Route53 configures DNS health checks to route traffic to the healthy endpoints and also to independently monitor the health of the application.

TEST ACCOUNT CREDENTIALS
User
	Email: test1@gmail.com
	Password: test

Service Provider
	Email: test2@gmail.com
	Password: test

GITHUB URL
https://github.com/ptndoss/Cloud_FoodBot.git


AWS RESOURCES SCREENSHOTS
EC2

ELB


AutoScaling





SNS Notifications

Route 53










Certificate Manager



CI/CD
Jenkins





CodeStar -> Code Commit, Code Build, Code Deploy


Code Pipeline -> Dev Environment







Code Pipeline -> Production


S3 -> Content from GitHub







S3 -> After Jenkins upload



CloudWatch








APPLICATION SCREENSHOTS
Welcome Page


User Signup


User Login 



Service Provider -> Options to the Signup/Login



Service Provider -> Signup


Service Provider -> Sign In

