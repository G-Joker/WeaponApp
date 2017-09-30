package com.weapon.joker.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.weapon.joker.app.stub.R;
import com.weapon.joker.app.stub.share.IShareListener;
import com.weapon.joker.app.stub.share.ShareParams;
import com.weapon.joker.app.stub.share.WXShareAction;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : WXEntryActivity
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/09/28
 *     desc   : 微信分享界面
 * </pre>
 */

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private static final String WX_APP_ID = "wxb2d4af31b04e5d5a";
    public static final String SHARE_PARAM = "param";
    public static final String SHARE_LISTENER = "share_listener";

    private ShareParams mParams;
    private WXShareAction mShareAction;
    private IShareListener mShareListener;

    private IWXAPI mApi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = WXAPIFactory.createWXAPI(this, WX_APP_ID);
        setContentView(R.layout.activity_wxshare);
        Intent intent = getIntent();
        mShareAction = new WXShareAction(this, mApi);
        if (intent != null) {
            mParams = (ShareParams) getIntent().getSerializableExtra(SHARE_PARAM);
            mShareListener = (IShareListener) getIntent().getSerializableExtra(SHARE_LISTENER);
        }
        if (mParams != null) {
            mShareAction.doShare(mParams, mShareListener);
            try {
                mApi.handleIntent(getIntent(), this);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("WXEntryActivity", "errorMessage:\t" + e.getMessage());
            }
        } else {
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mApi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.i("xwz--->", "onReq--------");
    }


    @Override
    public void onResp(BaseResp resp) {
        int result = 0;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.errcode_unsupported;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }

        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        this.finish();
    }

}
