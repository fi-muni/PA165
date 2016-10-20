# Running things

### NetBeans says it's already running
* Make sure it's not really running - `ps aux | netbeans`
* Delete the file `~/.netbeans/lock`

### Tomcat looks like nothing has deployed or changed
* Make sure Tomcat is not running - `ps aux | grep java`
* Kill it with `kill -9 <pid>` where `<pid>` is the number of the listed tomcat process (if any)

### Maven build fails with "... invalid target 8..."
* Check if you have Java 8 installed and evt. registered in NetBeans - Toools -> Java Platforms

### Tips
* Read the tasks from the master branch, not from the branches of seminars.

# Seminar 04
