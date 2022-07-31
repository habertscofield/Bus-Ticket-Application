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

public class TicketActivityAdapter extends RecyclerView.Adapter<TicketActivityAdapter.TicketViewHolder> {

    private List<TicketActivityItem> ticketList;
    private Context context;

    public TicketActivityAdapter(Context context,List<TicketActivityItem> ticketList ) {
        this.context = context;
        this.ticketList = ticketList;

    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.ticketshown, parent, false);
        return new TicketViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {

        TicketActivityItem ticketActivityItem = ticketList.get(position);
//       holder.ticketID.setText(ticketActivityItem.getTicketID());
        holder.travelsName.setText(ticketActivityItem.getBusName());
        holder.from.setText(ticketActivityItem.getFrom());
        holder.to.setText(ticketActivityItem.getTo());
        holder.Date.setText(ticketActivityItem.getIssueDate());
        holder.Time.setText(ticketActivityItem.getIssueTime());

    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }


    public class TicketViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ticketID, travelsName, from, to, Time, Date;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            ticketID = itemView.findViewById(R.id.ticketID);
            travelsName = itemView.findViewById(R.id.busname);
            from = itemView.findViewById(R.id.fromLocation);
            to = itemView.findViewById(R.id.toLocation);
            Time = itemView.findViewById(R.id.issueTime);
            Date = itemView.findViewById(R.id.issueDate);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
           Intent intent = new Intent(context,TicketDetailsActivity.class);
            String ticketid = ticketList.get(position).getTicketID();
            String issuetime = ticketList.get(position).getIssueTime();
           intent.putExtra("issueTime",issuetime);
           intent.putExtra("ticketID",ticketid);
            context.startActivity(intent);
        }
    }
}


