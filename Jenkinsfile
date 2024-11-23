pipeline {
    agent any
    environment {
        DOCKER_IMAGE = 't0ky0le/task-service:1.0' // Nombre de la imagen en Docker Hub
    }
    stages {
        stage('Checkout') {
            steps {
                echo 'Clonando el repositorio...'
                git branch: 'main', url: 'https://github.com/TokyoF/task-service-taskflow.git' // Cambia con tu URL
            }
        }
        stage('Build') {
            steps {
                echo 'Construyendo el proyecto con Maven...'
                bat './mvnw.cmd clean package -DskipTests' // Compila el proyecto
            }
        }
        stage('Docker Build') {
            steps {
                echo 'Construyendo la imagen Docker...'
                bat "docker build -t ${DOCKER_IMAGE} ." // Construye la imagen Docker
            }
        }
        stage('Docker Push') {
            steps {
                echo 'Subiendo la imagen a Docker Hub...'
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    bat "docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD%"
                    bat "docker push ${DOCKER_IMAGE}" // Sube la imagen a Docker Hub
                }
            }
        }
        stage('Docker Compose Down') {
            steps {
                echo 'Bajando cualquier instancia previa de Docker Compose...'
                bat 'docker rm -f task-service || true' // Elimina el contenedor si existe
                bat 'docker-compose down' // Baja cualquier instancia previa
            }
        }
        stage('Docker Compose Up') {
            steps {
                echo 'Levantando el contenedor con Docker Compose...'
                bat 'docker-compose up -d' // Levanta el contenedor
            }
        }
    }
    post {
        always {
            echo 'Finalizando el pipeline.'
        }
        success {
            echo 'Pipeline completado exitosamente.'
        }
        failure {
            echo 'Error en el pipeline.'
        }
    }
}
