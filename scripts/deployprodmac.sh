#!/usr/bin/env zsh

mvn clean package

echo 'Copy files ...'

#scp -i Users/vitaliy/.ssh/id_rsa
scp -i ~/.ssh/id_rsa \
target/BuyerAssistant-1.3.0.jar \
vitaliy@45.155.207.175:/home/vitaliy/

echo 'Restart server ...'

#pgrep java | xargs kill -9
ssh -tt -i ~/.ssh/id_rsa vitaliy@45.155.207.175 <<EOF
pgrep java | xargs kill -KILL
wait
nohup java -jar BuyerAssistant-1.3.0.jar > log.txt &
sleep 5 && exit
EOF

echo 'Bye!'

