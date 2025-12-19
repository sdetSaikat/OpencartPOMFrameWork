pipeline {
    agent any 
    stages {
        stage('Build') { 
            steps {
               
                echo 'Building the application...'
                sh 'make build' // Executes a shell command
            }
        }
        stage('Test') {
            steps {
                echo 'Running tests...'
                
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying to staging...'
            }
        }
    }
  
}
