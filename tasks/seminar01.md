## Seminar 01 Tasks 
**Task 01** Open IDE. Main IDE for this course is Netbeans 7.3.1. This IDE is installed in B130 and accessible through modules. Use the following commands to run IDE (its good to firstly delete temporal directories ~/.netbeans and ~/.personal_domain . Create hello world Java application and run it using the IDE.
```
  module add netbeans-7.3.1-loc
  netbeans &
```

**Task 02** Find out which version of Maven is installed on your machine. You MUST use terminal where you added the module from Task 01. Notes: Maven was covered on the lecture. The module from Task 01 adds the Maven on your system path. This means that you must add module from Task 01 in EVERY terminal that you will be using for running netbeans or for running any Maven commands. Other notes: your local maven repo is in /tmp/maven-repo-$LOGIN.

**Task 03** Create hello world Java application using Maven from command line. Use "archetype" plugin and goal "generate". Then modify the printed text (from command line) to Hello PA165. Run this application from commandline using Maven exec plugin goal java

**Task 04** Create an acount on https://github.com/. Create a repository there. Import the hello world application from Task 03 to this repository. Hints: http://readwrite.com/2013/09/30/understanding-github-a-journey-for-beginners-part-1

**Task 05** Tomcat is installed in /usr/local/share/Modules/netbeans-7.3.1/apache-tomcat-7.0.34 Work from IDE. Add Apache Tomcat to your servers in IDE (Servers section in Services tab). You must create a user there (e.g. admin/admin). Do not forget to set Catalina base directory to writable, empty and existing directory on your local disk e.g. /tmp/tomcatworkdir. Start and stop your tomcat from services tab. it should start without any exceptions.

**Task 06** Create a hello world web application from IDE. Then deploy it to your Tomcat in IDE.

**Task 07** Clone git branch TODO. This branch contains 2 projects. hello-java7 and hello-tom-web. The first one is not buildable (mvn compile should fail) because it contains  code compliant only with Java 7. Your task is to modify pom.xml so that compiler plugin uses target version of Java 1.7. 

**Task 08** Now your task is to use embedded (through tomcat7 maven plugin. It has goal run) to run web Java application from Task 07 using command line and maven. Firstly you must package the app using Maven and then use tomcat7 plugin. After the web app is started you can use web browser to test it works. Hints: Use documentation of tomcat7 plugin. Also make sure some other Tomcat is not running e.g. the one in IDE or from some other process "ps -ef | grep tomcat". If you find out there is running tomcat of some other user logged into the machine the only solution is hard restart (which is not allowed in B130) or you have to switch computers. Please always report such Tomcats to CVT: send an e-mail to unix@fi.muni.cz with your machine name and ask them to kill the tomcat on the machine.

