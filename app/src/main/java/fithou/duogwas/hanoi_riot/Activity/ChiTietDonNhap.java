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

public class ChiTietDonNhap extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {
    TextView tv_mahdnhap, tv_ngaynhaphd, tv_tongtienhd;
    ImageView iv_anhsp;
    HRDBHelper hrdbHelper;
    ArrayList<Hoa_Don> hdnhap;
    ListView lv_chitietdonnhap;
    ListChiTietHoaDonAdapter listChiTietHoaDonAdapter;
    ImageButton img_btnHome;
    SearchView searchView;
    Integer result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chi_tiet_don_nhap);
        AnhXa();
        DocDuLieu();
        setOnClick();
    }

    private void DocDuLieu() {
        String ngay = getIntent().getStringExtra("ngaynhap");
        tv_ngaynhaphd.setText(ngay);
        String id = getIntent().getStringExtra("idhd");
        tv_mahdnhap.setText(id);
        Cursor cursor = hrdbHelper.getWritableDatabase().rawQuery("SELECT * FROM ChiTietNhapHang WHERE idNhap =?", new String[]{id});
        while (cursor.moveToNext()) {
            hdnhap.add(new Hoa_Don(
                    cursor.getString(1),
                    cursor.getBlob(2),
                    cursor.getInt(3),
                    cursor.getInt(4)
            ));
        }
        listChiTietHoaDonAdapter = new ListChiTietHoaDonAdapter(ChiTietDonNhap.this, R.layout.custom_lv_chitiethoadon, hdnhap);
        lv_chitietdonnhap.setAdapter(listChiTietHoaDonAdapter);

        Cursor cursorSum = hrdbHelper.getWritableDatabase().rawQuery("SELECT SUM(giaNhap*slNhap) FROM ChiTietNhapHang WHERE idNhap =?", new String[]{id});
        while (cursorSum.moveToNext()) {
            result = cursorSum.getInt(0);
        }
        String dem = Integer.toString(result);
        tv_tongtienhd.setText(dem);
    }

    private void AnhXa() {
        tv_tongtienhd = findViewById(R.id.tv_tongtienhd);
        tv_mahdnhap = findViewById(R.id.tv_mahdnhap);
        tv_ngaynhaphd = findViewById(R.id.tv_ngaynhaphd);
        iv_anhsp = findViewById(R.id.iv_anhsp);
        hdnhap = new ArrayList<Hoa_Don>();
        lv_chitietdonnhap = findViewById(R.id.lv_chitietdonnhap);
        hrdbHelper = new HRDBHelper(this);
        img_btnHome = findViewById(R.id.img_btnHome);
        searchView = findViewById(R.id.search_view);
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
                intent = new Intent(ChiTietDonNhap.this, HoaDonNhapHang.class);
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
        ArrayList<Hoa_Don> ctNhaplist = new ArrayList<>();
        for (Hoa_Don hoa_don : hdnhap) {
            if (hoa_don.ten.toLowerCase().contains(newText.toLowerCase())) {
                ctNhaplist.add(hoa_don);
            }
        }
        listChiTietHoaDonAdapter = new ListChiTietHoaDonAdapter(ChiTietDonNhap.this, R.layout.custom_lv_chitiethoadon, ctNhaplist);
        lv_chitietdonnhap.setAdapter(listChiTietHoaDonAdapter);
        return true;
    }
}