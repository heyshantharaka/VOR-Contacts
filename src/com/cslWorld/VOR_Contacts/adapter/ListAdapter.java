package com.cslWorld.VOR_Contacts.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cslWorld.VOR_Contacts.R;
import com.cslWorld.VOR_Contacts.entity.Contact;

public class ListAdapter extends BaseAdapter {

    private TreeSet<Contact> sortedList;


    private LayoutInflater mInflater;

    public ListAdapter(Context context, ArrayList<Contact> contacts) {
        sortedList = new TreeSet<Contact>();
        sortedList.addAll(contacts);
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return sortedList.size();
    }

    public Object getItem(int position) {
        Iterator<Contact> it = sortedList.iterator();
        int i = -1;
        Contact current = null;
        while(it.hasNext() && i < position) {
            current = it.next();
            i++;
            if(i == position){
                break;
            }
        }
        return current;
    }

    public void clearAll() {
        sortedList.clear();
        notifyDataSetChanged();
    }

    public long getItemId(int position) {

        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.contact_item, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.name);
            holder.letter = (TextView) convertView.findViewById(R.id.lbl_toggle_discription);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ArrayList<Contact> ContactsList = new ArrayList<Contact>(sortedList);
        holder.txtName.setText(ContactsList.get(position).getName());
        char c = ContactsList.get(position).getName().charAt(0);
        holder.letter.setText(c+"");

        if(position ==  0){

            holder.letter.setVisibility(View.VISIBLE);


        }
        else{

            if(ContactsList.get(position).compareTo(ContactsList.get(position-1)) == 0){

                holder.letter.setVisibility(View.GONE);

            }

            else{

                holder.letter.setVisibility(View.VISIBLE);

            }

        }

        return convertView;
    }

    static class ViewHolder {

        TextView txtName;
        TextView letter;


    }



}
