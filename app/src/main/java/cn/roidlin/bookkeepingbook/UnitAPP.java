package cn.roidlin.bookkeepingbook;

import android.app.Application;
import cn.roidlin.bookkeepingbook.data.DBManger;
import cn.roidlin.bookkeepingbook.data.repository.BookkeepingRepository;
import cn.roidlin.bookkeepingbook.data.repository.LocalBookkeepingRepository;

/**
 * @author LINZHENGZ
 * @version 1.0
 * Create by 20222022/12/20 17:23
 */

public class UnitAPP extends Application {
    private static BookkeepingRepository repository;

    public static BookkeepingRepository getRepository() {
        return repository;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化数据库与本地数据仓库
        DBManger.initDB(getApplicationContext());
        repository = new LocalBookkeepingRepository();
    }
}

