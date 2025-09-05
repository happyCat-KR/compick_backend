pipeline {
  agent any
  options { skipDefaultCheckout(true) }
  environment {
    SSH_CRED = 'deploy-ssh'
    DEPLOY_DIR = '/home/ec2-user/app/compick'
  }
  triggers { githubPush() }
  stages {
    stage('Checkout'){ steps { checkout scm } }
    stage('Build'){ steps { sh './gradlew clean bootJar -x test' } }
    stage('Deploy'){
      steps {
        withCredentials([string(credentialsId: 'backends-hosts', variable: 'BACKENDS')]) {
          sshagent(credentials: [env.SSH_CRED]) {
            sh '''
              set +x
              for h in $BACKENDS; do
                scp -q -o StrictHostKeyChecking=accept-new -o LogLevel=ERROR build/libs/*.jar ec2-user@$h:${DEPLOY_DIR}/app.jar
                ssh -q -o StrictHostKeyChecking=accept-new -o LogLevel=ERROR ec2-user@$h "cd ${DEPLOY_DIR} && docker compose up -d --force-recreate"
              done
            '''
          }
        }
      }
    }
  }
}
