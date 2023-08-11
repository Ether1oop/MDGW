package dataType;

import lombok.NoArgsConstructor;
import util.BinaryUtil;
import util.int64;
@NoArgsConstructor
public class MDEntryPx extends int64 {
    public MDEntryPx(long value) {
        super(value);
    }
    // 行情条目价格, N18（6）

}
