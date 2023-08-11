package dataType;

import org.jetbrains.annotations.TestOnly;
import util.BinaryUtil;
import util.int64;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocalTimestamp extends int64 {

    public LocalTimestamp(){
        super();
    }
    public LocalTimestamp(long value) {
        super(value);
    }
    // 本地时间戳
    //YYYYMMDDHHMMSSsss（毫秒），
    //YYYY  =  0000-9999,  MM  =  01-12,
    //DD = 01-31, HH = 00-23, MM =
    //00-59,  SS  =  00-60  (秒)，sss=000-999
    //(毫秒)。
    public String toDateType(){
        String content = String.valueOf(this.getValue());
        content = content.substring(0, 4) + "-"
                + content.substring(4, 6) + "-"
                + content.substring(6, 8) + " "
                + content.substring(8, 10) + ":"
                + content.substring(10,12) + ":"
                + content.substring(12,14) + "."
                + content.substring(14,17);
        return content;
    }

}
