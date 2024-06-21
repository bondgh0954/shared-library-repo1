#!/user/bin/env groovy

package com.example
class Docker implements Serializable{
    def script

    Docker(script){
        this.script =script
    }


    buildJar(){
        script.echo 'building jar file from maven application........'
        script.sh 'mvn clean package'
    }
    buildImage(String imageName){
        script.echo 'building application into docker image.......'
        script.sh "docker build -t $imageName ."
    }
    dockerLogin(){
        script.echo 'logging in to private docker repository.....'
        script.withCredentials([script.usernamePassword(credentialsId:'dockerhub-credentials',usernameVariable:'USER',passwordVariable:'PASS')]){
            script.sh "echo '${script.PASS}' | docker login -u '${script.USER}' --password-stdin"
        }
    }
    pushImage(String imageName){
        script.sh "docker push $imageName"
    }

}