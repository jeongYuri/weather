package com.example.myweekweather;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private TextView tv_outPut;
    //Timer timer;
    String weather_data;
    String apiurl ="http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa?";
    String ServiceKey = "servicekey";
    String numOfRows = "10";
    String page_no = "1";
    String dataType = "XML";
    String regId="11B10101";

    long mNow = System.currentTimeMillis();
    Date mDate = new Date(mNow);
    SimpleDateFormat formatYDM = new SimpleDateFormat("yyyyMMdd");
    String YDM = formatYDM.format(mDate);

    String tmFc=YDM+"0600";

    Date cal = Calendar.getInstance().getTime();
    Date result_date = new Date(cal.getTime()+(1000*60*60*24*3));
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd",Locale.getDefault());
    String current = sdf.format(result_date);
    Date result_date2 = new Date(cal.getTime()+(1000*60*60*24*4));
    String current2 = sdf.format(result_date2);
    Date result_date3 = new Date(cal.getTime()+(1000*60*60*24*5));
    String current3 = sdf.format(result_date3);
    Date result_date4 = new Date(cal.getTime()+(1000*60*60*24*6));
    String current4 = sdf.format(result_date4);
    Date result_date5 = new Date(cal.getTime()+(1000*60*60*24*7));
    String current5 = sdf.format(result_date5);
    Date result_date6 = new Date(cal.getTime()+(1000*60*60*24*8));
    String current6 = sdf.format(result_date6);
    Date result_date7 = new Date(cal.getTime()+(1000*60*60*24*9));
    String current7 = sdf.format(result_date7);
    Date result_date8 = new Date(cal.getTime()+(1000*60*60*24*10));
    String current8 = sdf.format(result_date8);




    String url = apiurl+
            "ServiceKey=" + ServiceKey +
            "&numOfRows=" + numOfRows +
            "&pageNo=" + page_no +
            "&dataType=" + dataType +
            "&regId=" + regId +
            "&tmFc=" + tmFc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_outPut = (TextView) findViewById(R.id.tv_outPut);

        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();

        new Thread(new Runnable() {
            @Override
            public void run() {
                weather_data = getWeatherXmlData();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_outPut.setText(weather_data);
                    }
                });
            }
        }).start();
    }

    String getWeatherXmlData() {

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
                        else if (tag.equals("taMin3")) {
                            buffer.append(current+" "+"최저기온:");
                            xpp.next();
                            buffer.append(xpp.getText()+"℃");
                            buffer.append("\n");
                        }else if(tag.equals("taMax3")){
                            buffer.append(current+" "+"최고 기온:");
                            xpp.next();
                            buffer.append(xpp.getText()+"℃");
                            buffer.append("\n");
                        }else if(tag.equals("taMin4")){
                            buffer.append(current2+" "+"최저 기온:");
                            xpp.next();
                            buffer.append(xpp.getText()+"℃");
                            buffer.append("\n");
                        }else if(tag.equals("taMax4")){
                            buffer.append(current2+" "+"최고 기온:");
                            xpp.next();
                            buffer.append(xpp.getText()+"℃");
                            buffer.append("\n");
                        }else if(tag.equals("taMin5")){
                            buffer.append(current3+" "+"최저 기온:");
                            xpp.next();
                            buffer.append(xpp.getText()+"℃");
                            buffer.append("\n");
                        }else if(tag.equals("taMax5")){
                            buffer.append(current3+" "+"최고 기온:");
                            xpp.next();
                            buffer.append(xpp.getText()+"℃");
                            buffer.append("\n");
                        }else if(tag.equals("taMin6")){
                            buffer.append(current4+" "+"최저 기온:");
                            xpp.next();
                            buffer.append(xpp.getText()+"℃");
                            buffer.append("\n");
                        }else if(tag.equals("taMax6")){
                            buffer.append(current4+" "+"최고 기온:");
                            xpp.next();
                            buffer.append(xpp.getText()+"℃");
                            buffer.append("\n");
                        }else if(tag.equals("taMin7")){
                            buffer.append(current5+" "+"최저 기온:");
                            xpp.next();
                            buffer.append(xpp.getText()+"℃");
                            buffer.append("\n");
                        }else if(tag.equals("taMax7")){
                            buffer.append(current5+" "+"최고 기온:");
                            xpp.next();
                            buffer.append(xpp.getText()+"℃");
                            buffer.append("\n");
                        }else if(tag.equals("taMin8")){
                            buffer.append(current6+" "+"최저 기온:");
                            xpp.next();
                            buffer.append(xpp.getText()+"℃");
                            buffer.append("\n");
                        }else if(tag.equals("taMax8")){
                            buffer.append(current6+" "+"최고 기온:");
                            xpp.next();
                            buffer.append(xpp.getText()+"℃");
                            buffer.append("\n");
                        }else if(tag.equals("taMin9")){
                            buffer.append(current7+" "+"최저 기온:");
                            xpp.next();
                            buffer.append(xpp.getText()+"℃");
                            buffer.append("\n");
                        }else if(tag.equals("taMax9")){
                            buffer.append(current7+" "+"최고 기온:");
                            xpp.next();
                            buffer.append(xpp.getText()+"℃");
                            buffer.append("\n");
                        }else if(tag.equals("taMin10")){
                            buffer.append(current8+" "+"최저 기온:");
                            xpp.next();
                            buffer.append(xpp.getText()+"℃");
                            buffer.append("\n");
                        }else if(tag.equals("taMax10")){
                            buffer.append(current8+" "+"최고 기온:");
                            xpp.next();
                            buffer.append(xpp.getText()+"℃");
                            buffer.append("\n");
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
            Log.d("onPostEx", "출력 값 : "+s);
        }
    }


}
