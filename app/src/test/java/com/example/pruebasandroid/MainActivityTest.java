package com.example.pruebasandroid;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ListView;

import com.example.pruebasandroid.auxlib.CustomTestRunner;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.ShadowListView;
import org.robolectric.shadows.ShadowToast;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Jordi on 14/12/2015.
 */
@RunWith(CustomTestRunner.class)
public class MainActivityTest {


    MainActivity main;
    ListView listView;
    BroadcastReceiver receiver;
    Context context;


    @Before
    public void init()
    {
        context= RuntimeEnvironment.application.getApplicationContext();
        main = Robolectric.setupActivity(MainActivity.class);
        listView = (ListView) main.findViewById(R.id.listView);
        receiver=main.getBroadcastReceiver();
    }

    @After
    public void teardown() {
    }

    @Test
    public void listViewItemClickTest()
    {
        ShadowListView shadowListView = Shadows.shadowOf(listView);
        shadowListView.performItemClick(0);

        Intent expectedIntentItem0 = new Intent(main, WidgetActivity.class);
        assertThat(Shadows.shadowOf(main).getNextStartedActivity()).isEqualTo(expectedIntentItem0);

        shadowListView.performItemClick(1);
        Intent expectedIntentItem1 = new Intent(main, VolleyTest.class);
        ShadowIntent shadowIntent=Shadows.shadowOf(expectedIntentItem1);
        assertThat(shadowIntent.getComponent().getClassName().equals(VolleyTest.class));
    }

    @Test
    public void broadcastReceiverTest()
    {
        Intent receiverIntent = new Intent();
        receiverIntent.putExtra("result", Activity.RESULT_OK);
        receiver.onReceive(context,receiverIntent);

        assertThat(ShadowToast.getTextOfLatestToast().equals("Broadcast received"));

        ShadowToast.reset();
        receiverIntent.putExtra("result", Activity.RESULT_CANCELED);
        receiver.onReceive(context, receiverIntent);

        assertNull(ShadowToast.getTextOfLatestToast());

        List<ShadowApplication.Wrapper> registeredReceivers =  ShadowApplication.getInstance().getRegisteredReceivers();
        Assert.assertFalse(registeredReceivers.isEmpty());
    }

    @Test
    public void serviceTest()
    {
        Intent serviceIntent = Shadows.shadowOf(main).getNextStartedService();
        assertNotNull(serviceIntent);
    }

    @Test
    public void onPauseTest()
    {
        main.onPause();
        List<ShadowApplication.Wrapper> registeredReceivers =  ShadowApplication.getInstance().getRegisteredReceivers();
        Assert.assertTrue(registeredReceivers.isEmpty());
    }

    @Test
    public void asyncTaskTest()
    {
        MainActivity.TestAsyncTask task = new MainActivity.TestAsyncTask();

        task.execute();

        Robolectric.flushBackgroundThreadScheduler();

        try {
            assertNotNull(task.get());
            assertEquals("AsyncTask message",task.get(),"AsyncTask");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        task.execute("test2");

        Robolectric.flushBackgroundThreadScheduler();

        try {
            assertNotNull(task.get());
            assertEquals("AsyncTask message 2",task.get(),"test2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
