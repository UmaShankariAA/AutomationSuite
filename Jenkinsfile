pipeline {
    agent any
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
                sh 'mvn -B -DskipTests clean package' 
            }
        }
        stage('Test') {
            steps {
                sh 'mvn --batch-mode -V -U -e test -Dsurefire.useFile=false -X'
            }
        }
    }
}
