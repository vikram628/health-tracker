package com.healthtracker.empoweringtechnologies.healthtracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class MedicalLocationAdapter extends ArrayAdapter{
    List list = new ArrayList();
    public MedicalLocationAdapter(Context context, int resource)
    {
        super(context, resource);
    }


    public void add(MedicalLocation object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        MedicalLocationHolder medicallocationholder = new MedicalLocationHolder();

        if(row == null)
        {
            LayoutInflater layoutinflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutinflater.inflate(R.layout.medicallocation,parent,false);
            medicallocationholder.tx_name = (TextView) row.findViewById(R.id.medicallocationname);
            row.setTag(medicallocationholder);
        }
        else
        {
            medicallocationholder = (MedicalLocationHolder) row.getTag();
        }

        MedicalLocation medicallocation = (MedicalLocation) this.getItem(position);
        medicallocationholder.tx_name.setText(medicallocation.get_medicallocation());
        return row;

    }

    public static class MedicalLocationHolder
    {
        TextView tx_name;
    }
}
