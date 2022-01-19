**This seminar is an old version and has been deprecated: please use the project on GitLab:** https://gitlab.fi.muni.cz/pa165/seminar-01

## Seminar 01 Tasks 

**Task 01** Open IDE. Main IDE for this course is IntelliJ IDEA. This IDE is installed in B130 (or should be installed in your personal PC under school license (see below) in the case of virtual seminars) and accessible through modules. Use the following commands to run IDE. Create hello world Java application and run it using the IDE.
```
  module add idea
  idea.sh &
```
The IntelliJ IDEA needs a licence. If you don't already have one, just visit https://www.jetbrains.com/student/ and create a new JetBrains account with an email from the domain @mail.muni.cz, you will get a student licence for all JetBrains products for one year.
Note: its a good idea to put the `module add` command into your ~/.bashrc

**Task 02** Find out which version of Maven is installed on your machine using `which mvn`. There is a local installation of Maven on each machine. Notes: Maven was covered on the lecture. Your local maven repo is in `~/.m2`.

**Task 03** Create hello world Java application using Maven from command line. Use "archetype" [look at documentation](https://maven.apache.org/archetype/maven-archetype-plugin/usage.html) plugin and goal "generate". With every maven plugin, including the archetype, its beneficial to review [usage page of documentation](https://maven.apache.org/archetype/maven-archetype-plugin/usage.html#). Run this application from commandline using Maven exec plugin goal java [mvn exec:java](https://www.mojohaus.org/exec-maven-plugin/usage.html). Note that you must firstly compile the source code using `mvn compile`.

**Task 04** Create an acount on https://github.com/. Create a repository there. Import the hello world application from Task 03 to this repository. Hints: http://readwrite.com/2013/09/30/understanding-github-a-journey-for-beginners-part-1

**Task 05** Checkout git branch seminar01 from https://github.com/fi-muni/PA165 (use git clone and then git checkout). Use https git URL. This branch contains 2 projects. hello-java11 and hello-tom-web. 
```
cd
git clone https://github.com/fi-muni/PA165
cd PA165
git checkout seminar01
```

**Task 06** Try to compile hello-java11 `mvn clean compile`. It will fail, because the default configuration of maven is not to use source and target level of Java 11. To fix that we need to configure compiler plugin in pom.xml so that target and source versions of java are at least 11. [See documentation and configure your the pom.xml](https://maven.apache.org/plugins/maven-compiler-plugin/examples/set-compiler-source-and-target.html). After you successfully configure the pom.xml it should be able to compile the project with `mvn compile` 

**Task 07** Now your task is to configure embedded Tomcat 9 using Cargo plugin in `hello-tom-web` project. After its configured, you will be able to start the embedded Tomcat 9 and the application will be accessible through web browser. Import the project into your IDE and after that configure cargo-maven3-plugin with configuration: 

```
     <plugin>
        <groupId>org.codehaus.cargo</groupId>
        <artifactId>cargo-maven3-plugin</artifactId>
        <version>1.9.1</version>
        <configuration>
          <container>
            <containerId>tomcat9x</containerId>
            <artifactInstaller>
              <groupId>org.apache.tomcat</groupId>
              <artifactId>tomcat</artifactId>
              <version>${tomcat.version}</version>
            </artifactInstaller>
          </container>
        </configuration>
      </plugin>
```

After that use `mvn clean install cargo:run` go to [http://localhost:8080/my-webapp/hello](http://localhost:8080/my-webapp/hello) and you should see "Hello world!". Next task is to make additional configuration to cargo-maven3-plugin to publish the app on different context `/my-different-context`. This is the configuration you need to add: 
```
          <deployables>
            <deployable>
              <properties>
                <context>/my-different-context</context>
              </properties>
            </deployable>
          </deployables>

```
After you correctly configure and re-run the app, you will see the hello world on `http://localhost:8080/my-different-context/hello`
