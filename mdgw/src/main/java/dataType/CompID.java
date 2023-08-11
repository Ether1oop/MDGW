package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import util.BinaryUtil;
import util.stringByte;

public class CompID extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 20;

    public CompID(String value){
        super(value, 20);
    }


}
