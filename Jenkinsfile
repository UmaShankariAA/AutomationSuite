pipeline {
    agent any

    tools {
        maven 'mvn'
        jdk 'jdk23'
    }

    environment {
        GIT_SSH_COMMAND = 'ssh -o StrictHostKeyChecking=no' // Skip host 
key checking
        // Uncomment below to disable Jenkins Content Security Policy if 
needed for external resources in reports
        // hudson.model.DirectoryBrowserSupport.CSP = "default-src 'self'; 
style-src 'self' 'unsafe-inline'; script-src 'self' 'unsafe-inline';"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    credentialsId: 'uma_git',
                    url: 
'git@github.com:UmaShankariAA/AutomationSuite.git'
            }
        }

        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package -X' 
            }
        }

        stage('Test') {
            steps {
                sh 'mvn --batch-mode -V -U -e test 
-Dsurefire.useFile=false -X'
            }
        }

        stage('Publish Report') {
            steps {
                // Publish the HTML report generated during the test stage
                publishHTML(target: [
                    reportName: 'Test Report',
                    reportDir: 'path/to/report',  // Replace with actual 
path to your report directory
                    reportFiles: 'index.html',    // Replace with the main 
HTML report file
                    alwaysLinkToLastBuild: true,
                    keepAll: true
                ])
            }
        }
    }

    post {
        always {
            // Optionally archive reports or other artifacts
            archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: 
true

            // Clean up workspace
            cleanWs()
        }
    }
}
