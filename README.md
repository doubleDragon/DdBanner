## Description

This is an attempt to make Banner easier to use.

Features built in:
-  auto play banner with ViewPager
-  custom simple indicator base on [CircleIndicator](https://github.com/ongakuer/CircleIndicator)

## AttributeSet
```xml
<declare-styleable name="DdIndicator">
        <attr name="dd_width" format="dimension"/>
        <attr name="dd_height" format="dimension"/>
        <attr name="dd_margin" format="dimension"/>
        <attr name="dd_animator" format="reference"/>
        <attr name="dd_animator_reverse" format="reference"/>
        <attr name="dd_drawable" format="reference"/>
        <attr name="dd_drawable_unselected" format="reference"/>
    </declare-styleable>
    
    <declare-styleable name="DbBanner">
        <attr name="dd_targetX" format="integer"/>
        <attr name="dd_targetY" format="integer"/>
        <attr name="dd_loop_delay" format="integer"/>
        <attr name="dd_change_duration" format="integer"/>
    </declare-styleable>
```

##Integration

Step 1.Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

```
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```
Step 2. Add the dependency
```
    dependencies {
        compile 'com.github.doubleDragon:DdBanner:v1.0.0'
    }
```

## Usage
   Not complete
