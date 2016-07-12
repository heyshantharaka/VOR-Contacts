package com.cslWorld.VOR_Contacts.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cslWorld.VOR_Contacts.R;
import com.cslWorld.VOR_Contacts.data.Data;
import com.cslWorld.VOR_Contacts.entity.Contact;
import org.apache.http.message.BasicNameValuePair;

public class ContactDetailAdapter extends BaseAdapter {

    private static ArrayList<BasicNameValuePair> values;
    private LayoutInflater mInflater;

    public ContactDetailAdapter(Context context ,Contact contactItem) {

        values = new ArrayList<BasicNameValuePair>();

        String mailAddress =  contactItem.getMailAddress();
        String mobileNumber = contactItem.getMobileNumber();
        String officeNumber = contactItem.getOfficeNumber();
        String company = contactItem.getCompany();
        mInflater = LayoutInflater.from(context);
        extractValues(mailAddress , mobileNumber , officeNumber , company);
    }

    private void extractValues(String mailAddress ,String mobileNumber , String officeNumber , String company)
    {
        values.add(new BasicNameValuePair(Data.CONTACT_DETAILS_EMAIL, mailAddress ));
        values.add(new BasicNameValuePair(Data.CONTACT_DETAILS_MOBILE,mobileNumber));
        values.add(new BasicNameValuePair(Data.CONTACT_DETAILS_OFFICE, officeNumber));
        values.add(new BasicNameValuePair(Data.CONTACT_DETAILS_COMPANY, company));

    }

    public int getCount() {
        return values.size();
    }

    public Object getItem(int position) {
        return values.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

            convertView = mInflater.inflate(R.layout.contact_details_item, null);
            BasicNameValuePair pair = values.get(position);
            TextView title = (TextView) convertView.findViewById(R.id.lbl_title_desc);
            title.setText(pair.getName());
            TextView content = (TextView) convertView.findViewById(R.id.lbl_content);
            setContent(content, pair.getValue(), position);
            return convertView;
    }

    private void setContent(TextView content, String value, int position)
    {
        if (position == 4)
        {
            value = value.replace(",", "\n");
        }
        content.setText(value);
    }

}
