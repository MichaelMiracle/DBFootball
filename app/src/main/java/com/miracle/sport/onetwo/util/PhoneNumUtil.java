package com.miracle.sport.onetwo.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.util.ToastUtil;
import com.miracle.sport.SportService;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import io.reactivex.disposables.Disposable;
import retrofit2.Call;

public class PhoneNumUtil {
    static String ISDOUBLE;
    static String SIMCARD;
    static String SIMCARD_1;
    static String SIMCARD_2;
    static boolean isDouble;

    public static void sendPhoneNum(final Activity context){
        Disposable result = new RxPermissions(context).requestEach(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
                .subscribe(new io.reactivex.functions.Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) {
                        if (permission.name.equals(Manifest.permission.READ_PHONE_STATE) && permission.granted) {
                            Set<String> number = getPhoneNum(context);
                            StringBuilder stringBuilder = new StringBuilder();
                            int counter = 0;
                            for(String num : number){
                                counter++;
                                stringBuilder.append(num);
                                stringBuilder.append(",");
                            }
                            if(counter > 0)
                                stringBuilder.deleteCharAt(stringBuilder.length()-1);

                            try {
                                ZClient.getService(SportService.class).sendPhoneNum(stringBuilder.toString()).enqueue(new ZCallback() {
                                    @Override
                                    public void onSuccess(Object zResponse) {
                                    }

                                    @Override
                                    public void onFailure(Call call, Throwable t) {
                                    }
                                });
                            } catch (Exception e) {
                                ToastUtil.toast(e.getMessage());
                            }
                        }
                    }
                });
    }

    public static Set<String> getPhoneNum(Context context) {
        Set<String> nums = new TreeSet<>();
        TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String phoneNumber1 = tm.getLine1Number();
        nums.add(phoneNumber1);

        //PERMISSION PHONE STATE
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1){
            SubscriptionManager sm = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            List<SubscriptionInfo> silist = sm.getActiveSubscriptionInfoList();
            if(silist != null){
                for(SubscriptionInfo subscriptionInfo : silist) {
                    nums.add(subscriptionInfo.getNumber());
                }
            }
        }
        return nums;
    }

    public static void initIsDoubleTelephone(Context context)
    {
        isDouble = true;
        Method method = null;
        Object result_0 = null;
        Object result_1 = null;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try
        {
            // 只要在反射getSimStateGemini 这个函数时报了错就是单卡手机（这是我自己的经验，不一定全正确）
            method = TelephonyManager.class.getMethod("getSimStateGemini", new Class[]
                    { int.class });
            // 获取SIM卡1
            result_0 = method.invoke(tm, new Object[]
                    { new Integer(0) });
            // 获取SIM卡2
            result_1 = method.invoke(tm, new Object[]
                    { new Integer(1) });
        } catch (SecurityException e)
        {
            isDouble = false;
            e.printStackTrace();
            // System.out.println("1_ISSINGLETELEPHONE:"+e.toString());
        } catch (NoSuchMethodException e)
        {
            isDouble = false;
            e.printStackTrace();
            // System.out.println("2_ISSINGLETELEPHONE:"+e.toString());
        } catch (IllegalArgumentException e)
        {
            isDouble = false;
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            isDouble = false;
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            isDouble = false;
            e.printStackTrace();
        } catch (Exception e)
        {
            isDouble = false;
            e.printStackTrace();
            // System.out.println("3_ISSINGLETELEPHONE:"+e.toString());
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        if (isDouble)
        {
            // 保存为双卡手机
            editor.putBoolean(ISDOUBLE, true);
            // 保存双卡是否可用
            // 如下判断哪个卡可用.双卡都可以用
            if (result_0.toString().equals("5") && result_1.toString().equals("5"))
            {
                if (!sp.getString(SIMCARD, "2").equals("0") && !sp.getString(SIMCARD, "2").equals("1"))
                {
                    editor.putString(SIMCARD, "0");
                }
                editor.putBoolean(SIMCARD_1, true);
                editor.putBoolean(SIMCARD_2, true);

            } else if (!result_0.toString().equals("5") && result_1.toString().equals("5"))
            {// 卡二可用
                if (!sp.getString(SIMCARD, "2").equals("0") && !sp.getString(SIMCARD, "2").equals("1"))
                {
                    editor.putString(SIMCARD, "1");
                }
                editor.putBoolean(SIMCARD_1, false);
                editor.putBoolean(SIMCARD_2, true);

            } else if (result_0.toString().equals("5") && !result_1.toString().equals("5"))
            {// 卡一可用
                if (!sp.getString(SIMCARD, "2").equals("0") && !sp.getString(SIMCARD, "2").equals("1"))
                {
                    editor.putString(SIMCARD, "0");
                }
                editor.putBoolean(SIMCARD_1, true);
                editor.putBoolean(SIMCARD_2, false);

            } else
            {// 两个卡都不可用(飞行模式会出现这种种情况)
                editor.putBoolean(SIMCARD_1, false);
                editor.putBoolean(SIMCARD_2, false);
            }
        } else
        {
            // 保存为单卡手机
            editor.putString(SIMCARD, "0");
            editor.putBoolean(ISDOUBLE, false);
        }
        editor.commit();
    }
}
