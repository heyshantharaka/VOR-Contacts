package com.cslWorld.VOR_Contacts.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.cslWorld.VOR_Contacts.R;
import com.cslWorld.VOR_Contacts.adapter.ListAdapter;
import com.cslWorld.VOR_Contacts.data.Data;
import com.cslWorld.VOR_Contacts.entity.Contact;
import com.cslWorld.VOR_Contacts.web.JsonHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by heshan.lokuge on 4/2/14.
 */
public class ContactListActivity extends Activity {

    /*variables for json parsing*/
    private JsonHandler jsonHandler;
    private String jsonText;
    private String contactDetails;
    JSONArray contactsArray;

    /*variables for list activities*/
    private ListView contactsList;
    private ListAdapter listAdapter;
    ArrayList<Contact> contactsArrayList;

    boolean connected;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);

        /*initialise activities */
        init();
        /*initialise activities */
        getJsonData();

    }

    public void init(){
        contactsList = (ListView)findViewById(R.id.contactsList);
        contactsArrayList = new ArrayList<Contact>();
        jsonHandler = new JsonHandler();

         /*when an item in the list is clicked */
        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                Object o = contactsList.getItemAtPosition(position);
                final Contact contactObject = (Contact) o;

                /*go to new activity to display contact details*/
                Intent contactDetailsIntent = new Intent(ContactListActivity.this , ContactDetails.class);
                contactDetailsIntent.putExtra("name",contactObject.getName());
                contactDetailsIntent.putExtra("title",contactObject.getTitle());
                contactDetailsIntent.putExtra("mailAddress",contactObject.getMailAddress());
                contactDetailsIntent.putExtra("mobileNumber",contactObject.getMobileNumber());
                contactDetailsIntent.putExtra("officeNumber",contactObject.getOfficeNumber());
                contactDetailsIntent.putExtra("company" , contactObject.getCompany());
                startActivity(contactDetailsIntent);


            }
        });
    }

    /* this method connect to web service and get the contact details as ajson data*/
    public void  getJsonData(){

        /*check the network connection */
        connected = jsonHandler.checkNetworkConnection(ContactListActivity.this);
        /*connect to web service and get the json text*/
        if(connected == true){

            jsonText = jsonHandler.getJsonData(Data.URL_FULL_DIRECTORY);
            //Toast.makeText(getApplicationContext() , jsonText , Toast.LENGTH_LONG).show();
            //Log.d("JSON String" , jsonText);
            /* create contact objects for each contact*/
            createContacts();
            /*create a list of contacts and display it*/
            insertToList();
        }

        else{

            AlertDialog.Builder alert = new AlertDialog.Builder(ContactListActivity.this);
            alert.setTitle("No Internet Access");
            alert.setMessage("Do you want to turn on the Internet?");

            alert.setPositiveButton("Settings",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            startActivity(intent);
                            //finish();

                        }
                    });
            alert.setNeutralButton("Retry",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            getJsonData();

                        }
                    });
            alert.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                           ((Activity) ContactListActivity.this).finish();

                        }
                    });

            alert.show();

        }

    }


    /* this method get the contact details and create contact objects for each contact*/
    public void createContacts() {

        /*get the employee contact details to String */
        contactDetails = jsonHandler.getJSONValue(jsonText , "c");
        //Toast.makeText(getApplicationContext() , contactDetails , Toast.LENGTH_LONG).show();
        //Log.d("JSON String" , contactDetails);

        /* get the keys and values of each employee */
        try {
            //get contact details as a json array and create contact objects for each contact
            contactsArray = new JSONArray(contactDetails);

            for (int i = 0; i < contactsArray.length(); i++) {

                if(!contactsArray.get(i).equals(null)){

                    JSONObject jsonobject = contactsArray.getJSONObject(i);

                    String name = jsonobject .getString("n");
                    String title = jsonobject .getString("t");
                    String mailAddress =jsonobject .getString("e");
                    String mobileNumber = "";
                    if(jsonobject.has("m")){

                        mobileNumber = jsonobject .getString("m");
                    }

                    String officeNumber =jsonobject .getString("o");
                    String company = jsonobject.getString("c");

                /*create contact objects for each contact*/
                    Contact contact = new Contact(name , title ,mailAddress ,mobileNumber ,officeNumber , company);
                    contactsArrayList.add(contact);


                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    /* this method insert the contact details to list view*/
    public void insertToList(){

        listAdapter = new ListAdapter(this ,contactsArrayList );
        contactsList.setAdapter(listAdapter);

    }

    /*when back button is clicked*/
    @Override
    public void onBackPressed(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Close the Application");
        alert.setMessage("Are you sure you want to exit?");

        alert.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        ContactListActivity.this.finish();

                    }
                });

        alert.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        dialog.dismiss();
                    }
                });

        alert.show();
    }

}
