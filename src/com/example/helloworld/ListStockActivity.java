package com.example.helloworld;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.String;

import android.view.View;
import android.widget.*;
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
import android.widget.AdapterView.OnItemClickListener;

/**
 * Created by massimiliano on 05/08/15.
 */
public class ListStockActivity extends ListActivity  {
    //New
    ListView listView;

    static final int READ_BLOCK_SIZE = 100;

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
                //ArrayList<String> stocks = new ArrayList<String>();
                ArrayList<String> stocksFile = new ArrayList<String>();
                //stocks.add("GOOGL");
                //stocks.add("FB");
                //stocks.add("EBAY");
                WriteBtn();
                stocksFile = ReadBtn();
                //Check this web site
//http://android-er.blogspot.it/2011/04/read-text-file-from-internet-using-java.html
                for (int i = 0; i < stocksFile.size(); i++) {
                    //Log.d("FINANZA: ", "test " + stocks.size());
                    StockBean stock = StockTickerDAO.getInstance().getStockPrice(stocksFile.get(i));
                    //StockBean stock = StockTickerDAO.getInstance().getStockPrice(stocks.get(i));
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
/*
    public void getRemoteStockList(){
        try {
            File file = new File("http://www.amicofriz.it/fz-webservice/stock.xml");
            InputStream is = new FileInputStream(file.getPath());
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(is));
            doc.getDocumentElement().normalize();

            NodeList nodelist = doc.getElementsByTagName("consignment");
            Node node = nodeList.item(0);
            Element fstElmnt = (Element) node;
            String id=fstElmnt.getAttribute("iid");
            String consignmentId=(String)fstElmnt.Element("consignmentId");
            String orderCode=(String)fstElmnt.Element("orderCode");

        }
        catch (Exception e)
        {
            System.out.println("XML Pasing Excpetion = " + e);
        }
    }
    */

    public ArrayList ReadBtn() {
        //reading text from file
        ArrayList<String> stocks2 = new ArrayList<String>();
        try {
            String dir = getFilesDir().toString();
            Log.d("FINANZA: ", dir);
            FileInputStream fileIn=openFileInput("stockFile.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            //AddFRIZ
            BufferedReader in = new BufferedReader(InputRead);
            String line = null;
            //StringBuilder responseData = new StringBuilder();
            while((line = in.readLine()) != null) {
                stocks2.add(line);
                //responseData.append(line);
            }
            /*
            char[] inputBuffer= new char[READ_BLOCK_SIZE];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
                stocks2.add(readstring);

            }
            */
            InputRead.close();
            //Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stocks2;
    }

    public void WriteBtn() {
        // add-write text into file
        try {
            String dir = getFilesDir().toString();
            Log.d("FINANZA: ", dir);
            FileOutputStream fileout=openFileOutput("stockFile.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write("GOOGL\n");
            outputWriter.write("FB\n");
            outputWriter.close();

            //display file saved message
            //Toast.makeText(getBaseContext(), "File saved successfully!",
            //        Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}