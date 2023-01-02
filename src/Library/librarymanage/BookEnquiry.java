package Library.librarymanage;

import dao.BookDao;
import util.DbUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.Vector;

public class BookEnquiry extends JFrame {
    DefaultTableModel dtm=null;
    JTable jt=new JTable();
    Connection ct=null;
    DbUtil dbUtil=new DbUtil();

    public BookEnquiry(){
        JLabel jLabel = new JLabel("书名");
        JLabel jLabel1 = new JLabel("作者");
        JTextField jTextField = new JTextField(8);
        JTextField jTextField1 = new JTextField(8);
        JButton jButton = new JButton("返回");
        JButton jButton1=new JButton("查询");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String bookName=jTextField.getName();
                String author=jTextField1.getText();
                Book book=new Book(bookName,author);
                fillTable(book);

        }
        });
        this.add(jLabel);
        this.add(jTextField);
        this.add(jLabel1);
        this.add(jTextField1);
        this.add(jButton1);
        this.add(jButton);
        DefaultTableModel model = new DefaultTableModel();
        Vector columnNames =new Vector();
        columnNames.add("编号");
        columnNames.add("书名");
        columnNames.add("作者");
        columnNames.add("出版社");
        columnNames.add("状态");
        jt=new JTable(model);
        JScrollPane jsp=new JScrollPane(jt);
        Vector data=new Vector();
        model.setDataVector(data,columnNames);
        fillTable(new Book());
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LibraryMenu();
                dispose();
            }
        });
        this.add(jsp);
        this.setLayout(new FlowLayout());
        this.setBounds(700,500,500, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    private void fillTable(Book book){
        dtm= (DefaultTableModel) jt.getModel();
        dtm.setRowCount(0);
        try{
            ct= dbUtil.getCon();
            ResultSet rs=BookDao.list(ct,book);
            while(rs.next()){
                Vector v=new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                v.add(rs.getString(5));
                dtm.addRow(v);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                dbUtil.closeCon(ct);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public static void main(String[] args) {
        new BookEnquiry();
    }
}

