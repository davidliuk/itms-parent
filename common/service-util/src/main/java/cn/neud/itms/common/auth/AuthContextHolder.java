package cn.neud.itms.common.auth;

import cn.neud.itms.vo.user.UserVo;

// ThreadLocal工具类
public class AuthContextHolder {

    //用户id
    private static ThreadLocal<Long> userId = new ThreadLocal<>();

    //用户仓库id
    private static ThreadLocal<Long> wareId = new ThreadLocal<>();

    //用户信息对象
    private static ThreadLocal<UserVo> userLoginVo = new ThreadLocal<>();

    //userId操作的方法
    public static void setUserId(Long _userId) {
        userId.set(_userId);
    }

    public static Long getUserId() {
        return userId.get();
    }

    public static Long getWareId() {
        return wareId.get();
    }

    public static void setWareId(Long _wareId) {
        wareId.set(_wareId);
    }

    public static UserVo getUserLoginVo() {
        return userLoginVo.get();
    }

    public static void setUserLoginVo(UserVo _userVo) {
        userLoginVo.set(_userVo);
    }

}
