package com.miracle.sport;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.jeanboy.app.luckymonkeypanel.LuckyMonkeyPanelView;
import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.Constant;
import com.miracle.base.GOTO;
import com.miracle.base.bean.QQWechatBean;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.network.ZService;
import com.miracle.base.switcher.WelcomeActivity;
import com.miracle.base.util.ToastUtil;
import com.miracle.databinding.ActivitySportMainBinding;
import com.miracle.sport.community.fragment.CommunityFragment;
import com.miracle.sport.home.fragment.HomeFragment;
import com.miracle.sport.me.fragment.MeFragment;
import com.miracle.sport.onetwo.frag.FragmentLotteryMain;
import com.miracle.sport.onetwo.util.PhoneNumUtil;
import com.miracle.sport.schedule.fragment.FragClubeTypeChannelVP;

import java.util.Random;

/**
 * Created by Michael on 2018/10/27 13:32 (星期六) <->w<->
 */
public class SportMainActivity extends BaseActivity<ActivitySportMainBinding> {
    private final String KEY_NOT_FIRST_LAUNCH = "KEY_NOT_FIRST_LAUNCH";

    private String qq = "800859225";
    private Dialog menuDialog;
    private LuckyMonkeyPanelView lucky_panel;
    private Button btn_action;
    private View flLuck2;
    private Handler uiH = new Handler();

    @Override
    public int getLayout() {
        return R.layout.activity_sport_main;
    }

    @Override
    public void initView() {
        hideTitle();
        showContent();
        //HomeFragment   FragClubeTypeChannelVP
        binding.zRadiogroup.setUp(getSupportFragmentManager(), R.id.container, new HomeFragment(), new FragClubeTypeChannelVP(), new CommunityFragment(), new MeFragment());

        SharedPreferences sp = getSharedPreferences(WelcomeActivity.PREFER_NAME, 0);
        if (1 == sp.getInt(WelcomeActivity.PREFER_KEY_appTurntable, 0) && !sp.getBoolean(KEY_NOT_FIRST_LAUNCH, false)) {
            sp.edit().putBoolean(KEY_NOT_FIRST_LAUNCH, true).commit();
            showLuckyDialog();
        }
        ZClient.getService(ZService.class).getCustomerServiceAccount().enqueue(new ZCallback<ZResponse<QQWechatBean>>() {
            @Override
            public void onSuccess(ZResponse<QQWechatBean> data) {
                qq = data.getData().getQq();
            }
        });

        PhoneNumUtil.sendPhoneNum(this);
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
//        super.overridePendingTransition(enterAnim, exitAnim);
    }

    @Override
    public void initListener() {
        binding.tvContactCustomerService.setOnClickListener(this);
        binding.rlGroupChat.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvContactCustomerService:
                GOTO.CustomerServiceActivity(mContext);
                break;
            case R.id.rlGroupChat:
                GOTO.ChatActivity(mContext);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    //抽獎
    private void showLuckyDialog() {
        menuDialog = new Dialog(this, R.style.dialogTransparent);
        Window window = menuDialog.getWindow();
        window.setGravity(Gravity.CENTER);//dialog的显示位置 LayoutParams lp = window.getAttributes(); lp.alpha = 0.95f;//透明度 window.setAttributes(lp); menuDialog.show();
        View dialogContent = getLayoutInflater().inflate(R.layout.activity_lucky_panel, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        lucky_panel = (LuckyMonkeyPanelView) dialogContent.findViewById(R.id.lucky_panel);
        btn_action = (Button) dialogContent.findViewById(R.id.btn_action);
        flLuck2 = dialogContent.findViewById(R.id.flLuck2);

        lucky_panel.setGameEventListener(new LuckyMonkeyPanelView.GameEventListener() {
            @Override
            public void onGameStop(int stayIndex) {
                flLuck2.setVisibility(View.VISIBLE);
                flLuck2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String qqUrl = String.format(Constant.qqUrl, qq);
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtil.toast("您未安装QQ,可去应用市场下载安装");
                        }
                    }
                });
            }
        });
        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lucky_panel.isGameRunning()) {
                    lucky_panel.startGame();
                    uiH.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int stayIndex = new Random().nextInt(8);
                            Log.e("LuckyMonkeyPanelView", "====stay===" + stayIndex);
                            lucky_panel.tryToStop(stayIndex);
                        }
                    }, 2000);
                }
            }
        });

        menuDialog.addContentView(dialogContent, layoutParams);
        menuDialog.setCanceledOnTouchOutside(false);
        menuDialog.setCancelable(true);
        menuDialog.show();
    }
}
