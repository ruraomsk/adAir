package ru.list.ruraomsk.adair.slot;

import android.util.Log;

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
            while (slot.isWork()&&!slot.isMessage()){
                sleep(100);
            }
            //Пришло сообшение от устройства
        } catch (InterruptedException e) {
            Log.d("adAir",e.getMessage());
        }

    }
}
