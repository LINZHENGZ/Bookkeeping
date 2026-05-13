package cn.roidlin.bookkeepingbook.ui.common;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

/**
 * 统一的屏幕适配工具类。
 *
 * 设计约定：
 * 1. XML 中优先继续使用 dp/sp，保持 Android 原生适配能力。
 * 2. 代码里动态设置尺寸、边距、字体、Dialog 宽度时，统一走这个工具类。
 * 3. 自定义 View 中如果需要根据设计稿设置尺寸，也直接调用这里的方法。
 */
public final class ScreenAdapter {
    private static final float DESIGN_WIDTH_DP = 360f;
    private static final float DESIGN_HEIGHT_DP = 800f;

    private ScreenAdapter() {
    }

    public static int dp(@NonNull Context context, float valueDp) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                valueDp,
                getDisplayMetrics(context)
        ));
    }

    public static int sp(@NonNull Context context, float valueSp) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                valueSp,
                getDisplayMetrics(context)
        ));
    }

    public static float getWidthScale(@NonNull Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        float screenWidthDp = metrics.widthPixels / metrics.density;
        return screenWidthDp / DESIGN_WIDTH_DP;
    }

    public static float getHeightScale(@NonNull Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        float screenHeightDp = metrics.heightPixels / metrics.density;
        return screenHeightDp / DESIGN_HEIGHT_DP;
    }

    public static float getMinScale(@NonNull Context context) {
        return Math.min(getWidthScale(context), getHeightScale(context));
    }

    public static int scaleWidth(@NonNull Context context, float designDp) {
        return dp(context, designDp * getWidthScale(context));
    }

    public static int scaleHeight(@NonNull Context context, float designDp) {
        return dp(context, designDp * getHeightScale(context));
    }

    public static int scaleText(@NonNull Context context, float designSp) {
        return sp(context, designSp * getMinScale(context));
    }

    public static void applyViewHeight(@NonNull View view, float designDp) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            return;
        }
        layoutParams.height = scaleHeight(view.getContext(), designDp);
        view.setLayoutParams(layoutParams);
    }

    public static void applyViewSize(@NonNull View view, float widthDp, float heightDp) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            return;
        }
        layoutParams.width = scaleWidth(view.getContext(), widthDp);
        layoutParams.height = scaleHeight(view.getContext(), heightDp);
        view.setLayoutParams(layoutParams);
    }

    public static void applyPadding(@NonNull View view, float leftDp, float topDp,
                                    float rightDp, float bottomDp) {
        Context context = view.getContext();
        view.setPadding(
                scaleWidth(context, leftDp),
                scaleHeight(context, topDp),
                scaleWidth(context, rightDp),
                scaleHeight(context, bottomDp)
        );
    }

    public static void applyMargin(@NonNull View view, float leftDp, float topDp,
                                   float rightDp, float bottomDp) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            return;
        }

        Context context = view.getContext();
        ViewGroup.MarginLayoutParams marginLayoutParams =
                (ViewGroup.MarginLayoutParams) layoutParams;
        marginLayoutParams.setMargins(
                scaleWidth(context, leftDp),
                scaleHeight(context, topDp),
                scaleWidth(context, rightDp),
                scaleHeight(context, bottomDp)
        );
        view.setLayoutParams(marginLayoutParams);
    }

    public static void applyTextSize(@NonNull TextView textView, float designSp) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaleText(textView.getContext(), designSp));
    }

    public static void applyDialogWidth(@NonNull Dialog dialog, float widthPercent,
                                        float maxWidthDp, int gravity) {
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        Context context = dialog.getContext();
        DisplayMetrics metrics = getDisplayMetrics(context);
        int screenWidth = metrics.widthPixels;
        int targetWidth = Math.round(screenWidth * clampPercent(widthPercent));

        if (maxWidthDp > 0) {
            targetWidth = Math.min(targetWidth, dp(context, maxWidthDp));
        }

        WindowManager.LayoutParams params = window.getAttributes();
        params.width = targetWidth;
        params.gravity = gravity;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(params);
    }

    public static void applyBottomDialog(@NonNull Dialog dialog, float widthPercent, float maxWidthDp) {
        applyDialogWidth(dialog, widthPercent, maxWidthDp, Gravity.BOTTOM);
    }

    public static void applyTopDialog(@NonNull Dialog dialog, float widthPercent, float maxWidthDp) {
        applyDialogWidth(dialog, widthPercent, maxWidthDp, Gravity.TOP);
    }

    private static float clampPercent(float percent) {
        if (percent <= 0f) {
            return 1f;
        }
        return Math.min(percent, 1f);
    }

    private static DisplayMetrics getDisplayMetrics(@NonNull Context context) {
        return context.getResources().getDisplayMetrics();
    }
}
