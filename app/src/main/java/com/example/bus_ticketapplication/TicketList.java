package com.example.bus_ticketapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TicketList extends ArrayAdapter<TicketActivityItem> {

    private final Activity context;
    private final List<TicketActivityItem> ticketList;

    public TicketList(Activity context, List<TicketActivityItem> ticketList) {
        super(context, R.layout.list_tickets, ticketList);
        this.context = context;
        this.ticketList = ticketList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_tickets, null, true);


        TextView textViewTravelsName = listViewItem.findViewById(R.id.busname);
        TextView textViewFrom = listViewItem.findViewById(R.id.fromLocation);
        TextView textViewTo = listViewItem.findViewById(R.id.toLocation);
        TextView textViewDate = listViewItem.findViewById(R.id.issueDate);
        TextView textViewTime=listViewItem.findViewById(R.id.issueTime);



        TicketActivityItem ticketActivityItem = ticketList.get(position);
        textViewTravelsName.setText(ticketActivityItem.getBusName());
        textViewFrom.setText(ticketActivityItem.getFrom());
        textViewTo.setText(ticketActivityItem.getTo());
        textViewDate.setText(ticketActivityItem.getIssueDate());
        textViewTime.setText(ticketActivityItem.getIssueTime());


        return listViewItem;
    }
}
