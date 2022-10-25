package com.example.bus_ticketapplication.Admin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bus_ticketapplication.R;

import java.util.List;

public class BusList extends ArrayAdapter<Bus2> {

    private final Activity context;
    private final List<Bus2> busList;

    public BusList(Activity context, List<Bus2> busList) {
        super(context, R.layout.list_bus, busList);
        this.context = context;
        this.busList = busList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_bus, null, true);


        TextView textViewTravelsName = listViewItem.findViewById(R.id.text_view_busName);
        TextView textViewBusNumber = listViewItem.findViewById(R.id.text_view_busNumber);
        TextView textViewBusFare=listViewItem.findViewById(R.id.text_view_busFare);
        TextView textViewDate = listViewItem.findViewById(R.id.text_view_date);
        TextView textViewTime=listViewItem.findViewById(R.id.text_view_time);
        TextView textViewFrom = listViewItem.findViewById(R.id.text_view_from);
        TextView textViewTo = listViewItem.findViewById(R.id.text_view_to);


        Bus2 bus = busList.get(position);

        textViewTravelsName.setText(bus.getTravelsName());
        textViewBusNumber.setText(bus.getBusNumber());
        textViewBusFare.setText("Ksh."+bus.getFare());
        textViewDate.setText(bus.getDate());
        textViewTime.setText(bus.getTime());
        textViewFrom.setText(bus.getFrom());
        textViewTo.setText(bus.getTo());


        return listViewItem;
    }
}
