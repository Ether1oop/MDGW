package dataType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NoArgsConstructor;
import util.stringByte;

@NoArgsConstructor
@JsonIgnoreProperties
public class MDStreamID extends stringByte {
    @JsonIgnore
    public static int LENGTH = 3;

    public MDStreamID(String value) {
        super(value, 3);
    }


}
