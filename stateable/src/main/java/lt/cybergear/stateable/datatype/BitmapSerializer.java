package lt.cybergear.stateable.datatype;

import android.graphics.Bitmap;
import android.util.Log;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by marius on 15.4.6.
 */
public class BitmapSerializer extends JsonSerializer<Bitmap> {

    @Override
    public void serialize(Bitmap value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        value.compress(Bitmap.CompressFormat.PNG, 100, stream);
        value.recycle();
        gen.writeBinary(stream.toByteArray());
    }

}
