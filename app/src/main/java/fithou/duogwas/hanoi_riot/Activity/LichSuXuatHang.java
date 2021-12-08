package fithou.duogwas.hanoi_riot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fithou.duogwas.hanoi_riot.Adapter.ListLichSuAdapter;
import fithou.duogwas.hanoi_riot.Class.Hoa_Don;
import fithou.duogwas.hanoi_riot.Database.HRDBHelper;
import fithou.duogwas.hanoi_riot.R;

public class LichSuXuatHang extends AppCompatActivity implements View.OnClickListener {
    HRDBHelper hrdbHelper;
    ListView lv_lichsuxuathang;
    ListLichSuAdapter listLichSuAdapter;
    ArrayList<Hoa_Don> hoa_don;
    String tenspcheck;
    TextView tv_tensp;
    ImageButton img_btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lich_su_xuat_hang);
        AnhXa();
        DocDuLieu();
        setOnClick();
    }

    private void DocDuLieu() {
        tenspcheck = getIntent().getStringExtra("tensp");
        tv_tensp.setText(tenspcheck);
        Cursor cursor = hrdbHelper.getWritableDatabase().rawQuery("SELECT DonXuatHang.idXuat, giaXuat, slXuat, giaXuat*slXuat as[thanh toán], ngayXuat FROM DonXuatHang INNER JOIN ChiTietXuatHang WHERE DonXuatHang.idXuat = ChiTietXuatHang.idXuat and tenSp=?", new String[]{tenspcheck});
        while (cursor.moveToNext()) {
            hoa_don.add(new Hoa_Don(
                    cursor.getString(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getString(4)
            ));
        }
        listLichSuAdapter = new ListLichSuAdapter(LichSuXuatHang.this, R.layout.custom_lv_lichsu, hoa_don);
        lv_lichsuxuathang.setAdapter(listLichSuAdapter);
    }

    private void AnhXa() {
        hrdbHelper = new HRDBHelper(this);
        lv_lichsuxuathang = findViewById(R.id.lv_lichsuxuathang);
        hoa_don = new ArrayList<Hoa_Don>();
        tv_tensp = findViewById(R.id.tv_tensp);
        img_btnHome = findViewById(R.id.img_btnHome);
    }

    private void setOnClick() {
        img_btnHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.img_btnHome:
                intent = new Intent(LichSuXuatHang.this, KhoHang.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}