###
# deploy.sh
###

PROFILE=$STORAGE_SERVICE_DEPLOY_PROFILE
SERVER_NAME=$STORAGE_SERVICE_NAME
SERVER_PORT=$STORAGE_SERVICE_PORT
ENV_PATH=$STORAGE_SERVICE_ENV_PATH

# Application Stop
if [ "$(docker ps -aq -f name=$SERVER_NAME)" ]; then
    echo -n "🚫 Stop Docker Container : "
    docker rm -f $SERVER_NAME
else
    echo "🚫 There is no running container named $SERVER_NAME"
fi

# Image Build
if [ "$(docker images -q $SERVER_NAME)" ]; then
    echo "🗑️ Remove Docker Image : "
    docker image rm $SERVER_NAME
else
    echo "🗑️ There is no Docker image named $SERVER_NAME"
fi
docker build --build-arg PROFILE=$PROFILE -t $SERVER_NAME .

# Docker Run
echo -n "🚀 Docker $SERVER_NAME Container Start! : "
docker run -d \
--name $SERVER_NAME \
-p $SERVER_PORT:$SERVER_PORT \
-e PROFILE=$PROFILE \
--env-file $ENV_PATH \
$SERVER_NAME