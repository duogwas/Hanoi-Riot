package fithou.duogwas.hanoi_riot.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import fithou.duogwas.hanoi_riot.Database.HRDBHelper;
import fithou.duogwas.hanoi_riot.R;

public class NhapGiay extends AppCompatActivity implements View.OnClickListener {
    ImageView iv_anhsp;
    ImageButton img_btnHome;
    AppCompatButton btn_chupanh, btn_chonanh, btn_huy, btn_them, btn_xemkho;
    TextInputEditText mahd_add, tensp_add, giasp_add, soluongsp_add, ngaynhapsp_add;
    ActivityResultLauncher<Intent> activityResultLauncher, activityResultLauncher1;
    HRDBHelper hrdbHelper;

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
                break;

            default:
                break;

        }
    }
}