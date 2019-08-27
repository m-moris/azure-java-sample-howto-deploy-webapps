@echo on 
call mvn -f linux-jre8/pom.xml clean package azure-webapp:deploy 
call mvn -f windows-jetty/pom.xml clean package azure-webapp:deploy
call mvn -f linux-tomcat85/pom.xml clean package azure-webapp:deploy -Dspring-boot.repackage.skip=true
call mvn -f windows-tomcat85/pom.xml clean package azure-webapp:deploy -Dspring-boot.repackage.skip=true