package database;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class User {
    public static int JJJ = 1;

    int id;
    String name;

    public User(){
        this.id = 1;
        this.name = "rte";
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }


}
