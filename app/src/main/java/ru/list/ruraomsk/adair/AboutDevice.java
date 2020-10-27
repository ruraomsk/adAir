package ru.list.ruraomsk.adair;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AboutDevice extends ViewController  {
    TextView device_number;
    TextView device_version_hard;
    TextView device_version_soft;
    TextView device_chanel;
    TextView device_status_pbs;
    TextView device_status_gps;
    TextView device_status_pspd;
    TextView device_power;
    TextView device_memory;
    TextView satelite_GPS;
    TextView time_RTC;
    TextView time_GPS;
    TextView time_System;




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, null);
        device_number=v.findViewById(R.id.device_number);
        device_version_hard=v.findViewById(R.id.device_version_hard);
        device_version_soft=v.findViewById(R.id.device_version_soft);
        device_chanel=v.findViewById(R.id.device_chanel);
        device_status_pbs=v.findViewById(R.id.device_status_pbs);
        device_status_gps=v.findViewById(R.id.device_status_gps);
        device_status_pspd=v.findViewById(R.id.device_status_pspd);
        device_power=v.findViewById(R.id.device_power);
        device_memory=v.findViewById(R.id.device_memory);

        satelite_GPS=v.findViewById(R.id.satelite_GPS);
        time_RTC=v.findViewById(R.id.time_RTC);
        time_GPS=v.findViewById(R.id.time_GPS);
        time_System=v.findViewById(R.id.time_System);
        return v;
    }

    @Override
    public void onStart() {
        Common.RegitrationFragment("about",this);
        super.onStart();
    }

    @Override
    public void onStop() {
        Common.UnRegitrationFragment("about");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void View(){

        Common.ViewData(device_number,"#BRD.IDNUM");
        Common.ViewData(device_version_hard,"#BRD.HWVER");
        Common.ViewData(device_version_soft,"#BRD.SWVER");
        Common.ViewData(device_chanel,"#SYS.CHAN");
        Common.ViewData(device_status_pbs,"#SYS.MCODE");
        Common.ViewData(device_status_gps,"#GPS.ERROR");
        Common.ViewData(device_status_pspd,"#485.ERROR");
        Common.ViewData(device_power,"#PWR.STATE");
        Common.ViewData(device_memory,"#MEM.ERROR");
        Common.ViewData(satelite_GPS,"#GPS.SAT");
        Common.ViewData(time_RTC,"#SYS.TIME");
        Common.ViewData(time_GPS,"#GPS.TIME");
        Common.ViewData(time_System,"#SYSTEM.TIME");
    }
}