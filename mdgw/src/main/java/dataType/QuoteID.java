package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.stringByte;

public class QuoteID extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 10;

    public QuoteID(String value) {
        super(value, 10);
    }
}
