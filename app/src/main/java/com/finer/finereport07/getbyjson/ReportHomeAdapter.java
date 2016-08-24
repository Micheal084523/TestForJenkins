package com.finer.finereport07.getbyjson;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.finer.com.finer.report.data.ReportTree;
import com.finer.report.utils.TAGUtils;

import java.util.List;

/**
 * Created by finereport07 on 2016/8/23.
 */
public class ReportHomeAdapter extends ArrayAdapter<ReportTree>
{
    Context context = null;
    int resource = 0;
    List<ReportTree> listReportTree = null;
    public ReportHomeAdapter(Context ctx, @LayoutRes int res, @NonNull List<ReportTree> objects) {
        super(ctx, res, objects);

        context = ctx;
        resource =res;
        listReportTree = objects;
    }

    @Override
    public int getCount() {
        return listReportTree.size();
    }

    @Override
    public int getPosition(ReportTree item) {
        return super.getPosition(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        {
            convertView  = LayoutInflater.from(context).inflate(resource,null);
        }

        TextView txtViewText = (TextView)convertView.findViewById(R.id.txtvw_text);
        txtViewText.setText(listReportTree.get(position).getMpTags().get(TAGUtils.TAG_TEXT));
        TextView txtViewType = (TextView)convertView.findViewById(R.id.txtvw_type);
        if (listReportTree.get(position).getType() == ReportTree.TYPE_FOLDER)
        {
            txtViewType.setText("目录");

        }else {
            txtViewType.setText("图表");
        }

        return convertView;
    }
}