package dao;

import Library.vipmanage.Vip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VipDao {
    public Vip login(Connection con, Vip vip)throws Exception{
        Vip resultVip=null;
        String sql="select * from t_vip where username=? and password=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, vip.getUserName());
        pstmt.setString(2,vip.getPassword());
        ResultSet rs=pstmt.executeQuery();
        if(rs.next()){
            resultVip=new Vip();
            resultVip.setId(rs.getInt("id"));
            resultVip.setUserName(rs.getString("username"));
            resultVip.setPassword(rs.getString("password"));
            resultVip.setSex(rs.getString("sex"));
        }
        return resultVip;
    }
}
