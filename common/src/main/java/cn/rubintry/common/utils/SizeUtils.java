package cn.rubintry.common.utils;

import android.content.res.Resources;

/**
 * @author logcat
 */
public class SizeUtils {

    public static int dp2Px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
