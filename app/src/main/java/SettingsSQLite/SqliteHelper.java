package SettingsSQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by TAN-PC on 10/12/2016.
 */

public class SqliteHelper extends SQLiteOpenHelper {


    public static final String TABLE_SETTINGS ="Settings";
    public static final String KEY_ID ="_id";
    public static final String KEY_VALVES ="Valves";
    public static final String KEY_ROWS="Rows";
    public static final String KEY_IMAGES ="Images";
    public static final String KEY_THREHOLD="Threhold";
    public static final String KEY_IP ="Ip";
    public static final String KEY_PORT ="Port";
    public static final String KEY_LANGUAGES ="Languages";
    public static final String KEY_THEMES ="Themes";

    public static final String TABLE_IMAGE ="images";
    public static final String KEY_PATH ="path";
    public static final String KEY_ID_IMANGE ="_id_image";

    public static final String TABLE_IMAGES_DIRECTORY ="images_directory";
    public static final String KEY_DIRECTORY ="path_directory";
    public static final String KEY_ID_DIRECTORY="_id_directory";

//    public static final String TABLE_BINARY_192 ="Binary192Valves";
//    public static final String ID_BIN192="idBin";
//    public static final String NAME_BIN192="NameBin";
//    public static final String WIDTH_BIN192_="WidthBin";
//    public static final String HEIGHT_BIN192_="HeightBin";
//    public static final String IMAGE_BIN192="ImageBin";
//
//    public static final String TABLE_HOME = "Home";
//    public static final String ID_HOME="IdHome";
//    public static final String TIMES_HOME="TimesHome";
//    public static final String AFTER_HOME="AfterHome";



    //    public SqliteHelper(Context context) {
//        super(context, PROJECTDATABASE, null, 1);
//        Log.e("DATABASE OPERATION","DATABASE CREATE /OPENED...");
//    }
    public void deleteImage(String path) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... ");

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IMAGE, KEY_PATH + " = ?",
                new String[]{path});
        db.close();
    }

    public long CountImage() {
        SQLiteDatabase db = this.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(db, TABLE_IMAGE);
        Log.i("Data1",""+cnt);
        db.close();
        return cnt;
    }

    public void addImage(String path) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PATH, path);
        db.insert(TABLE_IMAGE, null, values);
        db.close();
    }

    public SqliteHelper (Context context,String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        Log.e("DATABASE OPERATION","DATABASE CREATE /OPENED...");
    }
    //Truy Van khong tra ket qua
    public void QueryData(String sql)
    {
        SQLiteDatabase database =getWritableDatabase();
        database.execSQL(sql);
    }
    public Cursor GetData(String sql){
        SQLiteDatabase database=getReadableDatabase();
        //database.close();
        return database.rawQuery(sql,null);
    }

    //count Row
//    public long CountImage192Valves() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        long cnt  = DatabaseUtils.queryNumEntries(db, TABLE_BINARY_192);
//        db.close();
//        return cnt;
//    }
//    public void CreateHome(SQLiteDatabase db){
//        String CREATE_TABLE_HOME="CREATE TABLE IF NOT EXISTS "+TABLE_HOME+" ("
//                +ID_HOME+" INTEGER PRIMARY KEY AUTOINCREMENT, "
//                +TIMES_HOME+" INT,"
//                +AFTER_HOME+" INT)";
//        db.execSQL(CREATE_TABLE_HOME);
//        InitialHome(db);
//    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Nên gọi các phương thức tạo bảng
//        CreateHome(db);
        CreateSettingsAndInitialValues(db);
        CreateBinary_192Valves(db);
        CreateImageTable(db);
        CreateImageDirectory(db);
    }
    public void InitialHome(SQLiteDatabase db){

        ContentValues contentValues = new ContentValues();
//        contentValues.put(ID_HOME, 0);
//        contentValues.put(TIMES_HOME, 5);
//        contentValues.put(AFTER_HOME, 5);
//        db.insert(TABLE_HOME, null, contentValues);

    }
    public boolean updateHome( int times,int after) {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(TIMES_HOME,times );
//        contentValues.put(AFTER_HOME, after);
        try {
//            db.update(TABLE_HOME, contentValues, "_id = " + 0, null);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    //Delete Binary Image by ID
    public void DETELE__BINARY_192Valves(int id){
        SQLiteDatabase db=getWritableDatabase();
//        db.execSQL("DELETE FROM "+TABLE_BINARY_192+" WHERE "+ID_BIN192+"=" +String.valueOf(id));
    }

    public void CreateValvesTable(SQLiteDatabase db){

    }

    public  void CreateImageTable(SQLiteDatabase db) {
        String CREATE_TABLE_IMAGE="CREATE TABLE IF NOT EXISTS "+TABLE_IMAGE+"("
                +KEY_ID_IMANGE+" INTEGER PRIMARY KEY,"
                +KEY_PATH+" TEXT);";
        db.execSQL(CREATE_TABLE_IMAGE);
    }

    //insert Binary Image To Database
    public void INSERT_BINARY_192Valves(String name,int width,int height, byte[] image){
            SQLiteDatabase db=getWritableDatabase();
//            String sql="INSERT INTO "+TABLE_BINARY_192+" VALUES(NULL,?,?,?,?)";
//            SQLiteStatement statement = db.compileStatement(sql);
//            statement.clearBindings();
//            statement.bindString(1,name);
//            statement.bindLong(2,width);
//            statement.bindLong(3,height);
//            statement.bindBlob(4,image);
//            statement.executeInsert();
    }

    public void CreateBinary_192Valves(SQLiteDatabase db){

    }
    public void CreateSettingsAndInitialValues(SQLiteDatabase db)
    {

            String CREATE_TABLE_SETTINGS="CREATE TABLE IF NOT EXISTS "+TABLE_SETTINGS+" ("
                    +KEY_ID+" INTEGER PRIMARY KEY, "
                    +KEY_VALVES+" INTERGER,"
                    +KEY_ROWS+" INTEGER,"
                    +KEY_IMAGES+" INTEGER,"
                    +KEY_THREHOLD+" INTEGER,"
                    +KEY_IP+" TEXT,"
                    +KEY_PORT+" INTEGER,"
                    +KEY_LANGUAGES+" INTEGER,"
                    +KEY_THEMES+" INTEGER)";
            db.execSQL(CREATE_TABLE_SETTINGS);
            insertSettings(db);
            Log.e("DATABASE OPERATION","TABLE SETTINGS CREATED");
    }
    public void CreateImageDirectory(SQLiteDatabase db){
        String CREATE_TABLE_IMAGE_DIRECTORY="CREATE TABLE IF NOT EXISTS "+TABLE_IMAGES_DIRECTORY+"("
                +KEY_ID_DIRECTORY+" INTEGER PRIMARY KEY, "
                +KEY_DIRECTORY+" TEXT);";
        db.execSQL(CREATE_TABLE_IMAGE_DIRECTORY);
        insertDirectory(db);
        Log.e("DATABASE OPERATION","TABLE DIRECTORY CREATED");
    }

//    public void insertSettingsValues (SQLiteDatabase db){
//        ContentValues contentValues =new ContentValues();
//        contentValues.put(KEY_ID, 0);
//        contentValues.put(KEY_VALVES, 0);
//        contentValues.put(KEY_ROWS, 150);
//        contentValues.put(KEY_IMAGES, 2000);
//        contentValues.put(KEY_THREHOLD, 128);
//        contentValues.put(KEY_IP, "192.168.1.1");
//        contentValues.put(KEY_PORT, 8888);
//        contentValues.put(KEY_LANGUAGES, 0);
//        contentValues.put(KEY_THEMES, 0);
//        db=this.getWritableDatabase();
//        db.insert(TABLE_SETTINGS,null,contentValues);
//        Log.e("DATABASE OPERATION","ONE ROW INSERTED");
//    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Thực hiện các thao tác chỉnh sửa cấu trúc bảng của DB

            db.execSQL("DROP TABLE IF EXISTS "+TABLE_SETTINGS);
//            db.execSQL("DROP TABLE IF EXISTS "+TABLE_BINARY_192);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_IMAGE);

            db.execSQL("DROP TABLE IF EXISTS "+TABLE_IMAGES_DIRECTORY);
        onCreate(db);

    }
//    //thêm bản ghi
    public void insertSettings(SQLiteDatabase db){

            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_ID, 0);
            contentValues.put(KEY_VALVES, 320);
            contentValues.put(KEY_ROWS, 1);
            contentValues.put(KEY_IMAGES, 1);
            contentValues.put(KEY_THREHOLD, 128);
            contentValues.put(KEY_IP, "192.168.4.1");
            contentValues.put(KEY_PORT, 1234);
            contentValues.put(KEY_LANGUAGES, 0);
            contentValues.put(KEY_THEMES, 0);
            db.insert(TABLE_SETTINGS, null, contentValues);

    }
    public void insertDirectory(SQLiteDatabase db){

        ContentValues contentValues = new ContentValues();
        String Imagefoler_dir= Environment.getExternalStorageDirectory()
                .getPath() + "/folder_name";
        contentValues.put(KEY_ID_DIRECTORY, 0);
        contentValues.put(KEY_DIRECTORY, Imagefoler_dir);

        db.insert(TABLE_IMAGES_DIRECTORY, null, contentValues);

    }
    //Cập nhật bản ghi
    public boolean updateSettings( int valves,int rows,int images,int threhold,String ip, int port, int languages,int themes) {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_VALVES, valves);
        contentValues.put(KEY_ROWS, rows);
        contentValues.put(KEY_IMAGES, images);
        contentValues.put(KEY_THREHOLD, threhold);
        contentValues.put(KEY_IP, ip);
        contentValues.put(KEY_PORT, port);
        contentValues.put(KEY_LANGUAGES, languages);
        contentValues.put(KEY_THEMES, themes);
        try {
            db.update(TABLE_SETTINGS, contentValues, "_id = " + 0, null);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean updateDirectory( String directory_path) {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DIRECTORY, directory_path);
//        contentValues.put(KEY_ROWS, rows);
//        contentValues.put(KEY_IMAGES, images);
//        contentValues.put(KEY_THREHOLD, threhold);
//        contentValues.put(KEY_IP, ip);
//        contentValues.put(KEY_PORT, port);
//        contentValues.put(KEY_LANGUAGES, languages);
//        contentValues.put(KEY_THEMES, themes);
        try {
            db.update(TABLE_IMAGES_DIRECTORY, contentValues, "_id_directory = " + 0, null);
            Log.e(TAG, "updateDirectory: "+ directory_path.toString());
        } catch (Exception ex) {
            Log.e(TAG, "updateDirectory: "+ ex.toString());
            return false;
        }
        return true;
    }
//    //Tạo Bảng
    public boolean createTable(String stringCreateTable) {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            db.execSQL(stringCreateTable);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
//
//    //Xóa bảng
//    public boolean deleteTable(String tableName) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        try {
//            db.execSQL("delete from " + tableName);
//        } catch (Exception ex) {
//            return false;
//        }
//        return true;
//    }
//
//    public Cursor getAllUser(String tableName) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from " + tableName, null);
//        return res;
//    }
//
//    public List<SettingsObject> getAllUserConvert(String tableName) {
//        List<SettingsObject> userObjects = new ArrayList<SettingsObject>();
//        Cursor cursor = getAllUser(tableName);
//        if (cursor.moveToFirst()) {
//
//            while (cursor.isAfterLast() == false) {//Cách lấy dữ liệu từ curssor
//                String id = cursor.getString(cursor
//                        .getColumnIndex("id"));
//                String name = cursor.getString(cursor
//                        .getColumnIndex("name"));
//
//                userObjects.add(new SettingsObject(id, name));
//                cursor.moveToNext();
//            }
//        }
//        return userObjects;
//    }


//    //Xóa bản ghi
//    public Integer deleteUser(String tableName, String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(tableName, "id = " + id, null);
//    }
//    //Xóa bản ghi với nhiều hơn 1 điều kiện
//    public Integer deleteUserByNameAndID(String tableName, String id, String name) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(tableName, "id = " + id + " AND " + "name=" + name, null);
//    }
}
