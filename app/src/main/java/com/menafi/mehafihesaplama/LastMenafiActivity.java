package com.menafi.mehafihesaplama;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.menafi.db.Database;
import com.menafi.domain.Menafi;
import com.menafi.domain.Person;

import java.util.List;

public class LastMenafiActivity extends Activity{
    private ListView lvMenafiler;
    private ArrayAdapter<String> aaMenafiler;
    private Button btnNewMenafi;
    private TextView tvAddMenafi;
    private EditText etAddMenafi;
    private Button btnAddMenafi;
    private LinearLayout llNewMenafi;
    private List<Menafi> menafiler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menafiler_activity);

        lvMenafiler=(ListView)findViewById(R.id.lvMenafiler);
        btnNewMenafi=(Button)findViewById(R.id.btnNewMenafi);
        llNewMenafi=(LinearLayout)findViewById(R.id.llNewMenafi);

        aaMenafiler=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        Database db=new Database(getApplicationContext());
        menafiler=db.getMenafiler();
        refresh(menafiler);

        tvAddMenafi=new TextView(this);
        etAddMenafi=new EditText(this);
        btnAddMenafi=new Button(this);

        tvAddMenafi.setText("Yeni Bir Menafi Adı Giriniz");
        btnAddMenafi.setText("Ekle");
        btnNewMenafi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llNewMenafi.addView(tvAddMenafi);
                llNewMenafi.addView(etAddMenafi);
                llNewMenafi.addView(btnAddMenafi);
            }
        });


        btnAddMenafi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();

                values.put("name",etAddMenafi.getText().toString());
                values.put("recordDateTimestamp", System.currentTimeMillis());

                Database db=new Database(getApplicationContext());

                db.insert(values,"MENAFI");
                menafiler=db.getMenafiler();
                int menafiId=refresh(menafiler);
                ContentValues values2=new ContentValues();
                List<Person> persons=db.getPersons();
                for(int i=0;i<persons.size();i++) {
                    values2.put("menafi_id", menafiId);
                    values2.put("person_id", persons.get(i).getId());
                    db.insert(values2, "MENAFI_TUTAR");
                }
                etAddMenafi.setText("");
            }
        });



        lvMenafiler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent("android.intent.action.newMenafi");
                System.out.println("position= "+position);
                System.out.println("MenafiId asıl= "+menafiler.get(position).getId());

                i.putExtra("menafiId",menafiler.get(position).getId());
                System.out.println("menafiId send = "+menafiler.get(position).getId());
                startActivity(i);
            }
        });
    }



    private int refresh( List<Menafi> menafiler){
        aaMenafiler.clear();
        for(int i=0;i<menafiler.size();i++){
            aaMenafiler.add(menafiler.get(i).getName());
        }
        lvMenafiler.setAdapter(aaMenafiler);

        int id=menafiler.size()!=0?menafiler.get(menafiler.size()-1).getId():0;
        return id;
    }
}
