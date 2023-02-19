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
    public static int delete(Connection con, int id)throws Exception{
        String sql="delete from t_vip where id=?";
        String sql1="SET @auto_id = 0";
        String sql2="UPDATE t_vip SET id = (@auto_id :=@auto_id + 1)";
        String sql3="ALTER TABLE t_vip AUTO_INCREMENT = 1";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setInt(1,id);
        int a=pstmt.executeUpdate();
        con.prepareStatement(sql1).executeUpdate();
        con.prepareStatement(sql2).executeUpdate();
        con.prepareStatement(sql3).executeUpdate();
        return a;
    }

    public static int update(Connection con, Vip vip)throws Exception {
        String sql="update t_vip set password=? where password=?";
        String sql1="SET @auto_id = 0";
        String sql2="UPDATE t_vip SET id = (@auto_id :=@auto_id + 1)";
        String sql3="ALTER TABLE t_vip AUTO_INCREMENT = 1";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,vip.getUserName());
        pstmt.setString(2,vip.getPassword());
        int a=pstmt.executeUpdate();
        con.prepareStatement(sql1).executeUpdate();
        con.prepareStatement(sql2).executeUpdate();
        con.prepareStatement(sql3).executeUpdate();
        return a;
    }
    public static void sort(Connection con,String a)throws Exception{
        String sql="create table t_vip_copy as select * from t_vip order by "+a;
        String sql1="alter table t_vip_copy drop id";
        String sql2="alter table t_vip_copy add id int(11) not null auto_increment primary key first";
        String sql3="drop table t_vip";
        String sql4="alter table t_vip_copy rename to t_vip";
        con.prepareStatement(sql).executeUpdate();
        con.prepareStatement(sql1).executeUpdate();
        con.prepareStatement(sql2).executeUpdate();
        con.prepareStatement(sql3).executeUpdate();
        con.prepareStatement(sql4).executeUpdate();
    }
    public int incert(Connection con, Vip vip)throws Exception{
        double id=0;
        String sql="insert into t_vip values(?,?,?,?)";
        String sql123="select * from t_vip where username=?";
        PreparedStatement pstme1=con.prepareStatement(sql123);
        pstme1.setString(1,vip.getUserName());
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setDouble(1,vip.getId());
        pstmt.setString(2,vip.getUserName());
        pstmt.setString(3,vip.getPassword());
        pstmt.setString(4,vip.getSex());
        int a=pstmt.executeUpdate();
        String sql12="select * from t_vip";
        String sql2="UPDATE t_vip SET id = (@auto_id :=@auto_id + 1)";
        ResultSet rs=con.prepareStatement(sql12).executeQuery();
        while(rs.next()){
            id=rs.getDouble("id");
        }
        String sql1="SET @auto_num = "+id;
        con.prepareStatement(sql1).executeUpdate();
        con.prepareStatement(sql2).executeUpdate();
        return a;
    }
}
