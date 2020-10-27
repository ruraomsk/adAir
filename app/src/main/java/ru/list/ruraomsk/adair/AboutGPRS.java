package ru.list.ruraomsk.adair;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AboutGPRS extends ViewController {
    TextView modem_type;
    TextView modem_soft_version;
    TextView modem_sysop_GSM;
    TextView modem_level;
    TextView modem_count;
    TextView modem_ip_server;
    TextView modem_stage;
    TextView modem_status;
    TextView lan_ip_server;
    TextView lan_stage;
    TextView lan_status;
    TextView lan_ip;
    TextView lan_mask;
    TextView lan_gate;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gprs, null);
        modem_type=v.findViewById(R.id.modem_type);
        modem_soft_version=v.findViewById(R.id.modem_soft_version);
        modem_sysop_GSM=v.findViewById(R.id.modem_sysop_GSM);
        modem_level=v.findViewById(R.id.modem_level);
        modem_count=v.findViewById(R.id.modem_count);
        modem_ip_server=v.findViewById(R.id.modem_ip_server);
        modem_stage=v.findViewById(R.id.modem_stage);
        modem_status=v.findViewById(R.id.modem_status);
        lan_ip_server=v.findViewById(R.id.lan_ip_server);
        lan_stage=v.findViewById(R.id.lan_stage);
        lan_status=v.findViewById(R.id.lan_status);
        lan_ip=v.findViewById(R.id.lan_ip);
        lan_mask=v.findViewById(R.id.lan_mask);
        lan_gate=v.findViewById(R.id.lan_gate);

        return v;
    }

    @Override
    public void View() {
        Common.ViewData(modem_type,"#MOD.TYPE");
        Common.ViewData(modem_soft_version,"#MOD.FWVER");
        Common.ViewData(modem_sysop_GSM,"#GSM.OPERA");
        Common.ViewData( modem_level,"#GSM.LEVEL");
        Common.ViewData( modem_count,"#SYS.DELAY");
        Common.ViewData( modem_ip_server,"#GSM.SRVIP");
        Common.ViewData( modem_stage,"#GSM.STATE");
        Common.ViewData( modem_status,"#GSM.ERROR");
        Common.ViewData( lan_ip_server,"#LAN.SRVIP");
        Common.ViewData( lan_stage,"#LAN.STATE");
        Common.ViewData( lan_status,"#LAN.ERROR");
        Common.ViewData( lan_ip,"#LAN.IPADR");
        Common.ViewData( lan_mask,"#LAN.MASK");
        Common.ViewData( lan_gate,"#LAN.GATE");
    }

    public void onStart() {
        Common.RegitrationFragment("gprs",this);
        super.onStart();
    }

    @Override
    public void onStop() {
        Common.UnRegitrationFragment("gprs");
        super.onStop();
    }

}