pipeline {
    agent any

    tools {
        maven 'mvn'
        jdk 'jdk23'
    }

    environment {
        GIT_SSH_COMMAND = 'ssh -o StrictHostKeyChecking=no' // Skip host key checking
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    credentialsId: 'uma_git',
                    url: 'git@github.com:UmaShankariAA/AutomationSuite.git'
            }
        }

        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package -X' 
            }
        }

        stage('Test') {
            steps {
                sh 'mvn --batch-mode -V -U -e test -Dsurefire.useFile=false -X'
            }
        }
    }

    post {
        always {
            // Publish the HTML report regardless of test results
            publishHTML(target: [
                reportName: 'Test Report',
                reportDir: 'reports',  // Replace with actual path to your report directory
                reportFiles: 'index.html',    // Replace with the main HTML report file
                alwaysLinkToLastBuild: true,
                keepAll: true
            ])
            
            // Optionally archive reports or other artifacts
            // archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true

            // Clean up workspace
            // cleanWs()
        }
    }
}

