package com.kdp.seekbar;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;


import com.kdp.seekbar.view.VerticalSeekbar;

public class MainActivity extends AppCompatActivity {

    private boolean isChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final VerticalSeekbar seekbar = findViewById(R.id.seekbar);

        //设置监听
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress > 50 && !isChanged){
                    isChanged = true;
                    seekbar.setThumbColor(Color.parseColor("#2f9de3"));
                    seekbar.setProgressColor(Color.parseColor("#2f9de3"));
                }else if (progress <= 50 && isChanged){
                    isChanged = false;
                    seekbar.setThumbColor(Color.parseColor("#ff0000"));
                    seekbar.setProgressColor(Color.parseColor("#ff0000"));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


    }
}
