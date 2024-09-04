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
        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Code Coverage') {
            steps {
                sh 'mvn jacoco:prepare-agent test jacoco:report'
            }
            post {
                always {
                    // Archive the JaCoCo HTML report for viewing
                    publishHTML(target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target/site/jacoco',
                        reportFiles: 'index.html',
                        reportName: 'JaCoCo Code Coverage'
                    ])
                    // Optionally, publish the coverage report in Cobertura format
                    cobertura autoUpdateHealth: false, autoUpdateStability: false, coberturaReportFile: '**/target/site/jacoco/jacoco.xml', conditionalCoverageTargets: '70, 0, 0', failUnhealthy: false, failUnstable: false
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                script {
                    def mvnHome = tool name: 'maven 3.9.9', type: 'maven'
                    withSonarQubeEnv('SonarQ') {
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
