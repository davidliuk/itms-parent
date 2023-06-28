package cn.neud.itms.common.auth;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.fun.SaFunction;
import cn.dev33.satoken.listener.SaTokenEventCenter;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpLogic;

import java.util.List;

public class StpUserUtil {
    public static final String TYPE = "user";
    public static StpLogic stpLogic = new StpLogic(TYPE);

    private StpUserUtil() {
    }

    public static String getLoginType() {
        return stpLogic.getLoginType();
    }

    public static void setStpLogic(StpLogic newStpLogic) {
        stpLogic = newStpLogic;
        SaManager.putStpLogic(newStpLogic);
        SaTokenEventCenter.doSetStpLogic(stpLogic);
    }

    public static StpLogic getStpLogic() {
        return stpLogic;
    }

    public static String getTokenName() {
        return stpLogic.getTokenName();
    }

    public static void setTokenValue(String tokenValue) {
        stpLogic.setTokenValue(tokenValue);
    }

    public static void setTokenValue(String tokenValue, int cookieTimeout) {
        stpLogic.setTokenValue(tokenValue, cookieTimeout);
    }

    public static void setTokenValue(String tokenValue, SaLoginModel loginModel) {
        stpLogic.setTokenValue(tokenValue, loginModel);
    }

    public static String getTokenValue() {
        return stpLogic.getTokenValue();
    }

    public static String getTokenValueNotCut() {
        return stpLogic.getTokenValueNotCut();
    }

    public static SaTokenInfo getTokenInfo() {
        return stpLogic.getTokenInfo();
    }

    public static void login(Object id) {
        stpLogic.login(id);
    }

    public static void login(Object id, String device) {
        stpLogic.login(id, device);
    }

    public static void login(Object id, boolean isLastingCookie) {
        stpLogic.login(id, isLastingCookie);
    }

    public static void login(Object id, long timeout) {
        stpLogic.login(id, timeout);
    }

    public static void login(Object id, SaLoginModel loginModel) {
        stpLogic.login(id, loginModel);
    }

    public static String createLoginSession(Object id) {
        return stpLogic.createLoginSession(id);
    }

    public static String createLoginSession(Object id, SaLoginModel loginModel) {
        return stpLogic.createLoginSession(id, loginModel);
    }

    public static void logout() {
        stpLogic.logout();
    }

    public static void logout(Object loginId) {
        stpLogic.logout(loginId);
    }

    public static void logout(Object loginId, String device) {
        stpLogic.logout(loginId, device);
    }

    public static void logoutByTokenValue(String tokenValue) {
        stpLogic.logoutByTokenValue(tokenValue);
    }

    public static void kickout(Object loginId) {
        stpLogic.kickout(loginId);
    }

    public static void kickout(Object loginId, String device) {
        stpLogic.kickout(loginId, device);
    }

    public static void kickoutByTokenValue(String tokenValue) {
        stpLogic.kickoutByTokenValue(tokenValue);
    }

    public static void replaced(Object loginId, String device) {
        stpLogic.replaced(loginId, device);
    }

    public static boolean isLogin() {
        return stpLogic.isLogin();
    }

    public static void checkLogin() {
        stpLogic.checkLogin();
    }

    public static Object getLoginId() {
        return stpLogic.getLoginId();
    }

    public static <T> T getLoginId(T defaultValue) {
        return stpLogic.getLoginId(defaultValue);
    }

    public static Object getLoginIdDefaultNull() {
        return stpLogic.getLoginIdDefaultNull();
    }

    public static String getLoginIdAsString() {
        return stpLogic.getLoginIdAsString();
    }

    public static int getLoginIdAsInt() {
        return stpLogic.getLoginIdAsInt();
    }

    public static long getLoginIdAsLong() {
        return stpLogic.getLoginIdAsLong();
    }

    public static Object getLoginIdByToken(String tokenValue) {
        return stpLogic.getLoginIdByToken(tokenValue);
    }

    public static Object getExtra(String key) {
        return stpLogic.getExtra(key);
    }

    public static Object getExtra(String tokenValue, String key) {
        return stpLogic.getExtra(tokenValue, key);
    }

    public static SaSession getSessionByLoginId(Object loginId, boolean isCreate) {
        return stpLogic.getSessionByLoginId(loginId, isCreate);
    }

    public static SaSession getSessionBySessionId(String sessionId) {
        return stpLogic.getSessionBySessionId(sessionId);
    }

    public static SaSession getSessionByLoginId(Object loginId) {
        return stpLogic.getSessionByLoginId(loginId);
    }

    public static SaSession getSession(boolean isCreate) {
        return stpLogic.getSession(isCreate);
    }

    public static SaSession getSession() {
        return stpLogic.getSession();
    }

    public static SaSession getTokenSessionByToken(String tokenValue) {
        return stpLogic.getTokenSessionByToken(tokenValue);
    }

    public static SaSession getTokenSession() {
        return stpLogic.getTokenSession();
    }

    public static SaSession getAnonTokenSession() {
        return stpLogic.getAnonTokenSession();
    }

    public static void checkActivityTimeout() {
        stpLogic.checkActivityTimeout();
    }

    public static void updateLastActivityToNow() {
        stpLogic.updateLastActivityToNow();
    }

    public static long getTokenTimeout() {
        return stpLogic.getTokenTimeout();
    }

    public static long getSessionTimeout() {
        return stpLogic.getSessionTimeout();
    }

    public static long getTokenSessionTimeout() {
        return stpLogic.getTokenSessionTimeout();
    }

    public static long getTokenActivityTimeout() {
        return stpLogic.getTokenActivityTimeout();
    }

    public static void renewTimeout(long timeout) {
        stpLogic.renewTimeout(timeout);
    }

    public static void renewTimeout(String tokenValue, long timeout) {
        stpLogic.renewTimeout(tokenValue, timeout);
    }

    public static List<String> getRoleList() {
        return stpLogic.getRoleList();
    }

    public static List<String> getRoleList(Object loginId) {
        return stpLogic.getRoleList(loginId);
    }

    public static boolean hasRole(String role) {
        return stpLogic.hasRole(role);
    }

    public static boolean hasRole(Object loginId, String role) {
        return stpLogic.hasRole(loginId, role);
    }

    public static boolean hasRoleAnd(String... roleArray) {
        return stpLogic.hasRoleAnd(roleArray);
    }

    public static boolean hasRoleOr(String... roleArray) {
        return stpLogic.hasRoleOr(roleArray);
    }

    public static void checkRole(String role) {
        stpLogic.checkRole(role);
    }

    public static void checkRoleAnd(String... roleArray) {
        stpLogic.checkRoleAnd(roleArray);
    }

    public static void checkRoleOr(String... roleArray) {
        stpLogic.checkRoleOr(roleArray);
    }

    public static List<String> getPermissionList() {
        return stpLogic.getPermissionList();
    }

    public static List<String> getPermissionList(Object loginId) {
        return stpLogic.getPermissionList(loginId);
    }

    public static boolean hasPermission(String permission) {
        return stpLogic.hasPermission(permission);
    }

    public static boolean hasPermission(Object loginId, String permission) {
        return stpLogic.hasPermission(loginId, permission);
    }

    public static boolean hasPermissionAnd(String... permissionArray) {
        return stpLogic.hasPermissionAnd(permissionArray);
    }

    public static boolean hasPermissionOr(String... permissionArray) {
        return stpLogic.hasPermissionOr(permissionArray);
    }

    public static void checkPermission(String permission) {
        stpLogic.checkPermission(permission);
    }

    public static void checkPermissionAnd(String... permissionArray) {
        stpLogic.checkPermissionAnd(permissionArray);
    }

    public static void checkPermissionOr(String... permissionArray) {
        stpLogic.checkPermissionOr(permissionArray);
    }

    public static String getTokenValueByLoginId(Object loginId) {
        return stpLogic.getTokenValueByLoginId(loginId);
    }

    public static String getTokenValueByLoginId(Object loginId, String device) {
        return stpLogic.getTokenValueByLoginId(loginId, device);
    }

    public static List<String> getTokenValueListByLoginId(Object loginId) {
        return stpLogic.getTokenValueListByLoginId(loginId);
    }

    public static List<String> getTokenValueListByLoginId(Object loginId, String device) {
        return stpLogic.getTokenValueListByLoginId(loginId, device);
    }

    public static String getLoginDevice() {
        return stpLogic.getLoginDevice();
    }

    public static List<String> searchTokenValue(String keyword, int start, int size, boolean sortType) {
        return stpLogic.searchTokenValue(keyword, start, size, sortType);
    }

    public static List<String> searchSessionId(String keyword, int start, int size, boolean sortType) {
        return stpLogic.searchSessionId(keyword, start, size, sortType);
    }

    public static List<String> searchTokenSessionId(String keyword, int start, int size, boolean sortType) {
        return stpLogic.searchTokenSessionId(keyword, start, size, sortType);
    }

    public static void disable(Object loginId, long time) {
        stpLogic.disable(loginId, time);
    }

    public static boolean isDisable(Object loginId) {
        return stpLogic.isDisable(loginId);
    }

    public static void checkDisable(Object loginId) {
        stpLogic.checkDisable(loginId);
    }

    public static long getDisableTime(Object loginId) {
        return stpLogic.getDisableTime(loginId);
    }

    public static void untieDisable(Object loginId) {
        stpLogic.untieDisable(loginId);
    }

    public static void disable(Object loginId, String service, long time) {
        stpLogic.disable(loginId, service, time);
    }

    public static boolean isDisable(Object loginId, String service) {
        return stpLogic.isDisable(loginId, service);
    }

    public static void checkDisable(Object loginId, String... services) {
        stpLogic.checkDisable(loginId, services);
    }

    public static long getDisableTime(Object loginId, String service) {
        return stpLogic.getDisableTime(loginId, service);
    }

    public static void untieDisable(Object loginId, String... services) {
        stpLogic.untieDisable(loginId, services);
    }

    public static void disableLevel(Object loginId, int level, long time) {
        stpLogic.disableLevel(loginId, level, time);
    }

    public static void disableLevel(Object loginId, String service, int level, long time) {
        stpLogic.disableLevel(loginId, service, level, time);
    }

    public static boolean isDisableLevel(Object loginId, int level) {
        return stpLogic.isDisableLevel(loginId, level);
    }

    public static boolean isDisableLevel(Object loginId, String service, int level) {
        return stpLogic.isDisableLevel(loginId, service, level);
    }

    public static void checkDisableLevel(Object loginId, int level) {
        stpLogic.checkDisableLevel(loginId, level);
    }

    public static void checkDisableLevel(Object loginId, String service, int level) {
        stpLogic.checkDisableLevel(loginId, service, level);
    }

    public static int getDisableLevel(Object loginId) {
        return stpLogic.getDisableLevel(loginId);
    }

    public static int getDisableLevel(Object loginId, String service) {
        return stpLogic.getDisableLevel(loginId, service);
    }

    public static void switchTo(Object loginId) {
        stpLogic.switchTo(loginId);
    }

    public static void endSwitch() {
        stpLogic.endSwitch();
    }

    public static boolean isSwitch() {
        return stpLogic.isSwitch();
    }

    public static void switchTo(Object loginId, SaFunction function) {
        stpLogic.switchTo(loginId, function);
    }

    public static void openSafe(long safeTime) {
        stpLogic.openSafe(safeTime);
    }

    public static void openSafe(String service, long safeTime) {
        stpLogic.openSafe(service, safeTime);
    }

    public static boolean isSafe() {
        return stpLogic.isSafe();
    }

    public static boolean isSafe(String service) {
        return stpLogic.isSafe(service);
    }

    public static boolean isSafe(String tokenValue, String service) {
        return stpLogic.isSafe(tokenValue, service);
    }

    public static void checkSafe() {
        stpLogic.checkSafe();
    }

    public static void checkSafe(String service) {
        stpLogic.checkSafe(service);
    }

    public static long getSafeTime() {
        return stpLogic.getSafeTime();
    }

    public static long getSafeTime(String service) {
        return stpLogic.getSafeTime(service);
    }

    public static void closeSafe() {
        stpLogic.closeSafe();
    }

    public static void closeSafe(String service) {
        stpLogic.closeSafe(service);
    }
}
