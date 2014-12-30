package sdibt.jznews.test;

import java.util.ArrayList;

import sdibt.jznews.entity.News;
import sdibt.jznews.dao.NewsDaoImpl;

/**
 * Created by stalin on 12/25/2014.
 */
public class NewsDaoImplTest {


    public static void main(String[] args) {
        NewsDaoImpl ndi;
        ArrayList<News> a = new ArrayList<News>();
        ndi = new NewsDaoImpl();
        a = ndi.getNewsByAuthor("宁");
        System.out.print(a.toString());
    }
}
