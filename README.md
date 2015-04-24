# CyberGear Stateable

*Version:* ***0.0.3***, *Min SDK version:* ***10*** *(* *Android:* ***2.3.3*** *)*

 - Collecting StateVariables through all parents.
 - Added Bitmap serializer.
 - StateUtil ObjectMapper become accessible for customizations.
 - Do not lose time zone after restoring dates (tested with Joda module)
 - Improved logging (you can add your apps prefix to tag)
 - You can set tag right padding (it is useful while watching through terminal)
 - On some fails to map variables throws StateableException
 - You can prevent StateUtil to throw exception on mapping errors
 - It is possible to save nulls to state
 - Fixed bugs while getting list item type

*Version:* ***0.0.2***, *Min SDK version:* ***10*** *(* *Android:* ***2.3.3*** *)*

 - added some caching for faster working with reflection

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
    compile 'lt.cybergear:stateable:0.0.3'
}
```

## Usage Example

```Java
public class MainActivity extends ActionBarActivity {

    @StateVariable
    List<String> someVariableYouDontWantToLose;

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