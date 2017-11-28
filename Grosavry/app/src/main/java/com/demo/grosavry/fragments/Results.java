package com.demo.grosavry.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.grosavry.DBhelper;
import com.demo.grosavry.DatabaseSingleton;
import com.demo.grosavry.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public class Results extends Fragment {

    private ListView lv = null;
    //private ArrayAdapter<String> adapter = null;
    //private ArrayList<String> storeList = null;

    private DBhelper database;

    public Results(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = DatabaseSingleton.getInstance().getDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.results_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        DatabaseSingleton.storeList = new ArrayList<>();
        Cursor res = database.getTotal();
        DatabaseSingleton.adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_item_black, R.id.list_content,
                DatabaseSingleton.storeList);

        DatabaseSingleton.updateResults(res);

        lv = getView().findViewById(R.id.results_view);
        lv.setAdapter(DatabaseSingleton.adapter);

        final Context context = getActivity().getApplicationContext();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                  {
                                      @Override
                                      public void onItemClick(final AdapterView<?> parent, View view, int position, long id){

                                          final String value = (String) parent.getItemAtPosition(position);

                                          LinearLayout layout = new LinearLayout(context);
                                          AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
                                          builder.setTitle("Address:");
                                          String[] s = value.split(":");
                                          String name = s[0].trim();
                                          Log.d("Error", name);
                                          String address = "N/A";
                                          if(DatabaseSingleton.storeDistAddress.get(name) != null){
                                              address = DatabaseSingleton.storeDistAddress.get(name).address;
                                          }

                                          TextView text = new TextView(context);
                                          text.setText(address);
                                          text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26.0f);
                                          text.setTextColor(getResources().getColor(R.color.white));
                                          text.setGravity(Gravity.CENTER);
                                          text.setPadding(10, 10, 10, 10);

                                          layout.addView(text);

                                          builder.setView(layout);

                                          builder.show();

                                      }
                                  }
        );
    }


}
