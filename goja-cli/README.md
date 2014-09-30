# JFinal `japp` Commend line tool

## Usage

set `JAPP_HOME` with into you path.

example, japp unzip to `~/Workthing/02_sogyf/jfinal-gap/japp`:
    
    export PATH=/bin:/sbin:/usr/local/bin:/usr/bin:/usr/sbin:/usr/local/sbin:$MYSQL_HOME/bin:/opt/X11/bin:$NPM_APP/bin:$PYTHON_APP:$HOME/.rvm/bin:$RUBY_APP/bin:$TEXLIVE_HOME/bin/universal-darwin:$ANDROID_HOME/tools:~/Workthing/02_sogyf/jfinal-gap/japp:


then run

    ~
    ~ JFinal App gen.
    ~ Usage: jfgen cmd application_name [-options]
    ~
    ~ with, new          Create a new application
    ~       war          Export the application as a standalone WAR archive
    ~       syncdb       Sync Database table to Jfinal Model
    ~       idea         Convert the project to Intellij IDEA project
    ~       pom          Generate Maven POM file, and comply with the standard configuration Jfinal-app
    ~       db [-dir]    Init database script, the default mysql database
    ~       help         Show jfgen help
    ~

## Step by step to careate a applicaton.
> In this example. we will create a name for `towork` applications and use Maven or converted into common development of IDEA projct.

1. First create the project
	
		$ cd ~/projects
		$ japp new towork
		$ tree towork
		towork
		└── src
    		├── main
    		│   ├── java
    		│   │   └── app
    		│   │       ├── controllers
    		│   │       │   └── IndexController.java
    		│   │       ├── dtos
    		│   │       ├── interceptors
    		│   │       ├── jobs
    		│   │       ├── models
    		│   │       └── validators
    		│   ├── resources
    		│   │   ├── application.conf
    		│   │   ├── ehcache.xml
    		│   │   ├── logback.xml
    		│   │   ├── shiro.ini
    		│   │   └── sqlcnf
    		│   └── webapp
    		│       ├── WEB-INF
    		│       │   └── views
    		│       │       └── index.ftl
    		│       └── static
    		└── test
        		├── java
        		│   └── app
        		└── resources

		20 directories, 6 files

2. If you use Maven to develop, the executable `japp pom` command will project into standard Maven project, and then use your custom IDE open the Maven project will be.
		
		$ cd towork
		$ japp pom
		generate maven pom file Success!
		
3.  Or you can use the `japp idea` to generate idea project file.
		
		$ cd towork
		$ japp idea
		
4. Next, you can enjoy the use of idea for the developement of.