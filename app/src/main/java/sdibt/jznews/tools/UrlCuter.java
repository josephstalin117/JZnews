package sdibt.jznews.tools;

/**
 * Created by stalin on 12/15/2014.
 */
public class UrlCuter {


    private static UrlCuter instance;

    private UrlCuter() {
    }

    public static UrlCuter getInstance() {
        if (instance == null) {
            instance = new UrlCuter();
        }
        return instance;
    }

    public String getArticleUrl(String rectno) {
        String url = "http://xb.sdibt.edu.cn/contentpage.aspx?rectno=" + rectno;
        return url;
    }


    public String getTitleListUrl(String title) {
        String url = "http://xb.sdibt.edu.cn/search.aspx?id=0&keyword=" + title;
        return url;
    }

    public String getAuthorList(String author) {
        String url = "http://xb.sdibt.edu.cn/search.aspx?id=1&keyword=" + author;
        return url;
    }

    public String getIssueListUrl(String issue) {
        String url = "http://xb.sdibt.edu.cn/search.aspx?id=2&keyword=" + issue;
        return url;
    }


    public String getHomePage() {
        String url = "http://xb.sdibt.edu.cn/";
        return url;
    }
}
