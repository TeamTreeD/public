#!/bin/bash
report_file="target/surefire-reports/TEST-org.jugsaxony.treed.MyStrategyTest.xml"
error_file="error.txt"

mvn spring-boot:stop -P\!webapp,suppressjavafx > web_out.txt 2>&1

# Make sure no process listening to 8038 is still running
kill $(lsof -ti tcp:8038) 

mvn -q -P\!webapp,suppressjavafx -Dtest=org.jugsaxony.treed.MyStrategyTest surefire-report:report >> ${error_file} 2>&1
if [ -e ${report_file} ]
then
    cat ${report_file}
else
    cat ${error_file}
fi

# Run spring boot in background, exposing at 8038
mvn spring-boot:start -P\!webapp,suppressjavafx >> web_out.txt 2>&1
