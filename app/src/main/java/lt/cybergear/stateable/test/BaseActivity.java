package lt.cybergear.stateable.test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lt.cybergear.stateable.StateUtil;
import lt.cybergear.stateable.StateVariable;


public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        StateUtil.setDebug(BuildConfig.DEBUG);
        StateUtil.restoreState(this, savedState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        StateUtil.saveState(this, outState);
        super.onSaveInstanceState(outState);
    }
}
