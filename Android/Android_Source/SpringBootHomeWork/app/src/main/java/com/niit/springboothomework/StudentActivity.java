package com.niit.springboothomework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.niit.pojo.Student;
import com.niit.pojo.User;
import com.niit.util.ServerResponse;

import java.util.List;

public class StudentActivity extends AppCompatActivity {
    private static final String TAG ="StudentActivity" ;
    private ServerResponse<Student> serverResponse;
    private Gson gson = new Gson();
    private TextView txName2;
    private TextView txStudentsID2;
    private TextView txBatch2;
    private TextView txAge2;
    private TextView txAddress2;
    private TextView txEmai2;
    private TextView txContactNumber2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        txName2=(TextView)findViewById(R.id.txName2);
        txStudentsID2=(TextView)findViewById(R.id.txStudentsID2);
        txBatch2=(TextView)findViewById(R.id.txBatch2);
        txAge2=(TextView)findViewById(R.id.txAge2);
        txAddress2=(TextView)findViewById(R.id.txAddress2);
        txEmai2=(TextView)findViewById(R.id.txEmail2);
        txContactNumber2=(TextView)findViewById(R.id.txContactNumber2);
        Intent intent=getIntent();
        String studentstring=intent.getStringExtra("student");
        serverResponse=gson.fromJson(studentstring,ServerResponse.class);
        String studentstr=gson.toJson( serverResponse.getData());
        Student  student=gson.fromJson(studentstr,Student.class);
        String userstring=intent.getStringExtra("user");
        User user=gson.fromJson(userstring, User.class);
        txName2.setText(student.getName());
        txStudentsID2.setText(student.getStuid()+"");
        txBatch2.setText(student.getBatch());
        txAge2.setText(student.getAge()+"");
        txAddress2.setText(student.getAddress());
        txEmai2.setText(student.getEmail());
        txContactNumber2.setText(student.getContactnumber()+"");


        Button btModify= (Button)findViewById(R.id.btModify);
        btModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(StudentActivity.this,StudentsModifyActivity.class);
                i.putExtra("student",studentstr);
                i.putExtra("user",userstring);
                startActivity(i);
            }
        });
        Button btAsk = (Button)findViewById(R.id.btAsk);
        btAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(StudentActivity.this,StudentsAskActivity.class);
                i.putExtra("student",studentstr);
                startActivity(i);
            }
        });
    }
}