## Seminar 01 Tasks 
**Task 01** Open IDE. Main IDE for this course is Netbeans 8.1 This IDE is installed in B130 and accessible through modules. Use the following commands to run IDE (its good to firstly delete temporal directories ~/.netbeans and ~/.personal_domain). Create hello world Java application and run it using the IDE.
```
  module add netbeans-8.1-loc
  netbeans &
```
Note: Consider putting the `module add` command into your `~/.bashrc`, as the NetBeans module adds Maven on your `$PATH`. You can skip this step, but then remember to execute `module add netbeans-8.1-loc` in each terminal you open as all the following tasks require Maven.

**Task 02** Find out which version of Maven is installed on your machine using `which mvn`. You MUST use terminal where you added the module from Task 01. Notes: your local maven repo is in `/tmp/maven-repo-$LOGIN`.

**Task 03** Create hello world Java application using Maven from command line. Use "archetype" plugin and goal "generate". Run this application using Maven "exec" plugin and the "java" goal and it will print 'Hello World!'. Don't forget to compile the project first. Then modify the application by using only the command line to print text 'Hello PA165'. Run this application from command line again.

**Task 04** Create an acount on [GitHub](https://github.com/). Create a repository there. Import the hello world application from Task 03 to this repository. If you are new to `git` or GitHub, you can use this [hint](http://readwrite.com/2013/09/30/understanding-github-a-journey-for-beginners-part-1).

**Task 05** Checkout git branch seminar01 from https://github.com/fi-muni/PA165 (use `git clone` and then `git checkout`). Use https git URL. If you have ssh key-pair generated on your machine and set it on GitHub, you could use the ssh URL for cloning as well. This branch contains 2 projects. hello-java7 and hello-tom-web. 
```
cd
git clone https://github.com/fi-muni/PA165
cd PA165
git checkout seminar01
```

**Task 06** Open a new terminal and put Java 1.7 on your classpath using `module add jdk-1.7.0_75`. Now try to compile the hello-java7 project using `mvn clean compile`. It will fail, why? After you find out why it is failing fix it by changing configuration of the compiler plugin in the `pom.xml`. You must configure the plugin to compile with target and source version 1.7

Note: Use the project 'hello-tom-web' for the following two tasks.

**Task 07** Your next task is to configure and use embedded Tomcat7 plugin. Firstly configure tomcat7 maven plugin in pom.xml. After the plugin is configured, package the application using `mvn package`. The tomcat7 plugin has goal `run`. Use it to run the web application from this project. After the web app is started you can use web browser to test it works, it will be deployed on context '/my-webapp'. 

**Task 08** Your next task is to change the context to `my-different-context` and test it out using the web browser again. Hints: Use documentation of tomcat7 plugin. Also make sure some other Tomcat is not running e.g. the one in IDE or from some other process "ps -ef | grep tomcat". If you find out there is running tomcat of some other user logged into the machine the only solution is hard restart (which is not allowed in B130) or you have to switch computers. Please always report such Tomcats to CVT: send an e-mail to unix@fi.muni.cz with your machine name and ask them to kill the Tomcat on the machine.

