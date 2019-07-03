package com.botpy.constrainlayoutexample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.botpy.framelibrary.BaseSkinActivity;
import com.botpy.framelibrary.skin.SkinManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseSkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button, R.id.button2})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.button2:
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if(aBoolean) {
                                    String skinPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                                            File.separator + "app.skin";
                                    Log.d("MainActivity", "---skinPath: " + skinPath);
                                    SkinManager.getInstance().loadSkin(skinPath);
                                    Toast.makeText(MainActivity.this, "点击注册", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                break;
            default:
                break;
        }
    }
}
