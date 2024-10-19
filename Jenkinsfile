pipeline {
    agent any
    stages {
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
