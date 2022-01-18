package com.example.gongmoa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ContestInfoActivity extends AppCompatActivity {

    TextView theme;
    TextView startdate;
    TextView enddate;
    TextView introduce;
    TextView writedate;
    TextView userid;
    TextView field;
    ImageButton back;
    Button partylist;
    Button memberlist;

    String str_theme;
    String str_startdate;
    String str_enddate;
    String str_introduce;
    String str_writedate;
    String str_userid;
    String str_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contest);

        theme = findViewById(R.id.theme2);
        startdate = findViewById(R.id.startdate2);
        enddate = findViewById(R.id.enddate2);
        introduce = findViewById(R.id.introduce2);
        writedate = findViewById(R.id.writedate2);
        userid = findViewById(R.id.userid2);
        field = findViewById(R.id.field2);
        back = findViewById(R.id.back);
        partylist = findViewById(R.id.partylist);
        memberlist = findViewById(R.id.memberlist);

        str_theme = getIntent().getStringExtra("contest_name");
        str_startdate = getIntent().getStringExtra("contest_startdate");
        str_enddate = getIntent().getStringExtra("contest_enddate");
        str_introduce = getIntent().getStringExtra("contest_introduce");
        str_writedate = getIntent().getStringExtra("contest_writedate");
        str_userid = getIntent().getStringExtra("user_id");
        str_field = getIntent().getStringExtra("contest_field");


        theme.setText(str_theme);
        startdate.setText(str_startdate);
        enddate.setText(str_enddate);
        introduce.setText(str_introduce);
        writedate.setText("작성날짜: "+str_writedate);
        userid.setText("작성자: "+ str_userid);
        field.setText("("+str_field+")");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        partylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( ContestInfoActivity.this, PartylistActivity.class);
                intent.putExtra("contest_name", str_theme);
                startActivity(intent);
            }
        });

        memberlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( ContestInfoActivity.this, MemberlistActivity.class);
                intent.putExtra("contest_name", str_theme);
                startActivity(intent);
            }
        });

    }


}
