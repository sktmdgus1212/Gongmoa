package com.example.gongmoa;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MemberlistActivity extends AppCompatActivity {

    TextView contest_name;
    ImageButton back;
    String str_theme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memberlist);

        contest_name = findViewById(R.id.name);
        back = findViewById(R.id.back);

        str_theme = getIntent().getStringExtra("contest_name");
        contest_name.setText(str_theme);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
