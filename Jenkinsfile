pipeline {
  agent any

  environment {
    IMAGE_NAME = "your-dockerhub-username/hello-pavan-cicd"
    IMAGE_TAG  = "${env.BUILD_NUMBER}"
    KUBE_CONTEXT = "your-kube-context"
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build (Maven)') {
      steps {
        sh 'mvn -B -DskipTests clean package'
        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
      }
    }

    stage('Unit Tests') {
      steps {
        sh 'mvn test'
        junit '**/target/surefire-reports/*.xml'
      }
    }

    stage('Build Docker image') {
      steps {
        script {
          sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
        }
      }
    }

    stage('Push Docker image') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKERHUB_USER', passwordVariable: 'DOCKERHUB_PASS')]) {
          sh "echo $DOCKERHUB_PASS | docker login -u $DOCKERHUB_USER --password-stdin"
          sh "docker push ${IMAGE_NAME}:${IMAGE_TAG}"
          sh "docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${IMAGE_NAME}:latest"
          sh "docker push ${IMAGE_NAME}:latest"
        }
      }
    }

    stage('Deploy to Kubernetes') {
      steps {
        sh "kubectl set image deployment/hello-pavan-deployment hello-pavan=${IMAGE_NAME}:${IMAGE_TAG} --record || true"
        sh "kubectl rollout status deployment/hello-pavan-deployment --timeout=120s || true"
      }
    }
  }

  post {
    success {
      echo "Deployment successful: ${IMAGE_NAME}:${IMAGE_TAG}"
    }
    failure {
      echo "Build or deployment failed"
    }
  }
}