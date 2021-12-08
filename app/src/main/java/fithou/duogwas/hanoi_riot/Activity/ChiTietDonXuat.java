package fithou.duogwas.hanoi_riot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

import fithou.duogwas.hanoi_riot.Adapter.ListChiTietHoaDonAdapter;
import fithou.duogwas.hanoi_riot.Class.Hoa_Don;
import fithou.duogwas.hanoi_riot.Database.HRDBHelper;
import fithou.duogwas.hanoi_riot.R;

public class ChiTietDonXuat extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {
    TextView tv_mahdxuat, tv_ngayxuathd, tv_tongtienhd;
    ImageView iv_anhsp;
    ImageButton img_btnHome;
    SearchView searchView;
    HRDBHelper hrdbHelper;
    ArrayList<Hoa_Don> hdxuat;
    ListView lv_chitietdonxuat;
    ListChiTietHoaDonAdapter listChiTietHoaDonAdapter;
    Integer result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chi_tiet_don_xuat);
        AnhXa();
        DocDuLieu();
        setOnClick();
    }

    private void DocDuLieu() {
        String ngay = getIntent().getStringExtra("ngayxuat");
        tv_ngayxuathd.setText(ngay);
        String id = getIntent().getStringExtra("idhd");
        tv_mahdxuat.setText(id);
        Cursor cursor = hrdbHelper.getWritableDatabase().rawQuery("SELECT * FROM ChiTietXuatHang WHERE idXuat =?", new String[]{id});
        while (cursor.moveToNext()) {
            hdxuat.add(new Hoa_Don(
                    cursor.getString(1),
                    cursor.getBlob(2),
                    cursor.getInt(3),
                    cursor.getInt(4)
            ));
        }
        listChiTietHoaDonAdapter = new ListChiTietHoaDonAdapter(ChiTietDonXuat.this, R.layout.custom_lv_chitiethoadon, hdxuat);
        lv_chitietdonxuat.setAdapter(listChiTietHoaDonAdapter);

        Cursor cusorSum = hrdbHelper.getWritableDatabase().rawQuery("SELECT SUM(giaXuat*slXuat) FROM ChiTietXuatHang WHERE idXuat =?", new String[]{id});
        while (cusorSum.moveToNext()) {
            result = cusorSum.getInt(0);
        }
        String dem = Integer.toString(result);
        tv_tongtienhd.setText(dem);
    }

    private void AnhXa() {
        tv_tongtienhd = findViewById(R.id.tv_tongtienhd);
        tv_mahdxuat = findViewById(R.id.tv_mahdxuat);
        tv_ngayxuathd = findViewById(R.id.tv_ngayxuathd);
        iv_anhsp = findViewById(R.id.iv_anhsp);
        img_btnHome = findViewById(R.id.img_btnHome);
        searchView = findViewById(R.id.search_view);
        hdxuat = new ArrayList<Hoa_Don>();
        lv_chitietdonxuat = findViewById(R.id.lv_chitietdonxuat);
        hrdbHelper = new HRDBHelper(this);
    }

    public void setOnClick() {
        img_btnHome.setOnClickListener(this);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.img_btnHome:
                intent = new Intent(ChiTietDonXuat.this, HoaDonXuatHang.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<Hoa_Don> ctXuatlist = new ArrayList<>();
        for (Hoa_Don hoa_don : hdxuat) {
            if (hoa_don.ten.toLowerCase().contains(newText.toLowerCase())) {
                ctXuatlist.add(hoa_don);
            }
        }
        listChiTietHoaDonAdapter = new ListChiTietHoaDonAdapter(ChiTietDonXuat.this, R.layout.custom_lv_chitiethoadon, ctXuatlist);
        lv_chitietdonxuat.setAdapter(listChiTietHoaDonAdapter);
        return true;
    }
}