package com.finer.com.finer.report.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by finereport07 on 2016/8/23.
 */
public class ReportTree {
    public static final int TYPE_FOLDER = 0;
    public static final int TYPE_REPROT = 1;

    int type;
    List<ReportTree> reportTrees = new ArrayList<ReportTree>();
    Map<String,String> mpTags = new HashMap<>();



    public List<ReportTree> getReportTrees() {
        return reportTrees;
    }

    public void addReportTrees(ReportTree reportTree) {
        this.reportTrees.add(reportTree);
    }

    public Map<String, String> getMpTags() {
        return mpTags;
    }

    public void addMpTags(String key,String value) {
        this.mpTags.put(key,value);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
