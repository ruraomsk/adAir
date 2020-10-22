package ru.list.ruraomsk.adair.slot;

import android.util.Log;

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
                Log.d("adAirDebug","ReadData:"+slot.getMessage()+":");
                sleep(1000);
                slot.writeMessage("ole ole ole");
            }
        } catch (InterruptedException e) {
            Log.d("adAirDebug",e.getMessage());
        }
        Log.d("adAirDebug","Stop ReadData");

    }
}
