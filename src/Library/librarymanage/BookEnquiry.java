package Library.librarymanage;

import dao.BookDao;
import util.DbUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

public class BookEnquiry extends JFrame {
    JTable bookTable=new JTable();
    DbUtil dbUtil = new DbUtil();

    public BookEnquiry() {
        Container container = this.getContentPane();
        setBounds(1000,500,700,500);
        container.add(bookTable);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void filltable(Book book) {
        DefaultTableModel dtm = (DefaultTableModel) bookTable.getModel();
        dtm.setRowCount(0);
        Connection con = null;
        try {
            con = dbUtil.getCon();
            ResultSet rs = BookDao.list(con, book);
            while(rs.next()){
                Vector v=new Vector();
                v.add(rs.getString("num"));
                v.add(rs.getString("name"));
                v.add(rs.getString("author"));
                v.add(rs.getString("press"));
                v.add(rs.getString(""));
                dtm.addRow(v);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                dbUtil.closeCon(con);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new BookEnquiry();
    }
}
