package fithou.duogwas.hanoi_riot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fithou.duogwas.hanoi_riot.Adapter.ListSanPhamAdapter;
import fithou.duogwas.hanoi_riot.Class.SanPham;
import fithou.duogwas.hanoi_riot.Database.HRDBHelper;
import fithou.duogwas.hanoi_riot.R;

public class KhoHang extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {
    AppCompatButton btn_sort;
    TextView tv_tongslsp;
    ListView lv_khohang;
    ImageButton img_btnHome;
    FloatingActionButton fabBtnAddSp;
    ArrayList<SanPham> sanpham;
    HRDBHelper hrdbHelper;
    SearchView searchView;
    ListSanPhamAdapter listSanPhamAdapter;
    Integer result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kho_hang);
        AnhXa();
        setOnClick();
        DocDuLieu();
        DemSp();
    }

    private void DocDuLieu() {
        Cursor sp = hrdbHelper.SelectData("SELECT * FROM SanPham");
        while (sp.moveToNext()) {
            sanpham.add(new SanPham(
                    sp.getInt(0),
                    sp.getString(1),
                    sp.getInt(2),
                    sp.getBlob(3)
            ));
        }
        listSanPhamAdapter = new ListSanPhamAdapter(KhoHang.this, R.layout.custom_lv_sanpham, sanpham);
        lv_khohang.setAdapter(listSanPhamAdapter);
    }

    private void DemSp() {
        Cursor count = hrdbHelper.SelectData("SELECT COUNT(idSP) FROM SanPham");
        while (count.moveToNext()) {
            result = count.getInt(0);
        }
        String dem = Integer.toString(result);
        tv_tongslsp.setText(dem);
    }

    private void AnhXa() {
        tv_tongslsp = findViewById(R.id.tv_tongslsp);
        img_btnHome = findViewById(R.id.img_btnHome);
        fabBtnAddSp = findViewById(R.id.fabBtnAddSp);
        sanpham = new ArrayList<SanPham>();
        lv_khohang = findViewById(R.id.lv_khohang);
        hrdbHelper = new HRDBHelper(this);
        searchView = findViewById(R.id.search_view);
        btn_sort = findViewById(R.id.btn_sort);
    }

    private void setOnClick() {
        img_btnHome.setOnClickListener(this);
        fabBtnAddSp.setOnClickListener(this);
        searchView.setOnQueryTextListener(this);
        btn_sort.setOnClickListener(this);
    }

    private void SapXep() {
        ArrayList<SanPham> splist = new ArrayList<>();
        Cursor sp = hrdbHelper.SelectData("SELECT * FROM SanPham ORDER BY slSP ASC");
        //tăng dần theo sl, giảm dần thì thay bằng DESC
        while (sp.moveToNext()) {
            splist.add(new SanPham(
                    sp.getInt(0),
                    sp.getString(1),
                    sp.getInt(2),
                    sp.getBlob(3)
            ));
        }
        listSanPhamAdapter = new ListSanPhamAdapter(KhoHang.this, R.layout.custom_lv_sanpham, splist);
        lv_khohang.setAdapter(listSanPhamAdapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.img_btnHome:
                intent = new Intent(KhoHang.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.fabBtnAddSp:
                intent = new Intent(KhoHang.this, NhapGiay.class);
                startActivity(intent);
                break;

            case R.id.btn_sort:
                SapXep();
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
        ArrayList<SanPham> splist = new ArrayList<>();
        for (SanPham sanPham : sanpham) {
            if (sanPham.TenSp.toLowerCase().contains(newText.toLowerCase())) {
                splist.add(sanPham);
            }
        }
        listSanPhamAdapter = new ListSanPhamAdapter(KhoHang.this, R.layout.custom_lv_sanpham, splist);
        lv_khohang.setAdapter(listSanPhamAdapter);
        return true;
    }
}