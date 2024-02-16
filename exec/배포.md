# Docker

# 1. 도커 설치하기



> AWS EC2 환경에서 진행합니다.
> 그 외의 환경은 보장하지 못합니다.

가장 먼저 패키지를 최신 버젼으로 업데이트를 진행합니다.

```shell
sudo apt update -y
```

다음으로 https 관련 패키지 설치합니다.

```shell
sudo apt-get install \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common -y
```

gpg 키 및 저장소 추가를 합니다.

```shell
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
```

```shell
sudo add-apt-repository --yes \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
```

마지막으로 Docker 엔진을 설치합니다.

```shell
sudo apt-get install docker-ce docker-ce-cli containerd.io -y
```

정상적으로 설치가 되었는지 확인하기 위해 `docker -v` 명령어를 이용하여 확인합니다.

![](https://velog.velcdn.com/images/kimsei1124/post/8d589444-1c3e-4eb7-8c7d-4d496b7c3bb5/image.png)

정상적으로 설치된 것을 확인할 수 있습니다.

> ![](https://velog.velcdn.com/images/kimsei1124/post/aea7e318-3bc4-4187-9dca-4167853f7828/image.gif)

> 한번에 설치를 원하시면 아래에 적혀있는 명령어를 전체다 복사하여 붙여넣으시면 됩니다. 🚀
> ```shell
> sudo apt update -y
> 
> sudo apt-get install \
>  apt-transport-https \
>  ca-certificates \
>  curl \
>  gnupg-agent \
>  software-properties-common -y
> 
> curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
> 
> sudo add-apt-repository --yes \
> "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
> $(lsb_release -cs) \
> stable"
> 
> sudo apt-get install docker-ce docker-ce-cli containerd.io -y
> 
> docker -v
> ```

도커가 정상적으로 설치되었고 사용하고 있는 계정에 대해서 도커 사용 권한을 주려고 합니다.
이렇게 되면 `sudo`를 붙히지 않고 동작할 수 있도록 합니다.

`sudo usermod -aG docker [username]`

이후 도커도 재실행 시키고 계정도 로그아웃 후 재로그인 합니다.

`sudo service docker restart`

이후 `sudo` 없이 정상동작 하는지 `docker ps`로 확인합니다.

![](https://velog.velcdn.com/images/kimsei1124/post/10d82e4f-2012-4d11-934a-7e450ee7affa/image.png)

정상적으로 동작하는 것을 확인할 수 있습니다!

# 2. Docker-Compose with Infra



`infra-local/infra-script` 로 이동하여 `sh infra-up.sh`를 실행시킵니다. 데이터 삭제와 함께 종료는 `sh infra-down.sh`이고 멈추는 것은 `sh infra-stop.sh` 로 할 수 있습니다.

# 3. Spring Server



이후 `infra-local`로 이동하여 `sh init.sh`를 실행하면 서버가 가동합니다.

