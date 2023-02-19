package Library.vipmanage;

import Library.MainMenu;
import Library.librarymanage.Book;
import Library.librarymanage.LoginFrm;
import Library.librarymanage.operateBook;
import dao.BookDao;
import dao.VipDao;
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

public class VipMenu extends JFrame {
    public VipMenu(){
        Container container = this.getContentPane();
        JLabel jLabel = new JLabel("会员界面");
        container.add(jLabel);
        JComboBox status=new JComboBox();
        status.addItem(null);
        status.addItem("操作会员信息");
        status.addItem("将会员信息写入文件中");
        status.addItem("从文件中导入会员信息");
        JButton jButton = new JButton("确认");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = Objects.requireNonNull(status.getSelectedItem()).toString();
                switch (s){
                    case "操作会员信息":new operateVip();dispose();break;
                    case "将会员信息写入文件中":try {
                        VipGetTxt();
                        JOptionPane.showMessageDialog(null,"已写入文件中");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    };break;
                    case "从文件中导入会员信息":
                        try {
                            VipWriteToDat("D:\\library\\src\\Book-output.txt");
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        JOptionPane.showMessageDialog(null,"已成功从文件中导入");;break;
                }
            }
        });
        JButton jButton1=new JButton("返回");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenu();
                dispose();
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
    public static void VipGetTxt()throws Exception{
        DbUtil dbUtil=new DbUtil();
        Connection ct=null;
        File file=new File("D:\\library\\src\\Vip-output.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter fw=new FileWriter(file);
        try{
            ct=dbUtil.getCon();
            ResultSet rs= VipDao.list(ct,new Vip());
            fw.write("");
            while(rs.next()){
                fw.write(rs.getString("username")+"\t"+rs.getString("password")+"\t"+rs.getString("sex"));
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
    public static void VipWriteToDat(String path) throws Exception {
        File file = new File(path);
        if(!file.exists()){
            file.createNewFile();
        }
        VipDao vd=new VipDao();
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
            Vip vip=new Vip(st[0],st[2],st[1]);
            vd.add(ct,vip);
        }
        ct.close();
    }
}
