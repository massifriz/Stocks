package com.example.helloworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.String;
 
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
 
public class ListAnimalActivity extends ListActivity  {
	//New
	ListView listView;
	
ArrayList<HashMap<String, String>> animalList;
private ProgressDialog progressMessage;
JSONParser jParser = new JSONParser();
private static String url = "http://www.amicofriz.it/fz-webservice/msql.php";
JSONArray animals = null;
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);	
setContentView(R.layout.activity_list_link);
animalList = new ArrayList<HashMap<String, String>>();
new LoadAllProducts().execute();
}
 
class LoadAllProducts extends AsyncTask<String, String, String> {
 
@Override
protected void onPreExecute() {
super.onPreExecute();
progressMessage = new ProgressDialog(ListAnimalActivity.this);
progressMessage.setMessage("Loading ...");
progressMessage.setIndeterminate(false);
progressMessage.setCancelable(false);
progressMessage.show();
}
 
protected String doInBackground(String... args) {
	
List<NameValuePair> params = new ArrayList<NameValuePair>();
JSONObject json = jParser.makeHttpRequest(url, "GET", params);
 
Log.d("Animals: ", json.toString()); 
 
try {

	int success = json.getInt("success");
 
if (success == 1) {

animals = json.getJSONArray("links");
for (int i = 0; i < animals.length(); i++) {
JSONObject c = animals.getJSONObject(i);
String descr = c.getString("descr");
String link = c.getString("url");

HashMap<String, String> map = new HashMap<String, String>();
map.put("descr", descr);
map.put("Link", link);


    animalList.add(map);

}


}


//} catch (JSONException e) {
} catch (Exception e) {
e.printStackTrace();
}
 
return null;
}

protected void onPostExecute(String file_url) {
progressMessage.dismiss();
runOnUiThread(new Runnable() {
public void run() {

	ListAdapter adapter = new SimpleAdapter(
ListAnimalActivity.this, animalList,
R.layout.view_activity_list, new String[] { "descr","Link"},
new int[] { R.id.descr, R.id.Link});
setListAdapter(adapter);




}
});
 
} 
 
}
}