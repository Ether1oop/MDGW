package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.stringByte;

public class MemberID extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 6;

    public MemberID(String value) {
        super(value, 6);
    }
}
