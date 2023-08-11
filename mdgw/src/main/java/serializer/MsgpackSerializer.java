package serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.Serializers;
import message.Market_300111;
import message.Market_300211;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import util.stringByte;


public class MsgpackSerializer implements Serializer<Market_300111> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Headers headers, Market_300111 data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public byte[] serialize(String s, Market_300111 market300111) {

        try {
            System.out.println( objectMapper.writeValueAsString(market300111));
            return objectMapper.writeValueAsBytes(market300111);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
