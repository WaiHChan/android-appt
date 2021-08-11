package edu.pdx.cs410J.chanwai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;

public class AppointmentListAdapter extends ArrayAdapter<Appointment> {
    private static final String TAG = "AppointmentListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    public AppointmentListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Appointment> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String owner = getItem(position).getOwner();
        String description = getItem(position).getDescription();
        Date begin = getItem(position).getBeginTime();
        Date end = getItem(position).getEndTime();

        Appointment appointment = new Appointment(owner, description, begin, end);

        long durationInMins = duration(appointment);
        long diffMins = min(durationInMins);
        String duration = "Duration: " + diffMins + " minutes";

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvDescription = convertView.findViewById(R.id.textView1);
        TextView tvBegin = convertView.findViewById(R.id.textView2);
        TextView tvEnd = convertView.findViewById(R.id.textView3);
        TextView tvDuration = convertView.findViewById(R.id.textView4);

        tvDescription.setText("Description: " + description);
        tvBegin.setText("From " + appointment.getBDateString());
        tvEnd.setText("To " + appointment.getEDateString());
        tvDuration.setText(duration);

        return convertView;
    }

    public long duration(Appointment a){
        long diff = a.getEndTime().getTime() - a.getBeginTime().getTime();
        return diff / 60000;
    }

    public long min(long time) {
        return time % 60;
    }
}

