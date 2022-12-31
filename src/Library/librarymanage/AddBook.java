package Library.librarymanage;

import dao.BookDao;
import util.DbUtil;
import util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class AddBook extends JFrame {
    DbUtil dbUtil=new DbUtil();
    BookDao bd=new BookDao();
    public AddBook(){
        Container container = this.getContentPane();
        this.setLayout(new FlowLayout());
        setBounds(700,500,550,200);
        JPanel jPanel = new JPanel();jPanel.setLayout(new FlowLayout());
        JPanel jPanel1 = new JPanel();jPanel1.setLayout(new FlowLayout());
        JPanel jPanel2 = new JPanel();jPanel2.setLayout(new FlowLayout());
        JPanel jPanel3 = new JPanel();jPanel3.setLayout(new FlowLayout());
        JLabel jLabel1 = new JLabel("书名:");
        JLabel jLabel2= new JLabel("作者:");
        JLabel jLabel3 = new JLabel("出版社:");
        JTextField jTextField1 = new JTextField(10);
        JTextField jTextField2 = new JTextField(10);
        JTextField jTextField3 = new JTextField(10);
        jPanel1.add(jLabel1);jPanel1.add(jTextField1);
        jPanel2.add(jLabel2);jPanel2.add(jTextField2);
        jPanel3.add(jLabel3);jPanel3.add(jTextField3);
        JButton jb1 = new JButton("确认");
        JButton jb2 = new JButton("返回");
        JButton jb3 = new JButton("清空");
        container.add(jPanel);
        container.add(jPanel1);
        container.add(jPanel2);
        container.add(jPanel3);
        container.add(jb1);
        container.add(jb2);
        container.add(jb3);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = jTextField1.getText();
                String author = jTextField2.getText();
                String press = jTextField3.getText();
                if(StringUtil.isEmpty(name)||StringUtil.isEmpty(author)||StringUtil.isEmpty(press)){
                    JOptionPane.showMessageDialog(null,"内容不能为空");
                }
                else {
                    Book book = new Book(name,author,press);
                    jTextField1.setText(null);
                    jTextField2.setText(null);
                    jTextField3.setText(null);
                    Connection con=null;
                    try {
                        con= dbUtil.getCon();
                        int addNum=bd.add(con,book);
                        if(addNum==1){
                            JOptionPane.showMessageDialog(null,"图书添加成功");
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"图书添加失败");
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }finally {
                        try {
                            dbUtil.closeCon(con);
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
                new LibraryMenu();
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
    }
    public static void main(String[] args) {
        new AddBook();
    }
}
