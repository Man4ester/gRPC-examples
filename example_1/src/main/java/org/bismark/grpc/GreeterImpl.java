package org.bismark.grpc;

import io.grpc.stub.StreamObserver;
import org.bismark.grpc.GreeterGrpc;
import org.bismark.grpc.HelloRequest;
import org.bismark.grpc.HelloResponse;

public class GreeterImpl implements GreeterGrpc.Greeter {

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloResponse> responseObserver) {
        StringBuilder message = new StringBuilder();
        message.append(String.format("Hello %s ", req.getName()));
        if ("Khmelnitskyi".equals(req.getCity())) {
            message.append(String.format(" From cool city %s", req.getCity()));
        }
        HelloResponse reply = HelloResponse.newBuilder().setMessage(message.toString()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
