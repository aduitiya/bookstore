pipeline {
  agent any

  options {
    timestamps()
    ansiColor('xterm')
    buildDiscarder(logRotator(numToKeepStr: '20'))
  }

  parameters {
    string(name: 'BASE_URL', defaultValue: 'http://localhost:8000', description: 'Target API base URL')
    string(name: 'EMAIL', defaultValue: 'qa+ci@example.com', description: 'User email for signup/login')
    password(name: 'PASSWORD', defaultValue: 'StrongP@ssw0rd!', description: 'Password for signup/login')
  }

  stages {
    stage('Checkout') { steps { checkout scm } }
    stage('Verify') {
      steps {
        sh "mvn -B -q -DbaseUrl=${params.BASE_URL} -DEMAIL='${params.EMAIL}' -DPASSWORD='${params.PASSWORD}' verify"
      }
    }
    stage('Allure HTML') {
      steps { sh 'mvn -q io.qameta.allure:allure-maven:report || true' }
    }
  }
  post {
    always {
      junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
      archiveArtifacts artifacts: 'target/allure-results/**', allowEmptyArchive: true
      archiveArtifacts artifacts: 'target/site/allure-maven-plugin/**', allowEmptyArchive: true
      archiveArtifacts artifacts: 'target/site/jacoco/**', allowEmptyArchive: true
    }
  }
}
