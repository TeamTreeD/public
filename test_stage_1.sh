#!/bin/bash
report_file="target/surefire-reports/TEST-org.jugsaxony.treed.MyStrategyTest.xml"
error_file="error.txt"

#rm -R /webapp > /dev/null 2>&1
#mkdir /webapp > /dev/null 2>&1
#cd /webapp > /dev/null 2>&1
mvn spring-boot:stop -P\!webapp > web_out.txt 2>&1

#cd /data/task-data
mvn -q -P\!webapp -Dtest=org.jugsaxony.treed.MyStrategyTest surefire-report:report >> ${error_file} 2>&1
if [ -e ${report_file} ]
then
    cat ${report_file}
else
    cat ${error_file}
fi

# Copy code to /webapp so that it is not cleaned
#cp -R /data/task-data/. /webapp/
#cd /webapp
# Run spring boot in background, exposing at 8038
mvn spring-boot:start -P\!webapp >> web_out.txt 2>&1
