package lt.cybergear.stateable.datatype;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Created by marius on 15.4.6.
 */
public class AndroidBitmapDeserializer extends JsonDeserializer<Bitmap> {

    @Override
    public Bitmap deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        byte[] value = p.getBinaryValue();
        return BitmapFactory.decodeByteArray(value, 0, value.length);
    }

}
