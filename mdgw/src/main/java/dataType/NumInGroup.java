package dataType;

import lombok.NoArgsConstructor;
import util.uint32;
@NoArgsConstructor
public class NumInGroup extends uint32{
    //重复数
    //表示重复组的个数，正数
    public NumInGroup(long i) {
        super(i);
    }

}
