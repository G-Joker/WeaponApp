package com.weapon.joker.app.mine;

import com.weapon.joker.lib.mvvm.common.BaseModel;
import com.weapon.joker.lib.mvvm.common.BaseView;
import com.weapon.joker.lib.mvvm.common.BaseViewModel;

/**
 * class：   WeaponApp
 * author：  xiaweizi
 * date：    2017/9/17 10:52
 * e-mail:   1012126908@qq.com
 * desc:     我的管理类
 */
public class MineContact {
    public interface View extends BaseView{

    }

    public abstract class ViewModel extends BaseViewModel<View, Model>{

    }

    public abstract class Model extends BaseModel<ViewModel>{

    }
}
