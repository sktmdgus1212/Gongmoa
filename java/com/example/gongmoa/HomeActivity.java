package com.example.gongmoa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.fragment.app.Fragment;

public class HomeActivity extends Fragment implements AdapterView.OnItemClickListener {

        Button writecontest;
        Spinner filterSpinner;


        private static String TAG="phptest_MainActivity";

        private static final String TAG_JSON="webnautes";
        private static final String TAG_contest_name="contest_name";
        private static final String TAG_contest_image="contest_image";
        private static final String TAG_contest_field="contest_field";
        private static final String TAG_contest_startdate="contest_startdate";
        private static final String TAG_contest_enddate="contest_enddate";
        private static final String TAG_contest_writedate="contest_writedate";
        private static final String TAG_user_id="user_id";
        private static final String TAG_contest_introduce="contest_introduce";

        String name;
        String image;
        Bitmap image_file;
        String field;
        String startdate;
        String enddate;
        String writedate;
        String user_id;
        String introduce;
        private TextView mTextViewResult;
        ArrayList<HashMap<String,String>>mArrayList;
        ListView mlistView;
        String mJsonString;
        ArrayList<contest> al = new ArrayList<contest>();

       // HashMap<String, String> hashMap;


        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
                View view=inflater.inflate(R.layout.home,container,false);
                writecontest=(Button)view.findViewById(R.id.writecontest);

                mTextViewResult=(TextView)view.findViewById(R.id.textView_main_result);
                mlistView=(ListView)view.findViewById(R.id.listView_main_list);
                mArrayList=new ArrayList<>();

                GetData task=new GetData();
                task.execute("http://192.168.0.23/getcontest.php");

                writecontest.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                                Intent intent=new Intent(getActivity(), WriteContestActivity.class);
                                startActivity(intent);

                        }
                });

                filterSpinner=(Spinner)view.findViewById(R.id.spinner_filter);
                ArrayAdapter filterAdapter=ArrayAdapter.createFromResource(getActivity(),
                        R.array.filter,android.R.layout.simple_spinner_item);
                filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                filterSpinner.setAdapter(filterAdapter);


                return view;
        }



        private class GetData extends AsyncTask<String,Void,String> {
                ProgressDialog progressDialog;
                String errorString=null;

                @Override
                protected void onPreExecute(){
                        super.onPreExecute();

                        progressDialog=ProgressDialog.show(getActivity(),
                                "PleaseWait",null,true,true);
                }
                //---------------------------------------------------
                @Override
                protected void onPostExecute(String result){
                        super.onPostExecute(result);

                        progressDialog.dismiss();
                        mTextViewResult.setText(result);
                        Log.d(TAG,"response-"+result);

                        if(result==null){

                                mTextViewResult.setText(errorString);
                        }
                        else{

                                mJsonString=result;
                                showResult();
                        }
                }
                //--------------------------------------------
                @Override
                protected String doInBackground(String...params){

                        String serverURL=params[0];


                        try{

                                URL url=new URL(serverURL);
                                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();


                                httpURLConnection.setReadTimeout(5000);
                                httpURLConnection.setConnectTimeout(5000);
                                httpURLConnection.connect();


                                int responseStatusCode=httpURLConnection.getResponseCode();
                                Log.d(TAG,"responsecode-"+responseStatusCode);

                                InputStream inputStream;
                                if(responseStatusCode==HttpURLConnection.HTTP_OK){
                                        inputStream=httpURLConnection.getInputStream();
                                }
                                else{
                                        inputStream=httpURLConnection.getErrorStream();
                                }


                                InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"UTF-8");


                               // inputStreamReader.mark(5);


                                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

                                StringBuilder sb=new StringBuilder();
                                String line;

                                while((line=bufferedReader.readLine())!=null){
                                        sb.append(line);
                                }


                                bufferedReader.close();


                                return sb.toString().trim();


                        }catch(Exception e){

                                Log.d(TAG,"InsertData:Error",e);
                                errorString=e.toString();

                                return null;
                        }

                }
        }
        //---------------------------
        private void showResult(){
                try{
                        JSONObject jsonObject=new JSONObject(mJsonString);
                        JSONArray jsonArray=jsonObject.getJSONArray(TAG_JSON);

                        for(int i=0 ; i < jsonArray.length() ; i++){

                                JSONObject item=jsonArray.getJSONObject(i);

                                name=item.getString(TAG_contest_name);
                                image=item.getString(TAG_contest_image);
                                image_file=StringToBitmap(item.getString(TAG_contest_image));
                                field=item.getString(TAG_contest_field);
                                if(field.equals("1")){
                                        field="기획/아이디어";
                                }
                                else if(field.equals("2")){
                                        field="웹/모바일/IT";
                                }
                                else if(field.equals("3")){
                                        field="광고/마케팅";
                                }
                                else if(field.equals("4")){
                                        field="디자인/캐릭터";
                                }
                                else if(field.equals("5")){
                                        field="봉사활동";
                                }
                                else if(field.equals("6")){
                                        field="예체능";
                                }
                                startdate=item.getString(TAG_contest_startdate);
                                enddate=item.getString(TAG_contest_enddate);
                                writedate=item.getString(TAG_contest_writedate);
                                user_id=item.getString(TAG_user_id);
                                introduce = item.getString(TAG_contest_introduce);

                                al.add(new contest(name, image_file, startdate, enddate, writedate, introduce, field, user_id));


                                MyAdapter adapter = new MyAdapter(
                                        getActivity(), // 현재화면의 제어권자
                                        R.layout.listview_item,  // 리스트뷰의 한행의 레이아웃
                                        al);         // 데이터

                                mlistView.setAdapter(adapter);

                                mlistView.setOnItemClickListener(this);

                        }

                 /*       Customadapter adapter = new Customadapter(getActivity(), 0 , hashMap);
                        mlistView.setAdapter(adapter);*/
   /*    SimpleAdapter adapter=new SimpleAdapter(
        getActivity(),mArrayList,R.layout.listview_item,
        new String[]{TAG_contest_name,TAG_contest_image,TAG_contest_field,
        TAG_contest_startdate,TAG_contest_enddate,TAG_contest_writedate,TAG_user_id},
        new int[]{R.id.theme,R.id.picture,R.id.field,R.id.start,R.id.end,R.id.writedate,R.id.userid}
        );
*/
                }catch(JSONException e){

                        Log.d(TAG,"showResult:",e);
                }

        }



     /*   @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ContestInfoActivity.class);
                intent.putExtra("contest_name", name);
                intent.putExtra("contest_image", image);
                intent.putExtra("contest_field", field);
                intent.putExtra("contest_stratdate", startdate);
                intent.putExtra("contest_enddate", enddate);
                intent.putExtra("contest_writedate", writedate);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
        }*/


        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent( getActivity(), ContestInfoActivity.class);
                intent.putExtra("contest_name", al.get(i).theme);
                intent.putExtra("contest_image", al.get(i).img);
                intent.putExtra("contest_field", al.get(i).field);
                intent.putExtra("contest_startdate", al.get(i).startdate);
                intent.putExtra("contest_enddate", al.get(i).enddate);
                intent.putExtra("contest_writedate", al.get(i).writedate);
                intent.putExtra("user_id", al.get(i).userid);
                intent.putExtra("contest_introduce", al.get(i).introduce);
                startActivity(intent);
        }


        public static Bitmap StringToBitmap(String encodedString){
                try{
                        byte[] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
                        Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);

                        System.out.println("HomeActivity >>>al >>> " + encodedString);
                        System.out.println("HomeActivity >>>al >>> " + encodeByte);
                        System.out.println("HomeActivity >>>al >>> " + bitmap);
                        return bitmap;
                  } catch(Exception e) {
                       e.getMessage();
                        return null;
                       }
        }

        class MyAdapter extends BaseAdapter { // 리스트 뷰의 아답타
                Context context;
                int layout;
                ArrayList<contest> al;
                LayoutInflater inf;
                public MyAdapter(Context context, int layout, ArrayList<contest> al) {
                        this.context = context;
                        this.layout = layout;
                        this.al = al;
                        inf = (LayoutInflater)context.getSystemService
                                (Context.LAYOUT_INFLATER_SERVICE);
                }
                @Override
                public int getCount() {
                        return al.size();
                }
                @Override
                public Object getItem(int position) {
                        return al.get(position);
                }
                @Override
                public long getItemId(int position) {
                        return position;
                }
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                        if (convertView==null) {
                                convertView = inf.inflate(layout, null);
                        }
                        ImageView image = (ImageView)convertView.findViewById(R.id.picture);
                        TextView name = (TextView)convertView.findViewById(R.id.theme);
                        TextView startdate = (TextView)convertView.findViewById(R.id.start);
                        TextView enddate= (TextView)convertView.findViewById(R.id.end);
                        TextView writedate = (TextView)convertView.findViewById(R.id.writedate);
                        TextView field = (TextView)convertView.findViewById(R.id.field);
                        TextView userid = (TextView)convertView.findViewById(R.id.userid);

                        contest m = al.get(position);
                        image.setImageBitmap(m.img);
                        name.setText(m.theme);
                        startdate.setText("시작마감일: "+m.startdate);
                        enddate.setText(m.enddate);
                        writedate.setText("작성날짜: "+m.writedate);
                        field.setText("분야: "+m.field);
                        userid.setText("작성자: "+m.userid);
                        return convertView;
                }
        }


        class contest { // 자바빈
                String theme = "";
                String startdate = "";
                String enddate = "";
                String writedate = "";
                String introduce = "";
                String field = "";
                Bitmap img; // 이미지
                String userid = "";
                public contest(String theme, Bitmap img, String startdate, String enddate, String writedate, String introduce, String field, String userid) {
                        super();
                        this.theme = theme;
                        this.img = img;
                        this.startdate = startdate;
                        this.enddate = enddate;
                        this.writedate = writedate;
                        this.introduce = introduce;
                        this.field = field;
                        this.userid = userid;
                }
        }






}