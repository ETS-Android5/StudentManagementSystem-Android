package com.niit.springboothomework;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.niit.pojo.LeaveDTO;
import com.niit.pojo.Student;
import com.niit.pojo.util.requestUrl;
import com.niit.util.OkHttpUtil;
import com.niit.util.ServerResponse;

public class StudentsAskActivity extends AppCompatActivity {
    private static final String TAG ="StudentsAskActivity" ;
//    private String url="http://192.168.43.134:8086/restful/Student/ask_leave";
    private String url=requestUrl.StudentAskLeave.getAll();
    private ServerResponse<Student> serverResponse;
    private Gson gson = new Gson();
    private static final int GET = 1;
    private static final int POST = 2;
    private Intent intent;
    private OkHttpUtil okHttpUtil=new OkHttpUtil();
    private EditText edName;
    private EditText editTextDate;
    private EditText editTextTextPersonName;
    private LeaveDTO leaveDTO;
    private String studentstr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch(msg.what){
                case GET:
                    //获取数据
                    break;
                case POST:
                    //获取数据
                    Log.v(TAG,(String)msg.obj);
                    ServerResponse<Object> serverResponse= gson.fromJson((String)msg.obj, ServerResponse.class);
                    if(serverResponse.getStatus()==401){
                        Toast.makeText(StudentsAskActivity.this,"fail add",Toast.LENGTH_LONG).show();
                    }else {
                        StudentsAskActivity.this.finish();
                    }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_ask);
        edName=(EditText)findViewById(R.id.edName);
        editTextDate=(EditText)findViewById(R.id.editTextDate);
        editTextTextPersonName=(EditText)findViewById(R.id.editTextTextPersonName);
        Intent intent=getIntent();
        studentstr=intent.getStringExtra("student");
        Student  student=gson.fromJson(studentstr,Student.class);
        edName.setText(student.getName());

        Button bt=(Button)findViewById(R.id.btAsk);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox ck=(CheckBox)findViewById(R.id.checkBox);
               //判断checkbox
                if(ck.isChecked()){
                    leaveDTO=new LeaveDTO();
                    leaveDTO.setStuid(student.getStuid());
                    leaveDTO.setLeavedate(editTextDate.getText().toString());
                    leaveDTO.setReason(editTextTextPersonName.getText().toString());

                    okHttpUtil.setObj(leaveDTO);
                    okHttpUtil.setUrl(url);
                    okHttpUtil.getDataFromPost(handler);
                }
                else{
                    AlertDialog.Builder al=new AlertDialog.Builder(StudentsAskActivity.this);
                    al.setTitle("Attention");
                    al.setMessage("You have to guarantee my personal safety and not violate the law after asking for leave.\n");
                    al.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    al.show();
                }
            }
        });

    }
}


