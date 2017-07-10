package com.ins.newproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaoinstan on 2017/7/4.
 * Domain数据库管理者
 * 提供以下增删改查方法
 * {@link #insert(Domain)}
 * {@link #delete(int)}
 * {@link #update(Domain)}
 * {@link #insertOrUpdate(Domain)}
 * {@link #query(int)}
 * {@link #queryAll()}
 */

class DBDomainManager {
    private static DBDomainManager instance;
    private DBDomainHelper dbDomainHelper;

    private DBDomainManager(Context context) {
        this.dbDomainHelper = new DBDomainHelper(context);
    }

    public static DBDomainManager getInstance(Context context) {
        if (instance == null) {
            synchronized (DBDomainManager.class) {
                instance = new DBDomainManager(context);
            }
        }
        return instance;
    }

    public void insertOrUpdate(Domain domain) {
        Domain query = this.query(domain.getId());
        if (query == null) {
            insert(domain);
        } else {
            update(domain);
        }
    }

    public int insert(Domain domain) {
        if (domain == null || domain.getId() == 0) {
            return -1;
        }
        ContentValues cv = new ContentValues();
        cv.put("id", domain.getId());
        cv.put("domain", domain.getDomain());
        cv.put("note", domain.getNote());
        SQLiteDatabase sqLiteDatabase = dbDomainHelper.getWritableDatabase();
        try {
            return (int) sqLiteDatabase.insert(DBDomainHelper.TABLE_NAME, null, cv);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
    }

    public void update(Domain domain) {
        ContentValues cv = new ContentValues();
        cv.put("domain", domain.getDomain());
        cv.put("note", domain.getNote());
        SQLiteDatabase sqLiteDatabase = this.dbDomainHelper.getWritableDatabase();
        try {
            sqLiteDatabase.update(DBDomainHelper.TABLE_NAME, cv, "id=?", new String[]{domain.getId() + ""});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
    }


    public Domain query(int id) {
        String whereClause = "id=?";
        String[] selectionArgs = new String[]{id + ""};
        SQLiteDatabase sqLiteDatabase = this.dbDomainHelper.getReadableDatabase();
        try {
            Cursor e = sqLiteDatabase.query(DBDomainHelper.TABLE_NAME, null, whereClause, selectionArgs, null, null, null, null);
            if (e.moveToFirst()) {
                return getEntityFromCursor(e);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
    }

    public
    @NonNull
    List<Domain> queryAll() {
        SQLiteDatabase sqLiteDatabase = dbDomainHelper.getReadableDatabase();
        try {
            List<Domain> list = new ArrayList();
            Cursor e = sqLiteDatabase.query(DBDomainHelper.TABLE_NAME, null, null, null, null, null, null);
            while (e.moveToNext()) {
                Domain ipEntity = this.getEntityFromCursor(e);
                if (ipEntity != null) {
                    list.add(ipEntity);
                }
            }
            e.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
    }

    public void delete(int id) {
        if (id == 0) {
            return;
        }
        SQLiteDatabase sqLiteDatabase = dbDomainHelper.getWritableDatabase();
        try {
            sqLiteDatabase.delete(DBDomainHelper.TABLE_NAME, "id=?", new String[]{id + ""});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
    }

    private Domain getEntityFromCursor(Cursor cursor) {
        try {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String domain = cursor.getString(cursor.getColumnIndex("domain"));
            String note = cursor.getString(cursor.getColumnIndex("note"));
            return new Domain(id, domain, note);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class DBDomainHelper extends SQLiteOpenHelper {
        static final String TABLE_NAME = "t_domain";
        static final String DATABASE_NAME = "domain.db";
        static final int DATABASE_VERSION = 1;
        static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (id int primary key, domain varchar(256), note varchar(256))";

        DBDomainHelper(Context context) {
            this(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        private DBDomainHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public void onCreate(SQLiteDatabase db) {
            System.out.println("创建数据库和表");
            db.execSQL(CREATE_TABLE_SQL);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion > oldVersion) {
                //更新表操作，如果变结构变化太大又需要保持数据，推荐将数据以对象形式全部查出，删除原表建立新表后再讲数据批量插入
                //一定要把历史建表语句保存在类里（CREATE_TABLE_SQL1，CREATE_TABLE_SQL2...），否则得不偿失
            }
        }
    }

    static class Domain {
        private int id;
        private String domain;
        private String note;

        public Domain() {
        }

        public Domain(int id, String domain, String note) {
            this.id = id;
            this.domain = domain;
            this.note = note;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        @Override
        public String toString() {
            return "Domain{" +
                    "id=" + id +
                    ", domain='" + domain + '\'' +
                    ", note='" + note + '\'' +
                    '}';
        }
    }
}

