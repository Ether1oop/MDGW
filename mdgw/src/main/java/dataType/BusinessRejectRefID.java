package dataType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.stringByte;

public class BusinessRejectRefID extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 10;

    public BusinessRejectRefID(String value) {
        super(value, 10);
    }
}
