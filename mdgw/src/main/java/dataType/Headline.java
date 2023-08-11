package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.stringByte;

public class Headline extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 128;

    public Headline(String value) {
        super(value, 128);
    }
}
