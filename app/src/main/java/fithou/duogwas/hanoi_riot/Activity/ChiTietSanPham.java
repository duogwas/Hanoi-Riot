package fithou.duogwas.hanoi_riot.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import fithou.duogwas.hanoi_riot.Database.HRDBHelper;
import fithou.duogwas.hanoi_riot.R;

public class ChiTietSanPham extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton btn_chupanh, btn_chonanh, btn_luu, btn_quaylai, btn_xoa, btn_lichsu;
    TextInputEditText tensp_chitiet;
    ImageView iv_anhsp;
    ImageButton img_btnHome;
    HRDBHelper hrdbHelper;
    ActivityResultLauncher<Intent> activityResultLauncher, activityResultLauncher1;
    String ten_chitiet;
    byte[] anh_chitiet;
    int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        AnhXa();
        DocDuLieu();
        setOnClick();

        //nút chụp ảnh
        btn_chupanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mayAnh = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (mayAnh.resolveActivity(getPackageManager()) != null) {
                    activityResultLauncher.launch(mayAnh);
                } else {
                    Toast.makeText(ChiTietSanPham.this, "lỗi rồi", Toast.LENGTH_SHORT).show();
                }
            }
        });
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            // There are no request codes
                            Intent data = result.getData();
                            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                            iv_anhsp.setImageBitmap(bitmap);
                        }
                    }
                });

        //nút chọn ảnh
        btn_chonanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chonAnh = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (chonAnh.resolveActivity(getPackageManager()) != null) {
                    activityResultLauncher1.launch(chonAnh);
                } else {
                    Toast.makeText(ChiTietSanPham.this, "lỗi rồi", Toast.LENGTH_SHORT).show();
                }
            }
        });
        activityResultLauncher1 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            // There are no request codes
                            //Intent data = result.getData();
                            Intent data = result.getData();
                            Uri imageUri = data.getData();
                            Bitmap bitmap = null;
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            iv_anhsp.setImageBitmap(bitmap);
                        }
                    }
                });

    }

    public void DocDuLieu() {
        id = getIntent().getIntExtra("id", -1);
        Cursor cursor = hrdbHelper.getWritableDatabase().rawQuery("SELECT * FROM SanPham WHERE idSP =?", new String[]{id + ""});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                ten_chitiet = cursor.getString(1);
                anh_chitiet = cursor.getBlob(3);
            }
            cursor.close();
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(anh_chitiet, 0, anh_chitiet.length);
        iv_anhsp.setImageBitmap(bitmap);
        tensp_chitiet.setText(ten_chitiet);
    }

    public void AnhXa() {
        btn_lichsu = findViewById(R.id.btn_lichsu);
        btn_chupanh = findViewById(R.id.btn_chupanh);
        btn_chonanh = findViewById(R.id.btn_chonanh);
        btn_luu = findViewById(R.id.btn_luu);
        btn_quaylai = findViewById(R.id.btn_quaylai);
        btn_xoa = findViewById(R.id.btn_xoa);
        img_btnHome = findViewById(R.id.img_btnHome);
        tensp_chitiet = findViewById(R.id.tensp_chitiet);
        iv_anhsp = findViewById(R.id.iv_anhsp);
        hrdbHelper = new HRDBHelper(this);
    }

    public void setOnClick() {
        btn_lichsu.setOnClickListener(this);
        btn_chupanh.setOnClickListener(this);
        btn_chonanh.setOnClickListener(this);
        btn_luu.setOnClickListener(this);
        btn_quaylai.setOnClickListener(this);
        btn_xoa.setOnClickListener(this);
        img_btnHome.setOnClickListener(this);
    }

    public byte[] ImageViewToByte(ImageView imageView) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return bytes;
    }

    public void updateSanPham() {
        String ten = tensp_chitiet.getText().toString();
        byte[] anh = ImageViewToByte(iv_anhsp);
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenSp", ten);
        contentValues.put("anhSp", anh);
        hrdbHelper.getWritableDatabase().update("SanPham", contentValues, "idSp = ?", new String[]{id + ""});
        hrdbHelper.getWritableDatabase().update("ChiTietNhapHang", contentValues, "tenSp = ?", new String[]{ten_chitiet});
        hrdbHelper.getWritableDatabase().update("ChiTietXuatHang", contentValues, "tenSp = ?", new String[]{ten_chitiet});
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_luu:
                updateSanPham();
                intent = new Intent(ChiTietSanPham.this, KhoHang.class);
                startActivity(intent);
                break;

            case R.id.btn_quaylai:
                intent = new Intent(ChiTietSanPham.this, KhoHang.class);
                startActivity(intent);
                break;

            case R.id.btn_xoa:
                break;

            case R.id.img_btnHome:
                intent = new Intent(ChiTietSanPham.this, KhoHang.class);
                startActivity(intent);
                break;

            case R.id.btn_lichsu:
                break;

            default:
                break;

        }
    }
}