pipeline {
    agent any

    tools {
        maven 'Maven 3.x' 
        jdk 'Java 21'     
    }

    stages {
        stage('Repository Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Sanitize Environment') {
            steps {
                sh 'mvn clean validate'
            }
        }

        stage('Source Compilation') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('Execute Core Test Suite') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Binary Packaging') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        }
        success {
            echo 'Operational Status Code 200: Continuous delivery compilation sequence completed successfully!'
        }
        failure {
            echo 'Operational Error: Execution pipeline sequence faulted. Inspect terminal telemetry data logs.'
        }
    }
}
