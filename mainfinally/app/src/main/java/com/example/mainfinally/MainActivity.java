package com.example.mainfinally;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;



public class MainActivity extends AppCompatActivity {

    private TextView tv_outPut;
    private TextView temp;
    //private TextView wind;
    //private TextView rainstate;

    String weather_data;
    String temp_data;
    //String wind_data;
    //String rain_data;

    String Service_key = "service key";
    String num_of_rows = "1000";
    String page_no = "1";
    String date_type = "XML";
    long mNow = System.currentTimeMillis();
    Date mDate = new Date(mNow);
    String nx = "51";
    String ny = "69";
    SimpleDateFormat formatYDM = new SimpleDateFormat("yyyyMMdd");
    String YDM = formatYDM.format(mDate);
    SimpleDateFormat formatTime = new SimpleDateFormat("HH00");
    String getTime = String.valueOf(Integer.parseInt(formatTime.format(mDate))+300);
    String base_date = YDM;
    String base_time = getTime;
    //String base_time = "1400";

    String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?" +
            "ServiceKey=" + Service_key +
            "&numOfRows=" + num_of_rows +
            "&pageNo=" + page_no +
            "&dataType=" + date_type +
            "&base_date=" + base_date +
            "&base_time=" + base_time +
            "&nx=" + nx +
            "&ny=" + ny;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 위젯에 대한 참조.
        tv_outPut = (TextView) findViewById(R.id.tv_outPut);
        temp = (TextView) findViewById(R.id.temp);


        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();

        new Thread(new Runnable() {
            @Override
            public void run() {
                weather_data = getWeatherXmlData();
                temp_data = getTempXMLData();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_outPut.setText(weather_data);
                        temp.setText(temp_data);

                    }
                });
            }
        }).start();

    }

    String getTempXMLData(){
        StringBuffer buffer = new StringBuffer();

        try {
            URL urll = new URL(url);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = urll.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;
            int i = 0;
            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기

                        if (tag.equals("item")) ; // 첫번째 검색결과
                        else if (tag.equals("obsrValue")) {
                            i++;
                            if (i == 4) {
                                buffer.append("기온 ");
                                xpp.next();
                                buffer.append(xpp.getText() + "℃");
                                buffer.append("\n");
                            }


                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if (tag.equals("item")) buffer.append("\n");
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
        return buffer.toString();

    }

    String getWeatherXmlData(){

        StringBuffer buffer = new StringBuffer();

        try {
            URL urll = new URL(url);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = urll.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;
            int i = 0;
            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기

                        if (tag.equals("item")) ; // 첫번째 검색결과
                        else if (tag.equals("obsrValue")) {
                            i++;
                            if (i == 2) {
                                //buffer.append("습도 : ");
                                xpp.next();
                                buffer.append(xpp.getText() + "%");
                                buffer.append("\n");
                            }


                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if (tag.equals("item")) buffer.append("\n");
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
        return buffer.toString();
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpConnection requestHttpURLConnection = new RequestHttpConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.

            tv_outPut.setText(s);
            temp.setText(s);
            //wind.setText(s);
            //rainstate.setText(s);
            Log.d("onPostEx", "출력 값 : "+s);
        }
    }
}
