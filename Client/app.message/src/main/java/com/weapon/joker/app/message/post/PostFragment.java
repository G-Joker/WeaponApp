package com.weapon.joker.app.message.post;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.weapon.joker.app.message.BR;
import com.weapon.joker.app.message.R;
import com.weapon.joker.app.message.databinding.FragmentPostBinding;
import com.weapon.joker.lib.mvvm.common.BaseFragment;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.post.PostFragment
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/17
 *     desc   : 公告 fragment
 * </pre>
 */

public class PostFragment extends BaseFragment<PostViewModel, PostModel> implements PostContact.View{

    private FragmentPostBinding mDataBinding;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_post;
    }

    @Override
    public void initView() {
        mDataBinding = ((FragmentPostBinding) getViewDataBinding());
        setToolbar();
        getViewModel().getPostData(false);
    }

    /**
     * Toolbar 相关设置
     */
    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mDataBinding.toolbar);
        // 设置 toolbar 具有返回按钮
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        mDataBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    @Override
    public int getBR() {
        return BR.postModel;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_post_clear, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(mContext, "清除公告", Toast.LENGTH_SHORT).show();
        return true;
    }

    /**
     * 更新刷新状态
     * @param state
     */
    @Override
    public void refreshFinish(int state) {
        mDataBinding.pullRefreshLayout.refreshFinish(state);
    }
}
