package util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;


@NoArgsConstructor
public class stringByte {
    @JsonIgnore
    @Setter
    private int byte_size;

    @Setter
    @Getter
    private String value;



    public stringByte(String value, int byte_size){
        this.value = value.trim();
        this.byte_size = byte_size;
    }

//    public stringByte(int byte_size){
//        this.byte_size = byte_size;
//        this.value = stringByte.rightPad("", byte_size, ' ');
//    }

    public byte[] toBytes() {
        ByteBuffer allocate = ByteBuffer.allocate(byte_size);
        allocate.put(stringByte.rightPad(value, byte_size, ' ').getBytes(StandardCharsets.UTF_8));
        return allocate.array();
    }

    public void resumeFrom(byte[] bytes) {
        value = new String(bytes, StandardCharsets.UTF_8).trim();
    }

    public static String rightPad(String value, int size, char padChar){
        StringBuilder builder = new StringBuilder(value);
        for(int i = 0; i < size - value.length(); i++) {
            builder.append(padChar);
        }
        return builder.toString();
    }

    public String toString() {
        return value;
    }

    public int getLength(){
        return byte_size;
    }

    public void setByte_size(int byte_size) {
        this.byte_size = byte_size;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
