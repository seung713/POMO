package com.example.gbridgeproject;

public class UserInfo implements  Comparable<UserInfo> {
    String ID;
    String PW;
    String Name;
    String Col;
    String Dept;
    String Count;

    public UserInfo(String userId, String userPw, String userName, String userCol, String userDept, String userCount) {
        this.ID = userId;
        this.PW = userPw;
        this.Name = userName;
        this.Col = userCol;
        this.Dept = userDept;
        this.Count = userCount;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPW() {
        return PW;
    }

    public void setPW(String PW) {
        this.PW = PW;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCol() {
        return Col;
    }

    public void setCol(String col) {
        Col = col;
    }

    public String getDept() {
        return Dept;
    }

    public void setDept(String dept) {
        Dept = dept;
    }


    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    @Override
    public int compareTo(UserInfo o) {
        // 현재 멤버 변수의 값이 파라미터로 넘어온 값보다
        if(Integer.parseInt(this.Count) < Integer.parseInt(o.Count))
            return -1;  // 작으면 음수
        else if(Integer.parseInt(this.Count) == Integer.parseInt(o.Count))
            return 0;   // 같으면 0
        else
            return 1;   // 크면 양수
    }
}
