
package com.example.gongmoa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

public class WriteContestActivity extends AppCompatActivity {

    ImageButton back;
    Button write;


    EditText contest_name;
    int field_num;
    EditText contest_startdate;
    EditText contest_enddate;
    //int user_seq;
    Date contest_writedate;
    ImageView contest_image;
    EditText contest_introduce;
    String user_id;


    RadioGroup field_radioGroup;

    final int GET_GALLERY_IMAGE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_contest);

        long now = System.currentTimeMillis();
        back = findViewById(R.id.back);
        write = findViewById(R.id.write);

        contest_name = (EditText) findViewById(R.id.contest_name);
        contest_startdate= (EditText) findViewById(R.id.startdate);
        contest_enddate= (EditText) findViewById(R.id.enddate);
        contest_writedate=new Date(now);
        field_num=0;
        contest_image = (ImageButton) findViewById(R.id.contest_image);
        contest_introduce = (EditText) findViewById(R.id.introduce_edittext);
        user_id = ((LoginActivity)LoginActivity.context_main).contest_user_id;


        //System.out.println(user_id);
        //뒤로가기 버튼------------------------------------
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //갤러리 버튼--------------------------------------
        contest_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        //------------------------------------------------
       write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String contest_name_str = contest_name.getText().toString();
                int contest_startdate_int = Integer.parseInt(contest_startdate.getText().toString());
                int contest_enddate_int = Integer.parseInt(contest_enddate.getText().toString());
                String contest_writedate_str = convertDatetoString(contest_writedate);
                String contest_introduce_str = contest_introduce.getText().toString();
                Bitmap bitmap = ((BitmapDrawable)contest_image.getDrawable()).getBitmap();
                //System.out.println(bitmap);
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 게시글등록에 성공한 경우
                                Toast.makeText(getApplicationContext(), "게시글 등록 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(WriteContestActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else { // 게시글등록에 실패한 경우
                                Toast.makeText(getApplicationContext(), "게시글 작성 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                WriteContestRequest writeContestRequest = new WriteContestRequest(contest_name_str, field_num, contest_startdate_int, contest_enddate_int, contest_writedate_str, contest_introduce_str, user_id, bitmap, responseListener);
                RequestQueue queue = Volley.newRequestQueue(WriteContestActivity.this);
                queue.add(writeContestRequest);
            }
        });
    //-----------------------------------------
        //분야버튼!!!!!!!!!!!!!!!!!!!!!!!111
        field_radioGroup = (RadioGroup) findViewById(R.id.field_radiogroup);
        field_radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            contest_image.setImageURI(selectedImageUri);
        }
    }

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i == R.id.rg_btn1){
                    field_num=1;
                }
                else if(i == R.id.rg_btn2){
                    field_num=2;
                }
                else if(i == R.id.rg_btn3){
                    field_num=3;
                }
                else if(i == R.id.rg_btn4){
                    field_num=4;
                }
                else if(i == R.id.rg_btn5){
                    field_num=5;
                }
                else if(i == R.id.rg_btn6){
                    field_num=6;
                }

            }
    };

    public static String convertDatetoString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        return str;
    }



}

