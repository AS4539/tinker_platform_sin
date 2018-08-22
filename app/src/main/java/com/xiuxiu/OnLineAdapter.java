package com.xiuxiu;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ${weicheng} on 2018/8/22.
 */

public class OnLineAdapter extends BaseQuickAdapter<OnLineBean,BaseViewHolder> {

    public OnLineAdapter(@Nullable List<OnLineBean> data) {
        super(R.layout.item_online, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OnLineBean item) {
        helper.setText(R.id.key_text,item.getKay());
        helper.setText(R.id.value_text,item.getValue());
    }
}
