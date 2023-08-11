package database.Mapper;

import database.POJO.message_300192;
import message.OrderMarket_300192;

import java.util.List;

public interface Mapper300192 {
    List<message_300192> selectAll();

    void addAll(message_300192 message);
}
