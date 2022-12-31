package Library.vipmanage;

import Library.librarymanage.Book;

import javax.swing.*;
import java.awt.*;

public class AddVip extends JFrame{
    Book[] books = new Book[50];
    int i=0;
    public AddVip(){
            Container container = this.getContentPane();
            this.setLayout(new FlowLayout());
            setBounds(1000,500,700,200);
            JPanel jPanel = new JPanel();jPanel.setLayout(new FlowLayout());
            JPanel jPanel1 = new JPanel();jPanel1.setLayout(new FlowLayout());
            JPanel jPanel2 = new JPanel();jPanel2.setLayout(new FlowLayout());
            JPanel jPanel3 = new JPanel();jPanel3.setLayout(new FlowLayout());
            JLabel jLabel = new JLabel("编号:");
            JLabel jLabel1 = new JLabel("书名:");
            JLabel jLabel2= new JLabel("作者:");
            JLabel jLabel3 = new JLabel("出版社:");
            JTextField jTextField = new JTextField(10);
            JTextField jTextField1 = new JTextField(10);
            JTextField jTextField2 = new JTextField(10);
            JTextField jTextField3 = new JTextField(10);
            jPanel.add(jLabel);jPanel.add(jTextField);
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
    }
}
