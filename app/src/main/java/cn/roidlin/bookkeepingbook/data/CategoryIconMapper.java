package cn.roidlin.bookkeepingbook.data;

import cn.roidlin.bookkeepingbook.R;

/**
 * 统一根据分类名称和收支类型解析图标资源。
 *
 * 不能把 R.mipmap 的整数值当成稳定数据长期持久化到数据库里，
 * 因为资源重新编译后这些整数值可能变化。这里统一在运行时重新映射，
 * 这样旧数据库里的账单数据也能继续安全显示。
 */
public final class CategoryIconMapper {
    private CategoryIconMapper() {
    }

    public static int getNormalIcon(String typeName, int kind) {
        if (kind == 1) {
            return getIncomeNormalIcon(typeName);
        }
        return getOutcomeNormalIcon(typeName);
    }

    public static int getSelectedIcon(String typeName, int kind) {
        if (kind == 1) {
            return getIncomeSelectedIcon(typeName);
        }
        return getOutcomeSelectedIcon(typeName);
    }

    private static int getOutcomeNormalIcon(String typeName) {
        if ("餐饮".equals(typeName)) return R.mipmap.canyin2;
        if ("交通".equals(typeName)) return R.mipmap.gongjiao2;
        if ("购物".equals(typeName)) return R.mipmap.gouwu2;
        if ("服饰".equals(typeName)) return R.mipmap.fushi2;
        if ("日用品".equals(typeName)) return R.mipmap.riyongpin2;
        if ("娱乐".equals(typeName)) return R.mipmap.yule2;
        if ("零食".equals(typeName)) return R.mipmap.lingshi2;
        if ("烟酒茶".equals(typeName)) return R.mipmap.yanjiu2;
        if ("学习".equals(typeName)) return R.mipmap.xuexi2;
        if ("医疗".equals(typeName)) return R.mipmap.yiliao2;
        if ("住宅".equals(typeName)) return R.mipmap.zhuzhai2;
        if ("水电煤".equals(typeName)) return R.mipmap.shuidian2;
        if ("通讯".equals(typeName)) return R.mipmap.tongxun2;
        if ("人情往来".equals(typeName)) return R.mipmap.renqing2;
        return R.mipmap.qita2;
    }

    private static int getOutcomeSelectedIcon(String typeName) {
        if ("餐饮".equals(typeName)) return R.mipmap.canyin;
        if ("交通".equals(typeName)) return R.mipmap.gongjiao;
        if ("购物".equals(typeName)) return R.mipmap.gouwu;
        if ("服饰".equals(typeName)) return R.mipmap.fushi;
        if ("日用品".equals(typeName)) return R.mipmap.riyongpin;
        if ("娱乐".equals(typeName)) return R.mipmap.yule;
        if ("零食".equals(typeName)) return R.mipmap.lingshi;
        if ("烟酒茶".equals(typeName)) return R.mipmap.yanjiu;
        if ("学习".equals(typeName)) return R.mipmap.xuexi;
        if ("医疗".equals(typeName)) return R.mipmap.yiliao;
        if ("住宅".equals(typeName)) return R.mipmap.zhuzhai;
        if ("水电煤".equals(typeName)) return R.mipmap.shuidian;
        if ("通讯".equals(typeName)) return R.mipmap.tongxun;
        if ("人情往来".equals(typeName)) return R.mipmap.renqing;
        return R.mipmap.qita;
    }

    private static int getIncomeNormalIcon(String typeName) {
        if ("薪资".equals(typeName)) return R.mipmap.xinzi2;
        if ("借入".equals(typeName)) return R.mipmap.jieru2;
        if ("收款".equals(typeName)) return R.mipmap.qiandai2;
        if ("奖金".equals(typeName)) return R.mipmap.jiangjin2;
        return R.mipmap.qita2;
    }

    private static int getIncomeSelectedIcon(String typeName) {
        if ("薪资".equals(typeName)) return R.mipmap.xinzi;
        if ("借入".equals(typeName)) return R.mipmap.jieru;
        if ("收款".equals(typeName)) return R.mipmap.qiandai;
        if ("奖金".equals(typeName)) return R.mipmap.jiangjin;
        return R.mipmap.qita3;
    }
}
