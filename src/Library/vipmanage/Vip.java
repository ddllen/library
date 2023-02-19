package Library.vipmanage;

public class Vip {
    private double id;
    private String userName;
    private String sex;
    private String password;

    public Vip(String userName, String sex) {
        this.userName = userName;
        this.sex=sex;
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

    public double getId() {
        return id;
    }

    public void setId(double id) {
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
