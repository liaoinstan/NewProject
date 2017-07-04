package com.ins.newproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        SQLiteDatabase sqLiteDatabase = this.dbDomainHelper.getWritableDatabase();
        try {
            Cursor e = sqLiteDatabase.query(DBDomainHelper.TABLE_NAME, null, whereClause, selectionArgs, null, null, null, null);
            if (!e.moveToFirst()) {
                return null;
            } else {
                if (e.moveToNext()) {
                    return getEntityFromCursor(e);
                } else {
                    return null;
                }
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

    public List<Domain> queryAll() {
        SQLiteDatabase sqLiteDatabase = dbDomainHelper.getWritableDatabase();
        try {
            ArrayList list = new ArrayList();
            Cursor e = sqLiteDatabase.query(DBDomainHelper.TABLE_NAME, null, null, null, null, null, null);
            if (e.moveToFirst()) {
                while (e.moveToNext()) {
                    Domain ipEntity = this.getEntityFromCursor(e);
                    if (ipEntity != null) {
                        list.add(ipEntity);
                    }
                }
            }
            e.close();
            return list;
        } catch (Exception var8) {
            var8.printStackTrace();
            return null;
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
        static final String TABLE_NAME = "IpInfo";
        static final String DATABASE_NAME = "domain.db";
        static final int DATABASE_VERSION = 1;
        static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (id Integer primary key autoincrement, domain text, note text);";

        DBDomainHelper(Context context) {
            this(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        private DBDomainHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_SQL);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion > oldVersion) {
                //none
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
    }
}

