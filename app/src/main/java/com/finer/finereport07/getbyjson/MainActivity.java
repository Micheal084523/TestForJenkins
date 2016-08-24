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
import com.finer.com.finer.report.data.LoginData;
import com.finer.com.finer.report.data.ReportReferenceMgr;
import com.finer.com.finer.report.data.ReportTree;
import com.finer.report.utils.HttpCookieUtils;
import com.finer.report.utils.HttpUtils;
import com.finer.report.utils.JsonTreeUtils;
import com.finer.report.utils.TAGUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    public static final String URL_LOGIN = "http://192.168.155.1:8075/WebReport/ReportServer";
    public static final int LOGIN_REQUEST_CODE = 1000;

    ReportTree reportTree = null;
    ListView homeList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        this.startActivityForResult(intent,LOGIN_REQUEST_CODE);

        homeList = (ListView)findViewById(R.id.lstvw_root_reports);

        homeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent1 = new Intent(MainActivity.this,ReportListActivicty.class);
                ReportReferenceMgr.getThisInstance().setLstReportTree( reportTree.getReportTrees().get(i).getReportTrees());

                startActivity(intent1);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null) {
            Boolean bSuccess = data.getBooleanExtra("SUCCESS", false);
            Log.d(TAGUtils.TAG_LOGD,"--->Get the homepage");
            if (bSuccess != null && bSuccess == true) {
                HomePageGetTask task = new HomePageGetTask();
                task.execute((Void) null);
            }else
            {
                Toast.makeText(MainActivity.this,"获取到错误的主页路径",Toast.LENGTH_SHORT).show();
                finish();
            }
        }else
        {
            finish();
        }
    }

    public class HomePageGetTask extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... voids) {

            String homePageUrl = URL_LOGIN ;//+ HomeData.getInstance().getHomePageUrl();
            Map<String,String> mpParams = new HashMap<String, String>();

            mpParams.put("__device__","android");
            mpParams.put("__mobileapp__","true");
            mpParams.put("cmd","module_getrootreports");
            mpParams.put("isMobile","yes");
            mpParams.put("op","fs_main");
            mpParams.put("id","-1");
            Log.d(TAGUtils.TAG_LOGD,"--->Start to Get Folders");
           String result = HttpCookieUtils.sendGetMessage(homePageUrl,mpParams,HomeData.getInstance().getCookies());




            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            reportTree = JsonTreeUtils.CreateReportTree(s);

            ReportHomeAdapter adapter = new ReportHomeAdapter(MainActivity.this,R.layout.list_item_reports,reportTree.getReportTrees());

            homeList.setAdapter(adapter);
        }
    }
}
