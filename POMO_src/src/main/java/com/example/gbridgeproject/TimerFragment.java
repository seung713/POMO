package com.example.gbridgeproject;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class TimerFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, FireBaseDBCallback, MainActivity.OnBackPressedListener{
    MainActivity activity;
    FireBaseDBManager fbdbm;

    int cnt=0;

    Button btn_start;
    Button btn_pause;
    ImageView img_pomo;
    TextView tv_timer;
    SeekBar sb_seek;
    TextView tv_seek;

    long STUDY_TIME_IN_MILLIS = 25000;   // 공부 시간(기본 25분)
    long REST_TIME_IN_MILLIS = 5000;    // 쉬는 시간(5분 고정)
    long mTimerLeftInMillis;

    CountDownTimer studyCountDownTimer;
    CountDownTimer restCountDownTimer;

    public TimerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        /* -------------------- start: MainActivity -------------------- */
        activity = (MainActivity) getActivity();
        activity.img_timer.setColorFilter(null);
        activity.img_schedule.setColorFilter(null);
        activity.img_rank.setColorFilter(null);
        activity.tv_timer.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        activity.tv_schedule.setTextColor(Color.DKGRAY);
        activity.tv_rank.setTextColor(Color.DKGRAY);
        /* -------------------- end: MainActivity -------------------- */

        img_pomo = (ImageView)view.findViewById(R.id.img_pomo);
        tv_timer = (TextView)view.findViewById(R.id.tv_timer);
        btn_start = (Button)view.findViewById(R.id.btn_start);
        btn_pause = (Button)view.findViewById(R.id.btn_pause);
        sb_seek = (SeekBar)view.findViewById(R.id.sb_seek);
        tv_seek = (TextView)view.findViewById(R.id.tv_seek);

        // btn click
        btn_start.setOnClickListener(this);
        btn_pause.setOnClickListener(this);

        sb_seek.setOnSeekBarChangeListener(this);

        btn_pause.setVisibility(View.INVISIBLE);
        updateTimerText();

        fbdbm = new FireBaseDBManager();
        fbdbm.addFirebaseDBCallback(this);

        return view;
    }
    /* -------------------- start: SeekBar -------------------- */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tv_seek.setText(progress + "분 동안 타이머가 실행됩니다.\n * 쉬는 시간은 5분입니다.");
        STUDY_TIME_IN_MILLIS = progress * 1000; // 초 -> 분 *60
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
    /* -------------------- end: SeekBar -------------------- */

    /* -------------------- start: button click -------------------- */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start)
            startStudyTimer();
        else if (v.getId() == R.id.btn_pause)
            pauseTimer();
    }
    /* -------------------- end: SeekBar -------------------- */

    /* -------------------- start: startStudyTiemr()  -------------------- */
    private void startStudyTimer() { //25분 타이머
        mTimerLeftInMillis = STUDY_TIME_IN_MILLIS;

        cnt += STUDY_TIME_IN_MILLIS;

        studyCountDownTimer = new CountDownTimer(mTimerLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimerLeftInMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                btn_start.setVisibility(View.INVISIBLE);
                mTimerLeftInMillis = REST_TIME_IN_MILLIS;
                Toast.makeText(getContext(), "쉬는 시간입니다.", Toast.LENGTH_SHORT).show();

                startRestTimer();
            }
        }.start();
        btn_start.setVisibility(View.INVISIBLE);
        btn_pause.setVisibility(View.INVISIBLE);
        img_pomo.setVisibility(View.INVISIBLE);
        sb_seek.setVisibility(View.INVISIBLE);
        tv_seek.setVisibility(View.INVISIBLE);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    /* -------------------- end: startStudyTiemr()  -------------------- */

    /* -------------------- start: startRestTiemr()  -------------------- */
    private void startRestTimer() { //5분 타이머
        restCountDownTimer = new CountDownTimer(mTimerLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimerLeftInMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                btn_start.setVisibility(View.INVISIBLE);
                startStudyTimer();
            }
        }.start();
        btn_pause.setVisibility(View.VISIBLE);
        sb_seek.setVisibility(View.VISIBLE);
        tv_seek.setVisibility(View.VISIBLE);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        activity.img_schedule.setClickable(false);
        activity.img_rank.setClickable(false);
    }
    /* -------------------- end: startStudyTiemr()  -------------------- */

    /* -------------------- start: pauseTimer()  -------------------- */
    private void pauseTimer() {
        restCountDownTimer.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(getView().getContext()); //this -> getView().getContext

        builder.setTitle("POMO").setMessage("종료하시겠습니까?");

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String userId = getActivity().getIntent().getExtras().getString("userId");

                fbdbm.readUserInfo(userId);

                btn_start.setVisibility(View.VISIBLE);
                img_pomo.setVisibility(View.VISIBLE);
                btn_pause.setVisibility(View.INVISIBLE);
                activity.img_schedule.setClickable(true);
                activity.img_rank.setClickable(true);
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                startRestTimer();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void updateTimerText() {
        int minutes = (int) (mTimerLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimerLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        tv_timer.setText(timeLeftFormatted);
    }
    /* -------------------- end: pauseTimer()  -------------------- */

    /* -------------------- start: Firebase  -------------------- */
    @Override
    public void writeResult(boolean flag) {

    }

    @Override
    public void updateResult(boolean flag) {
        if(flag) {
            Log.i("TIMER", "count update 성공");
            Toast.makeText(getContext(),cnt/1000 +"분 공부하셨습니다! 수고하셨습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("TIMER", "count update 실패");
        }
    }

    @Override
    public void readResult(UserInfo info, boolean flag) {
        if(flag) {
            fbdbm.updateUserInfo(info.ID, String.valueOf(Integer.parseInt(info.Count)+cnt/1000));   // count update
        } else {

        }
    }

    @Override
    public void readAllResult(ArrayList<UserInfo> infolist, boolean flag) {

    }
    /* -------------------- end: Firebase  -------------------- */

    @Override
    public void onBack() {
        if(img_pomo.getVisibility() == View.INVISIBLE) {
            Toast.makeText(getContext(),"타이머 실행 중에는 뒤로 갈 수 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        activity = (MainActivity) getActivity();
        activity.setOnBackPressedListener(null);
        activity.onBackPressed();
    }

    // Fragment 호출 시 반드시 호출되는 오버라이드 메소드
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).setOnBackPressedListener(this);
    }
}