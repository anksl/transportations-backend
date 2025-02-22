FROM ibm-semeru-runtimes:open-11-jre
COPY target/transport-project.jar transport-project.jar
ENTRYPOINT ["java","-jar","/transport-project.jar"]