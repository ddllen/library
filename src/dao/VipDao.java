package dao;

import Library.librarymanage.Book;
import Library.vipmanage.Vip;
import util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VipDao {
    public int add(Connection con, Vip vip)throws Exception{
        String sql="insert into t_vip values(null,?,?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, vip.getUserName());
        pstmt.setString(2, vip.getPassword());
        pstmt.setString(3, vip.getSex());
        return pstmt.executeUpdate();
    }
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
    public static ResultSet list(Connection con, Vip vip)throws Exception{
        StringBuffer sb=new StringBuffer("select * from t_vip");
        if(StringUtil.isNotEmpty(vip.getUserName())){
            sb.append(" and username like '%"+vip.getUserName()+"%'");
        }
        if(StringUtil.isNotEmpty(vip.getSex())){
            sb.append(" and sex like '%"+vip.getSex()+"%'");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and","where"));
        return pstmt.executeQuery();
    }
}
