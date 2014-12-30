package sdibt.jznews.dao;

import java.util.ArrayList;

import sdibt.jznews.entity.News;

/**
 * Created by stalin on 12/11/2014.
 */
public interface NewsDao {

    public ArrayList<News> getLatestNews();
    public ArrayList<News> getAllNewsRectnoByIssue(String issue);
    public News getNewsByRestno(String restno);
    public ArrayList<News> getNewsByAuthor(String author);
    public ArrayList<News> getNewsByTitle(String title);

}
