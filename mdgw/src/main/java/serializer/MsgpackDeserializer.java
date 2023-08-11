package serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledDirectByteBuf;
import message.Market_300111;
import org.apache.kafka.common.serialization.Deserializer;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import util.Util;

import java.io.IOException;

public class MsgpackDeserializer implements Deserializer<String> {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String deserialize(String s, byte[] bytes) {
        try {
//            String temp = "{\"origTime\":{\"value\":20230803170445000},\"channelNo\":{\"value\":1013},\"mdStreamID\":\"010\",\"securityID\":\"300221\",\"securityIDSource\":\"102\",\"tradingPhaseCode\":\"E0\",\"prevClosePx\":{\"value\":57100},\"numTrades\":{\"value\":566},\"totalVolumeTrade\":{\"value\":71382500},\"totalValueTrade\":{\"value\":39778733700},\"noMDEntries\":{\"value\":16},\"mdEntryType\":[\"2\",\"4\",\"7\",\"8\",\"x1\",\"x2\",\"xe\",\"xf\",\"0\",\"0\",\"0\",\"1\",\"1\",\"1\",\"1\",\"x5\"],\"mdEntryPx\":[{\"value\":5730000},{\"value\":5670000},{\"value\":5730000},{\"value\":5560000},{\"value\":20000},{\"value\":10000},{\"value\":6850000},{\"value\":4570000},{\"value\":5720000},{\"value\":5710000},{\"value\":5560000},{\"value\":5730000},{\"value\":5740000},{\"value\":5750000},{\"value\":5760000},{\"value\":0}],\"mdEntrySize\":[{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":1400},{\"value\":20000},{\"value\":22316100},{\"value\":25000},{\"value\":136300},{\"value\":136000},{\"value\":147300},{\"value\":0}],\"mdPriceLevel\":[{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":1},{\"value\":2},{\"value\":3},{\"value\":1},{\"value\":2},{\"value\":3},{\"value\":4},{\"value\":0}],\"numberOfOrders\":[{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0}],\"noOrders\":[{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0},{\"value\":0}],\"orderQty\":[null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null],\"length\":0,\"msgType\":{\"value\":300111},\"bodyLength\":{\"value\":581},\"checksum\":{\"value\":223}}";
//            System.out.println(new String(bytes));
//            objectMapper.readValue(temp, Market_300111.class);
            return objectMapper.readValue(bytes, String.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
