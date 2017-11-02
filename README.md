Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Add the dependency

```groovy
dependencies {
		compile 'com.github.yasincidem:SimplestViewPagerIndicator:0.2'
	}
```

Set your xml
```xml
 <com.yasincidem.simplestviewpagerindicator.SimplestViewPagerIndicator
        android:id="@+id/indicator"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|center">

    </com.yasincidem.simplestviewpagerindicator.SimplestViewPagerIndicator>
```

Set your java
```java
SimplestViewPagerIndicator pagerIndicator;
ViewPager viewPager;
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        pagerIndicator = findViewById(R.id.indicator);
        
        viewPager.setAdapter(sectionsPagerAdapter);
        pagerIndicator.setupWithViewPager(viewPager);
    }
```

