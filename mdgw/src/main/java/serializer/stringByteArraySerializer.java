package serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import util.stringByte;

import java.io.IOException;

public class stringByteArraySerializer<T extends stringByte> extends JsonSerializer<T[]> {
    @Override
    public void serialize(T[] ts, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (int i = 0; i < ts.length; i++){
            jsonGenerator.writeString(ts[i].toString());
        }
        jsonGenerator.writeEndArray();
    }
}
