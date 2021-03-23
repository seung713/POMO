package com.example.gbridgeproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, FireBaseDBCallback {
    EditText et_id, et_pw;
    TextView tv_reg;
    Button btn_login;
    LinearLayout p_layout;
    FireBaseDBManager fbdbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = (EditText)findViewById(R.id.et_id);
        et_pw = (EditText)findViewById(R.id.et_pw);
        tv_reg = (TextView)findViewById(R.id.tv_reg);
        tv_reg.setOnClickListener(this);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        p_layout = (LinearLayout)findViewById(R.id.p_layout);

        fbdbm = new FireBaseDBManager();
        fbdbm.addFirebaseDBCallback(this);
    }

    @Override
    protected void onPause() {
        et_pw.setText("");
        et_id.setText("");
        super.onPause();
    }

    /* -------------------- start: click button -------------------- */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tv_reg) {
            /* -------------------- start: register button -------------------- */
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            /* -------------------- end: register button -------------------- */
        } else if(v.getId() == R.id.btn_login) {
            /* -------------------- start: login button -------------------- */
            final String userId = et_id.getText().toString();
            final String userPw = et_pw.getText().toString();

            if(userId.isEmpty())
                Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            else if(userPw.isEmpty())
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            else {
                fbdbm.readUserInfo(userId);
            }
            /* -------------------- end: login button -------------------- */
        }
    }
    /* -------------------- end: click button -------------------- */

    /* -------------------- start: Firebase  -------------------- */
    @Override
    public void writeResult(boolean flag) {

    }

    @Override
    public void updateResult(boolean flag) {

    }

    @Override
    public void readResult(UserInfo info, boolean flag) {
        if(flag) {
            if(info.PW.equals(et_pw.getText().toString())) {
                showProgress();
                Toast.makeText(this,info.Name+" 님 환영합니다!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("userId", info.ID);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this,"비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                et_pw.setText("");
            }
        } else {
            Toast.makeText(this,"존재하지 않는 사용자입니다.", Toast.LENGTH_SHORT).show();
            et_pw.setText("");
            et_id.setText("");
        }
    }

    @Override
    public void readAllResult(ArrayList<UserInfo> infolist, boolean flag) {

    }
    /* -------------------- end: Firebase  -------------------- */

    public void showProgress() {
        p_layout.setVisibility(View.VISIBLE);
    }

    private long pressedTime = 0;

    @Override
    public void onBackPressed() {
        if (pressedTime == 0) {
            Toast.makeText(this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            pressedTime = System.currentTimeMillis();
        } else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);
            if (seconds > 2000) {
                Toast.makeText(this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
                pressedTime = 0;
            } else {
                super.onBackPressed();
                finish();
                onDestroy();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }
}
