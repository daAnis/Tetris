package ru.ssau.fiit.tetris.db;

import android.provider.BaseColumns;

public final class TetrisContract {
    private TetrisContract() { }

    public String dropTableSQL(String tableName) {
        return "DROP TABLE IF EXISTS " + tableName;
    }

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "User";
        public static final String COLUMN_NAME_LOGIN = "login";
        public static final String COLUMN_NAME_PASSWORD = "password";

        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_LOGIN + " TEXT," +
                COLUMN_NAME_PASSWORD + " TEXT)";
    }

    public static class GlassEntry implements BaseColumns {
        public static final String TABLE_NAME = "Glass";
        public static final String COLUMN_NAME_WIDTH = "width";
        public static final String COLUMN_NAME_HEIGHT = "height";
        public static final String COLUMN_NAME_COLOUR = "colour";
        public static final String COLUMN_NAME_SPEED = "speed";
        public static final String COLUMN_NAME_POINTS = "points";

        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_WIDTH + " INTEGER," +
                        COLUMN_NAME_HEIGHT + " INTEGER," +
                        COLUMN_NAME_COLOUR + " INTEGER," +
                        COLUMN_NAME_SPEED + " REAL," +
                        COLUMN_NAME_POINTS + " REAL)";
    }

    public static class FigureEntry implements BaseColumns {
        public static final String TABLE_NAME = "Figure";
        public static final String COLUMN_NAME_POLYGON = "polygon";
        public static final String COLUMN_NAME_LEVEL = "level";

        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_POLYGON + " BLOB," +
                        COLUMN_NAME_LEVEL + " INTEGER)";
    }

    public static class LevelEntry implements BaseColumns {
        public static final String TABLE_NAME = "Level";
        public static final String COLUMN_NAME_NUMBER = "number";
    }

    public static class GameEntry implements BaseColumns {
        public static final String TABLE_NAME = "Game";
        public static final String COLUMN_NAME_POINTS = "points";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_POLYGON = "polygon";
        public static final String COLUMN_NAME_USER = "user";
        public static final String COLUMN_NAME_GLASS = "glass";
        public static final String COLUMN_NAME_LEVEL = "level";
    }

}
