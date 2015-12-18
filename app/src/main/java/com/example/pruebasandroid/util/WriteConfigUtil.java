package com.example.pruebasandroid.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.pruebasandroid.sqlite.Database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by Jordi on 14/12/2015.
 */
public class WriteConfigUtil {

    public static void writeConfiguration(Context ctx) {
        BufferedWriter writer = null;
        try {
            FileOutputStream openFileOutput =
                    ctx.openFileOutput("config.txt", Context.MODE_PRIVATE);
            openFileOutput.write("This is a test1.".getBytes());
            openFileOutput.write("This is a test2.".getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void exportDB(Context context) {
        File sd = Environment.getExternalStorageDirectory();
        File currentDB = context.getDatabasePath(Database.DATABASE_NAME);

        Log.i("DATA DIR",currentDB.getPath());

        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath =  Database.DATABASE_NAME;
        String backupDBPath = Database.DATABASE_NAME;
//        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
