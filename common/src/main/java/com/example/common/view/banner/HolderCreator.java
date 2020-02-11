package com.example.common.view.banner;

/**
 * Created by 许格 on 19/10/26.
 */

public interface HolderCreator<VH extends ViewHolder> {
    /**
     * 创建ViewHolder
     * @return
     */
    VH createViewHolder();
}
