package com.menafi.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.menafi.domain.Person;
import com.menafi.mehafihesaplama.R;

import java.util.List;


public class CustomAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Person> persons;

    public CustomAdapter(Context context,List<Person> persons) {
//        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = ((Activity) context).getLayoutInflater();
        this.persons = persons;
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int position) {
        return persons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return persons.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;

        rowView=inflater.inflate(R.layout.row_layout,null);
        TextView tvPersonName=(TextView)rowView.findViewById(R.id.tvPersonName);
        EditText etCost=(EditText)rowView.findViewById(R.id.etCost);
        Person person=persons.get(position);
        tvPersonName.setText(person.getName());

        etCost.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return rowView;
    }
}
