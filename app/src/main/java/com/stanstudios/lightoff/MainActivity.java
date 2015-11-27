package com.stanstudios.lightoff;

import android.app.ActionBar;
import android.app.Activity;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import java.util.List;

public class MainActivity extends Activity {
    private ToggleButton btnbatden;
    private boolean den = false;
    private Camera cam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnbatden = (ToggleButton) findViewById(R.id.btnbatden);
        btnbatden.setChecked(false);
        btnbatden.setText(null);
        btnbatden.setTextOn(null);
        btnbatden.setTextOff(null);
        btnbatden.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && hasFlash()) {
                    cam = Camera.open();
                    Camera.Parameters p = cam.getParameters();
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    cam.setParameters(p);
                    cam.startPreview();
                } else {
                    if (hasFlash()) {
                        cam.stopPreview();
                        cam.release();
                    }
                }
            }


        });
    }

    public boolean hasFlash() {
        if (cam == null) {
            return false;
        }
        Camera.Parameters parameters = cam.getParameters();

        if (parameters.getFlashMode() == null) {
            return false;
        }
        List<String> supportedFlashModes = parameters.getSupportedFlashModes();
        if (supportedFlashModes == null || supportedFlashModes.isEmpty() || supportedFlashModes.size() == 1 && supportedFlashModes.get(0).equals(Camera.Parameters.FLASH_MODE_OFF)) {
            return false;
        }
        return true;
    }
}
