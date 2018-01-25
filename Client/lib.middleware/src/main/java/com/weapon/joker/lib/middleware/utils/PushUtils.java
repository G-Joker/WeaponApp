package com.weapon.joker.lib.middleware.utils;

import android.content.Context;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;

import static com.weapon.joker.lib.middleware.utils.TagAliasOperatorHelper.ACTION_CLEAN;
import static com.weapon.joker.lib.middleware.utils.TagAliasOperatorHelper.ACTION_DELETE;
import static com.weapon.joker.lib.middleware.utils.TagAliasOperatorHelper.ACTION_SET;
import static com.weapon.joker.lib.middleware.utils.TagAliasOperatorHelper.sequence;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.middleware.utils.PushUtils
 *     e-mail : 1012126908@qq.com
 *     time   : 2018/01/25
 *     desc   :
 * </pre>
 */

public class PushUtils {

    /**
     * 设置推送别名
     *
     * @param context
     * @param alias
     */
    public static void setAlias(final Context context, final String alias) {
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = ACTION_SET;
        sequence++;
        tagAliasBean.alias = alias;
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(context, sequence, tagAliasBean);
    }

    /**
     * 删除推送别名
     *
     * @param context
     * @param alias
     */
    public static void unsetAlias(final Context context, final String alias) {
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = ACTION_DELETE;
        sequence++;
        tagAliasBean.alias = alias;
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(context, sequence, tagAliasBean);
    }

    /**
     * 设置推送别名
     *
     * @param context
     * @param tags
     */
    public static void setTags(final Context context, final Set<String> tags) {
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = ACTION_SET;
        sequence++;
        tagAliasBean.tags = tags;
        tagAliasBean.isAliasAction = false;
        TagAliasOperatorHelper.getInstance().handleAction(context, sequence, tagAliasBean);
    }

    /**
     * 删除所有tags / topics
     *
     * @param context
     */
    public static void unSetAllTags(Context context) {
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = ACTION_CLEAN;
        sequence++;
        tagAliasBean.isAliasAction = false;
        TagAliasOperatorHelper.getInstance().handleAction(context, sequence, tagAliasBean);
    }

    /**
     * 打开极光推送
     *
     * @param context
     */
    public static void resumePush(Context context) {
        if (JPushInterface.isPushStopped(context)) {
            LogUtils.i("JPush:\t" + "重启极光推送");
            JPushInterface.resumePush(context);
        }
    }

    /**
     * 关闭极光推送
     *
     * @param context
     */
    public static void stopPush(Context context) {
        if (!JPushInterface.isPushStopped(context)) {
            LogUtils.i("JPush:\t" + "关闭极光推送");
            JPushInterface.stopPush(context);
        }
    }

}
