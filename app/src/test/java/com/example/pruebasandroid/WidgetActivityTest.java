package com.example.pruebasandroid;

import android.widget.EditText;

import com.example.pruebasandroid.auxlib.CustomTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowActivity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Jordi on 18/12/2015.
 */
@RunWith(CustomTestRunner.class)
public class WidgetActivityTest {

    WidgetActivity widgetActTest;

    @Before
    public void init()
    {

//        widgetActTest = Robolectric.setupActivity(WidgetActivity.class);
        widgetActTest = Robolectric.buildActivity(WidgetActivity.class).create().get();
    }

    @Test
    public void widgetActivityTest()
    {
        ShadowActivity shadowWidgetActivity = Shadows.shadowOf(widgetActTest);
        assertThat(((EditText)shadowWidgetActivity.findViewById(R.id.editText)).getText().equals("Test"));
        shadowWidgetActivity.findViewById(R.id.button).performClick();

        assertThat(((EditText)shadowWidgetActivity.findViewById(R.id.editText)).getText().equals("Hola"));

    }
}
