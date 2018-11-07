package com.miracle.sport.community.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.ContextHolder;
import com.miracle.base.util.DisplayUtil;
import com.miracle.base.view.ZImagePreviewer;
import com.miracle.sport.community.bean.PostBean;

import java.util.List;

/**
 * Created by Michael on 2018/10/29 15:26 (星期一)
 */
public class PostListAdapter extends RecyclerViewAdapter<PostBean> {

    private LinearLayout.LayoutParams params;
    private int colorLike;
    private int colorDislike;

    public PostListAdapter() {
        super(R.layout.item_post);
        params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        params.setMargins(2, 0, 2, 0);
        colorLike = CommonUtils.getColor(R.color.red_ball);
        colorDislike = CommonUtils.getColor(R.color.main_color_grey);
    }

    @Override
    protected void convert(BaseViewHolder helper, final PostBean item) {
        helper.addOnClickListener(R.id.tvLike);

        helper.setText(R.id.tvUserName, item.getNickname());
        GlideApp.with(ContextHolder.getContext()).load(item.getImg()).placeholder(R.mipmap.defaule_img).into((ImageView) helper.getView(R.id.ivUserImg));

        helper.setText(R.id.tvTitle, item.getTitle());
        helper.setText(R.id.tvComment, item.getComment_num() + "");
        helper.setText(R.id.tvTime, item.getAdd_time());
        helper.setTextColor(R.id.tvLike, item.getClick() == 1 ? colorLike : colorDislike);
        helper.setText(R.id.tvLikeCount, item.getClick_num() + "");
        helper.setText(R.id.tvCircleName, item.getName());


        LinearLayout container = helper.getView(R.id.llPics);
        container.removeAllViews();
        List<String> thumbs = item.getThumb();
        if (thumbs != null && !thumbs.isEmpty()) {
            helper.setGone(R.id.llPics, true);
            for (int i = 0; i < thumbs.size(); i++) {
                ImageView imageView = new ImageView(ContextHolder.getContext());
                imageView.setLayoutParams(params);
                imageView.setAdjustViewBounds(true);
                imageView.setMaxHeight((int) DisplayUtil.dip2px(ContextHolder.getContext(), 200));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                GlideApp.with(ContextHolder.getContext()).load(thumbs.get(i))
                        .placeholder(R.mipmap.defaule_img)
                        .error(R.mipmap.empty)
                        .into(imageView);

                final int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ZImagePreviewer.getInstance(mContext).show(item.getThumb(), finalI);
                    }
                });
                container.addView(imageView);
            }
        } else {
            helper.setGone(R.id.llPics, false);
        }
    }
}
