package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.stringByte;

public class InvestorID extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 10;

    public InvestorID(String value) {
        super(value, 10);
    }
}
