package com.example.idc06.layout.library_reservation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by idc06 on 2016-12-28.
 */

public class AdminActivity extends AppCompatActivity{
    private TextView text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);
        text = (TextView)findViewById(R.id.db_text);
    }

    public void member_db(View v){
        String a = "1";
        new showTask().execute("http://10.142.47.250:8000/naiw/show_library.php?index=1",a);
    }
    public void reservation_db(View v){
        String s = "2";
        new showTask().execute("http://10.142.47.250:8000/naiw/show_library.php?index=2",s);
    }
    class showTask extends AsyncTask<String,Void,String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            text.setText(s);
        }

        @Override
        protected String doInBackground(String... params) {
            String link = params[0];
            String index = params[1];
            StringBuilder json = new StringBuilder();
            String result="";
            try {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                while(true){
                    String line = bufferedReader.readLine();
                    if(line==null)break;
                    json.append(line+"\n");
                }
                bufferedReader.close();
                result=show(json.toString(),index);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
        public String show(String json,String idx){
            String result="";
            if(idx.equals("1")) { // member
                Log.d("start","qqqqqq");
                try {
                    JSONArray ja = new JSONArray(json);
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        result += String.format("이름 : %s, 나이 : %s살, 성별 : %s, 번호 : %s, 아이디 : %s, 비밀번호 : %s \n", jo.getString("_name"), jo.getString("_age"), jo.getString("_gender"),
                                jo.getString("_phone"), jo.getString("_id"), jo.getString("_password"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(idx.equals("2")){ // reservation
                try {
                    JSONArray ja = new JSONArray(json);
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        result += String.format("시간 : %s, 예약시 시간 : %s, 자리번호 : %s, 아이디 : %s\n",
                                jo.getString("time"), jo.getString("now"), jo.getString("num"), jo.getString("id"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else result = "오류";


            return result;
        }
    }

}
