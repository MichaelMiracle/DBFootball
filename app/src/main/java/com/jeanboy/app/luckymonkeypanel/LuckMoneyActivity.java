package com.jeanboy.app.luckymonkeypanel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.miracle.R;

import java.util.Random;

public class LuckMoneyActivity extends AppCompatActivity {

    private LuckyMonkeyPanelView lucky_panel;
    private Button btn_action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_panel);

        lucky_panel = (LuckyMonkeyPanelView) findViewById(R.id.lucky_panel);
        btn_action = (Button) findViewById(R.id.btn_action);

        lucky_panel.setGameEventListener(new LuckyMonkeyPanelView.GameEventListener() {
            @Override
            public void onGameStop(int stayIndex) {

            }
        });
        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lucky_panel.isGameRunning()) {
                    lucky_panel.startGame();
                } else {
                    int stayIndex = new Random().nextInt(8);
                    Log.e("LuckyMonkeyPanelView", "====stay===" + stayIndex);
                    lucky_panel.tryToStop(stayIndex);
                }
            }
        });
    }
}
