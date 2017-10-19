package com.weapon.joker.lib.middleware.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.weapon.joker.lib.middleware.R;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.middleware.utils.AlerDialogFactory
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/19
 *     desc   :
 * </pre>
 */

public class AlerDialogFactory {

    private AlerDialogFactory() {
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
        return new AlertDialog.Builder(context).setView(new ProgressBar(context)).setTitle(desc).create();
    }


    /**
     * 创建一个 EditText 的对话框
     * 为了当输入内容为空点击确认键不关闭对话框，所以重写了 onClickListener
     *
     * @param context
     * @param title    对话框标题
     * @param listener 点击确认回调
     * @return 对话框
     */
    public static AlertDialog createOneEditDialog(final Context context, String title, final OnOneEditDialogConfirmListener listener) {
        View view = View.inflate(context, R.layout.dialog_one_edit, null);
        final EditText etCotent = view.findViewById(R.id.et_content);
        final AlertDialog alertDialog = new AlertDialog.Builder(context).setView(view)
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
                        if (TextUtils.isEmpty(etCotent.getText().toString())) {
                            Toast.makeText(context, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                        } else {
                            listener.onOneEditDialogConfirm(etCotent.getText().toString());
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
     * 一个编辑框点击确认事件回调
     */
    public interface OnOneEditDialogConfirmListener {
        void onOneEditDialogConfirm(String etContent);
    }
}
