package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.stringByte;

public class InvestorType extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 2;

    public InvestorType(String value) {
        super(value, 2);
    }
}
