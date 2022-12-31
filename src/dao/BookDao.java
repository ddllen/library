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
            sb.append(" and b.name like '%"+book.getName()+"%'");
        }
        if(StringUtil.isNotEmpty(book.getAuthor())){
            sb.append(" and b.author like '%"+book.getAuthor()+"%'");
        }
        if(StringUtil.isNotEmpty((book.getPress()))){
            sb.append(" and b.press like '%"+book.getPress()+"%'");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and","where"));
        return pstmt.executeQuery();
    }
}
