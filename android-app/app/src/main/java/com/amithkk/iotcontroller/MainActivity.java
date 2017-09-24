package com.amithkk.iotcontroller;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextToSpeech tts;
    RequestQueue requestQueue;
    public  String state;
    public  String lstate;
    NavigationView navigationView;
    public IotAdapter adp;
    public void http(String state) {
        String url = "http://192.168.43.58/digital/5/" + state;
        // String url = "http://www.google.com";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //add request to queue
        requestQueue.add(stringRequest);
        //Toast.makeText(MainActivity.this, "Command Executed", Toast.LENGTH_SHORT).show();
    }
    public void http2(String state) {
        if(state.equals("0")){
            lstate ="lock";
        }else{
            lstate="unlock";
        }

        String url = "http://192.168.43.31/"+lstate;
        // String url = "http://www.google.com";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //add request to queue
        requestQueue.add(stringRequest);
        //Toast.makeText(MainActivity.this, "Command Executed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 100: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //t1.setText(result.get(0));
                    switch (result.get(0)) {
                        case "light on":
                            tts.speak("light switched on", TextToSpeech.QUEUE_FLUSH, null);
                            state = "0";
                            http(state);
                            break;
                        case "light off":
                            tts.speak("light switched off", TextToSpeech.QUEUE_FLUSH, null);
                            Toast.makeText(MainActivity.this, "light switched off", Toast.LENGTH_SHORT).show();
                            http("1");
                            break;
                        case "lock":
                            tts.speak("Lock Armed, Home is Safe", TextToSpeech.QUEUE_FLUSH, null);
                            Toast.makeText(MainActivity.this, "Successfully Locked", Toast.LENGTH_SHORT).show();
                            state = "0";
                            http2(state);
                            break;
                        case "unlock":
                            tts.speak("Lock Disarmed, Welcome Home", TextToSpeech.QUEUE_FLUSH, null);
                            Toast.makeText(MainActivity.this, "Successfully Unlocked", Toast.LENGTH_SHORT).show();
                            state = "1";
                            http2(state);
                            break;
                    }
                }
                break;

            }

            case 2:
                if(resultCode==RESULT_OK)
                {
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    Log.i("S","Added");
                    iotDevice i = new iotDevice(data.getStringExtra("type"),data.getStringExtra("ipaddr"),data.getStringExtra("name"));
                    adp.mList.add(i);
                    adp.notifyItemInserted(adp.mList.indexOf(i));
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getApplicationContext(), "OnVoiceAction", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What can i do for you?");
                try {
                    startActivityForResult(intent, 100);
                } catch (ActivityNotFoundException a) {

                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        Switch light = (Switch) findViewById(R.id.lbSwitch);
//        Switch lock = (Switch) findViewById(R.id.lockSwitch);
//
//        light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    Toast.makeText(getApplicationContext(), "Light On", Toast.LENGTH_SHORT).show();
//                    tts.speak("light switched on", TextToSpeech.QUEUE_FLUSH, null);
//                    state = "0";
//                    http(state);
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Light Off", Toast.LENGTH_SHORT).show();
//                    tts.speak("light switched off", TextToSpeech.QUEUE_FLUSH, null);
//                    state = "1";
//                    http(state);
//                }
//            }
//        });
//
//        lock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    Toast.makeText(getApplicationContext(), "Lock Armed", Toast.LENGTH_SHORT).show();
//                    tts.speak("Successfully Locked", TextToSpeech.QUEUE_FLUSH, null);
//                    state = "0";
//                    http2(state);
//                } else {
//                    Toast.makeText(getApplicationContext(), "Lock Disarmed", Toast.LENGTH_SHORT).show();
//                    tts.speak("Successfully Unlocked", TextToSpeech.QUEUE_FLUSH, null);
//                    state = "1";
//                    http2(state);
//                }
//            }
//        });
        tts = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "This Language is not supported");
                    } else {
                        // speak();
                    }
                } else
                    Log.e("error", "Initilization Failed!");
            }
        });
        ArrayList<iotDevice> arr = new ArrayList<>();
        arr.add(new iotDevice("Bulb","192.168.43.58","Light"));
        arr.add(new iotDevice("Lock","192.168.43.31","Lock"));
        adp= new IotAdapter(arr, getApplicationContext());
        rv.setAdapter(adp);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
        requestQueue = Volley.newRequestQueue(this); // 'this' is the Context
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_setup) {
            Intent i= new Intent(MainActivity.this,addDevice.class);
            startActivityForResult(i,2);
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
