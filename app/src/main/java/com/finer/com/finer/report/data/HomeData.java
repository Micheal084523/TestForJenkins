package com.finer.com.finer.report.data;

import java.util.List;

/**
 * Created by finereport07 on 2016/8/22.
 */
public class HomeData {

    private String homePageUrl = null;
    private List<String> cookies = null;


    private static HomeData thisInstance = null;
    private HomeData() {
    }
    public static  synchronized  HomeData getInstance()
    {
        if (thisInstance == null)
        {
            thisInstance = new HomeData();
        }
        return thisInstance;
    }

    public synchronized void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    public synchronized  String getHomePageUrl() {
        return homePageUrl;
    }

    public  synchronized void setCookies(List<String> cookies) {
        this.cookies = cookies;
    }

    public  synchronized List<String> getCookies() {
        return cookies;
    }
}
