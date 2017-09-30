package com.weapon.joker.lib.middleware.utils.debug;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.SensorManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.weapon.joker.lib.middleware.BuildConfig;
import com.weapon.joker.lib.middleware.utils.Util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * author : yueyang
 * date : 25/09/2017
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class ShakeManager implements ShakeDetector.ShakeListener {
    private static final String TAG = "ShakeManager";

    private ShakeDetector mShakeDetector;
    private final IGetActivity mGetActivity;

    private boolean mShowing = false;
    private long mLastShakeTime = -1;
    private static final long SHAKE_DELAY = 1000;

    private static class InstanceHolder {
        private static final ShakeManager sInstance = new ShakeManager();
    }

    public static ShakeManager getInstance() {
        return InstanceHolder.sInstance;
    }

    public ShakeManager() {
        this(new IGetActivity() {
            @Override
            public Activity getActivity() {
                return Util.getTopActivity();
            }
        });
    }

    public ShakeManager(IGetActivity getActivity) {
        if (getActivity == null) {
            throw new NullPointerException("IGetActivity can not be null");
        }
        mGetActivity = getActivity;
    }

    public interface IGetActivity {
        Activity getActivity();
    }

    public void unregisterShakeDetector() {
        if (!BuildConfig.DEBUG) {
            return;
        }
        if (mShakeDetector != null) {
            mShakeDetector.stop();
            mShakeDetector = null;
        }
    }

    public void registerShakeDetector(Context context) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        if (context == null) {
            throw new NullPointerException("Context can not be null");
        }
        context = context.getApplicationContext();
        if (mShakeDetector != null) {
            throw new RuntimeException("ShakeDetector has been registered");
        }
        mShakeDetector = new ShakeDetector(this);
        mShakeDetector.start((SensorManager) context.getSystemService(Context.SENSOR_SERVICE));
    }

    @Override
    public void onShake() {
        if (!BuildConfig.DEBUG) {
            return;
        }
        if (mShowing) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (mLastShakeTime != -1 && currentTimeMillis - mLastShakeTime < SHAKE_DELAY) {
            return;
        }
        mLastShakeTime = currentTimeMillis;
        final Activity activity = mGetActivity.getActivity();
        if (activity == null) {
            new Exception("Shake fail, getActivity is null").printStackTrace();
            return;
        }
        final Map<String, Object> map = new LinkedHashMap<>();
        String currentActivity = activity.getClass().getName();
        if (activity instanceof FragmentActivity) {
            @SuppressWarnings("RestrictedApi")
            List<Fragment> fragments = ((FragmentActivity) activity).getSupportFragmentManager().getFragments();
            if (fragments != null) {
                currentActivity += "\n";
                for (Fragment fragment : fragments) {
                    if (fragment == null) {
                        continue;
                    }
                    if (fragment instanceof IRegisterShakeDetector) {
                        ((IRegisterShakeDetector) fragment).registerShakeDetector(map);
                    }
                    currentActivity += "\t\t" + fragment.getClass().getSimpleName() + "\n";
                }
            }
        }
        map.put("当前Activity", currentActivity);
        if (map.size() < 1) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("开发者菜单");
        final String[] array = map.keySet().toArray(new String[map.size()]);
        builder.setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Object obj = map.get(array[which]);
                if (obj instanceof Class) {
                    try {
                        // 检查是否是Activity的子类
                        Class clazz = (Class) obj;
                        if (Activity.class.isAssignableFrom(clazz)) {
                            Intent intent = new Intent(activity, clazz);
                            activity.startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (obj instanceof Intent) {
                    activity.startActivity((Intent) obj);
                }
                if (obj instanceof DialogInterface.OnClickListener) {
                    ((DialogInterface.OnClickListener) obj).onClick(dialog, which);
                }
                if (obj instanceof Runnable) {
                    ((Runnable) obj).run();
                }
                if (obj instanceof String) {
                    new AlertDialog.Builder(activity)
                            .setTitle(array[which])
                            .setMessage(obj + "\n")
                            .show();
                }
                if (obj instanceof String[] && ((String[]) obj).length == 2) {
                    String[] array = (String[]) obj;
                    new AlertDialog.Builder(activity)
                            .setTitle(array[0])
                            .setMessage(array[1] + "\n")
                            .show();
                }
            }
        });
        AlertDialog dialog = builder.create();
        mShowing = true;
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mShowing = false;
            }
        });
        dialog.show();
    }
}