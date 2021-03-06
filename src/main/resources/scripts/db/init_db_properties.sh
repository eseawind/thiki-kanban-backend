#!/bin/sh
set -e
env_name=$1
env_params_path=$2
server_dir=$3

echo "init database properties ..."

echo "env:$1"
echo "env_params_path:$2"

source $2

if [ "$1" = "sit" ]
then
username=$username_sit
password=$password_sit
host_name=$host_name_sit
http_port=$http_port_sit
database_name=$database_name_sit
fi

if [ "$1" = "prod" ]
then
username=$username_prod
password=$password_prod
host_name=$host_name_prod
http_port=$http_port_prod
database_name=$database_name_prod
fi

echo "username: $username"
echo "password: $password"
echo "host_name: $host_name"
echo "http_port: $http_port"

echo "jdbc.driver=com.mysql.jdbc.Driver" >>src/main/resources/kanban.properties
echo "jdbc.url=jdbc:mysql://$host_name:3306/$database_name?useUnicode=true&characterEncoding=utf8" >>src/main/resources/kanban.properties
echo "jdbc.username=$username" >>src/main/resources/kanban.properties
echo "jdbc.password=$password" >>src/main/resources/kanban.properties
echo "http.port=$http_port" >>src/main/resources/kanban.properties

echo "init database properties done."

echo "copy deploy.sh to $server_dir directory"
mv src/main/resources/scripts/deploy.sh $server_dir
echo "copy done."
