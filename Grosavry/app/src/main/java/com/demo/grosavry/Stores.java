package com.demo.grosavry;



import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Stores extends AsyncTask<String, String, String > {

    private ProgressDialog pd;
    public interface StoreResponse {
        void finishedResult(String result);
    }

    public StoreResponse s = null;

    public Stores(StoreResponse s) {
        this.s = s;
    }

    @Override
    protected void onPreExecute(){
        pd = new ProgressDialog(LocationSingleton.activity, R.style.ProgressDialogStyle);
        pd.setTitle("Searching in " + LocationSingleton.radius + " radius.");
        pd.setMessage("Please wait...");

        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String store = "";
        String[] splitArray = new String[10];
        String storeLat = "";
        String storeLong = "";

        try {

            if (params[0].toLowerCase().contains("walmart")) {
                store = "walmart";
            }

            else if (params[0].toLowerCase().contains("loblaws")) {
                store = "loblaws";
            }

            else if (params[0].toLowerCase().contains("farmboy")) {
                store = "farm boy";
            }

            else if (params[0].toLowerCase().contains("freshco")) {
                store = "freshco";
            }

            else if (params[0].toLowerCase().contains("sobeys")) {
                store = "sobeys";
            }

            else {
                store = "NO STORE";
            }


            URL url = new URL(params[0]);

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buff = new StringBuffer();

            String line = "";

            while ((line = reader.readLine()) != null) {

                if (line.toLowerCase().contains("name") && line.toLowerCase().contains(store)) {
                    buff.append(line);
                }

                if (storeLat.isEmpty() &&  line.toLowerCase().contains("\"lat\"")) {
                    splitArray = line.split(": ");
                    storeLat = splitArray[1];

                }

                if (storeLong.isEmpty() && line.toLowerCase().contains("lng")) {
                    splitArray = line.split(": ");
                    storeLong = splitArray[1];

                }

                /*
                if (storeLong != ""){
                    break;
                }
                */

            }
            Log.d("Error", storeLat + " " + storeLong);
            if (!storeLong.isEmpty() && !storeLat.isEmpty()) {
                LocationSingleton.closestStore(storeLat, storeLong, store);
            }


            if (buff.toString() != "") {
                return "true";
            }

            else {
                return "false";
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        s.finishedResult(result);
        pd.dismiss();
        DatabaseSingleton.updateResults(DatabaseSingleton.getDatabase().getTotal());
    }

}