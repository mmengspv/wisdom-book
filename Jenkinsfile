
pipeline {
     agent {
          docker {
               image 'maven:3-jdk-11'  
               args '-p 33333:8080' 
          }
     }
     stages {
          stage('Source') {
               steps {
                    git branch: 'main',
                        url: 'https://github.com/mmengspv/wisdom-book'
               }
          }
          stage('Build') {
                environment {
                    HOME = '.'
                }
               steps {
                    bat 'mvn package -DskipTests'
               }
          }
          stage('Test') {
               steps {
                    echo 'testing...'
               }
          }
          stage('Deploy') {
               steps {
                    bat 'java -jar ./target/book-0.0.1-SNAPSHOT.jar'
               }
          }
     }
}

