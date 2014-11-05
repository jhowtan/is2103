# IS2103 AY 2014/15 Fall Semester - Enterprise Systems Development Concepts

# Requirements

Java SDK 1.7 or higher.

If installed with NetBeans 8.0, should contain all the necessary java files installed into the system.
This assignment was completed on NetBeans 8.0.1 with GlassFish 4.1, along with Java EE 7.

# Assignment 1: Module Search Server (RMI)

Refer to the instructions below to compile and run the server.

## Instructions for RMI:

Steps listed below are for running on Mac OS X.
There may be port conflicts with regards to the *tnameserv* service, make modifications to the code if necessary.

## Steps for RMI:

1. Compile all java classes in the modulesearchrmi directory.

     javac modulesearchrmi/*.java

2. Generate stub and skeleton:

     rmic modulesearchrmi.ModuleSearchServerImpl

3. Open a new window in terminal
4. Navigate to the directory above that of modulesearchrmi (/..)
5. Start the server application

     java modulesearchrmi.ModuleSearchServerApp

6. Open another terminal session
7. Navigate to the same directory as in step 4
8. Start the client application

     java modulesearchrmi.Main localhost


## Steps for RMI-IIOP:

1. Compile all java classes in the modulesearchiiop directory.

     javac modulesearchiiop/*.java

2. Generate stub and skeleton:

     rmic -iiop modulesearchiiop.ModuleSearchServerImpl

3. Start the TransientNameServer:

     sudo tnamserv

4. Open a new terminal session and navigate to the directory above that of modulesearchiiop (/..)

5. Start the server application

     java modulesearchiiop.ModuleSearchServerApp

6. Open another terminal session
7. Navigate to the same directory as in step 4
8. Start the client application

     java modulesearchiiop.Main localhost

# Assignment 2: Module Registration System (EJB)

## Disclaimer

Due to [Parkinson's Law](http://en.wikipedia.org/Parkinson's_Law), this project was a result of code converted from coffee, lots of help from all my fellow friends and seniors who have either survived this or are in this with me, all the matter of a span of 4 days.

In lieu of the deadline, I most seriously couldn't be bothered to do up a proper timetable (oops).

## Common Fixes to Problems

Before anything (don't panic):

* Ensure there are no errors in the code
* Check if RemoteInterfaces are included in projects
* Check if the @ notations are correctly defined for your file naming conventions
* Check if the correct libraries are included

Once you're sure there's nothing wrong with the code:

1. Restart GlassFish
2. Disconnect JavaDB service
3. Clean and build projects
4. Deploy projects
5. Pray for success.

*Sidenote: Really wish somebody gets rid of the Red Herring error message stack that's impossible to humanly trace to find out the problems that are not inherent within the code written.*

## Mac OS X GlassFish / InitialNamingContext bug

To resolve a particular InitialNamingContext error stack trace because the JDK is unable to bind to localhost for some reason, please modify your /etc/hosts file locally.

    sudo vim /etc/hosts

      1 ##
      2 # Host Database
      3 #
      4 # localhost is used to configure the loopback interface
      5 # when the system is booting.  Do not change this entry.
      6 ##
      7 127.0.0.1   localhost
      8 127.0.0.1   *<your computer name>*
      9 255.255.255.255 broadcasthost
     10 ::1             localhost

     :wq

Add in line 8, save and quit. Restart GlassFish and repeat the fix in Common Fixes.

## Closing words

Would like to express my deepest frustrations at the difficulties that Glassfish and Netbeans has caused for me (and the rest of us).

Appreciate the help obtained from the tutors, lecturer, and friends who have painstakingly peer-reviewed our work.
