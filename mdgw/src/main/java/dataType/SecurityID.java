package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.stringByte;

public class SecurityID extends stringByte {
    //证券代码
    @JsonIgnoreProperties
    public static int LENGTH = 8;

    public SecurityID(String value) {
        super(value, 8);
    }


}
