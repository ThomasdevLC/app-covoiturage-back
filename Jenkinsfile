pipeline {
    agent any
    tools {
        maven 'maven 3.9.9'
    }
    stages {
        stage('checkout') {
            steps {
                git 'https://github.com/ThomasdevLC/app-covoiturage-back.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                script {
                    def mvnHome = tool name: 'maven 3.9.9', type: 'maven'
                    withSonarQubeEnv('SonarQ') {
                        sh "${mvnHome}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=covoiturage-back -Dsonar.host.url=http://192.168.1.23:9000"
                    }
                }
            }
        }
    }
}
