package com.niit.springboothomework;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.niit.pojo.Student;
import com.niit.pojo.StudentDetail;
import com.niit.pojo.User;
import com.niit.pojo.util.requestUrl;
import com.niit.util.OkHttpUtil;
import com.niit.util.ServerResponse;

public class StudentsModifyActivity extends AppCompatActivity {

//    private String url="http://192.168.43.134:8086/restful/Student/modify_detail";
    private static final String TAG ="StudentsModifyActivity" ;
    private String url= requestUrl.StudentModifyDetail.getAll();
    private OkHttpUtil okHttpUtil=new OkHttpUtil();
    private ServerResponse<Student> serverResponse;
    private StudentDetail studentdetail;
    private static final int GET = 1;
    private static final int POST = 2;
    private Gson gson = new Gson();
    private Intent intent;
    private EditText edName;
    private EditText edPassword;
    private EditText edBatch;
    private EditText edAge;
    private EditText edAddress;
    private EditText edEmail;
    private EditText edContactNumber;




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
                    if(serverResponse.getStatus()==400){
                        Toast.makeText(StudentsModifyActivity.this,"fail update",Toast.LENGTH_LONG).show();
                    }else {

                        intent=new Intent(StudentsModifyActivity.this,MainActivity.class);
                        StudentsModifyActivity.this.finish();
                        startActivity(intent);
                    }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_modify);
        edName=(EditText)findViewById(R.id.edName);
        edPassword=(EditText)findViewById(R.id.edPassword);
        edBatch=(EditText)findViewById(R.id.edBatch);
        edAge=(EditText)findViewById(R.id.edAge);
        edAddress=(EditText)findViewById(R.id.edAddress);
        edEmail=(EditText)findViewById(R.id.edEmail);
        edContactNumber=(EditText)findViewById(R.id.edContactNumber);
        Intent intent=getIntent();
        String studentstr=intent.getStringExtra("student");
        Student  student=gson.fromJson(studentstr,Student.class);
        String userstring=intent.getStringExtra("user");
        User user=gson.fromJson(userstring, User.class);
        edName.setText(student.getName());
        edAddress.setText(student.getAddress());
        edAge.setText(student.getAge());
        edBatch.setText(student.getBatch());
        edContactNumber.setText(student.getContactnumber());
        edEmail.setText(student.getEmail());
        edPassword.setText(user.getPassword());

        switch (student.getBatch()){
            case "one":
                RadioButton radioButton=(RadioButton)findViewById(R.id.radioButton);
                radioButton.toggle();
                break;
            case "two":
                RadioButton radioButton2=(RadioButton)findViewById(R.id.radioButton2);
                radioButton2.toggle();
                break;
            case "three":
                RadioButton radioButton3=(RadioButton)findViewById(R.id.radioButton3);
                radioButton3.toggle();
                break;
        }

        Button bt = (Button)findViewById(R.id.btAsk);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提示框
                AlertDialog.Builder al=new AlertDialog.Builder(StudentsModifyActivity.this);
                al.setTitle("Attention");
                al.setMessage("Do you confirm the modification?");
                al.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        studentdetail=new StudentDetail();
                        StudentDetail studentdetail=new StudentDetail();
                        studentdetail.setAddress(edAddress.getText().toString());
                        studentdetail.setAge(edAge.getText().toString());
                        studentdetail.setBatch(edBatch.getText().toString());
                        studentdetail.setContactNumber(edContactNumber.getText().toString());
                        studentdetail.setEmail(edEmail.getText().toString());
                        studentdetail.setName(edName.getText().toString());
                        studentdetail.setStuID(student.getStuid());
                        studentdetail.setPassword(edPassword.getText().toString());

                        okHttpUtil.setObj(studentdetail);
                        okHttpUtil.setUrl(url);
                        okHttpUtil.getDataFromPost(handler);



                    }
                });
                al.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                al.show();

            }
        });

        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                EditText ed=(EditText)findViewById(R.id.edBatch);
                switch (checkedId){
                    case R.id.radioButton:
                        ed.setText("one");
                        break;
                    case R.id.radioButton2:
                        ed.setText("two");
                        break;
                    case R.id.radioButton3:
                        ed.setText("three");
                        break;
                }
            }
        });
    }
}