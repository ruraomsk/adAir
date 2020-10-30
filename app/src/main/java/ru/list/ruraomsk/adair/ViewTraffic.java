package ru.list.ruraomsk.adair;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class ViewTraffic extends  ViewController implements View.OnClickListener {
    Spinner traffic_type;
    TextView record_status;
    Button btn_cmd_record;
    Button btn_cmd_clear;
    TextView traffic_text;
    boolean status_recod=false;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_traffic, null);
        traffic_type=v.findViewById(R.id.traffic_type);
        record_status=v.findViewById(R.id.record_status);
        btn_cmd_record=v.findViewById(R.id.btn_cmd_record);
        btn_cmd_clear=v.findViewById(R.id.btn_cmd_clear);
        traffic_text=v.findViewById(R.id.traffic_text);
        btn_cmd_clear.setOnClickListener(this);
        btn_cmd_record.setOnClickListener(this);

        traffic_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int result = 0;
                switch (position) {
                    case 0:
                        result = 1;
                        break;
                    case 1: //GPS
                        result = 4;
                        break;
                    case 2: //MODEM
                        result = 2;
                        break;
                    case 3: //SERVER
                        result = 16;
                        break;
                    case 4: //MODEM+SERVER
                        result = 2 + 16;
                        break;
                    case 5: //PSPD
                        result = 8;
                        break;
                    case 6: //ИП
                        result = 32;
                        break;
                    case 7: //RS485
                        result = 0x28;
                        break;
                    case 8: //CAN
                        result = 64;
                        break;
                }
                Common.device.slot.writeMessage("#DBG.MODE:" + String.valueOf(result));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Common.device.slot.writeMessage("#DBG.MODE:000");

            }

        });
        return v;
    }

    @Override
    public void onStart() {
        Common.RegitrationFragment("traffic",this);
        super.onStart();
    }

    @Override
    public void onStop() {
        Common.UnRegitrationFragment("traffic");
        super.onStop();
    }

    public void View(){
        Common.ViewData(traffic_text,"#SYS.LOG");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cmd_record:
                Toast.makeText(Common.ctx, "Обмен записан", Toast.LENGTH_LONG).show();

                break;
            case R.id.btn_cmd_clear:
                Common.values.put("#SYS.LOG","");
//                Common.ViewData(traffic_text,"#SYS.LOG");
                break;
        }
    }
}