package util;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
public class uint8 implements Serializable {
    // 存放两个byte，以避免符号位丢失
    private byte value;

    public uint8(int i){
        value = BinaryUtil.intToBytes(i)[3];
    }

    public int getValue() {
        return BinaryUtil.bytesToUbyte(value);
    }

    public byte[] toBytes(){
        return new byte[]{value};
    }

    public static int getLength(){
        return 1;
    }

    public String toString(){
        return String.valueOf(getValue());
    }

    public void setValue(int i) {
        this.value = BinaryUtil.intToBytes(i)[3];
    }
}
