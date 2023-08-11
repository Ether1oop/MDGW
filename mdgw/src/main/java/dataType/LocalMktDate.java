package dataType;

import util.uint32;

public class LocalMktDate extends uint32{
    //本地市场日期
    //格式 YYYYMMDD，YYYY = 0000-9999,
    //MM = 01-12, DD = 01-31

    public LocalMktDate(long i) {
        super(i);
    }


}
