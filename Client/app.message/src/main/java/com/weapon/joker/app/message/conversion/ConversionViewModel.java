package com.weapon.joker.app.message.conversion;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.text.TextUtils;

import com.weapon.joker.app.message.BR;
import com.weapon.joker.app.message.R;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
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
        // 遍历消息列表，获取本地所有的历史消息列表
        items.clear();
        for (Conversation conversation : JMessageClient.getConversationList()) {
            String myUserName = JMessageClient.getMyInfo().getUserName();
            String userName = ((UserInfo) conversation.getTargetInfo()).getUserName();
            if (!TextUtils.equals(userName, myUserName)) {
                // 如果收到的消息通知，是本人的话，就不添加到列表中
                ConversionItemViewModel model = new ConversionItemViewModel(getContext(), conversation);
                items.add(model);
            }
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
