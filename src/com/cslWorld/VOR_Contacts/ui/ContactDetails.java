package com.cslWorld.VOR_Contacts.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.cslWorld.VOR_Contacts.R;
import com.cslWorld.VOR_Contacts.adapter.ContactDetailAdapter;
import com.cslWorld.VOR_Contacts.entity.Contact;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;


/**
 * Created by heshan.lokuge on 4/4/14.
 */
public class ContactDetails extends Activity {

    ListView contactDetails;
    TextView nameDisplay ;
    TextView titleDisplay;

    private ContactDetailAdapter adapter;
    Contact contactItem;
    boolean isFavoriteItem;


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details_screen);


        /*initialization*/
        init();

        /*get the details of the contact from the previous activity and insert them to an array list */
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            nameDisplay.setText(extras.getString("name"));
            titleDisplay.setText(extras.getString("title"));
            contactItem = new Contact(extras.getString("mailAddress"),extras.getString("mobileNumber"),extras.getString("officeNumber"),extras.getString("company"));
        }
        /*insert contact details for the list*/
        insertContactDetails();

    }

    public void init(){

        contactDetails = (ListView)findViewById(R.id.list_contact_deatils);
        nameDisplay = (TextView)findViewById(R.id.txt_name_detailed_contacts);
        titleDisplay = (TextView) findViewById(R.id.txt_name_title);
        isFavoriteItem = false;

        /*when an item in the contact details list is clicked*/
        contactDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object o = contactDetails.getItemAtPosition(position);
                BasicNameValuePair pair = (BasicNameValuePair) o;

                /* open the email sending intent*/
                if(position == 0) {

                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType(HTTP.PLAIN_TEXT_TYPE);
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, pair.getValue());
                    startActivity(emailIntent);

                }

                /* open the dialling intent*/
                else if(position == 1 || position == 2) {

                    Uri number = Uri.parse("tel:" + pair.getValue());
                    startActivity(new Intent(Intent.ACTION_DIAL, number));
                }
            }
        });
    }

    public void insertContactDetails(){

        adapter = new ContactDetailAdapter(this , contactItem);
        contactDetails.setAdapter(adapter);
    }

    /*when back button is clicked*/
    @Override
    public void onBackPressed(){

        finish();
    }

}
