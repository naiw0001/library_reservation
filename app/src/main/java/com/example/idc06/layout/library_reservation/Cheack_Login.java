package com.example.idc06.layout.library_reservation;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by idc06 on 2016-12-28.
 */

public class Cheack_Login extends AppCompatActivity{

    String result;
    String _id, _pw;
    ArrayList list_id = new ArrayList(); // id array
    ArrayList list_pw = new ArrayList(); // pw array

    public String getresult() {
        Log.d("asd",result);
        return result;
    }

    public void check(String id, String pw) {
        _id = id;
        _pw = pw;
        Log.d("asd",_id);
        Log.d("asd",_pw);
        class checkTask extends AsyncTask<String,Void,String> {

            @Override
            protected void onPostExecute(String login_result) {
                super.onPostExecute(login_result);

            }
            @Override
            protected String doInBackground(String... params) {
                StringBuilder jsonHtml = new StringBuilder();
                String link = "http://10.142.47.250:8000/naiw/member_select.php";
                try {
                    URL url = new URL(link);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    while (true) {
                        String line = br.readLine();
                        if (line == null) break;
                        jsonHtml.append(line + "\n");
                    }
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                result = login(jsonHtml.toString());
                Log.d("asdzxc",jsonHtml.toString());
                return result;
            }
        }
        try {
            result = new checkTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("asdx",result);
    }

    public String login(String json) {
        String r = "";
        String id;
        String pw;
        try {
            JSONArray ja = new JSONArray(json);
            for (int i = 0; i < ja.length(); i++) {
                Log.d("vvvvvvvvvvvvv", String.valueOf(ja.length()));
                JSONObject j = ja.getJSONObject(i);
                r += String.format("아이디 : %s 비번 : %s\n", j.getString("_id"), j.getString("_password"));
                id = j.getString("_id");
                Log.d("ccccccccccccc",id);
                pw = j.getString("_password");
                Log.d("aaaaaaaaaaa",pw);
                list_id.add(id);
                Log.d("nnnnnnnnnnnnnn",list_id.get(i).toString());
                list_pw.add(pw);
                Log.d("nnnnnnnnnnnnnnnnnss",list_pw.get(i).toString());
            }
            Log.d("asdzxc",r);

        } catch (Exception e) {}
        Log.d("zxczxc", String.valueOf(list_id.size()));
        for (int i = 0; i < list_id.size(); i++) {
            Log.d("xxxxxxxxxxxxxxxx",list_id.get(i).toString());
            Log.d("ddddddddddddddd",list_pw.get(i).toString());

            if (_id.equals(list_id.get(i).toString()) && _pw.equals(list_pw.get(i).toString())) {
                result = "OK";
                if(_id.equals("admin")){
                    result = "admin";
                    break;
                }
                break;
            }else {
                result = "Fail";
            }

        }

        return result;
    }

}