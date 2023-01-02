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
    Vector rowData,columnNames;
    JTable jt=null;
    JScrollPane jsp=null;
    Connection ct=null;
    BookDao db=null;
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
        fillTable(new Book());
        jt = new JTable(rowData,columnNames){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jsp = new JScrollPane(jt);
        this.add(jsp);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LibraryMenu();
                dispose();
            }
        });
        this.setLayout(new FlowLayout());
        this.setBounds(700,500,500, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    private void fillTable(Book book){
        db=new BookDao();
        columnNames=new Vector();
        columnNames.add("num");
        columnNames.add("name");
        columnNames.add("author");
        columnNames.add("press");
        columnNames.add("state");
        rowData = new Vector();
        try {
            Connection ct=dbUtil.getCon();
            ResultSet rs=db.list(ct,book);
            while(rs.next()){
                Vector hang=new Vector();
                hang.add(rs.getString(1));
                hang.add(rs.getString(2));
                hang.add(rs.getString(3));
                hang.add(rs.getString(4));
                if(Objects.equals(rs.getString(5), "1")){
                    hang.add("未借出");
                }
                else{
                    hang.add("已借出");
                }
                rowData.add(hang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                dbUtil.closeCon(ct);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new BookEnquiry();
    }
}

