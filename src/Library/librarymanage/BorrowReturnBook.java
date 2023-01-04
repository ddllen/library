package Library.librarymanage;

import dao.BookDao;
import util.DbUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

public class BorrowReturnBook extends JFrame {
    DefaultTableModel dtm=null;
    DefaultTableModel dtm1=null;
    JTable jt=new JTable();
    JTable jt1=new JTable();
    Connection ct=null;
    DbUtil dbUtil=new DbUtil();
    String Name;

    @Override
    public void setName(String name) {
        Name = name;
    }

    public BorrowReturnBook(){
        this.setLayout(new BorderLayout());
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        JLabel jLabel = new JLabel("未借图书");
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new BorderLayout());
        JLabel jLabel1 = new JLabel("你借的图书");
        DefaultTableModel model = new DefaultTableModel();
        DefaultTableModel model1=new DefaultTableModel();
        Vector columnNames =new Vector();
        columnNames.add("书名");
        columnNames.add("作者");
        columnNames.add("出版社");
        jt=new JTable(model){
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        jt1=new JTable(model1){
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        JScrollPane jsp=new JScrollPane(jt);
        JScrollPane jsp1=new JScrollPane(jt1);
        Vector data=new Vector();
        Vector data1=new Vector();
        model.setDataVector(data,columnNames);
        model1.setDataVector(data1,columnNames);
        fillTable(new Book());
        jPanel.add(jLabel,BorderLayout.NORTH);
        jPanel.add(jsp,BorderLayout.CENTER);
        jPanel1.add(jLabel1,BorderLayout.NORTH);
        jPanel1.add(jsp1,BorderLayout.CENTER);
        JPanel jPanel2 = new JPanel();
        JPanel jPanel3 = new JPanel();
        jPanel3.setLayout(new FlowLayout());
        JButton jButton = new JButton("借书");
        JButton jButton1 = new JButton("还书");
        JButton jButton2 = new JButton("返回");
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrm();
                dispose();
            }
        });
        JTextField jTextField = new JTextField(8);
        JTextField jTextField1 = new JTextField(8);
        JTextField jTextField2 = new JTextField(8);
        JLabel jLabel2 = new JLabel("书名:");
        JLabel jLabel3 = new JLabel("作者:");
        JLabel jLabel4 = new JLabel("出版社:");
        jTextField.setEditable(false);
        jTextField1.setEditable(false);
        jTextField2.setEditable(false);
        jPanel3.add(jLabel2);jPanel3.add(jTextField);
        jPanel3.add(jLabel3);jPanel3.add(jTextField1);
        jPanel3.add(jLabel4);jPanel3.add(jTextField2);
        jPanel3.add(jButton);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Book book=new Book();
                book.setName(jTextField.getText());
                book.setWho(Name);
                book.setState(false);
                try {
                    ct=dbUtil.getCon();
                    ResultSet rs=BookDao.list(ct,new Book());
                    while(rs.next()) {
                        if (rs.getString("name").equals(jTextField.getText())) {
                            if(rs.getBoolean("state"))BookDao.Lend(ct, book);
                        }
                        if(rs.getString("name").equals(jTextField.getText())){
                            if(!rs.getBoolean("state"))JOptionPane.showMessageDialog(null,"该书已被借出");
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
                fillTable(new Book());
            }
        });
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Book book=new Book();
                book.setName(jTextField.getText());
                book.setState(false);
                try {
                    ct=dbUtil.getCon();
                    ResultSet rs=BookDao.list(ct,new Book());
                    while(rs.next()) {
                        if (rs.getString("name").equals(jTextField.getText())) {
                            if(!rs.getBoolean("state"))BookDao.Return(ct, book);
                        }
                        if(rs.getString("name").equals(jTextField.getText())){
                            if(rs.getBoolean("state")){
                            JOptionPane.showMessageDialog(null,"该书未被借出");
                        }}
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
                fillTable(new Book());
            }
        });
        jPanel3.add(jButton1);
        jPanel3.add(jButton2);
        jPanel2.setLayout(new FlowLayout());
        jPanel2.add(jPanel);
        jPanel2.add(jPanel1);
        this.add(jPanel2,BorderLayout.CENTER);
        this.add(jPanel3,BorderLayout.SOUTH);
        jt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(!e.isTemporary()){
                    jt.clearSelection();
                }
            }
        });
        jt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row=jt.getSelectedRow();
                jTextField.setText((String) jt.getValueAt(row,0));
                jTextField1.setText((String) jt.getValueAt(row,1));
                jTextField2.setText((String) jt.getValueAt(row,2));
            }
        });
        jt1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(!e.isTemporary())jt1.clearSelection();
            }
        });
        jt1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row=jt1.getSelectedRow();
                jTextField.setText((String) jt1.getValueAt(row,0));
                jTextField1.setText((String) jt1.getValueAt(row,1));
                jTextField2.setText((String) jt1.getValueAt(row,2));
            }
        });
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocation(600,300);
        this.pack();
    }
    private void fillTable(Book book){
        dtm= (DefaultTableModel) jt.getModel();
        dtm1= (DefaultTableModel) jt1.getModel();
        dtm.setRowCount(0);
        dtm1.setRowCount(0);
        try{
            ct= dbUtil.getCon();
            ResultSet rs= BookDao.list(ct,book);
            while(rs.next()){
                if(rs.getBoolean("state"))
                {
                    Vector v=new Vector();
                    v.add(rs.getString(2));
                    v.add(rs.getString(3));
                    v.add(rs.getString(4));
                    dtm.addRow(v);
                }
                else{
                    if(rs.getString("who").equals(Name)) {
                        Vector v = new Vector();
                        v.add(rs.getString("name"));
                        v.add(rs.getString("author"));
                        v.add(rs.getString("press"));
                        dtm1.addRow(v);
                    }
                }
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
        new BorrowReturnBook();
    }
}
