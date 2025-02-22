pipeline {
    agent any
  environment {
    MAVEN_ARGS=" -e clean install"
    registry = ""
    dockerContainerName = 'app'
    dockerImageName = 'transport-project'
  }
  stages {
    stage('build') {
       steps {
   withMaven(maven: 'MAVEN_ENV') {
            bat "mvn ${MAVEN_ARGS}"
        }
       }
    }

  stage('docker-compose start') {
      steps {
       bat 'docker-compose up'
      }
    }
  }
}