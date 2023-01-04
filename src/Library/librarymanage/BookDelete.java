package Library.librarymanage;

import dao.BookDao;
import util.DbUtil;

import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

public class BookDelete extends JFrame {
    DefaultTableModel dtm = null;
    JTable jt = new JTable();
    Connection ct = null;
    DbUtil dbUtil = new DbUtil();

    public BookDelete() {
        Container container = this.getContentPane();
        container.setLayout(new FlowLayout());
        JLabel jLabel = new JLabel("图书名称");
        JTextField jTextField = new JTextField(16);
        JButton jButton = new JButton("查询");
        JButton jButton1 = new JButton("返回");
        container.add(jLabel);
        container.add(jTextField);
        container.add(jButton);
        container.add(jButton1);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new operateBook();
                dispose();
            }
        });
        DefaultTableModel model = new DefaultTableModel();
        Vector columnNames =new Vector();
        columnNames.add("编号");
        columnNames.add("书名");
        columnNames.add("作者");
        columnNames.add("出版社");
        columnNames.add("状态");
        jt=new JTable(model){
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        JScrollPane jsp=new JScrollPane(jt);
        Vector data=new Vector();
        model.setDataVector(data,columnNames);
        fillTable(new Book());
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String bookName=jTextField.getText();
                Book book=new Book();
                book.setName(bookName);
                fillTable(book);
            }
        });
        container.add(jsp);
        this.setVisible(true);
        this.setBounds(700,500,500, 300);
    }

    private void fillTable(Book book) {
        dtm = (DefaultTableModel) jt.getModel();
        dtm.setRowCount(0);
        try {
            ct = dbUtil.getCon();
            ResultSet rs = BookDao.list(ct, book);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                if(rs.getString(5).equals("1"))
                {
                    v.add("未借出");
                }
                else{
                    v.add("已借出");
                }
                dtm.addRow(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(ct);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new BookDelete();
    }
}
