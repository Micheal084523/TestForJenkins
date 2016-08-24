package com.finer.report.utils;

import android.util.Log;
import com.finer.com.finer.report.data.ReportTree;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by finereport07 on 2016/8/23.
 */
public class JsonTreeUtils {
    private static long  reportUID = 0;
    public static ReportTree CreateReportTree(String jsonString)
    {
        ReportTree tree = new ReportTree();
        try
        {
            //String utf8 = new String(result.getBytes("GBK"), "UTF-8");

            Log.d(TAGUtils.TAG_LOGD,"---> 0");
            JSONArray jsonArray = new JSONArray(jsonString);
            int length = jsonArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);

                Boolean hasChildren = false;
                if(jsonObject.has(TAGUtils.TAG_HASCHILDREN)) {
                    hasChildren = jsonObject.getBoolean(TAGUtils.TAG_HASCHILDREN);
                }
                if (hasChildren)
                {
                    String arrayString = jsonObject.getJSONArray(TAGUtils.TAG_CHILDNODES).toString();
                    ReportTree reportTree = JsonTreeUtils.CreateReportTree(arrayString);
                   // reportTree.addMpTags(TAGUtils.TAG_TEXT,jsonObject.getString(TAGUtils.TAG_TEXT));

                    reportTree.setType(ReportTree.TYPE_FOLDER);
                    //
                    Iterator<String> it = jsonObject.keys();
                    while (it.hasNext())
                    {
                        String keyStr = it.next();
                        reportTree.addMpTags(keyStr,jsonObject.getString(keyStr));
                    }
                    tree.addReportTrees(reportTree);

                }
                else
                {
                    ReportTree reportTree = new ReportTree();
                    reportTree.setType(ReportTree.TYPE_REPROT);
                    Iterator<String> it = jsonObject.keys();
                    while (it.hasNext())
                    {
                        String keyStr = it.next();
                        reportTree.addMpTags(keyStr,jsonObject.getString(keyStr));
                    }
                    tree.addReportTrees(reportTree);
                    //最低层图表元素
                }
            }
/*

            Log.d(TAGUtils.TAG_LOGD,"---> 1");
            JSONArray childJsonArray = jsonObject.getJSONArray("ChildNodes");
            Log.d(TAGUtils.TAG_LOGD,"---> 2");
            JSONObject childJsonObject = childJsonArray.optJSONObject(0);
            Log.d(TAGUtils.TAG_LOGD,"---> 3");
            JSONArray gchildJsonArray = childJsonObject.getJSONArray("ChildNodes");
            Log.d(TAGUtils.TAG_LOGD,"---> 4");
            JSONObject ggchildJsonObject = gchildJsonArray.optJSONObject(0);
            Log.d(TAGUtils.TAG_LOGD,"---> 5");
            String sss = ggchildJsonObject.getString("text");
            Log.d(TAGUtils.TAG_LOGD,"---> 6");

            Log.d(TAGUtils.TAG_LOGD, "--->text:"+ sss);
*/
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return tree;
    }
}
