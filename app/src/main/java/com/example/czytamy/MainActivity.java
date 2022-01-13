package com.example.czytamy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.CollationElementIterator;
import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLinkLoad = findViewById(R.id.buttonLinkLoad);
        EditText inputLinkSrc = findViewById(R.id.inputLinkSource);
        Button btnSearch = findViewById(R.id.buttonSearch);
        EditText inputSearch = findViewById(R.id.inputSearchCriteria);
        TextView textViewData = findViewById(R.id.textViewData);
        btnLinkLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        ArrayList<String> data = new ArrayList<String>();

                        try {
                            // Pobiera URL z pola tekstowego
                            URL url = new URL(inputLinkSrc.getText().toString());

                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            con.setConnectTimeout(60000);

                            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                            //Zmienna pomocnicza do czytania
                            String str;
                            while ((str = in.readLine()) != null) {
                                //Dodaje dane ze strony do listy
                                data.add(str);
                            }
                            in.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Ustawia zawartość listy do textview
                                textViewData.setText(data.get(0));
                            }
                        });
                    }
                }).start();
            }
        });
    }
}

