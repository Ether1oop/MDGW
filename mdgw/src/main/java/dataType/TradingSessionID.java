package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.stringByte;

public class TradingSessionID extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 4;

    public TradingSessionID(String value) {
        super(value, 4);
    }

}
