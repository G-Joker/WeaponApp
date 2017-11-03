package com.weapon.joker.app.mine.person;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.weapon.joker.app.mine.BR;
import com.weapon.joker.app.mine.R;
import com.weapon.joker.lib.middleware.utils.AlertDialogFactory;
import com.weapon.joker.lib.middleware.utils.ImageUtil;
import com.weapon.joker.lib.middleware.utils.LogUtils;
import com.weapon.joker.lib.middleware.utils.PreferencesUtils;
import com.weapon.joker.lib.middleware.utils.Util;
import com.weapon.joker.lib.net.JMessageCallBack;

import java.io.File;
import java.util.Calendar;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.mine.person.PersonCenterViewModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/13
 *     desc   : 个人中心 ViewModel
 * </pre>
 */

public class PersonCenterViewModel extends PersonCenterContact.ViewModel {

    /**
     * 图片存储路径
     */
    public static final String IMAGE_PATH = Environment.getExternalStorageDirectory().getPath() + "/WeaponApp/image_cache";
    /**
     * 图片文件完整名称
     */
    public static final String IMAGE_FILE_NAME = IMAGE_PATH + "/avatar.jpg";
    /**
     * 图片存储路径 Uri
     */
    private static final String IMAGE_PATH_URI = "file://" + IMAGE_FILE_NAME;

    /**
     * 拍摄
     */
    public static final int RESULT_PHOTO = 999;
    /**
     * 本地相册
     */
    public static final int RESULT_ALBUM = 1001;
    /**
     * 照片裁剪
     */
    public static final int RESULT_ZOOM = 1002;

    @Bindable
    public String userName;
    /**
     * 个性签名
     */
    @Bindable
    public String signature;

    @Bindable
    public Bitmap bitmap;
    @Bindable
    public File avatarFile;
    /**
     * 生日
     */
    @Bindable
    public String birthday;
    /**
     * 性别
     */
    @Bindable
    public String gender;

    /**
     * 当前用户信息
     */
    private UserInfo mUserInfo;
    /**
     * 正在加载对话框
     */
    private AlertDialog loadingDialog;
    /**
     * 更改头像 dialog
     */
    private BottomSheetDialog mChangeAvatarDialog;

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    public void setSignature(String signature) {
        this.signature = signature;
        notifyPropertyChanged(BR.signature);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        notifyPropertyChanged(BR.bitmap);
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
        notifyPropertyChanged(BR.birthday);
    }

    public void setGender(String gender) {
        this.gender = gender;
        notifyPropertyChanged(BR.gender);
    }

    public void setAvatarFile(File avatarFile) {
        this.avatarFile = avatarFile;
        notifyPropertyChanged(BR.avatarFile);
    }

    @Override
    public void attachView(PersonCenterContact.View view) {
        super.attachView(view);
        loadingDialog = AlertDialogFactory.createLoadingDialog(getContext(), "正在更新...");
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        mUserInfo = JMessageClient.getMyInfo();
        getUserInfoSuccess(mUserInfo);
    }

    /**
     * 成功获取用户信息
     *
     * @param userInfo 用户信息
     */
    private void getUserInfoSuccess(UserInfo userInfo) {
        // 设置显示名称
        setUserName(userInfo.getDisplayName());
        // 设置个性签名
        setSignature(TextUtils.isEmpty(userInfo.getSignature()) ? "暂无个性签名" : userInfo.getSignature());
        // 设置生日
        setBirthday(Util.getStrTime(userInfo.getBirthday(), "yyyy-MM-dd"));
        // 设置性别
        setGender(getGender(userInfo.getGender()));
        // 设置头像
        setAvatarFile(userInfo.getAvatarFile());
    }


    /*===============================按钮点击事件的处理========================================*/

    /**
     * 注销登录点击处理
     *
     * @param view
     */
    public void onLogoutClick(View view) {
        new AlertDialog.Builder(getContext()).setTitle("提示")
                                             .setMessage("确定退出登录？")
                                             .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                 @Override
                                                 public void onClick(DialogInterface dialog, int which) {
                                                     logout();
                                                 }
                                             })
                                             .setNegativeButton("取消", null)
                                             .show();
    }

    /**
     * 更新昵称
     *
     * @param view
     */
    public void onUpdateUserNameClick(View view) {
        AlertDialogFactory.createOneEditDialog(getContext(), "更新昵称", userName,  new AlertDialogFactory.OnOneEditDialogConfirmListener() {
            @Override
            public void onOneEditDialogConfirm(final String etContent) {
                if (!loadingDialog.isShowing()) {
                    loadingDialog.show();
                }
                mUserInfo.setNickname(etContent);
                JMessageClient.updateMyInfo(UserInfo.Field.nickname, mUserInfo, new JMessageCallBack() {
                    @Override
                    public void onSuccess() {
                        setUserName(etContent);
                        dismissDialog();
                        Toast.makeText(getContext(), "更新昵称成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed(int status, String desc) {
                        Toast.makeText(getContext(), desc, Toast.LENGTH_SHORT).show();
                        dismissDialog();
                    }
                });
            }
        }).show();
    }

    /**
     * 更新个性签名
     *
     * @param view
     */
    public void onUpdateSignatureClick(View view) {
        AlertDialogFactory.createOneEditDialog(getContext(), "更新个性签名", signature, new AlertDialogFactory.OnOneEditDialogConfirmListener
                () {
            @Override
            public void onOneEditDialogConfirm(final String etContent) {
                if (!loadingDialog.isShowing()) {
                    loadingDialog.show();
                }
                mUserInfo.setSignature(etContent);
                JMessageClient.updateMyInfo(UserInfo.Field.signature, mUserInfo, new JMessageCallBack() {
                    @Override
                    public void onSuccess() {
                        setSignature(etContent);
                        dismissDialog();
                        Toast.makeText(getContext(), "更新个性签名成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed(int status, String desc) {
                        Toast.makeText(getContext(), desc, Toast.LENGTH_SHORT).show();
                        dismissDialog();
                    }
                });
            }
        }).show();
    }

    /**
     * 点击更新生日事件处理
     * @param view
     */
    public void onUpdateBirthDayClick(View view) {
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(getContext(),
                                     new DatePickerDialog.OnDateSetListener() {
                                         @Override
                                         public void onDateSet(
                                                 DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                             // 获取DatePicker设置的数据
                                             StringBuilder sb = new StringBuilder();
                                             sb.append(year).append("-").append(monthOfYear + 1).append("-").append(dayOfMonth);
                                             long time = Util.getTime(sb.toString(), "yyyy-MM-dd");
                                             long curTime = System.currentTimeMillis();
                                             // 设置日期不能超过当前日期
                                             if (time < curTime) {
                                                 mUserInfo.setBirthday(time);
                                                 updateBirthDay(sb.toString());
                                             } else {
                                                 Toast.makeText(getContext(), "日期不能大于当前日期", Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     },
                                     Calendar.getInstance().get(Calendar.YEAR),
                                     Calendar.getInstance().get(Calendar.MONTH),
                                     Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    /**
     * 更新服务器中的生日
     */
    private void updateBirthDay(final String time) {
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        JMessageClient.updateMyInfo(UserInfo.Field.birthday, mUserInfo, new JMessageCallBack() {
            @Override
            public void onSuccess() {
                setBirthday(time);
                dismissDialog();
                Toast.makeText(getContext(), "更新生日成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(int status, String desc) {
                Toast.makeText(getContext(), desc, Toast.LENGTH_SHORT).show();
                dismissDialog();
            }
        });
    }

    /**
     * 更换头像点击事件处理
     *
     * @param view
     */
    public void onChangeAvatarClick(View view) {
        mChangeAvatarDialog = new BottomSheetDialog(getContext());
        View changeAvatarView = View.inflate(getContext(), R.layout.dialog_mine_change_avatar, null);
        mChangeAvatarDialog.setContentView(changeAvatarView);
        mChangeAvatarDialog.show();
        TextView tvPhoto = changeAvatarView.findViewById(R.id.tv_mine_photo);
        // 打开手机拍摄
        tvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAvatarDialog();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File dir = new File(IMAGE_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                // 指定调用相机拍照后照片的储存路径
                Uri imageUri = Uri.parse(IMAGE_PATH_URI);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                ((AppCompatActivity) getContext()).startActivityForResult(intent, RESULT_PHOTO);
            }
        });
        // 调用本地相册
        TextView tvAlbum = changeAvatarView.findViewById(R.id.tv_mine_album);
        tvAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAvatarDialog();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                ((AppCompatActivity) getContext()).startActivityForResult(intent, RESULT_ALBUM);
            }
        });
    }

    /**
     * 更新性别点击事件的处理
     * @param view
     */
    public void onUpdateGenderClick(View view) {
        AlertDialogFactory.createThreeRadioDialog(getContext(), "更新性别", new AlertDialogFactory.OnGenderChooseListener() {
            @Override
            public void onGenderChoose(final UserInfo.Gender gender) {
                if (!loadingDialog.isShowing()) {
                    loadingDialog.show();
                }
                mUserInfo.setGender(gender);
                JMessageClient.updateMyInfo(UserInfo.Field.gender, mUserInfo, new JMessageCallBack() {
                    @Override
                    public void onSuccess() {
                        dismissDialog();
                        setGender(getGender(gender));
                        Toast.makeText(getContext(), "更新性别成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed(int status, String desc) {
                        Toast.makeText(getContext(), desc, Toast.LENGTH_SHORT).show();
                        dismissDialog();
                    }
                });
            }
        }).show();
    }

    /**
     * 根据 {@link cn.jpush.im.android.api.model.UserInfo.Gender} 获取字符串 性别
     * @param gender
     * @return
     */
    private String getGender(UserInfo.Gender gender) {
        if (UserInfo.Gender.female == gender) {
            return "女";
        } else if (UserInfo.Gender.male == gender) {
            return "男";
        } else {
            return "未知";
        }
    }

    private void dismissAvatarDialog() {
        if (mChangeAvatarDialog != null && mChangeAvatarDialog.isShowing()) {
            mChangeAvatarDialog.dismiss();
        }
    }

    /**
     * 上传头像
     */
    public void uploadAvatar(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
            }
            final Bitmap bmp = extras.getParcelable("data");
            final File file = ImageUtil.bitmapToFile(getContext(), bmp, mUserInfo.getUserName());
            LogUtils.i("avatar", "upload:" + file.getName());
            JMessageClient.updateUserAvatar(file, new JMessageCallBack() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getContext(), "更新头像成功", Toast.LENGTH_SHORT).show();
                    setAvatarFile(file);
                    dismissDialog();
                }

                @Override
                public void onFailed(int status, String desc) {
                    Toast.makeText(getContext(), "更新头像失败：" + desc, Toast.LENGTH_SHORT).show();
                    dismissDialog();
                }
            });
        }
    }

    /**
     * 对图片进行裁剪
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop", "true");

            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);

            // outputX,outputY 是剪裁图片的宽高
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            intent.putExtra("return-data", true);
            intent.putExtra("noFaceDetection", true);
            ((AppCompatActivity) getContext()).startActivityForResult(intent, RESULT_ZOOM);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出登录相关操作
     */
    private void logout() {
        // 清楚本地的用户数据
        PreferencesUtils.clear(getContext());
        // JMessage 的登出
        JMessageClient.logout();
        // JPush alias 置为空
        JPushInterface.setAlias(getContext(), 2, "");
//        getView().finish();
        Intent intent  = new Intent();
        intent.setClassName(getContext(), "com.weapon.joker.app.main.MainActivity");
        getContext().startActivity(intent);
    }

    private void dismissDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

}
