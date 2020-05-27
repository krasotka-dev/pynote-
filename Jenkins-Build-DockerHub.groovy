import hudson.EnvVars

def dockerImage

node {
  def repositoryName = 'fsadykov/pynote'
  properties([
    parameters([
      booleanParam(defaultValue: false,
        description: 'Click this if you would like to deploy to latest',
        name: 'PUSH_LATEST'
        )])])

  // Pooling the docker image
  checkout scm

  stage('Build docker image') {
      // Build the docker image
      dockerImage = docker.build("fsadykov/pynote", "-f ${WORKSPACE}/Dockerfile .")
  }

  stage('Push image') {

     // Push docker image to the Docker hub
      docker.withRegistry('', 'dockerhub-credentials') {
          dockerImage.push("0.${BUILD_NUMBER}")
        if (params.PUSH_LATEST) {
              dockerImage.push("latest")
          }
      }
  }

  stage('clean up') {
         sh "docker rmi ${repositoryName}:0.${BUILD_NUMBER} --force "
         sh "docker rmi ${repositoryName}:latest --force"
         sh "rm -rf ${WORKSPACE}/*"
       }

}
