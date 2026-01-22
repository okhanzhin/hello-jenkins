pipeline {
    agent any

    triggers {
        githubPush()
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 30, unit: 'MINUTES')
        timestamps()
    }

    stages {
        stage('Checkout') {
            steps {
                echo "📥 Клонируем репозиторий..."
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo "🔨 Собираем проект..."
                sh './gradlew clean build'
            }
        }

        stage('Test') {
            steps {
                echo "🧪 Запускаем тесты..."
                sh './gradlew test'
            }
        }

        stage('Code Coverage') {
            steps {
                echo "📊 Генерируем отчёт покрытия кода..."
                sh './gradlew jacocoTestReport'
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'build/reports/jacoco/test/html',
                    reportFiles: 'index.html',
                    reportName: 'JaCoCo Report'
                ])
            }
        }

        stage('Quality Analysis') {
            steps {
                echo "✅ Проверяем качество кода..."
                sh './gradlew jacocoTestCoverageVerification || true'
            }
        }

        stage('Create Artifact') {
            steps {
                echo "📦 Создаём артефакт..."
                sh './gradlew printArtifacts'
            }
        }
    }

    post {
        always {
            echo "═══════════════════════════════════════"
            echo "📋 РЕЗУЛЬТАТЫ СБОРКИ"
            echo "═══════════════════════════════════════"

            archiveArtifacts artifacts: 'build/libs/**/*.jar',
                             allowEmptyArchive: true

            archiveArtifacts artifacts: 'build/reports/**',
                             allowEmptyArchive: true
            
            sh '''
                echo ""
                echo "✅ АРТЕФАКТЫ СБОРКИ:"
                echo "JAR файл: ${WORKSPACE}/build/libs/hello-jenkins-*.jar"
                echo "JaCoCo отчет: ${WORKSPACE}/build/reports/jacoco/test/html/index.html"
                echo ""
            '''
        }

        success {
            echo "✅ Сборка успешна!"
        }

        failure {
            echo "❌ Сборка не удалась!"
        }
    }
}