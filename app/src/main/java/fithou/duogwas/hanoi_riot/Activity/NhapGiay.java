package fithou.duogwas.hanoi_riot.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

import fithou.duogwas.hanoi_riot.Database.HRDBHelper;
import fithou.duogwas.hanoi_riot.R;

public class NhapGiay extends AppCompatActivity implements View.OnClickListener {
    ImageView iv_anhsp;
    ImageButton img_btnHome;
    AppCompatButton btn_chupanh, btn_chonanh, btn_huy, btn_them, btn_xemkho;
    TextInputEditText mahd_add, tensp_add, giasp_add, soluongsp_add, ngaynhapsp_add;
    ActivityResultLauncher<Intent> activityResultLauncher;
    HRDBHelper hrdbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nhap_giay);
        AnhXa();
        setOnClick();
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