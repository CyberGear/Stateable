package lt.cybergear.stateable;

import android.os.Bundle;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marius Kavoliūnas on 14.10.14.
 */
public class StateUtil {
    private static final String TAG = StateUtil.class.getSimpleName();
    private static boolean isDebug = false;
    private static boolean saveNulls = false;
    private static final String KEY_PREFIX = "SU.";

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final TypeFactory FACTORY = MAPPER.getTypeFactory();

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        MAPPER.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        MAPPER.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static void setDebug(boolean isDebug) {
        StateUtil.isDebug = isDebug;
    }

    public static void setSaveNulls(boolean saveNulls) {
        StateUtil.saveNulls = saveNulls;
    }

    public static void saveState(Object stateable, Bundle outState) {
        List<Field> stateFields = getAccessibleStateFields(stateable);
        for (Field field : stateFields) {
            putField(field, stateable, outState);
        }
        printBundleIfDebug("saveState", outState);
    }

    public static void restoreState(Object statable, Bundle savedState) {
        if (savedState == null) {
            return;
        }
        List<Field> stateFields = getAccessibleStateFields(statable);
        for (Field field : stateFields) {
            pullField(field, statable, savedState);
        }
        printBundleIfDebug("restoreState", savedState);
    }

    private static List<Field> getAccessibleStateFields(Object stateable) {
        List<Field> fields = new ArrayList<>();
        for (Field field : stateable.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(StateVariable.class)) {
                field.setAccessible(true);
                fields.add(field);
            }
        }
        return fields;
    }

    private static void putField(Field field, Object stateable, Bundle outState) {
        try {
            Object value = field.get(stateable);
            if (value != null || saveNulls) {
                outState.putString(KEY_PREFIX + field.getName(), MAPPER.writeValueAsString(value));
            }
        } catch (IOException e) {
            Log.e(TAG, "Json mapping error: " + field.getName(), e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Inaccessible field", e);
        }
    }

    private static void pullField(Field field, Object stateable, Bundle savedState) {
        try {
            setField(field, stateable, savedState);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Inaccessible field", e);
        } catch (IOException e) {
            Log.e(TAG, "Json mapping error", e);
        } catch (Exception e) {
            throw new RuntimeException("Unknown exception on " + field.getName() + " in class " + stateable.getClass().getName(), e);
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
