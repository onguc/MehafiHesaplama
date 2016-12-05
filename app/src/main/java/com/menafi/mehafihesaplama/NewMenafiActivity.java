package com.menafi.mehafihesaplama;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.menafi.db.Database;
import com.menafi.domain.MenafiTutar;

import java.util.List;

public class NewMenafiActivity extends Activity{
    private LinearLayout llVertical;
    private LinearLayout llResult;
    private Button btnUpdate;
    private Database db;
    private List<MenafiTutar> menafiTutars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_manafi_activity);

        btnUpdate=(Button)findViewById(R.id.btnMenafiUpdate);
        Bundle bundle = getIntent().getExtras();
        final int menafiId= bundle.getInt("menafiId");

        llVertical=(LinearLayout)findViewById(R.id.llVertical);
        llResult=(LinearLayout)findViewById(R.id.llResult);
        db=new Database(getApplicationContext());
       menafiTutars=db.getMenafiTutars(menafiId);


//        final List<Person> persons=db.getPersons();

        final EditText[] etCost=new EditText[menafiTutars.size()];
        final TextView[] tvName=new TextView[menafiTutars.size()];
        LinearLayout[] llRow=new LinearLayout[menafiTutars.size()];
        for(int i=0;i<menafiTutars.size();i++){
            llRow[i]=new LinearLayout(this);
            tvName[i]=new TextView(this);
            etCost[i]=new EditText(this);
            // yukseklik ve genişlik sırasıyla (lp)
            ViewGroup.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ViewGroup.LayoutParams lpEtCost=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            tvName[i].setText(menafiTutars.get(i).getName());
            tvName[i].setEms(10);

            etCost[i].setText(String.valueOf(menafiTutars.get(i).getToPaid()));
            etCost[i].setEms(10);

            etCost[i].setInputType(InputType.TYPE_CLASS_PHONE);
            etCost[i].setEms(10);

            etCost[i].setLayoutParams(lp);
            llRow[i].setLayoutParams(lp);
            tvName[i].setLayoutParams(lp);

            llRow[i].addView(tvName[i]);
            llRow[i].addView(etCost[i]);
            llRow[i].setOrientation(LinearLayout.HORIZONTAL);
            llVertical.addView(llRow[i]);
        }


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float total=0;
                for(int i=0;i<menafiTutars.size();i++){
                    total+=Float.parseFloat(etCost[i].getText().toString());
                }
                float avg=total/menafiTutars.size();
                ContentValues[] values=new ContentValues[menafiTutars.size()];
                String whereIds[]=new String[menafiTutars.size()];
                for(int i=0;i<menafiTutars.size();i++){
                    float toPaid=Float.parseFloat(etCost[i].getText().toString());
                    menafiTutars.get(i).setToPaid(toPaid);
                    menafiTutars.get(i).setToBePaid(avg-toPaid);

                    ContentValues value = new ContentValues();
                    value.put("to_paid", menafiTutars.get(i).getToPaid());
                    value.put("to_be_paid", menafiTutars.get(i).getToBePaid());

                    whereIds[i]="id="+menafiTutars.get(i).getId();
                    values[i]=value;
                }
                String tableName="MENAFI_TUTAR";
                db.updateRows(tableName,values,whereIds);
                menafiTutars=db.getMenafiTutars(menafiId);
                String message="(Kalan Miktar negatif ise o kadar alacak demektir.)\n"
                        +"Kişi Başına Düşen Tutar: "+menafiTutars.get(0).getAvarage()+" tl;\n";
                    for(MenafiTutar menafiTutar:menafiTutars){
                        message+=menafiTutar.getName()+" --> Yaptığı Harcama Tutarı: "+menafiTutar.getToPaid()+" tl --> Kalan Miktar: "+menafiTutar.getToBePaid()+" tl\n";
                    }
                System.out.println("uzunluk : "+message.length());
                System.out.println(message);

                final TextView tvMessage=new TextView(getApplicationContext());
                tvMessage.setText(message);
                llResult.addView(tvMessage);

                Button btnSendWhatsapp=new Button(getApplicationContext());
                Button btnSendSms=new Button(getApplicationContext());

                btnSendWhatsapp.setText("Whatsap İle Gönder");
                btnSendSms.setText("SMS İle Gönder");

                llResult.addView(btnSendWhatsapp);
                llResult.addView(btnSendSms);

                btnSendWhatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(whatsappInstalledOrNot("com.whatsapp")){

                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, tvMessage.getText());
                            sendIntent.setType("text/plain");
                            sendIntent.setPackage("com.whatsapp");
                            startActivity(sendIntent);
//                            String number = "905368110168;905423514566";
//                            String number = "905423514566";
//                            Uri uri = Uri.parse("smsto:" + number);

//                            Cursor c = getContentResolver().query(ContactsContract.Data.CONTENT_URI,
//                                    new String[] { ContactsContract.Contacts.Data._ID},
//                                    ContactsContract.Data.DATA1 + "=?;"+ContactsContract.Data.DATA2 + "=?",
//                                    new String[] {"905368110168@s.whatsapp.net","905423514566@s.whatsapp.net"}, null);
//
//                            c.moveToFirst();

//                            Intent i = new   Intent(Intent.ACTION_SENDTO, Uri.parse("content://com.android.contacts/data/"));
//                            i.setType("text/plain");
//                            String text = "my txt";
//                            i.setPackage("com.whatsapp"); // so that only Whatsapp reacts and not the chooser
//                            i.putExtra(Intent.EXTRA_TEXT, text);
//                            startActivity(i);
//                            c.close();
//                            Intent sendIntent = new Intent("android.intent.action.MAIN");
//                            sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
////                            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("905368110168")+"@s.whatsapp.net;"+ PhoneNumberUtils.stripSeparators("905423514566")+"@s.whatsapp.net");
////                            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("905423514566")+"@s.whatsapp.net");
//                            sendIntent.setPackage("com.whatsapp");
//                            sendIntent.setType("text/plain");
//                            startActivity(sendIntent);

//                            String smsNumber = "905368110168";
//                            Uri uri=Uri.parse("smsto:"+smsNumber);
//                            Intent sendIntent = new Intent("com.google.android.voicesearch.SEND_MESSAGE_TO_CONTACTS");
////                            sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
//                            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.VoiceMessagingActivity"));
//
//                            sendIntent.putExtra("com.google.android.voicesearch.extra.RECIPIENT_CONTACT_CHAT_ID", smsNumber);
//                            sendIntent.setPackage("com.whatsapp");
////                            sendIntent.setAction(Intent.ACTION_SEND);
////                            sendIntent.setType("text/plain");
////                            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber)+"@s.whatsapp.net");
////                            sendIntent.putExtra("sms_body", tvMessage.getText());
//                            startActivity(sendIntent);

//                            Uri uri = Uri.parse("smsto:" + smsNumber);
//                            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
//                            sendIntent.setPackage("com.whatsapp");
//                            startActivity(sendIntent);

                        }
                        else{
                            Uri uri=Uri.parse("market://details?id=com.whatsapp");
                            Intent intent=new Intent(Intent.ACTION_VIEW);
                            Toast.makeText(getApplicationContext(), "WhatsApp Yüklenmedi",Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }

//                        Intent sendIntent = new Intent();
//                        sendIntent.setAction(Intent.ACTION_SEND);
//                        sendIntent.putExtra(Intent.EXTRA_TEXT, tvMessage.getText());
//                        sendIntent.setType("text/plain");
//                        sendIntent.setPackage("com.whatsapp");
//                        startActivity(sendIntent);
                    }
                });


                btnSendSms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String numbers="";
                        for(MenafiTutar menafiTutar:menafiTutars){
                            numbers+=menafiTutar.getTelNo()+";";
                        }
                        System.out.println("numbers ="+numbers);
                        Intent intent=new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("sms:"+numbers));
//                           intent.setData(Uri.parse("sms:"+"+90536 811 01 68"));
//                        intent.putExtra("address","905368110168; 905423514566");
                        intent.putExtra("sms_body",tvMessage.getText());
//                        intent.setType("\"vnd.android-dir\"");
                        startActivity(intent);


                    }
                });



            }
        });



//        ListView listView=(ListView)findViewById(R.id.lvMenafi);
//        CustomAdapter customAdapter=new CustomAdapter(this, persons);

//        listView.setAdapter(customAdapter);

    }


    private boolean whatsappInstalledOrNot(String uri){
        PackageManager pm=getPackageManager();
        boolean app_installed=false;
        try {
            pm.getPackageInfo(uri,PackageManager.GET_ACTIVITIES);
            app_installed=true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed=false;
            e.printStackTrace();
        }

        return app_installed;
    }

}
