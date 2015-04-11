package lt.cybergear.stateable.test;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ImageView;

import com.fasterxml.jackson.datatype.joda.JodaModule;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.JodaTimePermission;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import lt.cybergear.stateable.StateUtil;
import lt.cybergear.stateable.StateVariable;


public class MainActivity2 extends BaseActivity {

    @StateVariable
    String variable;
    @StateVariable
    Wrapper wrapper = new Wrapper();
    @StateVariable
    DateTime jodaTime;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_main);
        StateUtil.getMapper().registerModule(new JodaModule());

        if (savedState == null) {
            variable = "value";
            wrapper.bitmap = ((BitmapDrawable)getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap();
            jodaTime = new DateTime(
                    2015, 8, 21, 17, 15,
                    DateTimeZone.forTimeZone(Calendar.getInstance().getTimeZone()));
            Log.e("TAGGAS", "init " + jodaTime.toString("yyyy-MM-dd kk:mm"));
        } else {
            ((ImageView)findViewById(R.id.image)).setImageBitmap(wrapper.bitmap);
            Log.e("TAGGAS", "rest " + jodaTime.toString("yyyy-MM-dd kk:mm"));
        }
    }

    public static class Wrapper {
        Bitmap bitmap;

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }
    }
}
