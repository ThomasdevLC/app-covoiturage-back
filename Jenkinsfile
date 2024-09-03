pipeline {
    agent any
    tools{
        maven 'maven 3.9.9'
    }
    stages {
        stage('checkout') {
            steps {
                git 'https://github.com/ThomasdevLC/app-covoiturage-back.git'
            }
        }
        stage('Build'){
           steps {
                sh 'mvn clean package'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                script {
                    def mvnHome = tool 'maven 3.9.9' //
                    withSonarQubeEnv('SonarQ') {
                        sh "${mvnHome}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=covoiturage-back -Dsonar.projectName='App Covoiturage Backend'"
                    }
                }
            }
        }
    }
}
