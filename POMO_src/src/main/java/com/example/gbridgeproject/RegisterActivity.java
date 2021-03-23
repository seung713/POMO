package com.example.gbridgeproject;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements TextWatcher, AdapterView.OnItemSelectedListener, View.OnClickListener, FireBaseDBCallback{
    EditText et_id, et_pw, et_pwChk, et_name;
    TextView tv_idChk;
    ImageView img_chk;
    Button btn_idChk, btn_reg, btn_cancel;
    Spinner sp_col, sp_dept;
    ArrayAdapter<CharSequence> ad_col, ad_dept;

    FireBaseDBManager fbdbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_id = (EditText)findViewById(R.id.et_id);
        et_pw = (EditText)findViewById(R.id.et_pw);
        et_pwChk = (EditText)findViewById(R.id.et_pwChk);
        et_name = (EditText)findViewById(R.id.et_name);

        tv_idChk = (TextView)findViewById(R.id.tv_idChk);
        img_chk = (ImageView)findViewById(R.id.img_chk);
        btn_idChk = (Button)findViewById(R.id.btn_idChk);
        btn_reg = (Button)findViewById(R.id.btn_reg);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);

        fbdbm = new FireBaseDBManager();
        fbdbm.addFirebaseDBCallback(this);

        // button click
        btn_idChk.setOnClickListener(this);
        btn_reg.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        // password check
        et_pw.addTextChangedListener(this);
        et_pwChk.addTextChangedListener(this);

        // Spinner
        sp_col = (Spinner)findViewById(R.id.sp_col);
        sp_dept = (Spinner)findViewById(R.id.sp_dept);
        ad_col = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.col, android.R.layout.simple_spinner_dropdown_item);
        ad_col.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_col.setAdapter(ad_col);
        sp_col.setOnItemSelectedListener(this);
    }
    /* -------------------- start: password check -------------------- */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0 && s.toString().equals(et_pw.getText().toString()) && s.toString().equals(et_pwChk.getText().toString())) {
            img_chk.setVisibility(View.VISIBLE);
        } else {
            img_chk.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    /* -------------------- end: password check -------------------- */

    /* -------------------- start: Spinner  -------------------- */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (ad_col.getItem(position).equals("가천리버럴아츠칼리지")) {
            ad_dept = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.la_dept, android.R.layout.simple_spinner_dropdown_item);
            ad_dept.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_dept.setAdapter(ad_dept);
        } else if (ad_col.getItem(position).equals("경영대학")) {
            ad_dept = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.b_dept, android.R.layout.simple_spinner_dropdown_item);
            ad_dept.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_dept.setAdapter(ad_dept);
        } else if (ad_col.getItem(position).equals("사회과학대학")) {
            ad_dept = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.ss_dept, android.R.layout.simple_spinner_dropdown_item);
            ad_dept.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_dept.setAdapter(ad_dept);
        } else if (ad_col.getItem(position).equals("인문대학")) {
            ad_dept = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.h_dept, android.R.layout.simple_spinner_dropdown_item);
            ad_dept.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_dept.setAdapter(ad_dept);
        } else if (ad_col.getItem(position).equals("법과대학")) {
            ad_dept = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.l_dept, android.R.layout.simple_spinner_dropdown_item);
            ad_dept.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_dept.setAdapter(ad_dept);
        } else if (ad_col.getItem(position).equals("공과대학")) {
            ad_dept = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.e_dept, android.R.layout.simple_spinner_dropdown_item);
            ad_dept.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_dept.setAdapter(ad_dept);
        } else if (ad_col.getItem(position).equals("바이오나노대학")) {
            ad_dept = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.bt_dept, android.R.layout.simple_spinner_dropdown_item);
            ad_dept.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_dept.setAdapter(ad_dept);
        } else if (ad_col.getItem(position).equals("IT융합대학")) {
            ad_dept = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.c_dept, android.R.layout.simple_spinner_dropdown_item);
            ad_dept.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_dept.setAdapter(ad_dept);
        } else if (ad_col.getItem(position).equals("한의과대학")) {
            ad_dept = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.km_dept, android.R.layout.simple_spinner_dropdown_item);
            ad_dept.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_dept.setAdapter(ad_dept);
        } else if (ad_col.getItem(position).equals("예술·체육대학")) {
            ad_dept = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.pe_dept, android.R.layout.simple_spinner_dropdown_item);
            ad_dept.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_dept.setAdapter(ad_dept);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /* -------------------- end: Spinner -------------------- */

    /* -------------------- start: click button -------------------- */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_idChk) {
            /* -------------------- start: id check -------------------- */
            final String userId = et_id.getText().toString();
            if(userId.isEmpty())
                Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            else
                fbdbm.readUserInfo(userId);
            /* -------------------- end: id check -------------------- */
        }
        else if (v.getId() == R.id.btn_reg) {
            /* -------------------- start: validation -------------------- */
            final String userId = et_id.getText().toString();
            final String userPw = et_pw.getText().toString();
            final String userPwChk = et_pwChk.getText().toString();
            final String userName = et_name.getText().toString();
            final String userCol = sp_col.getSelectedItem().toString();
            final String userDept = sp_dept.getSelectedItem().toString();

            if(userId.isEmpty() || userPw.isEmpty()  || userName.isEmpty())
                Toast.makeText(this, "회원정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
            else if(tv_idChk.getText().toString().contains("중복체크"))
                Toast.makeText(this, "아이디 중복체크해주세요.", Toast.LENGTH_SHORT).show();
            else if(tv_idChk.getText().toString().contains("이미"))
                Toast.makeText(this, "다른 아이디를 사용해주세요.", Toast.LENGTH_SHORT).show();
            else if(userPw.length() < 4)
                Toast.makeText(this, "비밀번호 4자리 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
            else if (!userPw.equals(userPwChk))
                Toast.makeText(this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
            else {
                UserInfo u_info = new UserInfo(userId, userPw, userName, userCol, userDept, "0");
                fbdbm.insertUserInfo(u_info);
            }
            /* -------------------- end: validation -------------------- */
        } else {
            finish();
        }
    }
    /* -------------------- end: click button -------------------- */

    /* -------------------- start: Firebase -------------------- */
    @Override
    public void writeResult(boolean flag) {
        if(flag) {
            Toast.makeText(this, "회원가입에 성공했습니다.\n로그인 페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "회원가입에 실패했습니다.\n다시 시도하시기 바랍니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void updateResult(boolean flag) {

    }

    @Override
    public void readResult(UserInfo info, boolean flag) {
        if(flag) {
            tv_idChk.setText("* 이미 사용 중인 아이디입니다.");
            tv_idChk.setTextColor(Color.RED);
        } else {
            tv_idChk.setText("* 사용가능한 아이디입니다.");
            tv_idChk.setTextColor(Color.BLUE);
        }
    }

    @Override
    public void readAllResult(ArrayList<UserInfo> infolist, boolean flag) {

    }
    /* -------------------- end: Firebase -------------------- */
}
