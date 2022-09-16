#!/bin/bash
report_file="target/surefire-reports/TEST-org.jugsaxony.treed.MyStrategyTest.xml"
error_file="error.txt"
boot_file="boot.txt"

mvn spring-boot:stop & > ${boot_file} 2&>1


mvn -q -Dtest=org.jugsaxony.treed.MyStrategyTest clean surefire-report:report > ${error_file} 2>&1
if [ -e ${report_file} ]
then
    cat ${report_file}
else
    cat ${error_file}
fi

# Run spring boot in background, exposing at 8037
nohup mvn spring-boot:start & >> ${boot_file} 2&>1
