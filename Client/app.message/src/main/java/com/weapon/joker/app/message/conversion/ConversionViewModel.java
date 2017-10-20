package com.weapon.joker.app.message.conversion;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.weapon.joker.app.message.BR;
import com.weapon.joker.app.message.R;
import com.weapon.joker.lib.middleware.utils.Util;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.conversion.ConversionViewModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/20
 *     desc   :
 * </pre>
 */

public class ConversionViewModel extends ConversionContact.ViewModel {

    public void init() {
        for (Conversation conversation : JMessageClient.getConversationList()) {
            int unReadMsgCnt = conversation.getUnReadMsgCnt();
            Message latestMessage = conversation.getLatestMessage();
            ConversionItemViewModel model = new ConversionItemViewModel(getContext());
            model.displayName = ((UserInfo) latestMessage.getTargetInfo()).getDisplayName();
            model.userName = ((UserInfo) latestMessage.getTargetInfo()).getUserName();
            model.lastContent = ((TextContent) latestMessage.getContent()).getText();
            model.unReadNum = unReadMsgCnt;
            model.lastTime = Util.getStrTime(latestMessage.getCreateTime(), "yyyy-MM-dd HH:mm");
            items.add(model);
        }
    }

    public final ItemBinding<ConversionItemViewModel> singleItem = ItemBinding.of(BR.conversionItemModel, R.layout.item_message_conversion);
    public final ObservableList<ConversionItemViewModel> items = new ObservableArrayList<>();
    public final BindingRecyclerViewAdapter.ItemIds<ConversionItemViewModel> itemIds
            = new BindingRecyclerViewAdapter.ItemIds<ConversionItemViewModel>() {
        @Override
        public long getItemId(int position, ConversionItemViewModel item) {
            return position;
        }
    };


}
