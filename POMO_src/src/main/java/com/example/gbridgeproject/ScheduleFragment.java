package com.example.gbridgeproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ScheduleFragment extends Fragment implements View.OnClickListener, MainActivity.OnBackPressedListener {
    MainActivity activity;
    ListView lv_todo;
    Button btn_add, btn_modify, btn_del;
    EditText et_todo;
    ArrayList<String> todo = new ArrayList<>();
    ArrayAdapter<String> adapter;

    public ScheduleFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        /* -------------------- start: MainActivity -------------------- */
        activity = (MainActivity) getActivity();
        activity.img_timer.setColorFilter(Color.DKGRAY);
        activity.img_schedule.setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
        activity.img_rank.setColorFilter(null);
        activity.tv_timer.setTextColor(Color.DKGRAY);
        activity.tv_schedule.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        activity.tv_rank.setTextColor(Color.DKGRAY);
        /* -------------------- end: MainActivity -------------------- */

        lv_todo = (ListView)view.findViewById(R.id.lv_todo);
        et_todo = (EditText)view.findViewById(R.id.et_todo);
        btn_add = (Button)view.findViewById(R.id.btn_add);
        btn_modify = (Button)view.findViewById(R.id.btn_modify);
        btn_del = (Button)view.findViewById(R.id.btn_del);

        todo = getStringArrayPref(getContext(), "todo");
        Log.d("todo", todo.toString());

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_single_choice, todo);
        lv_todo.setAdapter(adapter);

        //btn click
        btn_add.setOnClickListener(this);
        btn_modify.setOnClickListener(this);
        btn_del.setOnClickListener(this);

        return view;
    }
    /* -------------------- start: click button -------------------- */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_add) {
            /* -------------------- start: add button -------------------- */
            if (et_todo.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "일정을 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                todo.add(et_todo.getText().toString());
                //lv_todo.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                et_todo.setText("");
            }
            /* -------------------- end: add button -------------------- */
        } else if(v.getId() == R.id.btn_modify) {
            /* -------------------- start: modify button -------------------- */
            int count, checked ;
            count = adapter.getCount();     // 선택 개수
            if(et_todo.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "일정을 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                if (count > 0) {
                    checked = lv_todo.getCheckedItemPosition(); // 선택된 아이템의 position
                    if (checked > -1 && checked < count) {
                        todo.set(checked, et_todo.getText().toString());    // 아이템 수정
                        lv_todo.clearChoices(); // listview 선택 초기화
                        adapter.notifyDataSetChanged(); // listview 갱신
                        et_todo.setText("");
                    }
                } else {    // 선택한 아이템이 없을 때
                    Toast.makeText(getContext(), "수정할 일정을 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
            /* -------------------- end: modify button -------------------- */
        } else {
            /* -------------------- start: delete button -------------------- */
            int count, checked ;
            count = adapter.getCount();
            if (count > 0) {
                checked = lv_todo.getCheckedItemPosition(); // 현재 선택된 아이템의 position 획득
                if (checked > -1 && checked < count) {
                    todo.remove(checked);   // 아이템 삭제
                    lv_todo.clearChoices(); // listview 선택 초기화
                    adapter.notifyDataSetChanged();    // listview 갱신.
                    et_todo.setText("");
                }
            } else {    // 선택한 아이템이 없을 때
                Toast.makeText(getContext(), "삭제할 일정을 선택해주세요.", Toast.LENGTH_SHORT).show();
            }
            /* -------------------- end: delete button -------------------- */
        }
    }
    /* -------------------- end: click button -------------------- */

    /* -------------------- start: setStringArrayPref() -------------------- */
    // ArrayList를 Json으로 변환하여 SharedPreferences에 String을 저장
    private void setStringArrayPref(Context context, String key, ArrayList<String> values) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.apply();
    }
    /* -------------------- end: setStringArrayPref() -------------------- */

    /* -------------------- start: getStringArrayPref() -------------------- */
    // Json형식의 String을 가져와서 다시 ArrayList로 변환하는 코드
    private ArrayList getStringArrayPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList urls = new ArrayList();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);

                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }
    /* -------------------- end: getStringArrayPref() -------------------- */

    /* -------------------- start: setStringArrayNull() -------------------- */
    public void setStringArrayNull(Context context, String key, ArrayList<String> values) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        editor.clear().commit();
    }
    /* -------------------- end: setStringArrayNull() -------------------- */

    @Override
    public void onPause() {
        setStringArrayPref(getContext(), "todo", todo);
        super.onPause();
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
