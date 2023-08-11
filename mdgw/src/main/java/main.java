import netty_message.MarketBootstrap;

import java.net.UnknownHostException;


public class main {

    public static void main(String argc[]) throws UnknownHostException {
        MarketBootstrap bootstrap = MarketBootstrap.getInstance(null);
        Runtime.getRuntime().addShutdownHook(new Thread(bootstrap::logout));
        System.out.println("start vss");
        boolean connect = bootstrap.connect();
        if(connect){
            System.out.println("connected!");
        }
    }

}
