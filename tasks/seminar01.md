## Seminar 01 Tasks 
**Task 01** Open IDE. Main IDE for this course is Netbeans 7.3.1. This IDE is installed in B130 and accessible through modules. Use the following commands to run IDE (its good to firstly delete temporal directories ~/.netbeans and ~/.personal_domain . Create hello world Java application and run it using the IDE.
```
  module add netbeans-7.3.1-loc
  netbeans &
```
Note: its a good idea to put the module add command into your ~/.bashrc

**Task 02** Find out which version of Maven is installed on your machine. You MUST use terminal where you added the module from Task 01. Notes: Maven was covered on the lecture. The module from Task 01 adds the Maven on your system path. This means that you must add module from Task 01 in EVERY terminal that you will be using for running netbeans or for running any Maven commands. Other notes: your local maven repo is in /tmp/maven-repo-$LOGIN.

**Task 03** Create hello world Java application using Maven from command line. Use "archetype" plugin and goal "generate". Run this application and it will print 'Hello World!'. Then modify the application by using only the command line to print text 'Hello PA165'. Run this application from commandline using Maven exec plugin goal java. Note that you must firstly compile the source codes for 'java' goal to work.

**Task 04** Create an acount on https://github.com/. Create a repository there. Import the hello world application from Task 03 to this repository. Hints: http://readwrite.com/2013/09/30/understanding-github-a-journey-for-beginners-part-1

**Task 05** Tomcat is installed in /usr/local/share/Modules/netbeans-7.3.1/apache-tomcat-7.0.34 Work from IDE. Add Apache Tomcat to your servers in IDE (Servers section in Services tab). You must create a user there (e.g. admin/admin). Do not forget to set Catalina base directory to writable, empty and existing directory on your local disk e.g. /tmp/tomcatworkdir. Start and stop your tomcat from services tab. it should start without any exceptions. You should see output such as this:
```
  NFO: Starting ProtocolHandler ["ajp-bio-8009"]
  Sep 21, 2015 4:52:27 PM org.apache.catalina.startup.Catalina start
  INFO: Server startup in 393 ms
```

**Task 06** Create a new hello world web application from IDE (not related to the previous console application that you have created). Then deploy it to your Tomcat in IDE.

**Task 07** Clone git branch seminar01 from https://github.com/fi-muni/PA165 (use git clone and then git checkout). Use https git URL. This branch contains 2 projects. hello-java7 and hello-tom-web. Do not forget to checkout the branch seminar01 !
```
cd
git clone https://github.com/fi-muni/PA165
cd PA165
git checkout seminar01
```

**Task 08** Open a new terminal and put Java 1.7 on your classpath using 'module add jdk-1.7.0_67 ' now try to compile hello-java7. It will fail, why? After you find out why it is failing fix it by changing configuration of pom.xml. You must configure compiler plugin to compile with target and source version 1.7

**Task 09** Now your task is to configure and use embedded Tomcat7 plugin. You must firstly configure tomcat7 maven plugin in pom.xml. It has goal run, to run. Use it to run web Java application from Task 07 using command line. After the plugin is configured, you must package the app using Maven before using tomcat7 plugin. After the web app is started you can use web browser to test it works, it will be deployed on context '/my-webapp'. Your next task is to change this context to my-different-context and test it out using the web browser again.Hints: Use documentation of tomcat7 plugin. Also make sure some other Tomcat is not running e.g. the one in IDE or from some other process "ps -ef | grep tomcat". If you find out there is running tomcat of some other user logged into the machine the only solution is hard restart (which is not allowed in B130) or you have to switch computers. Please always report such Tomcats to CVT: send an e-mail to unix@fi.muni.cz with your machine name and ask them to kill the tomcat on the machine.

