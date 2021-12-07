package fithou.duogwas.hanoi_riot.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fithou.duogwas.hanoi_riot.Database.HRDBHelper;
import fithou.duogwas.hanoi_riot.R;

public class NhapGiay extends AppCompatActivity implements View.OnClickListener {
    ImageView iv_anhsp;
    ImageButton img_btnHome;
    AppCompatButton btn_chupanh, btn_chonanh, btn_huy, btn_them, btn_xemkho;
    TextInputEditText mahd_add, tensp_add, giasp_add, soluongsp_add, ngaynhapsp_add;
    ActivityResultLauncher<Intent> activityResultLauncher, activityResultLauncher1;
    HRDBHelper hrdbHelper;
    Integer sl = 0;
    Integer sl_cthd = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nhap_giay);
        AnhXa();
        setOnClick();

        //nút chụp ảnh
        btn_chupanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mayAnh = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (mayAnh.resolveActivity(getPackageManager()) != null) {
                    activityResultLauncher.launch(mayAnh);
                } else {
                    Toast.makeText(NhapGiay.this, "lỗi rồi", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(NhapGiay.this, "lỗi rồi", Toast.LENGTH_SHORT).show();
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

    private void AnhXa() {
        img_btnHome = findViewById(R.id.img_btnHome);
        btn_chupanh = findViewById(R.id.btn_chupanh);
        btn_chonanh = findViewById(R.id.btn_chonanh);
        btn_huy = findViewById(R.id.btn_huy);
        btn_them = findViewById(R.id.btn_them);
        btn_xemkho = findViewById(R.id.btn_xemkho);
        iv_anhsp = findViewById(R.id.iv_anhsp);
        mahd_add = findViewById(R.id.mahd_add);
        tensp_add = findViewById(R.id.tensp_add);
        giasp_add = findViewById(R.id.giasp_add);
        soluongsp_add = findViewById(R.id.soluongsp_add);
        ngaynhapsp_add = findViewById(R.id.ngaynhapsp_add);
        hrdbHelper = new HRDBHelper(this);
    }

    private void setOnClick() {
        img_btnHome.setOnClickListener(this);
        btn_chonanh.setOnClickListener(this);
        btn_huy.setOnClickListener(this);
        btn_them.setOnClickListener(this);
        btn_xemkho.setOnClickListener(this);
    }

    public byte[] ImageViewToByte(ImageView imageView) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return bytes;
    }

    public Date StringToDate(String date) throws ParseException {
        Date StringtoDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        return StringtoDate;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.img_btnHome:
                intent = new Intent(NhapGiay.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_xemkho:
                intent = new Intent(NhapGiay.this, KhoHang.class);
                startActivity(intent);
                break;

            case R.id.btn_huy:
                intent = new Intent(NhapGiay.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_them:
                String mahdnhap = mahd_add.getText().toString();
                String tensp = tensp_add.getText().toString();
                String gianhap = giasp_add.getText().toString();
                String soluong = soluongsp_add.getText().toString();
                String ngaynhap = ngaynhapsp_add.getText().toString();
                byte[] anhsp = ImageViewToByte(iv_anhsp);

                if (mahdnhap.equals("") || tensp.equals("") || gianhap.equals("") || soluong.equals("") || ngaynhap.equals("")) {
                    Toast.makeText(NhapGiay.this, "Vui lòng điền tất cả thông tin", Toast.LENGTH_LONG).show();
                } else {
                    Date dateinput = null;
                    try {
                        dateinput = StringToDate(ngaynhap);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date datenow = java.util.Calendar.getInstance().getTime();
                    int result = dateinput.compareTo(datenow);
                    if (result > 0) {
                        Toast.makeText(this, "Ngày nhập lớn hơn ngày hiện tại", Toast.LENGTH_LONG).show();
                    } else {
                        Integer giaNhap = Integer.parseInt(gianhap);
                        Integer soLuong = Integer.parseInt(soluong);
                        Boolean checkTenSP = hrdbHelper.checkTenSP(tensp);
                        if (checkTenSP == false) {
                            hrdbHelper.InsertSanPham(tensp, soLuong, anhsp);
                            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_LONG).show();
                        } else {
                            Cursor cursor = hrdbHelper.getWritableDatabase().rawQuery("SELECT * FROM SanPham WHERE tenSP =?", new String[]{tensp});
                            if (cursor != null) {
                                if (cursor.moveToFirst()) {
                                    sl = cursor.getInt(2);
                                }
                                cursor.close();
                            }
                            Integer tongsl = soLuong + sl;
                            hrdbHelper.getWritableDatabase().execSQL("UPDATE SanPham SET slSp =? WHERE tenSP=?", new String[]{tongsl + "", tensp});
                            Toast.makeText(this, "Thêm hàng thành công", Toast.LENGTH_LONG).show();
                            intent = new Intent(NhapGiay.this, KhoHang.class);
                            startActivity(intent);
                        }
                        Boolean checkIdNhapHang = hrdbHelper.checkIdNhapHang(mahdnhap);
                        if (checkIdNhapHang == false) {
                            hrdbHelper.InsertDonNhapHang(mahdnhap, ngaynhap);
                            hrdbHelper.InsertChiTietNhapHang(mahdnhap, tensp, anhsp, giaNhap, soLuong);
                            Toast.makeText(this, "Nhập hàng thành công", Toast.LENGTH_LONG).show();
                            intent = new Intent(NhapGiay.this, KhoHang.class);
                            startActivity(intent);
                        } else {
                            Boolean checkSanPhamChiTietNhap = hrdbHelper.checkSanPham_ChiTietDonNhap(mahdnhap, tensp);
                            if (checkSanPhamChiTietNhap == true) {
                                Cursor cursor = hrdbHelper.getWritableDatabase().rawQuery("SELECT * FROM ChiTietNhapHang WHERE tenSP =? and idNhap=?", new String[]{tensp, mahdnhap});
                                if (cursor != null) {
                                    if (cursor.moveToFirst()) {
                                        sl_cthd = cursor.getInt(4);
                                    }
                                    cursor.close();
                                }
                                Integer tongsl_cthd = soLuong + sl_cthd;
                                hrdbHelper.getWritableDatabase().execSQL("UPDATE ChiTietNhapHang SET slNhap =? WHERE tenSP=? and idNhap=?", new String[]{tongsl_cthd + "", tensp, mahdnhap});
                                Toast.makeText(this, "Thêm hàng thành công", Toast.LENGTH_LONG).show();
                                intent = new Intent(NhapGiay.this, KhoHang.class);
                                startActivity(intent);
                            } else {
                                hrdbHelper.InsertChiTietNhapHang(mahdnhap, tensp, anhsp, giaNhap, soLuong);
                                Toast.makeText(this, "Nhập thành công", Toast.LENGTH_LONG).show();
                                intent = new Intent(NhapGiay.this, KhoHang.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
                break;

            default:
                break;

        }
    }
}