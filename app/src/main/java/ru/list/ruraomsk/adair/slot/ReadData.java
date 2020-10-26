package ru.list.ruraomsk.adair.slot;

import android.util.Log;

import ru.list.ruraomsk.adair.Common;
import ru.list.ruraomsk.adair.Device;

public class ReadData  extends Thread{
    Slot slot;
    Device device;
    public ReadData(Slot slot, Device device) {
        this.slot=slot;
        this.device=device;
    }
    @Override
    public void run() {
        try {
            while(slot.isWork()){
                while (!slot.isMessage()){
                    if (!slot.isWork()) return;
                    sleep(100);
                }
                //Пришло сообшение от устройства
                String message=slot.getMessage();
                //Разбираем его на имя и текст
                if (!message.startsWith("#") || !message.contains(":") ||!message.contains(".") ){
                    Log.d("adAirDebug","Не данные !"+message);
                    continue;
                }
                String[] list=message.split(":");
                String last="";
                if (list.length >2){
                    for (int i = 1; i < list.length; i++) {
                        last+=list[i]+":";
                    }
                    last=last.substring(0,last.length()-1);
                } else {
                    if(list.length ==1) last=" ";
                    else last+=list[1];
                }
                if(last.length()==0) last=" ";
                Common.values.put(list[0],last);
                Log.d("adAirDebug","Ключ "+list[0]+"  <"+last+">");
            }
        } catch (InterruptedException e) {
//            if(e!=null) Log.d("adAirDebug",e.getMessage());
        }
        Log.d("adAirDebug","Stop ReadData");

    }
}
