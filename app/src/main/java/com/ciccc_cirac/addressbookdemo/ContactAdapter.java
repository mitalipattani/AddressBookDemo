package com.ciccc_cirac.addressbookdemo;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ciccc_cirac.addressbookdemo.data.DatabaseDescription;

/**
 * Created by CICCC-CIRAC on 2017-08-21.
 */

public class ContactAdapter extends
RecyclerView.Adapter<ContactAdapter.ContactViewHolder>{
    private Cursor cursor = null;
    //create a interface to handle onclick event
    public interface ContactAdapterInterface{
         void onClick(Uri uri);
    }

    //constructor
    public ContactAdapter(ContactAdapterInterface clickListener) {
        this.contactAdapterInterface = clickListener;
    }
    public ContactAdapterInterface contactAdapterInterface;
    //this takes data from loader from contactfragment
    public void notifyChange(Cursor cursor)
    {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //infalte the layout for list
        // (create a layout)
        View view = LayoutInflater.from(
                parent.getContext())
                .inflate(android.R.layout.simple_list_item_1,
                        parent,
                        false);
        return new ContactViewHolder(view);
    }
        // create a cursor object
    //sets the text of the list item
    //to display the search
    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.setRowID(cursor.getLong(cursor
                .getColumnIndex(
                        DatabaseDescription.Contact._ID)));
        holder.textContact.setText(cursor.getString(
                cursor.getColumnIndex(
                        DatabaseDescription.Contact.COLUMN_NAME
                )));
    }
    //returns total no of rows
    @Override
    public int getItemCount() {
        int count = 0;
        if(cursor!=null)
        {
            count = cursor.getCount();
        }
        return count;
    }


    // 1. create a inner class a view for each row of recyclerview
    // nested subclass of RecyclerView.ViewHolder used to implement
    // the view-holder pattern in the context of a RecyclerView
    public class ContactViewHolder extends
    RecyclerView.ViewHolder
    {
        //every row is having two data
        //1. Name 2)ID
        //Recyclerview is showing the Name only
        // We need Id for database operation
        public TextView textContact;
        private  long rowID;
        //configuring the RecyclerView ViewHolder
        public ContactViewHolder(View itemView) {
            super(itemView);
            textContact = (TextView)itemView
                    .findViewById(android.R.id.text1);
            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //adapter is attached with contactFragment
                            //so implemnet the interface in ContactFragment
                            //not in MainActivity
                            contactAdapterInterface.onClick(
                                    DatabaseDescription.Contact
                                            .buildContactUri(rowID));
                            //buildContactUri() that create a uri
                            //with selected rowID
                        }
                    });
        }
        //set database rowID for the Contact in this View Holder
        public void setRowID(long rowID)
        {
            this.rowID = rowID;
        }

    }

}
