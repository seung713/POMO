package com.example.gbridgeproject;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView img_timer, img_schedule, img_rank;
    TextView tv_timer, tv_schedule, tv_rank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment, new TimerFragment());
        fragmentTransaction.commit();

        img_timer = (ImageView)findViewById(R.id.img_timer);
        img_schedule = (ImageView)findViewById(R.id.img_schedule);
        img_rank = (ImageView)findViewById(R.id.img_rank);

        tv_timer = (TextView) findViewById(R.id.tv_timer);
        tv_schedule = (TextView)findViewById(R.id.tv_schedule);
        tv_rank = (TextView)findViewById(R.id.tv_rank);

        img_timer.setOnClickListener(this);
        img_schedule.setOnClickListener(this);
        img_rank.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Fragment fr;
        if(v.getId() == R.id.img_timer) {
            fr = new TimerFragment();
            img_timer.setClickable(false);
            img_schedule.setClickable(true);
            img_rank.setClickable(true);
        } else if(v.getId() == R.id.img_schedule) {
            fr = new ScheduleFragment();
            img_timer.setClickable(true);
            img_schedule.setClickable(false);
            img_rank.setClickable(true);
        } else {
            fr = new RankFragment();
            img_timer.setClickable(true);
            img_schedule.setClickable(true);
            img_rank.setClickable(false);
        }
        FragmentManager fm = getSupportFragmentManager();
        //fm.popBackStack();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fr);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private long pressedTime = 0;

    public interface OnBackPressedListener {
        public void onBack();
    }

    private OnBackPressedListener mBackListener;

    public void setOnBackPressedListener(OnBackPressedListener listener) {
        mBackListener = listener;
    }
    @Override
    public void onBackPressed() {
        if (mBackListener != null) {
            mBackListener.onBack();
        } else {
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

    @Override
    protected void onDestroy() {
        Fragment fr = new ScheduleFragment();
        ((ScheduleFragment) fr).setStringArrayNull(getApplicationContext(),"todo", null);
        super.onDestroy();
    }
}

