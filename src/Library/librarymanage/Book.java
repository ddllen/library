package Library.librarymanage;
public class Book {
    private String name;
    private String author;
    private double num;
    private String press;
    private String state="未借出";
    private String who="无人借走";
    private String ISBN;

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public Book(String name, String author, String press) {
        this.name = name;
        this.author = author;
        this.press = press;
    }

    public Book(String ISBN, String name, String author, String press, String state, String who) {
        this.name = name;
        this.author = author;
        this.press = press;
        this.state = state;
        this.who = who;
        this.ISBN = ISBN;
    }

    public Book() {

    }

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

