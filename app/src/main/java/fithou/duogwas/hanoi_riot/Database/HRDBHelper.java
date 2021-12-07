package fithou.duogwas.hanoi_riot.Database;//
// Created by duogwas on 07/12/2021.
//


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class HRDBHelper extends SQLiteOpenHelper {
    public HRDBHelper(Context context) {
        super(context, "QuanLyKhoHang.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table User(username TEXT primary key, password TEXT, phoneNumber TEXT)");
        MyDB.execSQL("create Table SanPham(idSP INTEGER primary key autoincrement, tenSp TEXT, slSP INTEGER, anhSP BLOB)");
        MyDB.execSQL("create Table DonNhapHang(idNhap TEXT primary key, ngayNhap TEXT)");
        MyDB.execSQL("create Table ChiTietNhapHang(idNhap TEXT, tenSp TEXT, anhSP BLOB, giaNhap INTEGER,slNhap INTERGER,foreign key(idNhap) references DonNhapHang(idNhap), foreign key(tenSp) references SanPham(tenSp))");
        MyDB.execSQL("create Table DonXuatHang(idXuat TEXT primary key, ngayXuat TEXT)");
        MyDB.execSQL("create Table ChiTietXuatHang(idXuat TEXT, tenSp TEXT, anhSP BLOB, giaXuat INTEGER, slXuat INTERGER, foreign key(idXuat) references DonXuatHang(idXuat), foreign key(tenSp) references SanPham(tenSp))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists User");
        MyDB.execSQL("drop Table if exists SanPham");
        MyDB.execSQL("drop Table if exists DonNhapHang");
        MyDB.execSQL("drop Table if exists ChiTietNhapHang");
        MyDB.execSQL("drop Table if exists DonXuatHang");
        MyDB.execSQL("drop Table if exists ChiTietXuatHang");
    }

    //insert, update, delete
    public void QueryData(String sql) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.execSQL(sql);
    }

    //select
    public Cursor SelectData(String sql) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        return MyDB.rawQuery(sql, null);
    }

    //booleanCheck return true: đã có dl đó trong bảng, return false: chưa có dl đó trong bảng

    //1. Bảng User
    //thêm dữ liệu vào bảng
    public boolean insertUser(String username, String password, String phoneNumber) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("phoneNumber", phoneNumber);
        long result = MyDB.insert("user", "hack", contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //check user đã tồn tại chưa
    public boolean checkUser(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from user where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    //check sdt đã được sd chưa
    public boolean checkPhoneNumber(String phoneNumber) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from user where phoneNumber = ?", new String[]{phoneNumber});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    //check tài khoản mật khẩu
    public boolean checkUserPassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from user where username = ? and password = ?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    //2. Bảng SanPham
    //thêm dữ liệu vào bảng
    public void InsertSanPham(String tenSp, Integer slSp, byte[] anhSp) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String sql = "INSERT INTO SanPham VALUES(null,?,?,?)";
        SQLiteStatement sqLiteStatement = MyDB.compileStatement(sql);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindString(1, tenSp);
        sqLiteStatement.bindDouble(2, slSp);
        sqLiteStatement.bindBlob(3, anhSp);
        sqLiteStatement.executeInsert();
    }

    //check sản phẩm đã có trong kho chưa
    public boolean checkTenSP(String tensp) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from SanPham where tenSP = ?", new String[]{tensp});
        if (cursor.getCount() > 0)
            return true; //đã có trong bảng
        else
            return false; //chưa có trong bảng
    }


    //3. Bảng DonNhapHang
    //thêm dữ liệu vào bảng
    public void InsertDonNhapHang(String idNhap, String ngayNhap) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String sql = "INSERT INTO DonNhapHang VALUES(?,?)";
        SQLiteStatement sqLiteStatement = MyDB.compileStatement(sql);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindString(1, idNhap);
        sqLiteStatement.bindString(2, ngayNhap);
        sqLiteStatement.executeInsert();
    }

    //check idDonNhapHang
    public boolean checkIdNhapHang(String idNhap) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from DonNhapHang where idNhap = ?", new String[]{idNhap});
        if (cursor.getCount() > 0)
            return true; //đã có id đó trong bảng
        else
            return false; //chưa có id đó trong bảng
    }

    //4. Bảng ChiTietNhapHang
    //thêm dl vào bảng
    public void InsertChiTietNhapHang(String idNhap, String tenSp, byte[] anhSP, Integer giaNhap, Integer slNhap) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String sql = "INSERT INTO ChiTietNhapHang VALUES(?,?,?,?,?)";
        SQLiteStatement sqLiteStatement = MyDB.compileStatement(sql);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindString(1, idNhap);
        sqLiteStatement.bindString(2, tenSp);
        sqLiteStatement.bindBlob(3, anhSP);
        sqLiteStatement.bindDouble(4, giaNhap);
        sqLiteStatement.bindDouble(5, slNhap);
        sqLiteStatement.executeInsert();
    }

    //5. Bảng DonXuatHang
    //thêm dl vào bảng
    public void InsertDonXuatHang(String idXuat, String ngayXuat) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String sql = "INSERT INTO DonXuatHang VALUES(?,?)";
        SQLiteStatement sqLiteStatement = MyDB.compileStatement(sql);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindString(1, idXuat);
        sqLiteStatement.bindString(2, ngayXuat);
        sqLiteStatement.executeInsert();
    }

    //check idXuatHang
    public boolean checkIdXuatHang(String idXuat) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from DonXuatHang where idXuat = ?", new String[]{idXuat});
        if (cursor.getCount() > 0)
            return true; //đã có trong bảng
        else
            return false; //chưa có trong bảng
    }

    //6. Bảng ChiTietXuatHang
    //thêm dl vào bảng
    public void InsertChiTietXuatHang(String idXuat, String tenSp, byte[] anhSP, Integer giaXuat, Integer slXuat) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String sql = "INSERT INTO ChiTietXuatHang VALUES(?,?,?,?,?)";
        SQLiteStatement sqLiteStatement = MyDB.compileStatement(sql);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindString(1, idXuat);
        sqLiteStatement.bindString(2, tenSp);
        sqLiteStatement.bindBlob(3, anhSP);
        sqLiteStatement.bindDouble(4, giaXuat);
        sqLiteStatement.bindDouble(5, slXuat);
        sqLiteStatement.executeInsert();
    }
}
