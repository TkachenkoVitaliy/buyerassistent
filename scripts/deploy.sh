#!/usr/bin/env zsh

mvn clean package

echo 'Copy files ...'

#scp -i Users/vitaliy/.ssh/id_rsa
scp -i \
target/BuyerAssistent-1.0.0.jar \
ssh vitaliy@194.177.23.80:/home/vitaliy/

echo 'Restart server ...'

ssh vitaliy@194.177.23.80 << EOF

pgrep java | xargs kill -9
nohup java -jar BuyerAssistent-1.0.0.jar > log.txt &

EOF

echo 'Bye!'

