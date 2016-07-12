package com.cslWorld.VOR_Contacts.web;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by heshan.lokuge on 3/19/14.
 */
public class JsonHandler {

    private StringBuilder builder;
    private String value;

    /**
     *
     * @param context - context of the application
     * @return connected - network status
     * this method checks the network status of the device
     */
    public boolean checkNetworkConnection(final Context context) {

//        boolean connected = false;
//
//        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = conManager.getActiveNetworkInfo();
//
//        if (netInfo == null || netInfo.isConnected() == false) {
//            AlertDialog.Builder alert = new AlertDialog.Builder(context);
//            alert.setTitle("No Internet Access");
//            alert.setMessage("Do you want to turn on the Internet?");
//
//            alert.setPositiveButton("Settings",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int whichButton) {
//
//                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
//                            context.startActivity(intent);
//                            dialog.cancel();
//
//                        }
//                    });
//
//            alert.setNeutralButton("Retry",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int whichButton) {
//
//                        }
//                    });
//            alert.setNegativeButton("Cancel",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int whichButton) {
//
//                            ((Activity) context).finish();
//                        }
//                    });
//
//            alert.show();
//
//            if (netInfo != null && netInfo.isConnected() == true) {
//                connected = true;
//            }
//        } else {
//            connected = true;
//        }
//
//        return connected;

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) for (int i = 0; i < info.length; i++)
                if (info[i].getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
        }
        return false;
    }

    /**
     * @param url - the url of which json string has to be accessed
     * @return - Json string of the url
     * returns the json String of the given url
     */
    public String getJsonData(String url) {

        HttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(url);

        try {
            HttpResponse response = client.execute(getRequest);
            InputStream jsonStream = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(jsonStream));
            builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    /**
     * @param jsonText - - the json string of which set of values should be obtained
     * @return - a list of values of the json string
     * @throws org.json.JSONException
     */
    public ArrayList getListOfValues(String jsonText) throws JSONException {

        ArrayList<String> valueList = new ArrayList<String>();
        JSONObject jsonObject = new JSONObject(jsonText);
        Iterator iterator = jsonObject.keys();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = jsonObject.optString(key);
            valueList.add(value);
        }

        return valueList;
    }

    /**
     * @param jsonData - the json string which contains the key mentioned
     * @param key-     the key of which value needed to be get
     * @return
     */
    public String getJSONValue(String jsonData, String key) {

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }


}
