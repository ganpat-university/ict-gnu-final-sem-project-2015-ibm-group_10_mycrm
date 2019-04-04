package com.example.mycrm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.HashMap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class userprofile extends AppCompatActivity implements OnMapReadyCallback {

    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    String ParseResult ;
    HashMap<String,String> ResultHash = new HashMap<>();
    String FinalJSonObject ;
    ProgressDialog pd;
    HttpParseget httpParse = new HttpParseget();
    ProgressDialog pDialog;
    TextView email,name,phone,org,add,dob,biod;
    String eemail,nname,pphone,oorg,aadd,ddob;
    private GoogleMap mMap;
    MapFragment mapFragment;
    Marker marker;
    String theid;
    TextView tryz;
    String HttpURL="https://mycrm-demo.eu-gb.mybluemix.net/listUsers?contact=";
    Context mContext= userprofile.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        /*email.setText(eemail);
        name.setText(nname);
        phone.setText(pphone);
        org.setText(oorg);
        add.setText(aadd);
        dob.setText(ddob);*/

        email = (TextView)findViewById(R.id.emailid);
        name = (TextView)findViewById(R.id.name);
        phone = (TextView)findViewById(R.id.contactones);
        org = (TextView)findViewById(R.id.orgn);
        add = (TextView)findViewById(R.id.addresss);
        dob = (TextView)findViewById(R.id.bdayz);
        biod =(TextView)findViewById(R.id.bioz);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        /*try {
            findLocation();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userprofile.this,userprofile.class);
                startActivity(intent);
            }
        });
        TabHost mTabHost = (TabHost)findViewById(R.id.tabHost);
        mTabHost.setup();
        //Lets add the first Tab
        TabHost.TabSpec mSpec = mTabHost.newTabSpec("First Tab");
        mSpec.setContent(R.id.first_Tab);
        mSpec.setIndicator("Profile");
        TextView sp =(TextView) findViewById(R.id.name);
        //String[] splited = sp.getText().toString().split("\\s+");
        //String urlimage="https://ui-avatars.com/api/?rounded=true&name="+splited[0]+"+"+splited[splited.length-1]+"&background=008577&color=fff";
        /*Picasso.with(mContext)
                .load(urlimage)
                .placeholder(R.drawable.mycrm)
                .error(R.drawable.mycrm)
                .into(test);*/
        mTabHost.addTab(mSpec);
        //Lets add the second Tab
/*        mSpec = mTabHost.newTabSpec("Second Tab");
        mSpec.setContent(R.id.second_Tab);
        mSpec.setIndicator("Second Tab");
        mTabHost.addTab(mSpec);
        tryz =(TextView)findViewById(R.id.iduser);
        tryz.setText("Kishan");*/
        //Lets add the third Tab


        mSpec = mTabHost.newTabSpec("Second Tab");
        mSpec.setContent(R.id.second_Tab);
        mSpec.setIndicator("Contact");
        mTabHost.addTab(mSpec);
        mTabHost.setCurrentTab(0);

//        EditText et = (EditText)findViewById(R.id.editText);
  //      String location = et.getText().toString();
        theid= getIntent().getStringExtra("ids");
        //theid="8812388456";
        Log.d("id",theid);
        //Toast.makeText(getApplicationContext(),theid,Toast.LENGTH_LONG).show();

        HttpURL=HttpURL+theid;
//        new JsonTask().execute("Url address here");
        //HttpWebCall(theid);
        new HttpAsyncTask().execute(HttpURL);

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onMapReady(GoogleMap googleMap) {
     /*   mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }*/
        this.mMap = googleMap;
        try {
            findLocation();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findLocation() throws IOException {

     //   EditText et = (EditText)findViewById(R.id.editText);
     //   String location = et.getText().toString();
        String location="Thaltej, Ahmedabad";
        Geocoder geocoder = new Geocoder(this);
        List<Address> list = geocoder.getFromLocationName(location, 1);
        Address add = list.get(0);
        String locality = add.getLocality();
        LatLng ll = new LatLng(add.getLatitude(), add.getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 150);
        mMap.moveCamera(update);
        if(marker != null)
            marker.remove();
        MarkerOptions markerOptions = new MarkerOptions()
                .title(location)
                .position(new LatLng(add.getLatitude(), add.getLongitude()));
        marker = mMap.addMarker(markerOptions);

    }

    public void addlocation(String location) throws IOException {

        //   EditText et = (EditText)findViewById(R.id.editText);
        //   String location = et.getText().toString();
        //String location="Thaltej, Ahmedabad";
        Geocoder geocoder = new Geocoder(this);
        List<Address> list = geocoder.getFromLocationName(location, 1);
        Address add = list.get(0);
        String locality = add.getLocality();
        LatLng ll = new LatLng(add.getLatitude(), add.getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 150);
        mMap.moveCamera(update);
        if(marker != null)
            marker.remove();
        MarkerOptions markerOptions = new MarkerOptions()
                .title(location)
                .position(new LatLng(add.getLatitude(), add.getLongitude()));
        marker = mMap.addMarker(markerOptions);

    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            //Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
            //etResponse.setText(result);
            JSONObject jsonObj= null;
            try {
                jsonObj = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                email.setText(jsonObj.getString("email"));
                name.setText(jsonObj.getString("name"));
                org.setText(jsonObj.getString("organization"));
                add.setText(jsonObj.getString("address"));
                dob.setText(jsonObj.getString("dob"));
                phone.setText(jsonObj.getString("phone"));
                biod.setText("My Name is "+jsonObj.getString("name"));
                ImageView test =(ImageView)findViewById(R.id.flags);
                String[] splited = jsonObj.getString("name").split("\\s+");
                String urlimage="https://ui-avatars.com/api/?rounded=true&name="+splited[0]+"+"+splited[splited.length-1]+"&background=008577&color=fff";
        Picasso.with(mContext)
                .load(urlimage)
                .placeholder(R.drawable.mycrm)
                .error(R.drawable.mycrm)
                .into(test);
                try {
                    addlocation(jsonObj.getString("address"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    //Method to show current record Current Selected Record
    /*public void HttpWebCall(final String PreviousListViewClickedItem){

        class HttpWebCallFunction extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = ProgressDialog.show(userprofile.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                pDialog.dismiss();

                //Storing Complete JSon Object into String Variable.
                FinalJSonObject = httpResponseMsg ;

                //Parsing the Stored JSOn String to GetHttpResponse Method.
                new GetHttpResponse(userprofile.this).execute();

            }

            @Override
            protected String doInBackground(String... params) {

                ResultHash.put("contact",params[0]);

                ParseResult = httpParse.postRequest(ResultHash, HttpURL);

                return ParseResult;
            }
        }

        HttpWebCallFunction httpWebCallFunction = new HttpWebCallFunction();

        httpWebCallFunction.execute(PreviousListViewClickedItem);
    }*/

/*
    // Parsing Complete JSON Object.
    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        public GetHttpResponse(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            try
            {
                if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;

                    try {
                        //jsonArray = new JSONArray(FinalJSonObject);



                       // JSONObject jsonObject;

                        //    jsonObject = jsonArray.getJSONObject(i);

                            // Storing Student Name, Phone Number, Class into Variables.
      /*                      NameHolder = jsonObject.getString("name").toString() ;
                            NumberHolder = jsonObject.getString("phone_number").toString() ;
                            ClassHolder = jsonObject.getString("class").toString() ;
    String eemail,nname,pphone,oorg,aadd,ddob;


                            JSONObject jsnobject = new JSONObject(FinalJSonObject);
                            eemail = jsnobject.getString("email").toString() ;
                            Log.d("Email",eemail);
                            nname = jsnobject.getString("name").toString() ;
                            pphone = jsnobject.getString("phone").toString() ;
                            oorg = jsnobject.getString("organization").toString() ;
                            aadd = jsnobject.getString("address").toString() ;
                            ddob = jsnobject.getString("dob").toString() ;
                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
/*
            // Setting Student Name, Phone Number, Class into TextView after done all process .
          /*  NAME.setText(NameHolder);
            PHONE_NUMBER.setText(NumberHolder);
            CLASS.setText(ClassHolder);*/
 /*         email.setText(eemail);
            name.setText(nname);
            org.setText(oorg);
            add.setText(aadd);
            dob.setText(ddob);
            phone.setText(pphone);


        }
    }*/


}