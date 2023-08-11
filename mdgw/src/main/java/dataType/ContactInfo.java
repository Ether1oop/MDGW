package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.stringByte;

public class ContactInfo extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 30;

    public ContactInfo(String value) {
        super(value, 30);
    }
}
