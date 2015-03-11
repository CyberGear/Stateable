package lt.cybergear.stateable.test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lt.cybergear.stateable.StateUtil;
import lt.cybergear.stateable.StateVariable;


public class MainActivity extends ActionBarActivity {

    /** objects */
    /**
     * nulls
     */
    @StateVariable
    private int simple1null;
    @StateVariable
    private float simple2null;
    @StateVariable
    private double simple3null;
    @StateVariable
    private byte simple4null;
    @StateVariable
    private char simple5null;
    @StateVariable
    private Integer simpleClass1null;
    @StateVariable
    private Float simpleClass2null;
    @StateVariable
    private Double simpleClass3null;
    @StateVariable
    private Byte simpleClass4null;
    @StateVariable
    private Character simpleClass5null;
    @StateVariable
    private String simpleClass6null;
    @StateVariable
    private Pojo pojo1null;

    /**
     * not nulls
     */
    @StateVariable
    private int simple1;
    @StateVariable
    private float simple2;
    @StateVariable
    private double simple3;
    @StateVariable
    private byte simple4;
    @StateVariable
    private char simple5;
    @StateVariable
    private Integer simpleClass1;
    @StateVariable
    private Float simpleClass2;
    @StateVariable
    private Double simpleClass3;
    @StateVariable
    private Byte simpleClass4;
    @StateVariable
    private Character simpleClass5;
    @StateVariable
    private String simpleClass6;
    @StateVariable
    private Pojo pojo1;

    /** arrays, collections and maps */
    /**
     * nulls
     */
    Collection<String> strings2null;
    String[] strings3null;
    Map<String, String> strings4null;
    CustomEnum customEnum0;

    /**
     * not nulls
     */
    @StateVariable
    Collection<String> strings2;
    @StateVariable
    Collection<Pojo> strings1;
    @StateVariable
    String[] strings3;
    @StateVariable
    Map<String, Pojo> strings4;
    @StateVariable
    CustomEnum customEnum;


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_main);

        StateUtil.setDebug(BuildConfig.DEBUG);
//        StateUtil.setSaveNulls(BuildConfig.DEBUG);

        StateUtil.restoreState(this, savedState);
        if (savedState == null) {
            simple1 = 12;
            simple2 = 1.2f;
            simple3 = 1.2d;
            simple4 = 0x00000000;
            simple5 = 'A';
            simpleClass1 = 13;
            simpleClass2 = 1.3f;
            simpleClass3 = 1.3d;
            simpleClass4 = simple4;
            simpleClass5 = 'B';
            simpleClass6 = "Testas";
            pojo1 = new Pojo();

            strings1 = Arrays.asList(new Pojo(), new Pojo(), new Pojo());
            strings2 = Arrays.asList("vienas", "du", "trys");
            strings3 = new String[]{"vienas", "du", "trys"};
            strings4 = new HashMap<>();
            strings4.put("key1", new Pojo());
            strings4.put("key2", new Pojo());
            strings4.put("key3", new Pojo());
            customEnum = CustomEnum.VALUE2;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        StateUtil.saveState(this, outState);
        super.onSaveInstanceState(outState);
    }
}
