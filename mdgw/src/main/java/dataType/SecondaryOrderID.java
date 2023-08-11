package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.stringByte;

public class SecondaryOrderID extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 16;

    public SecondaryOrderID(String value) {
        super(value, 16);
    }
}
