package com.example.helloworld;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InsertLinkActivity extends Activity {
    EditText link, descr;
    Button submit;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //initializing variables
        setContentView(R.layout.insert_view);
        submit = (Button) findViewById(R.id.button3);
        
        link = (EditText) findViewById(R.id.editLink);
        descr = (EditText) findViewById(R.id.editDescr);
        
        //submit = (Button) findViewById(R.id.button2);
        
        
        submit.setOnClickListener(new View.OnClickListener() {
            //getting strings needed to be imported from edit text
            
        	public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String link2 = link.getText().toString();
                String descr2 = descr.getText().toString();   
                Log.e("log_tag", "ERROR:  "+link2+" "+descr2);
                initializeData(link2, descr2);
    
        	}
        	
     //initialization method to import the accessed data to the right columns in table  
            
            private void initializeData(String link2, String descr2) {
                // TODO Auto-generated method stub
                //Add data to be send.
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("editLink", link2));
                nameValuePairs.add(new BasicNameValuePair("editDescr",descr2));
                sendData(nameValuePairs);
            }
            
// executing the HttpPost
            
            private void sendData(ArrayList<NameValuePair> data) {
                // TODO Auto-generated method stub
                 // 1) Connect via HTTP. 2) Encode data. 3) Send data.
                try
                {
                			StrictMode.ThreadPolicy policy = new
                			StrictMode.ThreadPolicy.Builder()
                			.permitAll().build();
                			StrictMode.setThreadPolicy(policy);
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://www.amicofriz.it/appAndroid/msql_insert.php");
                    httppost.setEntity(new UrlEncodedFormEntity(data));
                    HttpResponse response = httpclient.execute(httppost);
                }
                catch(Exception e)
                {
                    Log.e("log_tag", "Error:  "+e.toString());
                } 
            }
            

}
        
        		);
    }

}


