package util;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
public class int64 implements Serializable {
    long value;

    public int64(){}
    public int64(long value) {
        this.value = value;
    }

    public static int getLength(){
        return 64 / 8;
    }

    public long getValue(){
        return value;
    }

    public byte[] toBytes(){
        return BinaryUtil.longToBytes(value);
    }

    public String toString(){
        return String.valueOf(getValue());
    }

    public void setValue(long value) {
        this.value = value;
    }
}
