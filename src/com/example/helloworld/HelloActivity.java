package com.example.helloworld;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
 
public class HelloActivity extends Activity {
 
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_hello);
}
 
public void startListLinkActivity(View view) {
Intent objIndent = new Intent(getApplicationContext(), ListAnimalActivity.class);
startActivity(objIndent);
}

public void startInsertLinkActivity(View view) {
Intent objIndent = new Intent(getApplicationContext(), InsertLinkActivity.class);
startActivity(objIndent);
}

public void insertDataLink(View view) {
Intent objIndent = new Intent(getApplicationContext(), InsertLinkActivity.class);
startActivity(objIndent);
}
public void startListStockActivity(View view) {
Intent objIndent = new Intent(getApplicationContext(), ListStockActivity.class);
startActivity(objIndent);
}

public void openLink(){
	Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.google.it"));
	startActivity(browserIntent);
}

}