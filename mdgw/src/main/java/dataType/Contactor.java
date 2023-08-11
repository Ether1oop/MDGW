package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.stringByte;

public class Contactor extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 12;

    public Contactor(String value) {
        super(value, 12);
    }
}
