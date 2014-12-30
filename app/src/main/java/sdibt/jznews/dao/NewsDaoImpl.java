package sdibt.jznews.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import sdibt.jznews.entity.News;
import sdibt.jznews.tools.Spider;
import sdibt.jznews.tools.UrlCuter;

/**
 * Created by stalin on 12/11/2014.
 */
public class NewsDaoImpl implements NewsDao {
    @Override
    public ArrayList<News> getAllNewsRectnoByIssue(String issue) {
        return getNewsList(issue, 'i');
    }

    @Override
    public News getNewsByRestno(String restno) {
        Spider sp = new Spider();
        String c = "";
        try {
            c = sp.getHtmlContent((UrlCuter.getInstance().getArticleUrl(restno)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = sp.getTitle(c);
        String content = sp.getContent(c);
        News nw = new News(restno, title, content);
        return nw;
    }

    @Override
    public ArrayList<News> getNewsByAuthor(String author) {
        return getNewsList(author, 'a');
    }

    @Override
    public ArrayList<News> getNewsByTitle(String title) {
        return getNewsList(title, 't');
    }

    @Override
    public ArrayList<News> getLatestNews() {
        Spider sp = new Spider();
        String c = "";
        try {
            c = sp.getHtmlContent((UrlCuter.getInstance().getHomePage()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String issue = sp.getLatest(c);
        return getAllNewsRectnoByIssue(issue);
    }

    private static ArrayList<News> getNewsList(String s, char method) {
        ArrayList<News> nwList = new ArrayList<News>();
        Spider sp = new Spider();
        String c = "";
        try {

            switch (method) {
                case 'i':
                    c = sp.getHtmlContent((UrlCuter.getInstance().getIssueListUrl(s)));
                    break;
                case 't':
                    c = sp.getHtmlContent((UrlCuter.getInstance().getTitleListUrl(s)));
                    break;
                case 'a':
                    c = sp.getHtmlContent((UrlCuter.getInstance().getAuthorList(s)));
                    break;
                default:
                    throw new Exception();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("this method is not exist");
            e.printStackTrace();
        }
        String[] l = sp.getListRectno(c);
        String[] t = sp.getListTitle(c);
        if (l != null || t != null) {
            TreeMap tm;
            tm = sp.getListMap(l, t);
            //使用Iterator遍历HashMap
            Iterator it = tm.keySet().iterator();
            while (it.hasNext()) {
                String rectno = (String) it.next();
                String title = (String) tm.get(rectno);
                nwList.add(new News(rectno, title));
            }
            return nwList;
        } else {
            return null;
        }

    }
}
