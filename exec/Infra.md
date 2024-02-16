# Infra

# 1. micro-service.Jenkinsfile

> 각 서비스에 대한 변수만 수정해주시면 동작합니다.

```groovy
pipeline {
    agent any
    
    stages {
        stage('Member Service Clone') {
            steps {
                git branch: 'dev-be-member', credentialsId: 'gitlab-token', url: 'https://lab.ssafy.com/s10-webmobile2-sub2/S10P12A508.git'
            }
        }
        
        stage('bootJar - MemberService') {
            steps {
                dir('BE-MemberService') {
                    sh '''
                    chmod 755 gradlew
                    ./gradlew clean bootJar
                    '''
                }
            }
        }
        
        
        stage('SCP - MemberService') {
            steps {
                sh 'scp -r "/var/jenkins_home/workspace/Deploy Wondoo Member Service By Dev/BE-MemberService" ubuntu@3.34.252.188:/home/ubuntu/scp/dev/'
            }
        }
        
        stage('Deploy - MemberService') {
            steps {
                sh '''
                ssh ubuntu@3.34.252.188  '
                cd /home/ubuntu/scp/dev/BE-MemberService && 
                export MEMBER_SERVICE_DEPLOY_PROFILE=dev && 
                export MEMBER_SERVICE_NAME=dev-member-service && 
                export MEMBER_SERVICE_PORT=48080 && 
                export MEMBER_SERVICE_ENV_PATH=/home/ubuntu/scp/env/micro-service.env &&
                sh deploy.sh'
                '''
            }
        }
    }
    
     post {
        success {
            script {
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend (color: 'good', 
                message: "빌드 성공: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\n(<${env.BUILD_URL}|Details>)", 
                endpoint: 'https://meeting.ssafy.com/hooks/diojibnwxi8ufrtywpz1akkafa', 
                channel: 'wondoo-noti'
                )
            }
        }
        failure {
            script {
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend (
                    color: 'danger', 
                    message: "빌드 실패: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\n(<${env.BUILD_URL}|Details>)", 
                    endpoint: 'https://meeting.ssafy.com/hooks/diojibnwxi8ufrtywpz1akkafa', 
                    channel: 'wondoo-noti'
                )
            }
        }
    }
}
```

# 2. Frontend.Jenkinsfile

```groovy
pipeline {
    agent any
    
    stages {
        stage('Clone') {
            steps {
                git branch: 'dev-fe', credentialsId: 'gitlab-token', url: 'https://lab.ssafy.com/s10-webmobile2-sub2/S10P12A508.git'
            }
        }
        
        stage('Build') {
            steps {
                dir("FE") {
                    sh 'npm i --force && npm run build'
                }
            }
        }
        
        stage('Compression') {
            steps {
                dir("FE") {
                    sh '''
                        rm -rf node_modules
                        tar -cvf dist.tar dist
                    '''
                }
            }
        }
        
        stage('Deploy') {
            steps {
                sshagent(credentials: ['wondoo_ws']) {
                    sh '''
                        ssh -o StrictHostKeyChecking=no ubuntu@43.200.90.249 uptime
                        scp "/var/jenkins_home/workspace/Deploy Wondoo Frontend By Dev/FE/dist.tar" ubuntu@43.200.90.249:/home/ubuntu/scp/dev
                        ssh -t ubuntu@43.200.90.249 /home/ubuntu/scp/dev/deploy.sh
                    
                    '''
                }
            }
        }
    }
    
     post {
        success {
            script {
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend (color: 'good', 
                message: "빌드 성공: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\n(<${env.BUILD_URL}|Details>)", 
                endpoint: 'https://meeting.ssafy.com/hooks/diojibnwxi8ufrtywpz1akkafa', 
                channel: 'wondoo-noti'
                )
            }
        }
        failure {
            script {
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend (
                    color: 'danger', 
                    message: "빌드 실패: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\n(<${env.BUILD_URL}|Details>)", 
                    endpoint: 'https://meeting.ssafy.com/hooks/diojibnwxi8ufrtywpz1akkafa', 
                    channel: 'wondoo-noti'
                )
            }
        }
    }
}

```

# 3. Nginx



## 3.1. Defauilt



```text
# Default server configuration
#
server {
	listen 80 default_server;
	listen [::]:80 default_server;

	root /var/www/html;

	server_name wondoo.kr;

	location / {
		return 301 https://wondoo.kr$request_uri;
	}
}

# SSL 추가
server {
	listen 443 ssl;
	server_name wondoo.kr;

	ssl_certificate /etc/letsencrypt/live/wondoo.kr/fullchain.pem;  # SSL 인증서 파일
  ssl_certificate_key /etc/letsencrypt/live/wondoo.kr/privkey.pem;  # SSL 키 파일

	location / {
		proxy_pass https://fe.wondoo.kr;
	}
}
```

## 3.2 Frontend

```text
##
# Frontend Dev  SubDomain Conf
##

# SSL Server
server {
        listen 443 ssl;
        server_name fe.wondoo.kr;

	location / {
                root /home/ubuntu/scp/dev/dist;
                index index.html index.htm;
                try_files $uri $uri/ /index.html;
        }
}

# Redirect Server
server {
        listen 80;
        listen [::]:80;
        server_name fe.wondoo.kr;

        location / {
		root /home/ubuntu/scp/dev/dist;
		index index.html index.htm;
		try_files $uri $uri/ /index.html;
	}

	return 301 https://wondoo.kr$request_uri;
}
```

## 3.3 Backend

```text
##
# Dev API SubDomain Conf
##

# SSL Server
server {
        listen 443 ssl;
        server_name dev.wondoo.kr;

        location / {
                proxy_pass http://3.34.252.188:48000;
        }
}

# Redirect Server
server {
        listen 80;
        listen [::]:80;

        server_name dev.wondoo.kr;

        location / {
                return 301 https://dev.wondoo.kr$request_uri;
        }
}
```

## 3.4 Notification

```text
##
# Noti API SubDomain Conf
##

# SSL Server
server {
        listen 443 ssl;
      	server_name noti.wondoo.kr;

        location / {
                proxy_set_header Connection '';
		proxy_set_header Cache-Control 'no-cache';
		proxy_set_header X-Accel-Buffering 'no';
		proxy_set_header Content-Type 'text/event-stream';
		proxy_buffering off;
		chunked_transfer_encoding on;
		proxy_read_timeout 86400s;

		proxy_pass http://3.34.252.188:48180;
        }
}

# Redirect Server
server {
        listen 80;
        listen [::]:80;

        server_name noti.wondoo.kr;

        location / {
                return 301 https://noti.wondoo.kr$request_uri;
        }
}
```



## 3.5. Message

```text
##
# Message API SubDomain Conf
##

# SSL Server
server {
        listen 443 ssl;
        server_name message.wondoo.kr;

        location / {
		proxy_set_header Upgrade $http_upgrade;
		proxy_set_header Connection "upgrade";
		proxy_http_version 1.1;
                proxy_pass http://3.34.252.188:48480;
        }
}

# Redirect Server
server {
        listen 80;
        listen [::]:80;

        server_name message.wondoo.kr;

        location / {
                return 301 https://message.wondoo.kr$request_uri;
        }
}
```

