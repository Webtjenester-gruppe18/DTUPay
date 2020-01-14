pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        sh 'mvn clean install'
      }
    }
     stage('Build') {
          steps {
            sh 'docker build --tag dtupayimage:latest . '
          }
        }
     stage('Deploy') {
          steps {
            sh '''docker stop dtupay || true && docker rm dtupay || true;
            docker run -d -p 7777:8080 --name dtupay dtupayimage:latest
            '''
          }
     }
  }
}