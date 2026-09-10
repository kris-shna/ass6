pipeline {
    agent any

    tools {
        maven 'Maven 3.x' // References the Maven name in Jenkins Global Tools
        jdk 'Java 21'     // References your Java 21 system path configuration
    }

    stages {
        stage('Repository Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Sanitize Environment') {
            steps {
                // CHANGED: Used bat instead of sh for Windows compatibility
                bat 'mvn clean validate'
            }
        }

        stage('Source Compilation') {
            steps {
                // CHANGED: Used bat instead of sh
                bat 'mvn compile'
            }
        }

        stage('Execute Core Test Suite') {
            steps {
                // CHANGED: Used bat instead of sh
                bat 'mvn test'
            }
        }

        stage('Binary Packaging') {
            steps {
                // CHANGED: Used bat instead of sh
                bat 'mvn package -DskipTests'
            }
        }
    }

    post {
        always {
            // Added allowEmptyResults parameter to avoid halting build chains on custom reporting maps
            junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
            
            // Archives build artifacts safely for direct operational distribution
            archiveArtifacts allowEmptyArchive: true, artifacts: '**/target/*.jar', fingerprint: true
        }
        success {
            echo 'Operational Status Code 200: Continuous delivery compilation sequence completed successfully!'
        }
        failure {
            echo 'Operational Error: Execution pipeline sequence faulted. Inspect terminal telemetry data logs.'
        }
    }

}
