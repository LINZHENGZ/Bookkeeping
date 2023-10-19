package cn.roidlin.bookkeepingbook;

import android.app.Application;
import db.DBManger;

/**
 * @author LINZHENGZ
 * @version 1.0
 * Create by 20222022/12/20 17:23
 */

public class UnitAPP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化数据库
        DBManger.initDB(getApplicationContext());
    }
}
