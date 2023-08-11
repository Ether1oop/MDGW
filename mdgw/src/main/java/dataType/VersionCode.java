package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.stringByte;

public class VersionCode extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 16;

    public VersionCode(String value) {
        super(value, 16);
    }
}
