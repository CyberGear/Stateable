# CyberGear Stateable

*Version:* ***0.0.1***, *Min SDK version:* ***10*** *(* *Android:* ***2.3.3*** *)*

---

## Description

Tool witch makes state saving in Android so easy

## Including to your project

Project build.gradle
```Groovy
allprojects {
    repositories {
        ...
        maven {
            url "https://github.com/CyberGear/Stateable/raw/master/repo/"
        }
    }
}
```

module build.gradle
```Groovy
dependencies {
    ...
    compile 'lt.cybergear:stateable:0.0.1'
}
```

## Usage Example

```Java
public class MainActivity extends ActionBarActivity {

    @StateVariable
    Collection<String> strings2;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_main);
        StateUtil.restoreState(this, savedState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        StateUtil.saveState(this, outState);
        super.onSaveInstanceState(outState);
    }
}
```