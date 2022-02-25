package com.niit.springboothomework;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.niit.pojo.Student;
import com.niit.pojo.util.requestUrl;
import com.niit.util.OkHttpUtil;
import com.niit.util.ServerResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdministratorActivity extends AppCompatActivity {
    private static final String TAG ="AdministratorActivity" ;
    private String studentListString;
    private ServerResponse<List<Student>> serverResponse;
    private Gson gson = new Gson();
    private OkHttpUtil okHttpUtil=new OkHttpUtil();
    private static final int GET = 1;
//    private  String url="http://192.168.43.134:8086/restful/admin/to-home";
//    private  String url2="http://192.168.43.134:8086/restful/Admin/look_students_leave";
    private  String url= requestUrl.AdminToHome.getAll();
    private  String url2=requestUrl.AdminLookStudentLeave.getAll();

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch(msg.what){
                case GET:
                    ServerResponse<Object> serverResponse= gson.fromJson((String)msg.obj, ServerResponse.class);
                    if(serverResponse.getStatus()==2){ studentListString=(String)msg.obj;reload(studentListString);}
                    else if (serverResponse.getStatus()==1){
                       Intent intent=new Intent(AdministratorActivity.this,AdminCheckActivity.class);
                        intent.putExtra("studentLeaveDTOList",(String)msg.obj);
                        startActivityForResult(intent,1);
                        break;

                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Button btcheck = findViewById(R.id.btCheck);
        FloatingActionButton btAdd = findViewById(R.id.btAdd);
        Intent intent=getIntent();
        studentListString=intent.getStringExtra("studentList");
        reload(studentListString);

        btcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okHttpUtil.setUrl(url2);
                okHttpUtil.getDataFromGet(handler);
            }
        });
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorActivity.this,AdminAddActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            okHttpUtil.setUrl(url);
            okHttpUtil.getDataFromGet(handler);
        }
    }


    private void reload(String studentListString){
        serverResponse= gson.fromJson(studentListString, ServerResponse.class);
        List<Student> studentList=serverResponse.getData();
        String studentList2 =gson.toJson(studentList);
        Student[] students=gson.fromJson(studentList2,Student[].class);
        ListView listView = (ListView) this.findViewById(R.id.listView);

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
        for (Student student:students){
            HashMap<String, Object> item = new HashMap<String, Object>();

            item.put("name",student.getName());
            item.put("id",student.getStuid());
            item.put("others","Batch: "+student.getBatch()+"\nAge:"+student.getAge()+"\nAddress:"+student.getAddress()+"\nContactNumber:"+student.getContactnumber());
            item.put("studentstr", gson.toJson(student));
            data.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.student_item,
                new String[]{"name", "id", "others"}, new int[]{R.id.stuname, R.id.stuid, R.id.stubatchagecontactnumber});

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
                String studentstr=(String)data.get("studentstr");
                Intent intent =new Intent(AdministratorActivity.this,AdminEditActivity.class);
                intent.putExtra("studentstr",studentstr);
                startActivityForResult(intent, 1);
            }
        });




    }
    //点击返回上一页面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }
}