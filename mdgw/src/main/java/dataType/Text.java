package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.stringByte;

public class Text extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 200;

    public Text(String value) {
        super(value, 200);
    }

}
