package cn.roidlin.bookkeepingbook.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * @author LINZHENGZ
 * @version 1.0
 * Create by 20222022/12/20 16:13
 */

public class DBManger {

    private static SQLiteDatabase db;

    public static void initDB(Context context){

        DBOpenHelper helper = new DBOpenHelper(context);
        db = helper.getWritableDatabase();

    }


    @SuppressLint("Range")
    public static List<TypeBean>getTypeList(int kind) {

        List<TypeBean>list = new ArrayList<>();
        String sql = "select * from typetb where kind = " + kind;
        Cursor cursor = db.rawQuery(sql,null);

        //寰幆娓告爣
        while(cursor.moveToNext()){

            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            int imageId = cursor.getInt(cursor.getColumnIndex("imageid"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind1= cursor.getInt(cursor.getColumnIndex("kind"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));

            TypeBean typeBean = new TypeBean(id,typename,imageId,sImageId,kind1);
            list.add(typeBean);

        }

        return list;

    }

    /*
    * 鍚戣〃閲屾彃鍏ヤ竴鏉″厓绱?
    * */
    public static void insertItemToAccounttb(AccountBean bean){

        ContentValues values = new ContentValues();
        values.put("typename",bean.getTypename());
        values.put("sImageId",bean.getsImageId());
        values.put("beizhu",bean.getBeizhu());
        values.put("money",bean.getMoney());
        values.put("time",bean.getTime());
        values.put("year",bean.getYear());
        values.put("month",bean.getMonth());
        values.put("day",bean.getDay());
        values.put("kind",bean.getKind());
        db.insert("accounttb",null,values);

    }


    @SuppressLint("Range")
    public static List<AccountBean>getAccountListOneDayFromAccounttb(int year,int month,int day){

        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=? and day=? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + ""});

        //閬嶅巻绗﹀悎瑕佹眰鐨勬瘡涓€琛屾暟鎹?
        while (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String beizhu = cursor.getString(cursor.getColumnIndex("beizhu"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, beizhu, money, time, year, month, day, kind);
            list.add(accountBean);

        }

        return list;

    }


    //鑾峰彇鏌愪竴涓湀鐨勬敹鏀儏鍐?
    @SuppressLint("Range")
    public static List<AccountBean>getAccountListOnemonthFromAccounttb(int year,int month){

        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=?  order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + ""});

        //閬嶅巻绗﹀悎瑕佹眰鐨勬瘡涓€琛屾暟鎹?
        while (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String beizhu = cursor.getString(cursor.getColumnIndex("beizhu"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));

            AccountBean accountBean = new AccountBean(id, typename, sImageId, beizhu, money, time, year, month, day, kind);
            list.add(accountBean);

        }

        return list;

    }



    /*
    * 鑾峰彇鏌愪竴澶╃殑鏀嚭鎴栬€呮敹鍏ョ殑鎬婚噾棰?kind:鏀嚭==0, 鏀跺叆==1
    * */

    @SuppressLint("Range")
    public static float getSumMoneyOneday(int year, int month, int day, int kind){
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and day=? and kind=?";
        Cursor cursor = db.rawQuery(sql,new String[]{year+"",month+"",day+"",kind+""});
        //閬嶅巻
        if (cursor.moveToFirst()){

          float money =  cursor.getFloat(cursor.getColumnIndex("sum(money)"));
          total = money;
        }
        return total;
    }


    //鑾峰彇鏌愪竴涓湀鐨勬敮鍑烘垨鑰呮敹鍏ョ殑鎬婚噾棰?kind:鏀嚭==0, 鏀跺叆==1
    @SuppressLint("Range")
    public static float getSumMoneyMonth(int year, int month, int kind){
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and kind=?";
        Cursor cursor = db.rawQuery(sql,new String[]{year+"",month+"",kind+""});
        //閬嶅巻
        if (cursor.moveToFirst()){

            float money =  cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }

    //鑾峰彇鏌愪竴骞寸殑鏀嚭鎴栬€呮敹鍏ョ殑鎬婚噾棰?kind:鏀嚭==0, 鏀跺叆==1
    @SuppressLint("Range")
    public static float getSumMoneyYear(int year, int kind){
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and kind=?";
        Cursor cursor = db.rawQuery(sql,new String[]{year+"",kind+""});
        //閬嶅巻
        if (cursor.moveToFirst()){

            float money =  cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;

        }
        return total;
    }


    /*
    * 鏍规嵁浼犲叆鐨刬d锛屽垹闄ccountdb琛ㄤ腑鐨勪竴鏉℃暟鎹?
    * */


    public static int deleteItemFromAccounttbById(int id){

        int i = db.delete("accounttb","id=?",new String[]{id+""});
        return i;

    }


    /**
     * @description: 鏍规嵁澶囨敞鎼滅储鏀跺叆鎴栬€呮敮鍑虹殑鎯呭喌鍒楄〃
     * @author LINZHENGZ
     * @date 2023/12/26 11:44
     * @version 1.0
     **/

    @SuppressLint("Range")
    public static List<AccountBean>getAccountListListByRemarkFromAccounttb(String beizhu){

        List<AccountBean> list = new ArrayList<>();
        String sql = "select * from accounttb where beizhu like '%"+beizhu+"%'";
        Cursor cursor = db.rawQuery(sql,null);

        while (cursor.moveToNext()){

            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));

            int year = cursor.getInt(cursor.getColumnIndex("year"));
            int month = cursor.getInt(cursor.getColumnIndex("month"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));

            AccountBean accountBean = new AccountBean(id, typename, sImageId, beizhu, money, time, year, month, day, kind);
            list.add(accountBean);

        }

        return list;
    }

    /*
    * 鏌ヨ璁拌处鐨勮〃褰撲腑鏈夊嚑涓勾浠界殑淇℃伅
    * */

    @SuppressLint("Range")
    public static List<Integer> getYearListFromAccounttb(){

        List<Integer> list = new ArrayList<>();
        String sql = "select distinct(year) from accounttb order by year asc";
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()){
          int year = cursor.getInt(cursor.getColumnIndex("year"));
          list.add(year);
        }
        return list;
    }




}

