package com.demo.grosavry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ProgressDialog;
import android.util.Log;

import android.os.AsyncTask;
import android.util.Log;

public class StoreInfo extends AsyncTask<String, String, String> {

    private ProgressDialog pd;
    public interface StoreInformation {
        void finishedResult(String result);
    }

    public StoreInformation s = null;

    public StoreInfo(StoreInformation s) {
        this.s = s;
    }

    @Override
    protected void onPreExecute(){
        /*
        pd = new ProgressDialog(LocationSingleton.activity, R.style.ProgressDialogStyle);
        pd.setTitle("Searching in " + LocationSingleton.radius + " radius.");
        pd.setMessage("Please wait...");

        pd.show();
        */
    }


    @Override
    protected String doInBackground(String... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String[] splitArray = new String[10];
        String[] splitArray2 = new String[10];


        try {

            URL url = new URL(params[0]);

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buff = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {

                if (line.toLowerCase().contains("dest")){
                    splitArray2 = line.split("\\[ ");
                    splitArray2[1] = splitArray2[1].replaceAll("\\]", "").replaceAll(",", "");

                }

                if (line.toLowerCase().contains("text") && line.toLowerCase().contains("km")) {
                    splitArray = line.split(": ");
                    buff.append(line);
                    break;
                }


            }

            if (splitArray[1] != null || splitArray[1] != "") {
                return splitArray[1] + splitArray2[1];
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
        //pd.dismiss();
        DatabaseSingleton.updateResults(DatabaseSingleton.getDatabase().getTotal());
    }
}
