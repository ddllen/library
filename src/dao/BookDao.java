package dao;

import Library.librarymanage.Book;
import util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookDao {
    public int add(Connection con, Book book)throws Exception{
        String sql="insert into t_book values(null,?,?,?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, book.getName());
        pstmt.setString(2, book.getAuthor());
        pstmt.setString(3, book.getPress());
        pstmt.setBoolean(4,true);
        return pstmt.executeUpdate();
    }
    public static ResultSet list(Connection con, Book book)throws Exception{
        StringBuffer sb=new StringBuffer("select * from t_book");
        if(StringUtil.isNotEmpty(book.getName())){
            sb.append(" and name like '%"+book.getName()+"%'");
        }
        if(StringUtil.isNotEmpty(book.getAuthor())){
            sb.append(" and author like '%"+book.getAuthor()+"%'");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and","where"));
        return pstmt.executeQuery();
    }
    public static int delete(Connection con, String name)throws Exception{
        String sql="delete from t_book where name=?";
        String sql1="SET @auto_id = 0";
        String sql2="UPDATE t_vip SET id = (@auto_id :=@auto_id + 1)";
        String sql3="ALTER TABLE t_vip AUTO_INCREMENT = 1";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,name);
        int a=pstmt.executeUpdate();
        con.prepareStatement(sql1).executeUpdate();
        con.prepareStatement(sql2).executeUpdate();
        con.prepareStatement(sql3).executeUpdate();
        return a;
    }

    public static int update(Connection con, Book book)throws Exception {
        String sql="update t_book set name=?,author=?,press=? where num=?";
        String sql1="SET @auto_id = 0";
        String sql2="UPDATE t_vip SET id = (@auto_id :=@auto_id + 1)";
        String sql3="ALTER TABLE t_vip AUTO_INCREMENT = 1";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,book.getName());
        pstmt.setString(2,book.getAuthor());
        pstmt.setString(3, book.getPress());
        pstmt.setInt(4,book.getNum());
        int a=pstmt.executeUpdate();
        con.prepareStatement(sql1).executeUpdate();
        con.prepareStatement(sql2).executeUpdate();
        con.prepareStatement(sql3).executeUpdate();
        return a;
    }
    public static int lend(Connection con, Book book)throws Exception{
        String sql="update t_book set who=?,state=?where name=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, book.getWho());
        pstmt.setBoolean(2, book.getState());
        pstmt.setString(3, book.getName());
        return pstmt.executeUpdate();
    }
    public static int borrow(Connection con, Book book)throws Exception{
        String sql="update t_book set who=?,state=?where name=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, book.getWho());
        pstmt.setBoolean(2, book.getState());
        pstmt.setString(3, book.getName());
        return pstmt.executeUpdate();
    }
}
