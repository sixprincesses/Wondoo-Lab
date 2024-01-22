###
# deploy.sh
###
APP_NAME=eureka_server
IMAGE=wondoo_service_discovery
PORT=8761

./gradlew clean build

# Application Stop
if [ "$(docker ps -q -f name=$APP_NAME)" ]; then
    echo -n "🚫 Stop Docker Container : "
    docker rm -f $APP_NAME
else
    echo "🚫 There is no running container named $APP_NAME"
fi

# Image Build
if [ "$(docker images -q $IMAGE)" ]; then
    echo "🗑️ Remove Docker Image : "
    docker image rm $IMAGE
else
    echo "🗑️ There is no Docker image named $IMAGE"
fi
docker build . -t $IMAGE

# Docker Run
echo -n "🚀 Docker $APP_NAME Container Start! : "
docker run -d \
--name $APP_NAME \
-p $PORT:$PORT \
$IMAGE