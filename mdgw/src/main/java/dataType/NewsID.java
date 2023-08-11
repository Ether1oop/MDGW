package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.stringByte;

public class NewsID extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 8;

    public NewsID(String value) {
        super(value, 8);
    }
}
