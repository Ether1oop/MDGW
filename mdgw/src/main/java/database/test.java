package database;

import dataType.MDStreamID;
import lombok.Getter;

public class test{
    public static int JJJ = 1;
    @Getter
    MDStreamID mdStreamID = new MDStreamID("sad");


    public MDStreamID getMdStreamID() {
        return mdStreamID;
    }


}
