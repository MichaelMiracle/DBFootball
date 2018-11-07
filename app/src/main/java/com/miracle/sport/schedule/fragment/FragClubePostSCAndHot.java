package com.miracle.sport.schedule.fragment;

import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.miracle.R;
import com.miracle.base.network.PageLoadCallback;
import com.miracle.base.network.ZClient;
import com.miracle.databinding.FragClubePostBinding;
import com.miracle.sport.schedule.adapter.ClubePostSCAdapter;
import com.miracle.sport.schedule.bean.ClubeItem;
import com.miracle.sport.schedule.bean.ClubeType;
import com.miracle.sport.schedule.net.FootClubServer;

import retrofit2.Call;

//赛事比分内容
public class FragClubePostSCAndHot extends HandleFragment<FragClubePostBinding> {
    ClubeItem req;
    ClubeType parentType;
    PageLoadCallback callback;
    ClubePostSCAdapter adapter;
    boolean isHot = false;

    public ClubeType getParentType() {
        return parentType;
    }

    public void setParentType(ClubeType parentType) {
        this.parentType = parentType;
    }

    public ClubeItem getReq() {
        return req;
    }

    public void setReq(ClubeItem req) {
        this.req = req;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    @Override
    public void onHandleMessage(Message msg) {
        if(msg.what == 1)
            callback.onRefresh();
        else if(msg.what == 2){
            isHot = true;
            if(callback != null && adapter != null)
                callback.onRefresh();
        }
    }

    @Override
    public int getLayout() {
        return R.layout.frag_clube_post;
    }

    @Override
    public void initView() {
        adapter = new ClubePostSCAdapter(mContext);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        binding.recyclerView.setHasFixedSize(true);

        callback = new PageLoadCallback(adapter, binding.recyclerView) {
            @Override
            public void requestAction(int page, int limit) {
                if(isHot)
                    ZClient.getService(FootClubServer.class).getFootClubTypesHot(page,limit).enqueue(this);
                else
                    ZClient.getService(FootClubServer.class).getFootClubPostSC(parentType.getId(),req.getType(),page,limit).enqueue(this);
            }

            @Override
            public void onFinish(Call call) {
                super.onFinish(call);
                setUIStatus(ShowStat.NORMAL);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                super.onFailure(call, t);
                setUIStatus(ShowStat.ERR);
            }
        };
        callback.initSwipeRefreshLayout(binding.swipeRefreshLayout);

//        callback.onRefresh();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

    public void reqData(){
        uiHandler.sendEmptyMessage(1);
    }

    @Override
    public void loadData() {
        super.loadData();
        uiHandler.sendEmptyMessage(1);
    }
}
