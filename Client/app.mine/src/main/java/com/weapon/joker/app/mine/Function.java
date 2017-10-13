package com.weapon.joker.app.mine;

import com.weapon.joker.app.mine.login.LoginActivity;
import com.weapon.joker.app.mine.login.RegisterActivity;

/**
 * class：   WeaponApp
 * author：  xiaweizi
 * date：    2017/9/17 10:39
 * e-mail:   1012126908@qq.com
 * desc:
 */
interface Function {

    /**
     * {@link MineActivity 我的界面}
     * {@link LoginActivity 登录界面}
     * {@link RegisterActivity 注册界面}
     */
    void activity();

    /**
     * {@link MainFragment 我的 Fragment}
     */
    void fragment();

    void View();
}
