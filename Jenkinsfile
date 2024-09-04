pipeline {
    agent any
    parameters {
        choice(
            name: 'BRANCH_NAME', 
            choices: ['main', 'jenkins'], 
            description: 'select branch'
        )
    }
    tools {
        maven 'maven 3.9.9'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: "${BRANCH_NAME}", url: 'https://github.com/ThomasdevLC/app-covoiturage-back.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Unit Tests') {
            steps {
                sh 'mvn test'
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
    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
