# tpcc-soccer-manager-service
Spring Boot backend service

## Start Spring Boot Application in Server
```dtd
sudo apt-get update
sudo apt-get install openjdk-11-jdk
git clone https://github.com/johnb9682/tpcc-soccer-manager-service.git
cd tpcc-soccer-manager-service
git pull origin
./gradlew bootRun
./gradlew --daemon bootRun
ssh ubuntu@13.250.52.217  -i trip-planner-key-pair.pem
ps -ef | grep gradle
nohup ./gradlew bootRun >Output.log 2>&1 &
```

