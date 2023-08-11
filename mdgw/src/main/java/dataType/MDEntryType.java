package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NoArgsConstructor;
import util.stringByte;
@NoArgsConstructor
public class MDEntryType extends stringByte {
    @JsonIgnoreProperties
    public static int LENGTH = 2;

    public MDEntryType(String value) {
        super(value, 2);
    }
}
