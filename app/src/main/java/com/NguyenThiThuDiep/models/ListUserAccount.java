package com.NguyenThiThuDiep.models;

import java.util.ArrayList;

//vì chưa có dữ liệu nên giả lập
public class ListUserAccount {
    public static ArrayList<UserAccount> listUserAccounts()
    {
        ArrayList<UserAccount>list=new ArrayList<>();
        list.add(new UserAccount("admin","123","admin","Thu Diep",true));
        list.add(new UserAccount("employee","123","reporter","Sắp nổi tiếng",true));
        list.add(new UserAccount("employee1","123","technical;","Em bes",true));
        list.add(new UserAccount("employee2","123","tester","Cục dàng",true));
        return list;
    }
    public static UserAccount login(String username, String password)
    {
        UserAccount uc=null;
        //get sample data
        ArrayList<UserAccount> list=listUserAccounts();
        //check for login
        for (UserAccount u:list)
        {
            if(u.getUsername().equals(username)&&u.getPassword().equals(password))
            {
                uc=u;
                break;
            }
        }
        return uc;
    }
}
