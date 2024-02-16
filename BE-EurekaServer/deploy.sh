###
# deploy.sh
###

# Application Stop
if [ "$(docker ps -aq -f name=$EUREKA_SERVER_NAME)" ]; then
    echo -n "🚫 Stop Docker Container : "
    docker rm -f $EUREKA_SERVER_NAME
else
    echo "🚫 There is no running container named $EUREKA_SERVER_NAME"
fi

# Image Build
if [ "$(docker images -q $EUREKA_SERVER_NAME)" ]; then
    echo "🗑️ Remove Docker Image : "
    docker image rm $EUREKA_SERVER_NAME
else
    echo "🗑️ There is no Docker image named $EUREKA_SERVER_NAME"
fi
docker build --build-arg PROFILE=$EUREKA_SERVER_DEPLOY_PROFILE -t $EUREKA_SERVER_NAME .

# Docker Run
echo -n "🚀 Docker $EUREKA_SERVER_NAME Container Start! : "
docker run -d \
--name $EUREKA_SERVER_NAME \
-p $EUREKA_SERVER_PORT:$EUREKA_SERVER_PORT \
-e PROFILE=$EUREKA_SERVER_DEPLOY_PROFILE \
--env-file $EUREKA_SERVER_ENV_PATH \
$EUREKA_SERVER_NAME