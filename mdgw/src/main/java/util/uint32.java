package util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
public class uint32 implements Serializable {
    private byte[] value = new byte[4];

    public uint32(long i){
        byte[] temp = BinaryUtil.longToBytes(i);
        value[0] = temp[4];
        value[1] = temp[5];
        value[2] = temp[6];
        value[3] = temp[7];
    }

    public long getValue(){
        return BinaryUtil.bytesToUint(value);
    }

    public byte[] toBytes(){
        return value;
    }

    public static int getLength(){
        return 4;
    }

    public String toString(){
        return String.valueOf(getValue());
    }

    public void setValue(long i) {
        byte[] temp = BinaryUtil.longToBytes(i);
        value[0] = temp[4];
        value[1] = temp[5];
        value[2] = temp[6];
        value[3] = temp[7];
    }
}
