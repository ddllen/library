package dao;

import Library.librarymanage.Book;
import util.StringUtil;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    public int add(Connection con, Book book)throws Exception{
        String sql="insert into t_book values(null,?,?,?,?,?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, book.getISBN());
        pstmt.setString(2, book.getName());
        pstmt.setString(3, book.getAuthor());
        pstmt.setString(4, book.getPress());
        pstmt.setString(5, book.getState());
        pstmt.setString(6, book.getWho());
        String sql1="SET @auto_num = 0";
        String sql2="UPDATE t_book SET num = (@auto_num :=@auto_num + 1)";
        con.prepareStatement(sql1).executeUpdate();
        con.prepareStatement(sql2).executeUpdate();
        return pstmt.executeUpdate();
    }
    public int incert(Connection con, Book book)throws Exception{
        double num=0;
        String sql="insert into t_book values(?,?,?,?,?,?,?)";
        String sql123="select * from t_book where ISBN=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setDouble(1,book.getNum());
        pstmt.setString(2, book.getISBN());
        pstmt.setString(3, book.getName());
        pstmt.setString(4, book.getAuthor());
        pstmt.setString(5,book.getPress());
        pstmt.setString(6, book.getState());
        pstmt.setString(7,book.getWho());
        int a=pstmt.executeUpdate();
        String sql12="select * from t_book";
        String sql2="UPDATE t_book SET num = (@auto_num :=@auto_num + 1)";
        ResultSet rs=con.prepareStatement(sql12).executeQuery();
        while(rs.next()){
            num=rs.getDouble("num");
        }
        String sql1="SET @auto_num = "+num;
        con.prepareStatement(sql1).executeUpdate();
        con.prepareStatement(sql2).executeUpdate();
        return a;
    }
    public static ResultSet list(Connection con, Book book)throws Exception{
        StringBuffer sb=new StringBuffer("select * from t_book");
        if(StringUtil.isNotEmpty(book.getName())){
            sb.append(" and name like '%"+book.getName()+"%'");
        }
        if(StringUtil.isNotEmpty(book.getAuthor())){
            sb.append(" and author like '%"+book.getAuthor()+"%'");
        }
        sb.append(" order by num asc");
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and","where"));
        return pstmt.executeQuery();
    }
    public static int delete(Connection con, String name)throws Exception{
        String sql="delete from t_book where name=?";
        String sql1="SET @auto_num = 0";
        String sql2="UPDATE t_book SET num = (@auto_num :=@auto_num + 1)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,name);
        int a=pstmt.executeUpdate();
        con.prepareStatement(sql1).executeUpdate();
        con.prepareStatement(sql2).executeUpdate();
        return a;
    }

    public static int update(Connection con, Book book)throws Exception {
        String sql="update t_book set name=?,author=?,press=? where ISBN=?";
        String sql1="SET @auto_num = 0";
        String sql2="UPDATE t_book SET num = (@auto_num :=@auto_num + 1)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,book.getName());
        pstmt.setString(2,book.getAuthor());
        pstmt.setString(3,book.getPress());
        pstmt.setString(4,book.getISBN());
        int a=pstmt.executeUpdate();
        con.prepareStatement(sql1).executeUpdate();
        con.prepareStatement(sql2).executeUpdate();
        return a;
    }
    public static int Lend(Connection con, Book book)throws Exception{
        String sql="update t_book set who=?,state=? where name=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, book.getWho());
        pstmt.setString(2, book.getState());
        pstmt.setString(3, book.getName());
        return pstmt.executeUpdate();
    }
    public static int Return(Connection con, Book book)throws Exception{
        String sql="update t_book set who=?,state=? where name=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, "无人借走");
        pstmt.setString(2, "未借出");
        pstmt.setString(3, book.getName());
        return pstmt.executeUpdate();
    }
    public static void sort(Connection con,String a)throws Exception{
        String sql="create table t_book_copy as select * from t_book order by "+a;
        String sql1="alter table t_book_copy drop num";
        String sql2="alter table t_book_copy add num double(11) not null auto_increment primary key first";
        String sql3="drop table t_book";
        String sql4="alter table t_book_copy rename to t_book";
        con.prepareStatement(sql).executeUpdate();
        con.prepareStatement(sql1).executeUpdate();
        con.prepareStatement(sql2).executeUpdate();
        con.prepareStatement(sql3).executeUpdate();
        con.prepareStatement(sql4).executeUpdate();
    }
}
