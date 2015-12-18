package com.example.pruebasandroid;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pruebasandroid.services.CustomService;
import com.example.pruebasandroid.sqlite.Database;
import com.example.pruebasandroid.util.WriteConfigUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends Activity {

    @Bind(R.id.listView) ListView listView;
     BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

//        listView = (ListView) findViewById(R.id.listView);

        String[] strings = new String[]{
                "Test Edit Text",
                "Volley Test",
                "Test 3"
        };

        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0:
                        startActivity(new Intent(MainActivity.this, WidgetActivity.class));
                        break;

                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, VolleyTest.class);
                        intent1.putExtra("test", "hola");
                        startActivity(intent1);
                        break;
                }
            }
        });

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();

                if (bundle != null)
                {
                    int resultCode = bundle.getInt("result");

                    if (resultCode == RESULT_OK)
                    {
                        Toast.makeText(MainActivity.this,"Broadcast received",Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
        Database dbase = new Database(this);
        dbase.insertTabla1("nombre1", 2);
        dbase.insertTabla1("tab", 9);
        dbase.insertTabla1("safa", 22);
        dbase.insertTabla2("text1", 1);
        dbase.insertTabla2("text0", 0);
        dbase.insertTabla2("text2", 2);

        WriteConfigUtil.exportDB(this);

        startService(new Intent(this, CustomService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver,new IntentFilter(CustomService.SERVICE_RESPONSE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public  static class TestAsyncTask extends AsyncTask<String,Void,String>
    {

        String result;
        @Override
        protected String doInBackground(String... params) {
            if(params.length==0) result="AsyncTask";
            else result=params[0];
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    public BroadcastReceiver getBroadcastReceiver()
    {
        return receiver;
    }
}
