package ru.list.ruraomsk.adair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

import ru.list.ruraomsk.adair.DB.DB;
import ru.list.ruraomsk.adair.DB.UpdateDb;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final int REQUEST_CODE_DEVICES=1;
    final int REQUEST_CODE_SETTING=2;
    private TextView tvDeviceName;

    private Button btnTraffic;
    private Button btnTech;
    private Button btnHard;
    private Button btnBind;


    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx=this;
        Common.run(this);
        tvDeviceName=findViewById(R.id.device_name);
        btnTraffic=findViewById(R.id.cmd_traffic);
        btnTraffic.setOnClickListener(this);
        btnTech=findViewById(R.id.cmd_tech);
        btnTech.setOnClickListener(this);
        btnHard=findViewById(R.id.cmd_hard);
        btnHard.setOnClickListener(this);
        btnBind=findViewById(R.id.cmd_bind);
        btnBind.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cmd_traffic:
                startActivity(new Intent(this,Traffic.class));
                break;
            case R.id.cmd_tech:
                startActivity(new Intent(this,Tech.class));
                break;
            case R.id.cmd_hard:
                startActivity(new Intent(this,Hard.class));
                break;
            case R.id.cmd_bind:
                startActivity(new Intent(this,Bind.class));
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_update:
                //Обновляем БД устройств
                Common.db.clearAll();
                Common.run(this);
                break;
            case R.id.menu_choice:
                //выбираем устройство
                startActivityForResult(new Intent(this,ChoiceDevice.class),REQUEST_CODE_DEVICES);
                break;
            case R.id.menu_option:
                //выбираем устройство
                startActivityForResult(new Intent(this,Setting.class),REQUEST_CODE_SETTING);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("adAirDebug", "requestCode = " + requestCode + ", resultCode = " + resultCode);

        if(resultCode==RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_DEVICES:
                    Common.host=data.getStringExtra("host");
                    tvDeviceName.setText(Common.db.getName(Common.host));
                    break;
                case REQUEST_CODE_SETTING:
                    Common.run(ctx);
                    break;
            }
        }else {
            Toast.makeText(this, "Отмена", Toast.LENGTH_SHORT).show();
            Common.host="";
            tvDeviceName.setText("Устройство не выбрано");

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}