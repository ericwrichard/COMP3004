package com.demo.grosavry;



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

    public interface StoreResponse {
        void finishedResult(String result);
    }

    public StoreResponse s = null;

    public Stores(StoreResponse s) {
        this.s = s;
    }

    @Override
    protected String doInBackground(String... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String store = "";

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
    }

}