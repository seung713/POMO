package com.example.gbridgeproject;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.fragment.app.Fragment;

public class RankFragment extends Fragment implements FireBaseDBCallback, MainActivity.OnBackPressedListener{
    MainActivity activity;
    FireBaseDBManager fbdbm;
    ListView lv_rank;
    LinearLayout p_layout;

    public RankFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rank, container, false);
        /* -------------------- start: MainActivity -------------------- */
        activity = (MainActivity) getActivity();
        activity.img_timer.setColorFilter(Color.DKGRAY);
        activity.img_schedule.setColorFilter(null);
        activity.img_rank.setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
        activity.tv_timer.setTextColor(Color.DKGRAY);
        activity.tv_schedule.setTextColor(Color.DKGRAY);
        activity.tv_rank.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        /* -------------------- end: MainActivity -------------------- */

        lv_rank = (ListView)view.findViewById(R.id.lv_rank);
        p_layout = (LinearLayout)view.findViewById(R.id.p_layout);

        fbdbm = new FireBaseDBManager();
        fbdbm.addFirebaseDBCallback(this);
        fbdbm.readAllUserInfo();

        return view;
    }

    /* -------------------- start: Firebase  -------------------- */
    @Override
    public void writeResult(boolean flag) {

    }

    @Override
    public void updateResult(boolean flag) {

    }

    @Override
    public void readResult(UserInfo info, boolean flag) {

    }

    @Override
    public void readAllResult(ArrayList<UserInfo> infolist, boolean flag) {
        if(flag) {
            Collections.sort(infolist, Collections.<UserInfo>reverseOrder());
            result(infolist);
        } else {

        }
    }

    public void result(ArrayList<UserInfo> info) {
        List<String> rank = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, rank);
        lv_rank.setAdapter(adapter);

        for(int i=0; i<info.size(); i++) {
            if (!info.get(i).Count.equals("0"))
                rank.add((i + 1) + "등. " + info.get(i).ID + " - " + info.get(i).Count + "분\n" + info.get(i).Col + "/" + info.get(i).Dept);
        }
        adapter.notifyDataSetChanged();
        showRank();
    }
    /* -------------------- end: Firebase  -------------------- */

    public void showRank() {
        p_layout.setVisibility(View.GONE);
    }

    public void onBack() {
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
