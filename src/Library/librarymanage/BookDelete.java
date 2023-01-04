package Library.librarymanage;

import dao.BookDao;
import util.DbUtil;
import util.StringUtil;

import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        JLabel jLabel1 = new JLabel("编号:");
        JLabel jLabel2 = new JLabel("图书名称:");
        JLabel jLabel3 = new JLabel("作者:");
        JLabel jLabel4 = new JLabel("出版社:");
        JTextField jTextField1 = new JTextField(16);
        jTextField1.setEditable(false);
        JTextField jTextField2 = new JTextField(16);
        JTextField jTextField3 = new JTextField(16);
        JTextField jTextField4 = new JTextField(16);
        jt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row=jt.getSelectedRow();
                jTextField1.setText((String) jt.getValueAt(row,0));
                jTextField2.setText((String) jt.getValueAt(row,1));
                jTextField3.setText((String) jt.getValueAt(row,2));
                jTextField4.setText((String) jt.getValueAt(row,3));
            }
        });
        JButton jButton2 = new JButton("修改");
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String num=jTextField1.getText();
                String name=jTextField2.getText();
                String author=jTextField3.getText();
                String press=jTextField4.getText();
                if(StringUtil.isEmpty(num)){
                    JOptionPane.showMessageDialog(null,"请选择你要修改的图书");
                }
                Book book = new Book();
                book.setNum(Integer.parseInt(num));
                book.setName(name);
                book.setAuthor(author);
                book.setPress(press);
                Connection con=null;
                try{
                    con=dbUtil.getCon();
                    int modifyNum=BookDao.update(con,book);
                    if(modifyNum==1){
                        JOptionPane.showMessageDialog(null,"修改成功");
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"修改失败");
                    }
                    fillTable(new Book());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        dbUtil.closeCon(con);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        JButton jButton3 = new JButton("删除");
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String num=jTextField1.getText();
                String name=jTextField2.getText();
                if(StringUtil.isEmpty(num)){
                    JOptionPane.showMessageDialog(null,"请选择你要修改的图书");
                }
                Connection con=null;
                try{
                    con=dbUtil.getCon();
                    int modifyNum=BookDao.delete(con,name);
                    if(modifyNum==1){
                        JOptionPane.showMessageDialog(null,"删除成功");
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"删除失败");
                    }
                    fillTable(new Book());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        dbUtil.closeCon(con);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        container.add(jLabel1);container.add(jTextField1);
        container.add(jLabel2);container.add(jTextField2);
        container.add(jLabel3);container.add(jTextField3);
        container.add(jLabel4);container.add(jTextField4);
        container.add(jButton2);
        container.add(jButton3);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setBounds(700,200,500, 600);
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
}
