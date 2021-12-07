package fithou.duogwas.hanoi_riot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
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

public class KhoHang extends AppCompatActivity implements View.OnClickListener {
    TextView tv_tongslsp;
    ListView lv_khohang;
    ImageButton img_btnHome;
    FloatingActionButton fabBtnAddSp;
    ArrayList<SanPham> sanpham;
    HRDBHelper hrdbHelper;
    SearchView searchView;
    ListSanPhamAdapter listSanPhamAdapter;

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
    }

    private void DemSp() {
    }

    private void AnhXa() {
        tv_tongslsp = findViewById(R.id.tv_tongslsp);
        img_btnHome = findViewById(R.id.img_btnHome);
        fabBtnAddSp = findViewById(R.id.fabBtnAddSp);
        sanpham = new ArrayList<SanPham>();
        lv_khohang = findViewById(R.id.lv_khohang);
        hrdbHelper = new HRDBHelper(this);
        searchView = findViewById(R.id.search_view);
    }

    private void setOnClick() {
        img_btnHome.setOnClickListener(this);
        fabBtnAddSp.setOnClickListener(this);
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

            default:
                break;
        }
    }
}