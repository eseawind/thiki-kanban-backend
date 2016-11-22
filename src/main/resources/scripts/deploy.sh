#!/bin/sh
set -e
artifacts_path=$1
server_dir=$2
server_addr=$3
url=$3
echo "artifacts_path:$1"
echo "server_dir:$2"
echo "server_addr:$3"

sh killServer.sh

echo "clean server directory..."
cd $server_dir
rm -rf *.jar

echo "copy new artifact to server directory..."
mv $artifacts_path/*.jar $server_dir

echo "start server ..."
java -jar *.jar 1>> log.log 2>&1&

echo "deploy done."