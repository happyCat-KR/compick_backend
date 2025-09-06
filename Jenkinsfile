pipeline {
  agent any
  tools { jdk 'jdk17' }               // ← Jenkins Global Tool의 JDK 이름
  options { skipDefaultCheckout(true) }
  environment {
    SSH_CRED  = 'deploy-ssh'
    DEPLOY_DIR = '/home/ec2-user/app/compick'
  }
  triggers { githubPush() }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Build') {
      steps {
        sh '''
          chmod +x gradlew || true
          sed -i 's/\r$//' gradlew || true   # CRLF 제거(윈도우에서 올라온 경우)
          ./gradlew clean bootJar -x test --no-daemon
        '''
      }
    }

    stage('Deploy') {
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

  post {
    failure { echo 'Build 실패: gradlew 권한/라인엔딩, JDK 설정 확인' }
  }
}
