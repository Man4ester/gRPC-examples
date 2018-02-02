package org.bismark.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.bismark.grpc.GreeterGrpc;
import org.bismark.grpc.HelloRequest;
import org.bismark.grpc.HelloResponse;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloWorldClient {
    private static final Logger logger = Logger.getLogger(HelloWorldClient.class.getName());

    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    /**
     * Construct client connecting to HelloWorld server at {@code host:port}.
     */
    public HelloWorldClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build();
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * Say hello to server.
     */
    public void greet(String name, String city) {
        try {
            logger.info("Will try to greet " + name + " ...");
            HelloRequest request = HelloRequest.newBuilder()
                    .setName(name)
                    .setCity(city)
                    .build();
            HelloResponse response = blockingStub.sayHello(request);
            logger.info("Greeting: " + response.getMessage());
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "RPC failed", e);
            return;
        }
    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
    public static void main(String[] args) throws Exception {
        HelloWorldClient client = new HelloWorldClient("localhost", 50051);
        try {
      /* Access a service running on the local machine on port 50051 */
            String user = "world";
            String city = "no city";
            if (args.length > 0) {
                user = args[0]; /* Use the arg as the name to greet if provided */
            }
            if (args.length>1){
                city = args[1];
            }
            client.greet(user, city);
        } finally {
            client.shutdown();
        }
    }

}
