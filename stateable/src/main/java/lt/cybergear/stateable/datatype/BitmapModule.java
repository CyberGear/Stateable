package lt.cybergear.stateable.datatype;

import android.graphics.Bitmap;

import com.fasterxml.jackson.databind.module.SimpleModule;

import lt.cybergear.stateable.BuildConfig;

public class BitmapModule extends SimpleModule {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    public BitmapModule() {
        super(BuildConfig.APPLICATION_ID + "_" + BuildConfig.VERSION_NAME);
        addDeserializer(Bitmap.class, new BitmapDeserializer());
        addSerializer(Bitmap.class, new BitmapSerializer());
        // don't known if I need it yet
//        addKeyDeserializer(Bitmap.class, new BitmapKeyDeserializer());
    }

    @Override
    public String getModuleName() {
        return getClass().getSimpleName();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }
}