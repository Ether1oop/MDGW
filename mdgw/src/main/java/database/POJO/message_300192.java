package database.POJO;

import dataType.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class message_300192 {
    private int channelNo;
    private long applSeqNum;
    private String mdStreamID;
    private String securityID;
    private String securityIDSource;
    private long price;
    private long orderQty;
    private String side;
    private long transactTime;
    private String  ordType;

    public message_300192() {
        channelNo = 3;
        applSeqNum = 33;
        mdStreamID = "333";
        securityID = "3333";
        securityIDSource = "33333";
        price = 4;
        orderQty = 5;
        side = "55";
        transactTime = 99999;
        ordType = "555";
    }
}
