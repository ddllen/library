package Library.librarymanage;

import dao.BookDao;
import util.DbUtil;
import util.StringUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

public class InsertBook extends JFrame {
    DefaultTableModel dtm;
    JTable jt;
    Connection ct;
    DbUtil dbUtil=new DbUtil();
    BookDao bookDao=new BookDao();
    double num;
    public InsertBook(){
        Container container = this.getContentPane();
        this.setLayout(new BorderLayout());
        JPanel jPanel = new JPanel();
        JPanel jPanel1 = new JPanel();
        JLabel jLabel = new JLabel("ISBN");
        JLabel jLabel1 = new JLabel("书名:");
        JLabel jLabel2= new JLabel("作者:");
        JLabel jLabel3 = new JLabel("出版社:");
        JLabel jLabel4 = new JLabel("说明:选中某行后，将会在某行后面添加图书");
        JTextField jTextField = new JTextField(10);
        JTextField jTextField1 = new JTextField(10);
        JTextField jTextField2 = new JTextField(10);
        JTextField jTextField3 = new JTextField(10);
        jPanel.add(jLabel);jPanel.add(jTextField);
        jPanel.add(jLabel1);jPanel.add(jTextField1);
        jPanel.add(jLabel2);jPanel.add(jTextField2);
        jPanel.add(jLabel3);jPanel.add(jTextField3);
        JButton jb1 = new JButton("确认");
        JButton jb2 = new JButton("返回");
        JButton jb3 = new JButton("清空");
        jPanel1.add(jb1);
        jPanel1.add(jb2);
        jPanel1.add(jb3);
        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel();
        Vector columnNames =new Vector();
        columnNames.add("ISBN");
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
        container.add(jLabel4);
        JPanel jPanel3 = new JPanel();
        jPanel3.setLayout(new BorderLayout());
        jPanel3.add(jLabel4,BorderLayout.NORTH);
        jPanel3.add(jsp,BorderLayout.CENTER);
        container.add(jPanel3,BorderLayout.NORTH);
        container.add(jPanel,BorderLayout.CENTER);
        container.add(jPanel1,BorderLayout.SOUTH);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                int row=jt.getSelectedRow();
                try {
                    ct=dbUtil.getCon();
                    ResultSet rs=BookDao.list(ct,new Book());
                    while(rs.next()){
                        if(rs.getString("name").equals(jt.getValueAt(row,1))){
                            num=rs.getInt("num");
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }finally {
                    try {
                        dbUtil.closeCon(ct);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String ISBN = jTextField.getText();
                String name = jTextField1.getText();
                String author = jTextField2.getText();
                String press = jTextField3.getText();
                if(StringUtil.isEmpty(ISBN)||StringUtil.isEmpty(name)||StringUtil.isEmpty(author)||StringUtil.isEmpty(press)){
                    JOptionPane.showMessageDialog(null,"内容不能为空");
                }
                else {
                    Book book = new Book(name,author,press);
                    book.setISBN(ISBN);
                    book.setNum(num+0.1);
                    jTextField.setText(null);
                    jTextField1.setText(null);
                    jTextField2.setText(null);
                    jTextField3.setText(null);
                    try {
                        ct= dbUtil.getCon();
                        int addNum=bookDao.incert(ct,book);
                        if(addNum==1){
                            JOptionPane.showMessageDialog(null,"图书添加成功");
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"图书添加失败");
                        }
                        fillTable(new Book());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }finally {
                        try {
                            dbUtil.closeCon(ct);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new operateBook();
                dispose();
            }
        });
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextField1.setText(null);
                jTextField2.setText(null);
                jTextField3.setText(null);
            }
        });
        this.setLocation(700,300);
        this.pack();
    }
    private void fillTable(Book book){
        dtm= (DefaultTableModel) jt.getModel();
        dtm.setRowCount(0);
        try{
            ct= dbUtil.getCon();
            ResultSet rs=BookDao.list(ct,book);
            while(rs.next()){
                Vector v=new Vector();
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                v.add(rs.getString(5));
                v.add(rs.getString(6));
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
}
