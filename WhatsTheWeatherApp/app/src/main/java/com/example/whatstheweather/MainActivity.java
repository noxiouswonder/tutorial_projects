package com.example.whatstheweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public void userInput(View view){
        Button goButton = (Button) findViewById(R.id.goButton);
        EditText cityName = (EditText) findViewById(R.id.cityName);
        String cityNameString =  cityName.getText().toString();
        Log.i("City Searched" , cityNameString);

        DownloadWeather task = new DownloadWeather();
        task.execute("https://api.openweathermap.org/data/2.5/weather?q="+ cityNameString +"&APPID=2c5fc2f4c870af124f50aff57acf040e");
        //Actual : https://api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=2c5fc2f4c870af124f50aff57acf040e
        //Testing : https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=439d4b804bc8187953eb36d2a8c26a02
    }

    public class DownloadWeather extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            //Log.i("Info" , "Background Entered");

            try {
                //Log.i("Info" , "try entered");
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();//Mistake: getConnect instead of openConnection
                //Log.i("Info" , "connected");
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                //Log.i("Info" , "reading completed");
                int data = reader.read();
                while(data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }//Log.i("Info" , "while loop success");
                //Log.i("JSON" , result);
                return result;

            }   catch (Exception e){
                Log.i("Info" , "Connection Failed");
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            TextView mainTextView = (TextView) findViewById(R.id.mainTextView);
            TextView descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);

            try {
                JSONObject jsonObject = new JSONObject(s);

                String weatherInfo = jsonObject.getString("weather");

                Log.i("Weather Content" , weatherInfo);

                JSONArray arrWeather = new JSONArray(weatherInfo);

                for (int i = 0; i < arrWeather.length(); i++){
                    JSONObject jsonPart = arrWeather.getJSONObject(i);
                    Log.i("Main" , jsonPart.getString("main"));
                    Log.i("Description" , jsonPart.getString("description"));

                    mainTextView.setText("What's the weather: " + jsonPart.getString("main"));
                    descriptionTextView.setText("Description: " + jsonPart.getString("description"));

                }

            }   catch (Exception e){
                e.printStackTrace();
                Log.i("Info" , "Post Execution Failed");
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}