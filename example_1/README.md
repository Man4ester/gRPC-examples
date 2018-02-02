# Maven
```
mvn clean package
```
# Start gRPC services

## start server
```
java -cp target/grpctest-1.0-SNAPSHOT-jar-with-dependencies.jar org.bismark.grpc.HelloWorldServer
```

## start client
```
java -cp target/grpctest-1.0-SNAPSHOT-jar-with-dependencies.jar org.bismark.grpc.HelloWorldClient "Sasha" "Khmelnitskyi"
```