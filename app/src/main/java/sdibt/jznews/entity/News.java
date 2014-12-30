package sdibt.jznews.entity;

/**
 * Created by stalin on 12/11/2014.
 */
public class News {

    private String rectno;
    private String title;
    private String content;


    public News() {
        this.rectno = "";
        this.title = "";
        this.content = "";
    }

    public News(String rectno, String title) {
        this.rectno = rectno;
        this.title = title;
        this.content = "";
    }

    public News(String rectno, String title, String content) {
        this.rectno = rectno;
        this.title = title;
        this.content = content;
    }

    public String getRectno() {
        return rectno;
    }

    public void setRectno(String rectno) {
        this.rectno = rectno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "News{" +
                "rectno=" + rectno +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
