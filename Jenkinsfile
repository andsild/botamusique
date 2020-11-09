pipeline {
  agent any; 
  stages {
    stage('Build') {
      steps {
        sh '''
          ./gradlew build --info
          # Not necessary with docker and this lazy setup
          # ./gradlew publish --info
        '''
      }
    }

    stage('Deploy') {
      steps {
        sh '''
          #set +x # don't expose password
          #token="$(cat /run/secrets/deploy-password)"
          #echo "Doing curl https://qwde.no:9000/hooks/qwde-deploy?service=benkemannen&token=..."
          #curl "https://qwde.no:9000/hooks/qwde-deploy?token=$token&service=benkemannen&artifact=benkemannen&classifier=prod&extension=tar.gz"
          #set -x
# Assumes is run with 
          docker restart benkemannen
          '''
      }
    }
  }
}
