package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.stringByte;

public class InvestorName extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 120;

    public InvestorName(String value) {
        super(value, 120);
    }
}
