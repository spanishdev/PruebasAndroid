package com.example.pruebasandroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Jordi on 02/07/2015.
 */
public class VolleyTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://tenealive.com/DomoticaMobile/ws/send2.php?command=camslist&mac=64:70:02:BA:03:40&t=20150605120516&auth=e0818cf3faefb87d74d47c0c718187b34c923929";

        StringRequest stringRQ = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String aux = s.replace("\\r", "");
                aux=aux.replace("\\n"," \n ");
                Log.e("RESPONSE", aux);
                ((TextView)findViewById(R.id.textView)).setText(aux);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("ERROR", volleyError.getMessage());
            }
        });

        String img_url="http://192.168.11.30:8011/snapshot.cgi?user=admin&pwd=tenea2012";

        ImageRequest imgRQ = new ImageRequest(img_url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                ((ImageView)findViewById(R.id.imageView)).setImageBitmap(bitmap);
            }
        },0,0,null, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("ERROR", volleyError.getMessage());
            }
        });


        queue.add(stringRQ);
        queue.add(imgRQ);
        queue.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
