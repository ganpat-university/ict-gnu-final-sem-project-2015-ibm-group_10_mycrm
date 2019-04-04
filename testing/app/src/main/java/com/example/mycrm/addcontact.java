package com.example.mycrm;

import android.content.ActivityNotFoundException;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import java.util.HashMap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.SupportMapFragment;


public class addcontact extends AppCompatActivity {

    Switch bee;
    EditText name, email, address,mobileno, mobilenotwo,orgnization,bday;
    String nname, eemail, aaddress,mmobileno, mmobilenotwo,oorgnization,bbday;
    String ava;
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    String finalResult ;
    Button go;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "https://mycrm-demo.eu-gb.mybluemix.net/createUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontact);

        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        mobileno = (EditText)findViewById(R.id.mobileno);
//        mobilenotwo = (EditText)findViewById(R.id.orgnization);
        orgnization = (EditText)findViewById(R.id.orgnization);
        bday = (EditText)findViewById(R.id.bday);
        address = (EditText)findViewById(R.id.address);

        bee= (Switch)findViewById(R.id.available);

        go = (Button)findViewById(R.id.addcontact);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    // If EditText is not empty and CheckEditText = True then this block will execute.

                    AddContact(nname, eemail, aaddress,mmobileno, mmobilenotwo,oorgnization,bbday,ava);

                }
                else {

                    // If EditText is empty then this block will execute .
                    Toast.makeText(addcontact.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);


                }

            }
        });


    }

    public void AddContact(final String nname, final String  eemail, final String  aaddress, final String mmobileno, final String  mmobilenotwo, final String  oorgnization, final String bbday, final String ava){

        class AddContactClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(addcontact.this,"Loading...",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(addcontact.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("name",params[0]);

                hashMap.put("email",params[1]);

                hashMap.put("address",params[2]);

                hashMap.put("mobile",params[3]);

                hashMap.put("contactno",params[4]);

                hashMap.put("org",params[5]);

                hashMap.put("dob",params[6]);

                hashMap.put("avl",params[7]);

//                hashMap.put("bio","My Name is Raj");


                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        AddContactClass AddContactClassclass = new AddContactClass();

        AddContactClassclass.execute(nname, eemail, aaddress,mmobileno, mmobilenotwo,oorgnization,bbday,ava);
    }




/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
        //    finish(); // close this activity and return to preview activity (if there is any)
            return true;
        }

        return super.onOptionsItemSelected(item);

    }*/

    @Override
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

    public void CheckEditTextIsEmptyOrNot(){

        String temp,temps;
        nname = name.getText().toString();
        eemail = email.getText().toString();
        aaddress = address.getText().toString();
        mmobileno = mobileno.getText().toString();
        //mmobilenotwo = mobilenotwo.getText().toString();
        oorgnization =orgnization.getText().toString();
        bbday =bday.getText().toString();
        String s = eemail;
        String[] split = s.split("@");
        mmobilenotwo = split[0]+oorgnization;


        if (bee.isChecked())
            ava="true";
        else
            ava="false";

        if(TextUtils.isEmpty(nname) || TextUtils.isEmpty(eemail) || TextUtils.isEmpty(aaddress) || TextUtils.isEmpty(mmobileno) || TextUtils.isEmpty(mmobilenotwo) || TextUtils.isEmpty(oorgnization) || TextUtils.isEmpty(bbday))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }


}
