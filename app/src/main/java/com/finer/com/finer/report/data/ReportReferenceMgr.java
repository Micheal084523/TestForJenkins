package com.finer.com.finer.report.data;

import java.util.List;

/**
 * Created by finereport07 on 2016/8/23.
 */
public class ReportReferenceMgr {
    private List<ReportTree> lstReportTree;
    private static ReportReferenceMgr thisInstance = null;
    private ReportReferenceMgr()
    {}
    public static ReportReferenceMgr getThisInstance()
    {
        if (thisInstance == null)
        {
            thisInstance = new ReportReferenceMgr();
        }
        return  thisInstance;
    }

    public void setLstReportTree(List<ReportTree> lstReportTree) {
        this.lstReportTree = lstReportTree;

    }

    public List<ReportTree> getLstReportTree() {
        return lstReportTree;
    }
}
