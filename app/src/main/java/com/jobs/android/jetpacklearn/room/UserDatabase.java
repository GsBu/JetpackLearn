package com.jobs.android.jetpacklearn.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * 作者    你的名字
 * 时间    2021/12/14 15:12
 * 文件    JetpackLearn
 * 描述
 */
@Database(entities = {User.class, Library.class}, version = 2, exportSchema = false)
@TypeConverters(CompanyConverter.class)
public abstract class UserDatabase extends RoomDatabase {
    private static final String DB_NAME = "UserDatabase.db";
    private static volatile UserDatabase instance;

    public static synchronized UserDatabase getInstance(Context context){
        if(instance == null){
            instance = create(context);
        }
        return instance;
    }

    private static UserDatabase create(final Context context) {
        return Room.databaseBuilder(context, UserDatabase.class, DB_NAME)
                .addMigrations(migration_1_2()).build();
    }

    private static Migration migration_1_2(){
        return new Migration(1, 2) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {
                database.execSQL("ALTER TABLE user ADD COLUMN company BLOB");
            }
        };
    }

    public abstract UserDao getUserDao();
}
