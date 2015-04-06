package lt.cybergear.stateable.datatype;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import java.io.IOException;

/**
 * Created by marius on 15.4.6.
 */
public class BitmapKeyDeserializer extends KeyDeserializer {

    @Override
    public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Log.e("TAGGAS", ":) BitmapKeyDeserializer");
        return null;
    }

}
