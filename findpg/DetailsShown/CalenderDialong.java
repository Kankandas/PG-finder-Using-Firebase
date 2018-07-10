package com.example.kankan.findpg.DetailsShown;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.kankan.findpg.R;

public class CalenderDialong extends AppCompatDialogFragment implements CalendarView.OnDateChangeListener {
    private MyOwnCustomInterface customInterface;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.layout_calender,null);

        builder.setView(view);

        CalendarView calendarView=(CalendarView)view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);

        return builder.create();
    }
    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        String Date=dayOfMonth+"/"+month+"/"+year;

        customInterface.getDate(Date);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            customInterface=(MyOwnCustomInterface)context;
        } catch (Exception e) {
            Toast.makeText(getActivity(),"Please Select a Date",Toast.LENGTH_SHORT).show();
        }
    }

    public interface MyOwnCustomInterface
    {
        void getDate(String date);
    }

}
