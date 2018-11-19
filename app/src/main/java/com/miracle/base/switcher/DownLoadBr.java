package com.miracle.base.switcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.miracle.BuildConfig;

import java.io.File;

public class DownLoadBr extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getExtras() != null && intent.getExtras().containsKey("extra_download_id")){
            long brdid = intent.getExtras().getLong("extra_download_id");
            SharedPreferences sp = context.getSharedPreferences("DOWNLAOD",0);
            long ourid = sp.getLong("downloadkey",-1);
            String apkpath = sp.getString("downloadpath","");

            if(brdid == ourid){
                try{Toast.makeText(context.getApplicationContext(), "下载完成,开始安装!",Toast.LENGTH_LONG).show();
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    File apkFile = new File(apkpath);
                    Uri apkUri;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        apkUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID +".fileProvider", apkFile);
                    } else {
                        apkUri = Uri.fromFile(apkFile);
                    }
                    install.setDataAndType(apkUri, "application/vnd.android.package-archive");
                    context.startActivity(install);
                    sp.edit().clear().commit();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(context,"请手动安装apk文件",Toast.LENGTH_LONG).show();
                }finally {
                }
            }

        }
        Log.d("TAG", "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
    }
}
