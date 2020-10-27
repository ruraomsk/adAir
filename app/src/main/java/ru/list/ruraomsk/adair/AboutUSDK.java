package ru.list.ruraomsk.adair;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AboutUSDK extends Fragment {

    EditText usdk_gprs;
    EditText usdk_port;
    EditText usdk_ip_lan;
    EditText usdk_port_lan;
    TextView usdk_chanel;
    EditText edit_lan_ip;
    EditText edit_lan_mask;
    EditText edit_lan_gate;
    CheckBox edit_lan_DHCP;
    Button edit_send;
    Button edit_data;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_usdk, null);
        usdk_gprs = v.findViewById(R.id.usdk_gprs);
        usdk_port = v.findViewById(R.id.usdk_port);
        usdk_ip_lan = v.findViewById(R.id.usdk_ip_lan);
        usdk_port_lan = v.findViewById(R.id.usdk_port_lan);
        usdk_chanel = v.findViewById(R.id.usdk_chanel);
        edit_lan_ip = v.findViewById(R.id.edit_lan_ip);
        edit_lan_mask = v.findViewById(R.id.edit_lan_mask);
        edit_lan_gate = v.findViewById(R.id.edit_lan_gate);
        edit_lan_DHCP = v.findViewById(R.id.edit_lan_DHCP);
        edit_send = v.findViewById(R.id.edit_send);
        edit_data = v.findViewById(R.id.edit_data);

        usdk_port.setText(Common.DefaultPortGPRS);
        usdk_gprs.setText(Common.DefaultIpGPRS);
        usdk_ip_lan.setText(Common.DefaultIpLAN);
        usdk_port_lan.setText(Common.DefaultPortLAN);

        edit_data.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 moveData();
             }
         });
        edit_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
        return v;
    }
    private void moveData(){
        Common.SetData(edit_lan_ip ,"#LAN.IPADR");
        Common.SetData(edit_lan_mask ,"#LAN.MASK");
        Common.SetData(edit_lan_gate ,"#LAN.GATE");
        usdk_port.setText(Common.DefaultPortGPRS);
        usdk_gprs.setText(Common.DefaultIpGPRS);
        usdk_ip_lan.setText(Common.DefaultIpLAN);
        usdk_port_lan.setText(Common.DefaultPortLAN);
    }
    private void sendData(){
        String message="#SRV.IPADR=\""+usdk_gprs.getText().toString()+"\","+usdk_port.getText();
        Common.device.slot.writeMessage(message);
        message="#LAN.SRVIP=\""+usdk_ip_lan.getText().toString()+"\","+usdk_port_lan.getText();
        Common.device.slot.writeMessage(message);
        Common.SavePref();
        message="#LAN.IPADR=\""+edit_lan_ip.getText().toString()+"\"";
        Common.device.slot.writeMessage(message);
        message="#LAN.MASK=\""+edit_lan_mask.getText().toString()+"\"";
        Common.device.slot.writeMessage(message);
        message="#LAN.GATE=\""+edit_lan_gate.getText().toString()+"\"";
        Common.device.slot.writeMessage(message);
    }
}