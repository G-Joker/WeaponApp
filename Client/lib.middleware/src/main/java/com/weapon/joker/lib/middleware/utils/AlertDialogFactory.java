package com.weapon.joker.lib.middleware.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.weapon.joker.lib.middleware.R;

import cn.jpush.im.android.api.model.UserInfo;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.middleware.utils.AlertDialogFactory
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/19
 *     desc   :
 * </pre>
 */

public class AlertDialogFactory {


    private AlertDialogFactory() {
        throw new AssertionError();
    }


    /**
     * 创建正在加载的对话框
     *
     * @param context
     * @param desc    正在加载的描述
     * @return 正在加载的对话框
     */
    public static AlertDialog createLoadingDialog(Context context, String desc) {
        View view = View.inflate(context, R.layout.dialog_loading, null);
        AlertDialog alertDialog = new AlertDialog.Builder(context).setView(view).setTitle(desc).create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }


    /**
     * 创建一个 EditText 的对话框
     * 为了当输入内容为空点击确认键不关闭对话框，所以重写了 onClickListener
     *
     * @param context
     * @param content 输入框预设内容
     * @param title    对话框标题
     * @param listener 点击确认回调
     * @return 对话框
     */
    public static AlertDialog createOneEditDialog(final Context context, String title, String content,  final
    OnOneEditDialogConfirmListener listener) {
        View view = View.inflate(context, R.layout.dialog_one_edit, null);
        final EditText etContent = view.findViewById(R.id.et_content);
        if (!TextUtils.isEmpty(content)) {
            etContent.setText(content);
            etContent.setSelection(content.length());
            etContent.setFocusable(true);
        }
        final AlertDialog alertDialog =
                new AlertDialog.Builder(context).setView(view)
                                                .setPositiveButton("确认", null)
                                                .setNegativeButton("取消", null)
                                                .setTitle(title)
                                                .create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btPositive = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                Button btNegative = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                btPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(etContent.getText().toString())) {
                            Toast.makeText(context, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                        } else {
                            listener.onOneEditDialogConfirm(etContent.getText().toString());
                            alertDialog.dismiss();
                        }
                    }
                });

                btNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        return alertDialog;
    }

    /**
     * 创建设置系性别 的dialog
     * @param context
     * @param title
     * @return
     */
    public static AlertDialog createThreeRadioDialog(final Context context, String title, final OnGenderChooseListener listener) {
        final View view = View.inflate(context, R.layout.dialog_three_radio, null);
        RadioGroup rgDialog = view.findViewById(R.id.rg_dialog);
        final StringBuilder sb = new StringBuilder();
        sb.append("男");
        rgDialog.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sb.delete(0, sb.length());
                sb.append(((RadioButton) view.findViewById(checkedId)).getText());
            }
        });
        AlertDialog alertDialog =
                new AlertDialog.Builder(context)
                        .setView(view)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String gender = sb.toString();
                                if (listener != null) {
                                    if (TextUtils.equals(gender, "男")) {
                                        listener.onGenderChoose(UserInfo.Gender.male);
                                    } else if (TextUtils.equals(gender, "女")) {
                                        listener.onGenderChoose(UserInfo.Gender.female);
                                    } else {
                                        listener.onGenderChoose(UserInfo.Gender.unknown);
                                    }
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .setTitle(title)
                        .create();

        return alertDialog;
    }

    /**
     * 一个编辑框点击确认事件回调
     */
    public interface OnOneEditDialogConfirmListener {
        void onOneEditDialogConfirm(String etContent);
    }

    /**
     * 选择性别点击确认事件回调
     */
    public interface OnGenderChooseListener {
        void onGenderChoose(UserInfo.Gender gender);
    }
}
