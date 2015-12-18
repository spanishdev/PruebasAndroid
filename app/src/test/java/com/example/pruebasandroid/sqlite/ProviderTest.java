package com.example.pruebasandroid.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.pruebasandroid.auxlib.CustomTestRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowContentResolver;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Jordi on 17/12/2015.
 */
@RunWith(CustomTestRunner.class)
public class ProviderTest {

    Provider provider;
    ShadowContentResolver shadowResolver;

    @Before
    public void setup()
    {
        provider = new Provider();
        shadowResolver = Shadows.shadowOf(RuntimeEnvironment.application.getContentResolver());
        provider.onCreate();
        ShadowContentResolver.registerProvider(Provider.AUTHORITY,provider);
    }

    @After
    public void teardown() {
    }

    @Test
    public void testContentProvider()
    {
        Uri uri = Uri.withAppendedPath(Provider.MAIN_URL, "tabla1");
        ContentValues values = new ContentValues();
        values.put("name","nombre");
        values.put("numero",3);
        shadowResolver.insert(uri, values);
        Cursor cursorT1 = shadowResolver.query(uri, null, null, null, null);
        assertTrue(cursorT1.getCount() > 0);

        values = new ContentValues();
        values.put("name","segundo_nombre");
        values.put("numero",5);
        shadowResolver.insert(uri, values);
        cursorT1 = shadowResolver.query(uri, null, null, null, null);
        assertEquals(2,cursorT1.getCount());

        assertTrue(shadowResolver.delete(uri, "name=?", new String[]{"nombre"}) > 0);
        assertTrue(shadowResolver.delete(uri,"numero=?",new String[] { "55" })==0);

        cursorT1 = shadowResolver.query(uri, null, null, null, null);
        assertEquals(1,cursorT1.getCount());

        ContentValues updateVals = new ContentValues();
        updateVals.put("name","primer_nombre");
        updateVals.put("numero",100);

        assertTrue(shadowResolver.update(uri, updateVals,"name=?", new String[]{"segundo_nombre"}) > 0);

        cursorT1 = shadowResolver.query(uri, null, "numero=?",new String[]{ "100" }, null);
        assertTrue(cursorT1.moveToNext());
        assertTrue(cursorT1.getString(1).equals("primer_nombre"));
    }

}
