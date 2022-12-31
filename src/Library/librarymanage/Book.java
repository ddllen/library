package Library.librarymanage;
public class Book {
    private String name; //书名
    private String author; //作者
    public String num; //编号

    private String Press; //出版社
    private boolean state=true; //状态  true-未借出  false-已借出

    //通过构造函数给定书的属性

    public Book(String name, String author, String num, String Press) {
        this.name = name;
        this.author = author;
        this.num = num;
        this.Press = Press;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPress() {
        return Press;
    }

    public void setPress(String press) {
        Press = press;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}

