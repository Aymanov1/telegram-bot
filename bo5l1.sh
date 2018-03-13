#!/bin/sh
find target/ -name '*.war' -exec mv {} target/ROOT.war \;
mv target/ROOT.war /home/aymen/Documents/apache-tomcat-8.5.29/webapps/
cd /home/aymen/Documents/apache-tomcat-8.5.29/bin  
./catalina.sh run 