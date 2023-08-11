package configs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class sysconfig {

    private String host;
    private int port;
    private String password;
    private int heartbeatInterval;
    private String senderCompId;
    private String targetCompId;
    private String version;
    private int reconnect;
    private int reconnectInterval;
    private String driverName;
    private String url;
    private String user;
    private String user_password;
    private String topic;
    private String broker;

    public sysconfig(){
        this.host = "10.1.20.101";
        this.port = 26875;
        this.password = "VSS";
        this.heartbeatInterval = 10;
        this.senderCompId = "VSS";
        this.targetCompId = "VSS";
        this.version = "1.02";
        this.reconnect = 5;
        this.reconnectInterval = 10;
        this.url = "jdbc:ch://127.0.0.1:8123/mdgw";
        this.user = "admin";
        this.user_password = "password";
        this.topic = "mdgw";
        this.broker = "192.168.37.128:9092";
    }

    public sysconfig(String host,
                     int port,
                     String password,
                     int heartbeatInterval,
                     String senderCompId,
                     String targetCompId,
                     String version,
                     int reconnect,
                     int reconnectInterval,
                     String driverName,
                     String url,
                     String user,
                     String user_password,
                     String topic,
                     String broker) {
        this.host = host;
        this.port = port;
        this.password = password;
        this.heartbeatInterval = heartbeatInterval;
        this.senderCompId = senderCompId;
        this.targetCompId = targetCompId;
        this.version = version;
        this.reconnect = reconnect;
        this.reconnectInterval = reconnectInterval;
        this.driverName = driverName;
        this.url = url;
        this.user = user;
        this.user_password = user_password;
        this.topic = topic;
        this.broker = broker;
    }
}
