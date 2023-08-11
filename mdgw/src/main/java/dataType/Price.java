package dataType;

import lombok.NoArgsConstructor;
import util.BinaryUtil;
import util.int64;
@NoArgsConstructor
public class Price extends int64 {
    public Price(long value) {
        super(value);
    }
    // 价格，N13+(4)
}
