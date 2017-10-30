package com.weapon.joker.app.mine.person;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.weapon.joker.app.mine.BR;
import com.weapon.joker.app.mine.R;
import com.weapon.joker.app.mine.databinding.FragmentPersonCenterBinding;
import com.weapon.joker.lib.middleware.utils.ImageUtil;
import com.weapon.joker.lib.mvvm.common.BaseFragment;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;

import static android.app.Activity.RESULT_OK;
import static com.weapon.joker.app.mine.person.PersonCenterViewModel.RESULT_ALBUM;
import static com.weapon.joker.app.mine.person.PersonCenterViewModel.RESULT_PHOTO;
import static com.weapon.joker.app.mine.person.PersonCenterViewModel.RESULT_ZOOM;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.mine.person.PersonCenterFragment
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/13
 *     desc   :
 * </pre>
 */

public class PersonCenterFragment extends BaseFragment<PersonCenterViewModel, PersonCenterModel> implements PersonCenterContact.View {

    private FragmentPersonCenterBinding mDataBinding;

    private static final String EXTRA_USER_NAME = "user_name";

    @Override
    public int getLayoutId() {
        return R.layout.fragment_person_center;
    }

    @Override
    public void initView() {
        mDataBinding = ((FragmentPersonCenterBinding) getViewDataBinding());
        setToolbar();
        getViewModel().getUserInfo();
    }

    /**
     * Toolbar 相关设置
     */
    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mDataBinding.toolbar);
        // 设置 toolbar 具有返回按钮
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDataBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mDataBinding.collapsingToolbar.setTitle(JMessageClient.getMyInfo().getDisplayName());
    }

    @Override
    public int getBR() {
        return BR.personModel;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PHOTO:
                    File temp = new File(PersonCenterViewModel.IMAGE_FILE_NAME);
                    getViewModel().startPhotoZoom(Uri.fromFile(temp));
                    break;
                case RESULT_ALBUM:
                    File temp1 = new File(ImageUtil.getPathFromKitKatAlbum(getActivity(), data.getData()));
                    getViewModel().startPhotoZoom(Uri.fromFile(temp1));
                    break;
                case RESULT_ZOOM:
                    if (data != null) {
                        getViewModel().uploadAvatar(data);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
