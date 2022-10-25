package com.example.bus_ticketapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    private List<CustomRowItem> busList;
    private Context context;

    public CustomAdapter(Context context, List<CustomRowItem> busList ) {
        this.context = context;
        this.busList = busList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CustomRowItem customRowItem = busList.get(position);
        holder.textViewBusName.setText(customRowItem.getTravelsName());
        holder.textViewBusNumber.setText(customRowItem.getBusNumber());
        holder.textViewBusFare.setText("Ksh."+customRowItem.getFare());
        holder.textViewDate.setText(customRowItem.getDate());
        holder.textViewTime.setText(customRowItem.getTime());
        holder.textViewFrom.setText(customRowItem.getFrom());
        holder.textViewTo.setText(customRowItem.getTo());

    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewBusName, textViewBusNumber,textViewBusFare, textViewDate, textViewTime, textViewFrom,textViewTo;

        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            textViewBusName = itemView.findViewById(R.id.text_view_busName);
            textViewBusNumber = itemView.findViewById(R.id.text_view_busNumber);
            textViewBusFare=itemView.findViewById(R.id.text_view_busFare);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewTime = itemView.findViewById(R.id.text_view_time);
            textViewFrom = itemView.findViewById(R.id.text_view_from);
            textViewTo = itemView.findViewById(R.id.text_view_to);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String busId = busList.get(position).getBusId();
            String travelsName = busList.get(position).getTravelsName();
            String busNumber = busList.get(position).getBusNumber();
            String fare=busList.get(position).getFare();
            String date = busList.get(position).getDate();
            String time = busList.get(position).getTime();
            String from = busList.get(position).getFrom();
            String to = busList.get(position).getTo();

            Intent intent = new Intent(context, SeatActivity.class);

            intent.putExtra("NAME_BUS",travelsName);
            intent.putExtra("BUS_FARE",fare);
            intent.putExtra("BUS_ID",busId);
            intent.putExtra("DATE_BUS",date);
            intent.putExtra("TIME_BUS",time);
            intent.putExtra("FROM_BUS",from);
            intent.putExtra("TO_BUS",to);

            context.startActivity(intent);

        }


    }
}



