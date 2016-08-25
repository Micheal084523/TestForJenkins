package com.finer.finereport07.getbyjson;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.finer.com.finer.report.data.HomeData;
import com.finer.com.finer.report.data.ReportReferenceMgr;
import com.finer.com.finer.report.data.ReportTree;
import com.finer.report.utils.HttpCookieUtils;
import com.finer.report.utils.JsonTreeUtils;
import com.finer.report.utils.TAGUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportListActivicty extends AppCompatActivity {

    private List<ReportTree>  listReportTree = null;
    private ListView lstvwItem = null;
    private ReportHomeAdapter adapter = null;

    private int reportPosition = 0;
    private String sessionID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list_activicty);

        listReportTree = ReportReferenceMgr.getThisInstance().getLstReportTree();
        lstvwItem = (ListView)findViewById(R.id.lstvw_reports);

        adapter = new ReportHomeAdapter(ReportListActivicty.this,R.layout.list_item_reports,listReportTree);

        lstvwItem.setAdapter(adapter);

        lstvwItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAGUtils.TAG_LOGD,"--->Item clicked" );
                if (listReportTree.get(i).getType() == ReportTree.TYPE_FOLDER) {
                    Log.d(TAGUtils.TAG_LOGD,"--->folder" );
                    ReportReferenceMgr.getThisInstance().setLstReportTree(  listReportTree.get(i).getReportTrees());
                    Intent intent1 = new Intent(ReportListActivicty.this, ReportListActivicty.class);
                    startActivity(intent1);
                }
                else
                {

                    Log.d(TAGUtils.TAG_LOGD,"--->report" );
                    reportPosition = i;
                    HomePageGetTask task = new HomePageGetTask();

                    task.execute((Void) null);
                    //get report

                    Toast.makeText(ReportListActivicty.this,"获取报表"+listReportTree.get(i).getMpTags().get(TAGUtils.TAG_ID) + "数据",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private class HomePageGetTask extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... voids) {


            Map<String,String> mpParams = new HashMap<String, String>();

            mpParams.put("__device__","android");
            mpParams.put("__mobileapp__","true");
            mpParams.put("cmd","entry_report");
            mpParams.put("isMobile","yes");
            mpParams.put("op","fs_main");
            mpParams.put("id",listReportTree.get(reportPosition).getMpTags().get(TAGUtils.TAG_ID));

           String result = HttpCookieUtils.sendGetMessage(MainActivity.URL_LOGIN,mpParams ,HomeData.getInstance().getCookies());

            Log.d(TAGUtils.TAG_LOGD,"--->QueryOfParameters "+ result);
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            try{
                Log.d(TAGUtils.TAG_LOGD,"--->SessionID" );
                JSONObject jsonObject = new JSONObject(s);

                sessionID = jsonObject.getString("sessionid");
                Log.d(TAGUtils.TAG_LOGD,"--->SessionID" + sessionID);

                SessionPageGetTask task = new SessionPageGetTask();
                task.execute((Void)null);

            }catch (JSONException je)
            {

                je.printStackTrace();
            }


        }
    }


    private class SessionPageGetTask extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... voids) {


            Map<String,String> mpParams = new HashMap<String, String>();

            mpParams.put("__device__","android");
            mpParams.put("__mobileapp__","true");
            mpParams.put("cmd","json");
            mpParams.put("isMobile","yes");
            mpParams.put("op","page_content");
            mpParams.put("pn","1");
            mpParams.put("sessionID",sessionID);


            String result = HttpCookieUtils.sendGetMessage(MainActivity.URL_LOGIN,mpParams ,HomeData.getInstance().getCookies());

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("charts");
                if (jsonArray.length() >= 0)
                {
                    JSONObject objectchart = jsonArray.optJSONObject(0);
                    String stringURL = objectchart.getJSONArray("items").optJSONObject(0).getString("url");

                    Map<String,String> mpParamsurl = new HashMap<String, String>();
                    mpParamsurl.put("__device__","android");
                    mpParamsurl.put("__mobileapp__","true");
                    mpParamsurl.put("isMobile","yes");
                    String resulturl = HttpCookieUtils.sendGetMessage(MainActivity.URL_LOGIN + stringURL ,mpParams ,HomeData.getInstance().getCookies());
                    Log.d(TAGUtils.TAG_LOGD,"--->Chart Url Json" + resulturl);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {


            Log.d(TAGUtils.TAG_LOGD,"--->" + s);



        }
    }

}
