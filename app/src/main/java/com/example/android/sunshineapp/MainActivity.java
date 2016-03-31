package com.example.android.sunshineapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> maxTempList = new ArrayList<String>();
    ArrayList<String> minTempList = new ArrayList<String>();
    ArrayList<String> mainWeatherList = new ArrayList<String>();
    ArrayList<String> days = new ArrayList<String>();
    ArrayList<WeatherInfo> weatherInfo = new ArrayList<WeatherInfo>();
    ListView listView;
    int imageId;
    WeatherInfo weatherInfoItem;
    ArrayAdapter arrayAdapter;
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
    String dayOfWeek;
    //Context context;

    public class WeatherInfo{
        public int icon;
        public String day;
        public String mainWeather;
        public String maxTemperature;
        public String minTemperature;
        public String monthName;
        public String date;
        public String humidityValue;
        public String pressureValue;
        public String windValue;
        public WeatherInfo(){
            super();
        }
        public WeatherInfo(int icon,String day,String mainWeather,String maxTemperature,String minTemperature,String monthName,String date,String humidityValue,String pressureValue,String windValue){
            super();
            this.icon=icon;
            this.day=day;
            this.mainWeather=mainWeather;
            this.maxTemperature=maxTemperature;
            this.minTemperature=minTemperature;
            this.monthName=monthName;
            this.date = date;
            this.humidityValue=humidityValue;
            this.pressureValue=pressureValue;
            this.windValue=windValue;
        }
        public String toString(){
            return icon + " "+day+ " "+mainWeather+ " "+maxTemperature+ " "+minTemperature+" "+monthName+" "+date+" "+humidityValue+" "+pressureValue+" "+windValue;
        }
    }
    public class WeatherAdapter extends ArrayAdapter<WeatherInfo>{

        public WeatherAdapter(Context context,ArrayList<WeatherInfo> weather) {
            super(context, R.layout.list_item, weather);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //return super.getView(position, convertView, parent);

            weatherInfoItem=getItem(position);
           // System.out.println("Weather Item: "+weatherInfoItem);
            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }
                TextView dayName = (TextView)convertView.findViewById(R.id.dayName);
                TextView weatherDetail = (TextView) convertView.findViewById(R.id.weatherInfo);
                TextView maxTemp = (TextView) convertView.findViewById(R.id.maxTemp);
                TextView minTemp = (TextView) convertView.findViewById(R.id.minTemp);
                ImageView weatherImage = (ImageView) convertView.findViewById(R.id.imageView);
                //Log.i("Icon ", String.valueOf(weatherInfoItem.icon));
                weatherImage.setImageResource(weatherInfoItem.icon);
                dayName.setText(weatherInfoItem.day);
                weatherDetail.setText(weatherInfoItem.mainWeather);
                maxTemp.setText(weatherInfoItem.maxTemperature);
                minTemp.setText(weatherInfoItem.minTemperature);

            return convertView;
        }
    }


    public class DownloadNews extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            URL url;
            String result = "";
            HttpURLConnection httpURLConnection = null;
            try {
                url = new URL(params[0]);
                httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data =  reader.read();
                while (data != -1){
                char current = (char)data;
                    result += current;
                    data= reader.read();
                }
              //  Log.i("Result",result);
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("Value of S",s);
            super.onPostExecute(s);
            JSONArray weatherInfoArray;
            JSONArray jsonArray;
            JSONObject jsonObject;
            JSONObject tempInfo;
            String dateTime;
            String pressure;
            String humidity;
            String speed;
            Date date = new Date();

            try {
                jsonObject = new JSONObject(s);
//                if(jsonObject.has("weather")){
                jsonArray = jsonObject.getJSONArray("list");
               // weatherInfoArray = jsonArray.getJSONArray("weather");
                    //Log.i("Weather Info",weatherInfo);
                //}
                if(jsonArray != null){
                    //jsonArray = new JSONArray(weatherInfo);
                    Log.i("Json Array",jsonArray.toString());
                    Log.i("JSON Array length", String.valueOf(jsonArray.length()));
                    for(int i =0;i<jsonArray.length();i++){
                        //weatherInfoArray = (JSONArray)jsonArray.getJSONArray(i);
                        JSONObject jsonPart = (JSONObject)jsonArray.getJSONObject(i);
                        dateTime = jsonPart.getString("dt");
                        pressure = jsonPart.getString("pressure");
                        humidity = jsonPart.getString("humidity");
                        speed = jsonPart.getString("speed");
                        Log.i("Pressure",pressure);
                        Log.i("Humidity",humidity);
                        Log.i("Wind Speed",speed);
                        //System.out.println(Date);
                        Calendar calendar = Calendar.getInstance();
                        Calendar nowCalendar = Calendar.getInstance();
                        calendar.setTimeInMillis(Long.parseLong(dateTime) * 1000);
                       // int month = calendar.get(Calendar.DAY_OF_MONTH);
                        int dateValue = calendar.get(Calendar.DATE);
                        String monthName = (new SimpleDateFormat("MMMM").format(calendar.getTime()));
                        nowCalendar.setTime(date);
                        //int currentDay = nowCalendar.get(Calendar.DAY_OF_WEEK);
                        String currentDay = dateFormat.format(nowCalendar.getTime());
                        dayOfWeek = dateFormat.format(calendar.getTime());
                        weatherInfoArray = jsonPart.getJSONArray("weather");
                        tempInfo = jsonPart.getJSONObject("temp");
                        String minTemp = String.valueOf((int)(Double.parseDouble(tempInfo.getString("min"))))+ "\u2103";
                        String maxTemp = String.valueOf((int)(Double.parseDouble(tempInfo.getString("max"))))+ "\u2103";
                        minTempList.add(minTemp);
                        maxTempList.add(maxTemp);
                        mainWeatherList.add(weatherInfoArray.toString());
                        Log.i("dayValue", String.valueOf(dateValue));
                        Log.i("Month", monthName);
                        if(dayOfWeek.equalsIgnoreCase(currentDay) )
                            dayOfWeek = "Today";
//                        Log.i("MIN TEMP", minTemp);
//                        Log.i("MAX TEMP",maxTemp);
                        //Log.i("Weather Info Array",weatherInfoArray.toString());
                        //Log.i("Temp Info",tempInfo.toString());
                        for(int j=0;j<weatherInfoArray.length();j++){
                            JSONObject jsonMainPart = (JSONObject)weatherInfoArray.getJSONObject(j);
                            String mainPart = jsonMainPart.getString("main");
                            Log.i("Main Part", mainPart);
                            imageId = R.drawable.rain;
                            WeatherInfo infoList = new WeatherInfo(imageId,dayOfWeek,mainPart,maxTemp,minTemp,monthName,String.valueOf(dateValue),humidity,pressure,speed);
                            weatherInfo.add(infoList);
                            //System.out.println(weatherInfo);
                        }

                    }
                }
//                for(int i =0;i<weatherInfo.size();i++){
//                    System.out.println(weatherInfo.get(i));
//                }

                //arrayAdapter = new ArrayAdapter(getApplicationContext(),)
                WeatherAdapter adapter = new WeatherAdapter(getApplicationContext(),weatherInfo);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.i("Itme Clicked ", String.valueOf(position));
                        Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                        WeatherInfo clicked = weatherInfo.get(position);
                        String dayToShow = clicked.day;
                        intent.putExtra("dayOfWeek", dayToShow);
                        intent.putExtra("MaxTemp",clicked.maxTemperature);
                        intent.putExtra("MinTemp",clicked.minTemperature);
                        intent.putExtra("MonthValue",clicked.monthName);
                        intent.putExtra("date",clicked.date);
                        intent.putExtra("Main Weather",clicked.mainWeather);
                        intent.putExtra("Humidity",clicked.humidityValue);
                        intent.putExtra("Pressure",clicked.pressureValue);
                        intent.putExtra("Wind",clicked.windValue);
                        startActivity(intent);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DownloadNews downloadNews = new DownloadNews();

        try {
            downloadNews.execute("http://api.openweathermap.org/data/2.5/forecast/daily?APPID=9adc8b62b7623d71c24342f79e90dd02&id=1277333&units=metric&cnt=7").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
