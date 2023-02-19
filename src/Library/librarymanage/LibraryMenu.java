package Library.librarymanage;

import Library.MainMenu;
import dao.BookDao;
import util.DbUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LibraryMenu extends JFrame {
    public LibraryMenu() {
        Container container = this.getContentPane();
        JLabel jLabel = new JLabel("图书界面");
        container.add(jLabel);
        JComboBox status=new JComboBox();
        status.addItem(null);
        status.addItem("操作图书");
        status.addItem("借书与还书");
        status.addItem("将图书信息写入文件中");
        status.addItem("从文件中导入图书信息");
        JButton jButton = new JButton("确认");
        JButton jButton1=new JButton("返回");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenu();
                dispose();
            }
        });
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = Objects.requireNonNull(status.getSelectedItem()).toString();
                switch (s){
                    case "操作图书":new operateBook();dispose();break;
                    case "借书与还书":new LoginFrm();dispose();break;
                    case "将图书信息写入文件中":
                        try {
                            getTxt();
                            JOptionPane.showMessageDialog(null,"已写入文件中");
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }break;
                    case "从文件中导入图书信息":
                        try {
                            writeToDat("D:\\library\\src\\Book-output.txt");
                            JOptionPane.showMessageDialog(null,"已成功从文件中导入");
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                }
            }
        });
        container.setLayout(new FlowLayout());
        container.add(status);
        container.add(jButton);
        container.add(jButton1);
        setBounds(1000,500,200,200);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public static void getTxt()throws Exception{
        DbUtil dbUtil=new DbUtil();
        Connection ct=null;
        File file=new File("D:\\library\\src\\Book-output.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter fw=new FileWriter(file);
        try{
            ct=dbUtil.getCon();
            ResultSet rs= BookDao.list(ct,new Book());
            fw.write("");
            while(rs.next()){
                fw.write(rs.getString("ISBN")+"\t"+rs.getString("name")+"\t"+rs.getString("author")+"\t"+rs.getString("press")+"\t"+rs.getString("state")+"\t"+rs.getString("who"));
                fw.write("\r\n");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                dbUtil.closeCon(ct);
                fw.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void writeToDat(String path) throws Exception {
        File file = new File(path);
        if(!file.exists()){
            file.createNewFile();
        }
        BookDao bd=new BookDao();
        DbUtil dbUtil=new DbUtil();
        Connection ct=null;
        ct=dbUtil.getCon();
        List<String> list = new ArrayList<>();
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf8");
            BufferedReader bw = new BufferedReader(isr);
            String line = null;
            while((line = bw.readLine()) != null){
                list.add(line);
            }
            bw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        for(int i=0;i<list.size();i++){
            String[] st = list.get(i).split("\t");
            Book book=new Book(st[0],st[1],st[2],st[3],st[4],st[5]);
            bd.add(ct,book);
        }
        ct.close();
    }
}
