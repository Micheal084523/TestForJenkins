package com.finer.com.finer.report.data;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by finereport07 on 2016/8/23.
 */
public class ReportSet {
    private long  reportUID;
    Map<String,String> mpReport = new HashMap<String,String>();

    public Map<String, String> getMpReport() {
        return mpReport;
    }

    public void addMpReport(String key,String value) {
        this.mpReport.put(key,value);
    }

    public long getReportUID() {
        return reportUID;
    }

    public void setReportUID(long reportUID) {
        this.reportUID = reportUID;
    }
}
