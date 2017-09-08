# Java Vertical Scaling Load Test 
A simple app for testing JVM vertical scaling 


``` java [$JAVA_OPTS] -jar app.jar [sleep] ```
<small>
* $JAVA_OPTS - JVM start options, for example -Xmx2g -Xms32m -XX:+UseG1GC
* sleep - interval between memory load cycles in milliseconds, default is 10ms 
</small>
