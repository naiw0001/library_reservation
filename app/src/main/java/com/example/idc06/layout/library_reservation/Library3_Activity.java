package com.example.idc06.layout.library_reservation;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by idc06 on 2016-12-28.
 */
public class Library3_Activity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_sit[];
    private final int SIT_NUM = 30;
    private int sit_id[];
    private String num;
    private ViewFlipper vf;
    private LinearLayout li_L1,li_L2,li_L3;
    private String date_time, date, time;
    private DatePicker d_P;
    private TimePicker t_P;
    private AlertDialog.Builder dialog;
    private String id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library3);
        sit_id = new int[]{R.id.sit1, R.id.sit2, R.id.sit3, R.id.sit4, R.id.sit5, R.id.sit6, R.id.sit7, R.id.sit8, R.id.sit9, R.id.sit10,
                R.id.sit11, R.id.sit12, R.id.sit13, R.id.sit14, R.id.sit15, R.id.sit16, R.id.sit17, R.id.sit18, R.id.sit19,
                R.id.sit20, R.id.sit21, R.id.sit22, R.id.sit23, R.id.sit24, R.id.sit25, R.id.sit26, R.id.sit27, R.id.sit28, R.id.sit29, R.id.sit30};
        btn_sit = new Button[30];
        vf = (ViewFlipper)findViewById(R.id.viewf);
        li_L1 = (LinearLayout)findViewById(R.id.first_layout);
        li_L2 = (LinearLayout)findViewById(R.id.second_layout);
        li_L3 = (LinearLayout)findViewById(R.id.third_layout);
        d_P = (DatePicker)findViewById(R.id.date);
        t_P=(TimePicker)findViewById(R.id.time);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        for (int i = 0 ; i <SIT_NUM; i++){
            btn_sit[i] = (Button)findViewById(sit_id[i]);
            btn_sit[i].setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v) {
        for(int i = 0 ; i<SIT_NUM; i++){
            if(v.getId() == sit_id[i]){
                num = btn_sit[i].getText().toString();
            }
        }
    }

    public void next1(View v){
        vf.showNext();
        li_L1.setVisibility(View.GONE);
        li_L2.setVisibility(View.VISIBLE);
        li_L3.setVisibility(View.GONE);

    }

    public void back1(View v){
        vf.showPrevious();
        li_L1.setVisibility(View.VISIBLE);
        li_L2.setVisibility(View.GONE);
        li_L3.setVisibility(View.GONE);
    }

    public void next2(View v){
        vf.showNext();
        li_L1.setVisibility(View.GONE);
        li_L2.setVisibility(View.GONE);
        li_L3.setVisibility(View.VISIBLE);
    }

    public void back2(View v){
        vf.showPrevious();
        li_L1.setVisibility(View.GONE);
        li_L2.setVisibility(View.VISIBLE);
        li_L3.setVisibility(View.GONE);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void okay(View v){

        String year = String.valueOf(d_P.getYear());
        String month = String.valueOf(d_P.getMonth());
        String day = String.valueOf(d_P.getDayOfMonth());
        String hour = String.valueOf(t_P.getHour());
        String min = String.valueOf(t_P.getMinute());

        // now time
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String N_year = (new SimpleDateFormat("yyyy").format(date));
        N_year +="년";
        String N_month = (new SimpleDateFormat("MM").format(date));
        N_month +="월";
        String N_day = (new SimpleDateFormat("dd").format(date));
        N_day +="일";
        String N_hour = (new SimpleDateFormat("HH").format(date));
        N_hour +="시";
        String N_minute = (new SimpleDateFormat("mm").format(date));
        N_minute +="분";

        final String now = N_year + N_month + N_day + N_hour + N_minute;

        final String  result = year + "년" + month + "월" + day + "일" + hour + "시" + min + "분";

        dialog = new AlertDialog.Builder(Library3_Activity.this);
        dialog.setTitle("확인");
        dialog.setMessage(num + "번\n" +"시간 : " +result);
        dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database(result,now);
                startActivity(new Intent(Library3_Activity.this,Reservation.class));
                finish();
            }
        });
        dialog.setNegativeButton("취소",null);
        dialog.show();
    }

    public void database(String result,String now){
        String temp = "http://10.142.47.250:8000/naiw/reservation_li1.php";

        class Reserv_1 extends AsyncTask<String,Void,String> {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(),"좌석이 예약되었습니다,",Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String link = params[0];
                String time = params[1];
                String now_time = params[2];
                String num=params[3];
                String id = params[4];
                String data="";
//                String data;
                try {
                    data += "?time="+ URLEncoder.encode(time,"UTF-8");
                    data += "&now_time="+URLEncoder.encode(now_time,"UTF-8");
                    data += "&num="+URLEncoder.encode(num,"UTF-8");
                    data += "&id="+URLEncoder.encode(id,"UTF-8");
//                    data = URLEncoder.encode("time","UTF-8") + "="+URLEncoder.encode(time,"UTF-8");
//                    data +=  "&"+URLEncoder.encode("now_time","UTF-8")+"="+URLEncoder.encode(now_time, "UTF-8");
//                    data +=  "&"+URLEncoder.encode("num","UTF-8")+"="+URLEncoder.encode(num, "UTF-8");
//                    data +=  "&"+URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id, "UTF-8");
                    link += data;
                    Log.d("mmmmmmmmmmmmmmmm",data);
                    Log.d("nnnnnnnnnnnnnnnnnnnnnnn",link);
                    URL url = new URL(link);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.connect();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
//                    conn.setRequestMethod("POST");
//                    OutputStreamWriter outW = new OutputStreamWriter(conn.getOutputStream());
//                    outW.write(data);
//                    outW.flush();
//                    outW.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        new Reserv_1().execute(temp,result,now,num,id);
    }


}
