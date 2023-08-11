package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.stringByte;

public class BusinessRejectText extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 50;

    public BusinessRejectText(String value) {
        super(value, 50);
    }
}
