package db;

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

    private  static SQLiteDatabase db;

    public static void initDB(Context context){
        DBOpenHelper helper = new DBOpenHelper(context);
        db = helper.getWritableDatabase();
    }


    @SuppressLint("Range")
    public static List<TypeBean>getTypeList(int kind) {
        List<TypeBean>list = new ArrayList<>();
        String sql = "select * from typetb where kind = "+kind;
        Cursor cursor = db.rawQuery(sql,null);
        //循环游标
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
    * 向表里插入一条元素
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
        //遍历符合要求的每一行数据
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

}
