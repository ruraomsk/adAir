package ru.list.ruraomsk.adair;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ViewTech extends ViewController {
    Button tech_base;
    Button tech_send_statistic;
    TextView tech_time_1;
    TextView tech_time_2;
    TextView tech_time_3;
    TextView tech_time_4;
    TextView tech_time_5;
    TextView tech_time_6;
    TextView tech_time_7;
    TextView tech_time_8;

    TextView tech_rezim;
    TextView tech_PK;
    TextView tech_CK;
    TextView tech_NK;

    TextView tech_device;
    TextView tech_Trezim;
    TextView tech_status;

    TextView tech_switch;
    TextView tech_ftu;
    TextView tech_fts;
    TextView tech_tcycle;
    TextView tech_tstart;
    TextView tech_ttek;
    TextView tech_tnext;

    TextView tech_ring1;
    TextView tech_ring2;
    TextView tech_broken1;
    TextView tech_broken2;

    TextView tech_tvp1;
    TextView tech_tvp2;
    TextView tech_elblink;
    TextView tech_os;
    TextView tech_stress;
    TextView tech_input;
    TextView tech_power;
    TextView tech_door;




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tech, null);
        tech_base=v.findViewById(R.id.tech_base);
        tech_send_statistic=v.findViewById(R.id.tech_send_statistic);
        tech_time_1=v.findViewById(R.id.tech_time_1);
        tech_time_3=v.findViewById(R.id.tech_time_3);
        tech_time_2=v.findViewById(R.id.tech_time_2);
        tech_time_4=v.findViewById(R.id.tech_time_4);
        tech_time_5=v.findViewById(R.id.tech_time_5);
        tech_time_6=v.findViewById(R.id.tech_time_6);
        tech_time_7=v.findViewById(R.id.tech_time_7);
        tech_time_8=v.findViewById(R.id.tech_time_8);

        tech_rezim=v.findViewById(R.id.tech_rezim);
        tech_PK=v.findViewById(R.id.tech_PK);
        tech_CK=v.findViewById(R.id.tech_CK);
        tech_NK=v.findViewById(R.id.tech_NK);

        tech_device=v.findViewById(R.id.tech_device);
        tech_Trezim=v.findViewById(R.id.tech_Trezim);
        tech_status=v.findViewById(R.id.tech_status);

        tech_switch=v.findViewById(R.id.tech_switch);
        tech_ftu=v.findViewById(R.id.tech_ftu);
        tech_fts=v.findViewById(R.id.tech_fts);
        tech_tcycle=v.findViewById(R.id.tech_tcycle);
        tech_tstart=v.findViewById(R.id.tech_tstart);
        tech_ttek=v.findViewById(R.id.tech_ttek);
        tech_tnext=v.findViewById(R.id.tech_tnext);

        tech_ring1=v.findViewById(R.id.tech_ring1);
        tech_ring2=v.findViewById(R.id.tech_ring2);
        tech_broken1=v.findViewById(R.id.tech_broken1);
        tech_broken2=v.findViewById(R.id.tech_broken2);

        tech_tvp1=v.findViewById(R.id.tech_tvp1);
        tech_tvp2=v.findViewById(R.id.tech_tvp2);
        tech_elblink=v.findViewById(R.id.tech_elblink);
        tech_os=v.findViewById(R.id.tech_os);
        tech_stress=v.findViewById(R.id.tech_stress);
        tech_input=v.findViewById(R.id.tech_input);
        tech_power=v.findViewById(R.id.tech_power);
        tech_door=v.findViewById(R.id.tech_door);

        return v;
    }
    @Override
    public void onStart() {
        Common.RegitrationFragment("tech",this);
        super.onStart();
    }

    @Override
    public void onStop() {
        Common.UnRegitrationFragment("tech");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void View(){

        Common.ViewData(tech_time_1,"#TCH.TCSTA.TIME");
        Common.ViewData(tech_time_2,"#TCH.DKSTA.TIME");
        Common.ViewData(tech_time_3,"#TCH.PHASE.TIME");
        Common.ViewData(tech_time_4,"#TCH.TVPST.TIME");
        Common.ViewData(tech_time_5,"#TCH.PANEL.TIME");
        Common.ViewData(tech_time_6,"#TCH.INPST.TIME");
        Common.ViewData(tech_time_7,"#TCH.SENST.TIME");
        Common.ViewData(tech_time_8,"#TCH.SSTAT.TIME");

        String[] r1=Common.GetLines("#TCH.TCSTA");
        tech_rezim.setText(r1[0]);
        tech_PK.setText(r1[1]);
        tech_CK.setText(r1[2]);
        tech_NK.setText(r1[3]);

        String[] r2=Common.GetLines("#TCH.DKSTA");
        tech_device.setText(r2[0]);
        tech_Trezim.setText(r2[1]);
        tech_status.setText(r2[2]+" "+Common.values.get("#TCH.HARDW"));

        String[] r3=Common.GetLines("#TCH.PHASE");
        tech_switch.setText(r3[0]);
        tech_ftu.setText(r3[1]);
        tech_fts.setText(r3[2]);
        tech_tcycle.setText(r3[3]);
        tech_tstart.setText(r3[4]);
        tech_ttek.setText(r3[5]);
        tech_tnext.setText(r3[6]);

        int r4=Integer.parseInt(Common.values.get("#TCH.TVPST"),16);
        int tvp1=r4&0x3;
        int tvp2=(r4&0xc)>>2;
        switch (tvp1){
            case 0:
                tech_ring1.setText("ГОТОВ1");
                break;
            case 1:
                tech_ring1.setText("ВЫЗОВ1");
                break;
            case 2:
                tech_ring1.setText("ЖДИТЕ1");
                break;
       }
        switch (tvp2){
            case 0:
                tech_ring2.setText("ГОТОВ2");
                break;
            case 1:
                tech_ring2.setText("ВЫЗОВ2");
                break;
            case 2:
                tech_ring2.setText("ЖДИТЕ2");
                break;
        }
        if ((r4&0x10)!=0){
            tech_ring1.setBackgroundColor(Color.YELLOW);
        } else {
            tech_ring1.setBackgroundColor(Color.WHITE);
        }
        if ((r4&0x20)!=0){
            tech_ring2.setBackgroundColor(Color.YELLOW);
        } else {
            tech_ring2.setBackgroundColor(Color.WHITE);
        }
        if ((r4&0x40)!=0){
            tech_broken1.setText("НЕИСПР1");
        } else {
            tech_broken1.setText("ИСПРАВЕН");
        }
        if ((r4&0x80)!=0){
            tech_broken2.setText("НЕИСПР2");
        } else {
            tech_broken2.setText("ИСПРАВЕН");
        }

        int r5=Integer.parseInt(Common.values.get("#TCH.PANEL"),16);
        if((r5&1)!=0){
            tech_elblink.setBackgroundColor(Color.GREEN);
        } else {
            tech_elblink.setBackgroundColor(Color.WHITE);
        }
        if((r5&2)!=0){
            tech_os.setBackgroundColor(Color.GREEN);
        } else {
            tech_os.setBackgroundColor(Color.WHITE);
        }
        if((r5&4)!=0){
            tech_stress.setBackgroundColor(Color.GREEN);
        } else {
            tech_stress.setBackgroundColor(Color.WHITE);
        }
        if((r5&8)!=0){
            tech_input.setBackgroundColor(Color.GREEN);
        } else {
            tech_input.setBackgroundColor(Color.WHITE);
        }
        if((r5&16)!=0){
            tech_power.setBackgroundColor(Color.GREEN);
        } else {
            tech_power.setBackgroundColor(Color.WHITE);
        }
        if((r5&32)!=0){
            tech_door.setBackgroundColor(Color.GREEN);
        } else {
            tech_door.setBackgroundColor(Color.WHITE);
        }
        if((r5&64)!=0){
            tech_tvp1.setBackgroundColor(Color.GREEN);
        } else {
            tech_tvp1.setBackgroundColor(Color.WHITE);
        }
        if((r5&128)!=0){
            tech_tvp2.setBackgroundColor(Color.GREEN);
        } else {
            tech_tvp2.setBackgroundColor(Color.WHITE);
        }
    }

}