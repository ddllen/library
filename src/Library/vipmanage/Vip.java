package Library.vipmanage;

public class Vip {
    private int id;
    private String userName;
    private String sex;
    private String password;

    public Vip(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Vip(String userName, String sex, String password) {
        this.userName = userName;
        this.sex = sex;
        this.password = password;
    }

    public Vip(int id, String userName, String sex, String password) {
        this.id = id;
        this.userName = userName;
        this.sex = sex;
        this.password = password;
    }

    public Vip() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
