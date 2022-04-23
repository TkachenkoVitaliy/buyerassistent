#!/usr/bin/env bash

mvn clean package

echo 'Copy files ...'

#scp -i Users/vitaliy/.ssh/id_rsa
scp -i ~/.ssh/id_rsa \
target/BuyerAssistant-1.0.0.jar \
vitaliy@194.177.23.80:/home/vitaliy/

echo 'Restart server ...'

#pgrep java | xargs kill -9
ssh -i ~/.ssh/id_rsa vitaliy@194.177.23.80 << EOF

killall java
nohup java -jar BuyerAssistant-1.0.0.jar > log.txt &

EOF

echo 'Bye!'

