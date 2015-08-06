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
/**
 * Created by massimiliano on 05/08/15.
 */
public class ListStockActivity extends ListActivity  {
    //New
    ListView listView;

    ArrayList<HashMap<String, String>> stockList;
    private ProgressDialog progressMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_link);
        stockList = new ArrayList<HashMap<String, String>>();
        new LoadAllProducts().execute();
    }

    class LoadAllProducts extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressMessage = new ProgressDialog(ListStockActivity.this);
            progressMessage.setMessage("Loading ...");
            progressMessage.setIndeterminate(false);
            progressMessage.setCancelable(false);
            progressMessage.show();
        }

        protected String doInBackground(String... args) {

            try {
                ArrayList<String> stocks = new ArrayList<String>();
                stocks.add("GOOGL");
                stocks.add("FB");
                stocks.add("EBAY");
                for (int i = 0; i < stocks.size(); i++) {
                    Log.d("FINANZA: ", "test " + stocks.size());
                    //StockBean stock = StockTickerDAO.getInstance().getStockPrice("GOOGL");
                    StockBean stock = StockTickerDAO.getInstance().getStockPrice(stocks.get(i));
                    String Stock = stock.getTicker();

                    String value = "value: " + stock.getPrice();

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put("STOCK", Stock);
                    map.put("VALUE", value);
                    stockList.add(map);
                }
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
                            ListStockActivity.this, stockList,
                            R.layout.view_activity_stock, new String[] { "STOCK", "VALUE"},
                            new int[] { R.id.STOCK, R.id.VALUE});
                    setListAdapter(adapter);




                }
            });

        }

    }
}