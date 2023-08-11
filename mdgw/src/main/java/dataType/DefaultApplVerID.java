package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.stringByte;

public class DefaultApplVerID extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 32;

    public DefaultApplVerID(String value) {
        super(value, 32);
    }

}
