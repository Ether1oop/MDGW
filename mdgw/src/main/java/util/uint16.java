package util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class uint16 implements Serializable {
    private byte[] value = new byte[2];

    public uint16(int i){
        value[0] = BinaryUtil.intToBytes(i)[2];
        value[1] = BinaryUtil.intToBytes(i)[3];
    }

    public int getValue() {
        return BinaryUtil.bytesToUshort(value);
    }

    public byte[] toBytes(){
        return value;
    }

    public static int getLength(){
        return 2;
    }

    public String toString(){
        return String.valueOf(getValue());
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public void setValue(int i){
        value[0] = BinaryUtil.intToBytes(i)[2];
        value[1] = BinaryUtil.intToBytes(i)[3];
    }
}
