package lt.cybergear.stateable.test;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

import com.fasterxml.jackson.datatype.joda.JodaModule;

import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
            jodaTime = new DateTime(2015, 8, 21, 5, 25);
        } else {
            ((ImageView)findViewById(R.id.image)).setImageBitmap(wrapper.bitmap);
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
