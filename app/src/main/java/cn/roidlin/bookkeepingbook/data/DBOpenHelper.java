package cn.roidlin.bookkeepingbook.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import cn.roidlin.bookkeepingbook.R;

/**
 * @author LINZHENGZ
 * @version 1.0
 * Create by 20222022/12/19 16:08
 */

public class DBOpenHelper  extends SQLiteOpenHelper {

    public DBOpenHelper(@Nullable Context context) {
        super(context, "kepping.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table typetb(id integer primary key autoincrement,typename varchar(10),imageid integer,sImageId integer,kind integer)";
        db.execSQL(sql);
        insertType(db);

        sql = "create table accounttb(id integer primary key autoincrement,typename varchar(10),sImageId integer,beizhu varchar(80),money float," +
                "time varchar(60),year integer,month integer,day integer,kind integer)";
        db.execSQL(sql);

    }

    private void insertType(SQLiteDatabase db) {
            String sql = "insert into typetb(typename,imageId,sImageId,kind) values(?,?,?,?)";

            // 支出分类
            insertType(db, sql, "其他", R.mipmap.qita2, R.mipmap.qita, 0);
            insertType(db, sql, "餐饮", R.mipmap.canyin2, R.mipmap.canyin, 0);
            insertType(db, sql, "交通", R.mipmap.gongjiao2, R.mipmap.gongjiao, 0);
            insertType(db, sql, "购物", R.mipmap.gouwu2, R.mipmap.gouwu, 0);
            insertType(db, sql, "服饰", R.mipmap.fushi2, R.mipmap.fushi, 0);
            insertType(db, sql, "日用品", R.mipmap.riyongpin2, R.mipmap.riyongpin, 0);
            insertType(db, sql, "娱乐", R.mipmap.yule2, R.mipmap.yule, 0);
            insertType(db, sql, "零食", R.mipmap.lingshi2, R.mipmap.lingshi, 0);
            insertType(db, sql, "烟酒茶", R.mipmap.yanjiu2, R.mipmap.yanjiu, 0);
            insertType(db, sql, "学习", R.mipmap.xuexi2, R.mipmap.xuexi, 0);
            insertType(db, sql, "医疗", R.mipmap.yiliao2, R.mipmap.yiliao, 0);
            insertType(db, sql, "住宅", R.mipmap.zhuzhai2, R.mipmap.zhuzhai, 0);
            insertType(db, sql, "水电煤", R.mipmap.shuidian2, R.mipmap.shuidian, 0);
            insertType(db, sql, "通讯", R.mipmap.tongxun2, R.mipmap.tongxun, 0);
            insertType(db, sql, "人情往来", R.mipmap.renqing2, R.mipmap.renqing, 0);

            // 收入分类
            insertType(db, sql, "其他", R.mipmap.qita2, R.mipmap.qita3, 1);
            insertType(db, sql, "薪资", R.mipmap.xinzi2, R.mipmap.xinzi, 1);
            insertType(db, sql, "借入", R.mipmap.jieru2, R.mipmap.jieru, 1);
            insertType(db, sql, "收款", R.mipmap.qiandai2, R.mipmap.qiandai, 1);
            insertType(db, sql, "奖金", R.mipmap.jiangjin2, R.mipmap.jiangjin, 1);
    }

    private void insertType(SQLiteDatabase db, String sql, String typeName, int imageId, int selectedImageId, int kind) {
        db.execSQL(sql, new Object[]{typeName, imageId, selectedImageId, kind});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

