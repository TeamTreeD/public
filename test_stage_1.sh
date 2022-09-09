#!/bin/bash

report_file="target/surefire-reports/TEST-org.jugsaxony.treed.examples.VerticalRainbowTestAnimationStrategyTest.xml"
error_file="error.txt"

#mvn -q -Dtest=de.entwicklerheld.treedChallenge.TreedChallengeTests clean surefire-report:report > ${error_file} 2>&1
mvn -q clean surefire-report:report > ${error_file} 2>&1
if [ -e ${report_file} ]
then
    cat ${report_file}
else
    cat ${error_file}
fi