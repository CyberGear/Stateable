package lt.cybergear.stateable;

import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lt.cybergear.stateable.datatype.AndroidBitmapModule;

/**
 * Created by Marius Kavoliūnas on 14.10.14.
 */
public class StateUtil {
    private static final String TAG = StateUtil.class.getSimpleName();
    private static boolean isDebug = false;
    private static boolean throwExceptions = true;
    private static boolean saveNulls = false;
    private static final String KEY_PREFIX = "SU.";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final LruCache<String, List<Field>> fieldCache = new LruCache<>(300);

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        MAPPER.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        MAPPER.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.setTimeZone(Calendar.getInstance().getTimeZone());
        MAPPER.registerModule(new AndroidBitmapModule());
    }

    public static void setDebug(boolean isDebug) {
        StateUtil.isDebug = isDebug;
    }

    public static void setThrowExceptions(boolean throwExceptions) {
        StateUtil.throwExceptions = throwExceptions;
    }

    public static void setSaveNulls(boolean saveNulls) {
        StateUtil.saveNulls = saveNulls;
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }

    public static void saveState(Object stateable, Bundle outState) {
        List<Field> stateFields = getAccessibleStateFields(stateable);
        for (Field field : stateFields) {
            pushField(field, stateable, outState);
        }
        printBundleIfDebug("saveState: " + stateable.getClass(), outState);
    }

    public static void restoreState(Object stateable, Bundle savedState) {
        if (savedState == null) {
            return;
        }
        List<Field> stateFields = getAccessibleStateFields(stateable);
        for (Field field : stateFields) {
            pullField(field, stateable, savedState);
        }
        printBundleIfDebug("restoreState: " + stateable.getClass(), savedState);
    }

    private static List<Field> getAccessibleStateFields(Object stateable) {
        String cacheKey = stateable.getClass().getCanonicalName();
        List<Field> fields = fieldCache.get(cacheKey);
        if (fields != null) {
            return fields;
        }
        fields = new ArrayList<>();
        for (Field field : stateable.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(StateVariable.class)) {
                field.setAccessible(true);
                fields.add(field);
            }
        }
        fieldCache.put(cacheKey, fields);
        return fields;
    }

    private static void pushField(Field field, Object stateable, Bundle outState) {
        try {
            Object value = field.get(stateable);
            if (value != null || saveNulls) {
                outState.putString(KEY_PREFIX + field.getName(), MAPPER.writeValueAsString(value));
            }
        } catch (IOException e) {
            if (isDebug && throwExceptions) {
                throw new StateableException("Json mapping error: " + field.getName(), e);
            }
        } catch (IllegalAccessException e) {
            if (isDebug && throwExceptions) {
                throw new StateableException("Inaccessible field: " + field.getName(), e);
            }
        }
    }

    private static void pullField(Field field, Object stateable, Bundle savedState) {
        try {
            setField(field, stateable, savedState);
        } catch (IllegalAccessException e) {
            if (isDebug && throwExceptions) {
                throw new StateableException("Inaccessible field: " + field.getName(), e);
            }
        } catch (IOException e) {
            if (isDebug && throwExceptions) {
                throw new StateableException("Json mapping error: " + field.getName(), e);
            }
        } catch (Exception e) {
            if (isDebug && throwExceptions) {
                throw new StateableException("Unknown exception on " + field.getName() + " in class " + stateable.getClass().getName(), e);
            }
        }
    }

    private static void setField(Field field, Object stateable, Bundle state
    ) throws IOException, IllegalAccessException {
        if (state.containsKey(KEY_PREFIX + field.getName())) {
            field.set(
                    stateable,
                    MAPPER.readValue(state.getString(KEY_PREFIX + field.getName()), field.getType())
            );
        }
    }

    private static void printBundleIfDebug(String tag, Bundle outState) {
        if (isDebug) {
            Log.d(TAG, tag + ":");
            Log.d(TAG, "| {   ");
            for (String key : outState.keySet()) {
                if (key.startsWith(KEY_PREFIX)) {
                    Log.d(TAG, "|     " + key.substring(KEY_PREFIX.length()) + " : " + outState.get(key) + " ;");
                }
            }
            Log.d(TAG, "| }");
        }
    }

}
