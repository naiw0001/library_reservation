package com.example.idc06.layout.library_reservation;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by idc06 on 2016-12-28.
 */

public class LoginActivity extends AppCompatActivity{

    private EditText _id, _pw;
    private Button bt_login, bt_join;
    private AlertDialog.Builder dialog;
    private EditText ed_name, ed_age, ed_phone, ed_id, ed_pw;
    private View join_view;
    private String name, age, phone, gender, id, pw;
    private RadioButton j_man, j_woman;
    private int passwd, _age;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _id = (EditText) findViewById(R.id.id_edit);
        _pw = (EditText) findViewById(R.id.pw_edit);
        bt_login = (Button) findViewById(R.id.btn_login);
        bt_join = (Button) findViewById(R.id.btn_join);
        bt_login.setBackgroundColor(Color.argb(0, 0, 0, 0));
        bt_join.setBackgroundColor(Color.argb(0, 0, 0, 0));
    }

    //login method
    public void login(View v) {
        String id = _id.getText().toString();
        String pw = _pw.getText().toString();
        String result="";
        Log.d("ididididi",id);

        Cheack_Login cl = new Cheack_Login();
        cl.check(id, pw);
        result = cl.getresult();

        if (result.equals("OK")) {
            Toast.makeText(this, "로그인 되었습니다", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, Reservation.class);
            Log.d("vvvvvvvvvvvv",id);
            intent.putExtra("id", id);
            startActivity(intent);
            finish();
        } else if (result.equals("Fail")) {
            _id.setText("");
            _pw.setText("");
            Toast.makeText(this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();

        } else if (result.equals("admin")) {
            Toast.makeText(this, "관리자 모드", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        }

    }

    //sign up method
    public void join(View v) {
        join_view = (View) View.inflate(LoginActivity.this, R.layout.dialog_join, null);
        dialog = new AlertDialog.Builder(LoginActivity.this);
        dialog.setView(join_view);
        dialog.setTitle("회원가입");
        dialog.setPositiveButton("가입", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                j_man = (RadioButton) join_view.findViewById(R.id.join_man);
                j_woman = (RadioButton) join_view.findViewById(R.id.join_woman);
                if (j_man.isChecked()) gender = "남자";
                else gender = "여자";
                ed_name = (EditText) join_view.findViewById(R.id.name_join);
                ed_age = (EditText) join_view.findViewById(R.id.age_join);
                ed_phone = (EditText) join_view.findViewById(R.id.phone_join);
                ed_id = (EditText) join_view.findViewById(R.id.id_join);
                ed_pw = (EditText) join_view.findViewById(R.id.pw_join);
                name = ed_name.getText().toString();
                age = ed_age.getText().toString();
                phone = ed_phone.getText().toString();
                id = ed_id.getText().toString();
                pw = ed_pw.getText().toString();
                passwd = Integer.parseInt(pw);
                _age = Integer.parseInt(age);
                new Task().execute(id, pw, name, age, gender, phone);

//                DB_reservation db_reservation = new DB_reservation(getApplicationContext(),"inpyung.db",null,1 );
//                db_reservation.insert("insert into member values ('"+id+"',"+pw+",'"+name+"',"+age+",'"+gender+"','"+phone+"');");
                Toast.makeText(LoginActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

            }
        });
        dialog.setNegativeButton("취소", null);
        dialog.show();
    }

    class Task extends AsyncTask<String, String, String> {
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String pw = params[1];
            String name = params[2];
            String age = params[3];
            String gender = params[4];
            String phone = params[5];
            String data;
            BufferedReader bufferedReader;


            try {
                String link = "http://10.142.47.250:8000/naiw/member_insert.php";

                data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
                data +=  "&"+URLEncoder.encode("pw","UTF-8")+"="+URLEncoder.encode(pw, "UTF-8");
                data +=  "&"+URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name, "UTF-8");
                data +=  "&"+URLEncoder.encode("age","UTF-8")+"="+URLEncoder.encode(age, "UTF-8");
                data +=  "&"+URLEncoder.encode("gender","UTF-8")+"="+URLEncoder.encode(gender, "UTF-8");
                data +=  "&"+URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone, "UTF-8");
                Log.d("id",id);

                url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());
                outputStreamWriter.write(data);
                outputStreamWriter.flush();
                outputStreamWriter.close();
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String result = bufferedReader.readLine();
                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}

