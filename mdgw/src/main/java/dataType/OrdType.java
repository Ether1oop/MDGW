package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.stringByte;

public class OrdType extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 1;

    public OrdType(String value) {
        super(value, 1);
    }
}
