pipeline {
    agent any
    tools {
        maven 'maven 3.9.9'
    }
    stages {
        stage('checkout') {
            steps {
                git branch: 'jenkins', url: 'https://github.com/ThomasdevLC/app-covoiturage-back.git'
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

        // stage('Code Coverage') {
        //     steps {
        //         sh 'mvn jacoco:report'
        //     }
        //     post {
        //         always {
        //             jacoco execPattern: '**/target/jacoco.exec'
        //         }
        //     }
        // }


        stage('SonarQube Analysis') {
            steps {
                script {
                    def mvnHome = tool name: 'maven 3.9.9', type: 'maven'
                    withSonarQubeEnv('SonarQu') {
                    sh "${mvnHome}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=app-covoit -Dsonar.host.url=http://192.168.1.23:9000"


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
