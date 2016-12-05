package com.menafi.mehafihesaplama;//package com.menafi.menafihesap;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.menafi.db.Database;
import com.menafi.domain.Person;

import java.util.List;

public class AddPersonActivity extends Activity{

    private Button btnSavePerson;
    private Button btnGetContact;
    private EditText etName;
    private EditText etTelNo;
    private ListView lvPersons;
    ArrayAdapter<String> aaPersons;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_person_activity);

        aaPersons=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        btnSavePerson=(Button)findViewById(R.id.btnSavePerson);
        btnGetContact=(Button)findViewById(R.id.btnGetContact);
        etName=(EditText)findViewById(R.id.etName);
        etTelNo=(EditText)findViewById(R.id.etTelNo);
        lvPersons=(ListView)findViewById(R.id.lvPersons);

        Database db=new Database(getApplicationContext());
        List<Person> persons=db.getPersons();
        System.out.println("persons size = "+persons.size());
        for(int i=0;i<persons.size();i++) {
            aaPersons.add(persons.get(i).getName());
        }

        lvPersons.setAdapter(aaPersons);



        btnSavePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person=new Person();
                long timestamp=System.currentTimeMillis();

                person.setName(etName.getText().toString());
                person.setTelNo(etTelNo.getText().toString());
                person.setRecordTimestamp(timestamp);
                person.setDeleted(false);

                ContentValues values = new ContentValues();

                values.put("name", person.getName());
                values.put("tel_no", person.getTelNo());
                values.put("deleted", 0);
                values.put("recordTimestamp",person.getRecordTimestamp());

                Database db=new Database(getApplicationContext());

                db.insert(values,"PERSONS_TABLE");
                List<Person> persons=db.getPersons();
                aaPersons.clear();
                for(int i=0;i<persons.size();i++) {
                    aaPersons.add(persons.get(i).getName());
                }

                lvPersons.setAdapter(aaPersons);
                etName.setText("");
                etTelNo.setText("");
            }
        });


        btnGetContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

                startActivityForResult(intent, PICK_CONTANT);

            }
        });




    }
    private final int PICK_CONTANT=1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case PICK_CONTANT:
                if(resultCode==Activity.RESULT_OK){
                    Uri contactData = data.getData();
//                    Cursor c = managedQuery(contactData, null, null, null, null);

                    Cursor c = this.getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst())
                    {
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone =
                                c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1"))
                        {
                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
                            phones.moveToFirst();
                            String cNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            // Toast.makeText(getApplicationContext(), cNumber, Toast.LENGTH_SHORT).show();
                            etTelNo.setText(cNumber);
                        }
                    }
                }
        }
    }
}
