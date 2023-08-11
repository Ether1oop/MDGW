package dataType;

import lombok.NoArgsConstructor;
import util.BinaryUtil;
import util.int64;
@NoArgsConstructor
public class Amt extends int64 {
    public Amt(long value) {
        super(value);
    }
    // 金额 N18(4)

}
