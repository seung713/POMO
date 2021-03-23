package com.example.gbridgeproject;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Map;
import androidx.annotation.NonNull;

public class FireBaseDBManager {

    FireBaseDBCallback callback_instance = null;
    private DatabaseReference mDatabase;

    public FireBaseDBManager(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void addFirebaseDBCallback(FireBaseDBCallback callback_instance){
        this.callback_instance = callback_instance;
    }

    public void insertUserInfo(UserInfo info){
        mDatabase.child("users").child(info.ID).setValue(info).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if(callback_instance != null)
                    callback_instance.writeResult(true);
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(callback_instance != null)
                    callback_instance.writeResult(false);
            }
        });
    }

    public void updateUserInfo(String userId, String count){
        mDatabase.child("users").child(userId).child("count").setValue(count).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if(callback_instance != null)
                    callback_instance.updateResult(true);
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(callback_instance != null)
                    callback_instance.updateResult(false);
            }
        });
    }

    public void readUserInfo(String id){
        DatabaseReference ref = mDatabase.child("users").child(id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null) {
                    UserInfo user = makeUser((Map<String, Object>) dataSnapshot.getValue());
                    if (callback_instance != null) {
                        callback_instance.readResult(user, true);
                    }
                }
                else{
                    if (callback_instance != null) {
                        callback_instance.readResult(null, false);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(callback_instance != null){
                    callback_instance.readResult(null, false);
                }
            }
        });
    }

    public void readAllUserInfo(){
        DatabaseReference ref = mDatabase.child("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null) {
                    ArrayList<UserInfo> list = makeUserList((Map<String, Object>) dataSnapshot.getValue());
                    if (callback_instance != null) {
                        callback_instance.readAllResult(list, true);
                    }
                }
                else{
                    if (callback_instance != null) {
                        callback_instance.readAllResult(null, false);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(callback_instance != null){
                    callback_instance.readAllResult(null, false);
                }
            }
        });
    }

    private UserInfo makeUser(Map<String,Object> users){

        String userId = (String)users.get("id") == null ? "":(String)users.get("id");
        String userPw = (String)users.get("pw") == null ? "":(String)users.get("pw");
        String userName = (String)users.get("name") == null ? "":(String)users.get("name");
        String userCol = (String)users.get("col") == null ? "":(String)users.get("col");
        String userDept = (String)users.get("dept") == null ? "":(String)users.get("dept");
        String userCount = (String)users.get("count") == null ? "":(String)users.get("count");

        UserInfo info = new UserInfo(userId, userPw, userName, userCol, userDept, userCount);
        return info;
    }

    private ArrayList<UserInfo> makeUserList(Map<String,Object> users) {
        ArrayList<UserInfo> user_list = new ArrayList<>();

        for (Map.Entry<String, Object> entry : users.entrySet()){
            Map singleUser = (Map) entry.getValue();
            String userId = (String)singleUser.get("id") == null ? "":(String)singleUser.get("id");
            String userPw = (String)singleUser.get("pw") == null ? "":(String)singleUser.get("pw");
            String userName = (String)singleUser.get("name") == null ? "":(String)singleUser.get("name");
            String userCol = (String)singleUser.get("col") == null ? "":(String)singleUser.get("col");
            String userDept = (String)singleUser.get("dept") == null ? "":(String)singleUser.get("dept");
            String userCount = (String)singleUser.get("count") == null ? "":(String)singleUser.get("count");

            UserInfo info = new UserInfo(userId, userPw, userName, userCol, userDept, userCount);
            user_list.add(info);
        }
        return user_list;
    }

}
