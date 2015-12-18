package com.example.pruebasandroid.sqlite;

import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jordi on 14/12/2015.
 */
@RunWith(RobolectricTestRunner.class)
public class DatabaseTest {

    SQLiteDatabase db;
    Database database;

    @Before
    public void setUp() throws Exception {
        String filePath = getClass().getResource("/"+ Database.DATABASE_NAME).toURI().getPath();
        db = SQLiteDatabase.openDatabase(
                (new File(filePath)).getAbsolutePath(),
                null,
                SQLiteDatabase.OPEN_READWRITE);

        database=new Database(RuntimeEnvironment.application, filePath,1);

        // perform any db operations you want here

        assertNotNull(database);
    }

    @After
    public void tearDown()
    {
        db.close();
    }


    @Test(expected = IllegalArgumentException.class)
    public void testExceptions()
    {
        assertTrue(database.existTabla1(null));
        assertTrue(database.existTabla2(null));
    }

    @Test
    public void testInsertion()
    {
        assertTrue(database.getTabla1Count()>0);

        assertTrue(database.existTabla1("nombre1"));

        assertFalse(database.existTabla1("nombre3"));

        assertTrue(database.insertTabla1("testOne", 100) > -1);

        assertEquals(-2, database.insertTabla1("testOne",1010));



    }


}
