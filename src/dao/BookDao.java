package dao;

import Library.librarymanage.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
}
