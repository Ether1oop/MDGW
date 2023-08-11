package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.stringByte;

public class Memo extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 160;

    public Memo(String value) {
        super(value, 160);
    }
}
