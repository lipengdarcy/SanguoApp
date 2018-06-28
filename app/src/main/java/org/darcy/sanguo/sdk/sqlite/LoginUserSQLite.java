package org.darcy.sanguo.sdk.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 用户管理
 */
public class LoginUserSQLite extends SQLiteOpenHelper {
    public static final String DATABASENAME = "chujianusers.db";
    public static final String SQL_STRING = "create table user (_id integer primary key,username text, password text, issaved INTEGER,exe_time integer)";
    public static final String TABLE_NAME_STRING = "user";
    private static LoginUserSQLite datas;
    private static SQLiteDatabase db;

    private LoginUserSQLite(Context paramContext) {
        super(paramContext, "chujianusers.db", null, 1);
    }

    public static LoginUserSQLite getInstance(Context paramContext) {
        try {
            if (datas == null) {
                LoginUserSQLite localLoginUserSQLite = new LoginUserSQLite(paramContext);
                datas = localLoginUserSQLite;
            }
            db = datas.getWritableDatabase();
            return datas;
        } finally {
        }
    }

    public long delete(String paramString) {
        return db.delete("user", "username = '" + paramString + "'", null);
    }

    public long insertOrUpdate(String username, String password, int paramInt, long paramLong) {
        long result;
        boolean flag = false;
        String[] nameList = queryAllUserName();
        for (String name : nameList) {
            if (username.equals(name)) {
                flag = true;
                break;
            }
        }
        if(flag)
            result = update(username, password, paramInt, System.currentTimeMillis() / 1000L);
        else{
            ContentValues cv = new ContentValues();
            cv.put("username", username);
            cv.put("password", password);
            cv.put("issaved", Integer.valueOf(paramInt));
            cv.put("exe_time", Long.valueOf(paramLong / 1000L));
            result = db.insert("user", null, cv);
        }
        return result;
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
        paramSQLiteDatabase.execSQL("create table user (_id integer primary key,username text, password text, issaved INTEGER,exe_time integer)");
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        onCreate(paramSQLiteDatabase);
    }

    /**
     * 获取所有的用户名
     */
    public String[] queryAllUserName() {
        int i;
        String[] result = null;
        Cursor localCursor;
        if (db != null) {
            localCursor = db.query("user", null, null, null, null, null, null);
            int count = localCursor.getCount();
            result = new String[count];
            if (count > 0) {
                localCursor.moveToFirst();
                i = 0;
                while (i < count) {
                    result[i] = localCursor.getString(localCursor.getColumnIndex("username"));
                    localCursor.moveToNext();
                    ++i;
                }
            }
        }
        return result;
    }

    public int queryIsSavedByName(String name) {
        String sql = "select * from user where username = '" + name + "'";
        Cursor c = db.rawQuery(sql, null);
        int i = 0;
        if (c.getCount() > 0) {
            c.moveToFirst();
            i = c.getInt(c.getColumnIndex("issaved"));
        }
        return i;
    }

    public String queryLastUserName() {
        int i = 0;
        Object localObject1 = db.rawQuery("select max(exe_time) as maxTime from user", null);
        if (localObject1 == null)
            localObject1 = null;
        while (true) {
            do
                i = ((Cursor) localObject1).getInt(i);
            while (((Cursor) localObject1).moveToNext());

            localObject1 = null;
            Object localObject2 = "select *  from user where exe_time=" + i;
            localObject2 = db.rawQuery((String) localObject2, null);
            if (localObject2 == null)
                localObject1 = null;
            do
                localObject1 = ((Cursor) localObject2).getString(((Cursor) localObject2).getColumnIndex("username"));
            while (((Cursor) localObject2).moveToNext());
        }
    }

    public String queryPasswordByName(String paramString) {
        paramString = "select * from user where username = '" + paramString + "'";
        Cursor localCursor = db.rawQuery(paramString, null);
        paramString = "";
        if (localCursor.getCount() > 0) {
            localCursor.moveToFirst();
            paramString = localCursor.getString(localCursor.getColumnIndex("password"));
        }
        return paramString;
    }

    //更新用户名密码
    public long update(String username, String password, int paramInt, long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("username", username);
        localContentValues.put("password", password);
        localContentValues.put("issaved", Integer.valueOf(paramInt));
        localContentValues.put("exe_time", Long.valueOf(paramLong));
        return db.update("user", localContentValues, "username = '" + username + "'", null);
    }
}