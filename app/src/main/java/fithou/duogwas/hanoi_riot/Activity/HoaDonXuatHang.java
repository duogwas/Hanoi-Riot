package fithou.duogwas.hanoi_riot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fithou.duogwas.hanoi_riot.Adapter.ListHoaDonXuatAdapter;
import fithou.duogwas.hanoi_riot.Class.Hoa_Don;
import fithou.duogwas.hanoi_riot.Database.HRDBHelper;
import fithou.duogwas.hanoi_riot.R;

public class HoaDonXuatHang extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {
    HRDBHelper hrdbHelper;
    ArrayList<Hoa_Don> hoadon;
    ListView lv_hoadonxuat;
    ListHoaDonXuatAdapter listHoaDonXuatAdapter;
    ImageButton img_btnHome, img_btnChange;
    FloatingActionButton fabBtnAddhd;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hoa_don_xuat_hang);
        AnhXa();
        DocDuLieu();
        setOnClick();
    }

    private void DocDuLieu() {
        Cursor cursor = hrdbHelper.SelectData("SELECT * FROM DonXuatHang");
        while (cursor.moveToNext()) {
            hoadon.add(new Hoa_Don(
                    cursor.getString(0),
                    cursor.getString(1)
            ));
        }
        ListHoaDonXuatAdapter listHoaDonXuatAdapter = new ListHoaDonXuatAdapter(HoaDonXuatHang.this, R.layout.custom_lv_hoadonxuat, hoadon);
        lv_hoadonxuat.setAdapter(listHoaDonXuatAdapter);
    }

    private void AnhXa() {
        hoadon = new ArrayList<Hoa_Don>();
        lv_hoadonxuat = findViewById(R.id.lv_hoadonxuat);
        hrdbHelper = new HRDBHelper(this);
        img_btnHome = findViewById(R.id.img_btnHome);
        img_btnChange = findViewById(R.id.img_btnChange);
        fabBtnAddhd = findViewById(R.id.fabBtnAddHd);
        searchView = findViewById(R.id.search_view);
    }

    public void setOnClick() {
        img_btnHome.setOnClickListener(this);
        img_btnChange.setOnClickListener(this);
        fabBtnAddhd.setOnClickListener(this);
        searchView.setOnQueryTextListener(this);
    }

    private void DialogHoaDon(int gravity) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_chonloaihoadon);
        dialog.setCanceledOnTouchOutside(false);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = gravity;
        window.setAttributes(windowAttribute);

        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        AppCompatButton btn_hdnhap, btn_hdxuat, btn_thoat;
        btn_hdnhap = dialog.findViewById(R.id.btn_hdnhap);
        btn_hdxuat = dialog.findViewById(R.id.btn_hdxuat);
        btn_thoat = dialog.findViewById(R.id.btn_thoat);

        btn_hdnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HoaDonXuatHang.this, HoaDonNhapHang.class);
                startActivity(intent);
            }
        });

        btn_hdxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.img_btnHome:
                intent = new Intent(HoaDonXuatHang.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.img_btnChange:
                DialogHoaDon(Gravity.CENTER);
                break;

            case R.id.fabBtnAddHd:
                intent = new Intent(HoaDonXuatHang.this, XuatGiay.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    public Date StringToDate(String date) throws ParseException {
        Date StringtoDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        return StringtoDate;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //query input form: dd//mm/yyyy-dd/mm/yyyy
        ArrayList<Hoa_Don> hdlist = new ArrayList<>();
        String dateinput1 = query.split("-")[0];
        String dateinput2 = query.split("-")[1];

        for (Hoa_Don hoa_don : hoadon) {
            //ngày của hoá đơn
            Date datehd = null;
            try {
                datehd = StringToDate(hoa_don.ngay);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //ngày start
            Date datebefore = null;
            try {
                datebefore = StringToDate(dateinput1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //ngày end
            Date dateafter = null;
            try {
                dateafter = StringToDate(dateinput2);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int result1 = datebefore.compareTo(datehd);
            int result2 = datehd.compareTo(dateafter);
            //ngày start<ngày hd<ngày end
            if (result1 < 0 && result2 < 0) {
                hdlist.add(hoa_don);
            }
        }
        listHoaDonXuatAdapter = new ListHoaDonXuatAdapter(this, R.layout.custom_lv_hoadonxuat, hdlist);
        lv_hoadonxuat.setAdapter(listHoaDonXuatAdapter);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<Hoa_Don> hdlist = new ArrayList<>();
        for (Hoa_Don hoa_don : hoadon) {
            if (hoa_don.ngay.toLowerCase().contains(newText.toLowerCase())) {
                hdlist.add(hoa_don);
            }
        }
        listHoaDonXuatAdapter = new ListHoaDonXuatAdapter(HoaDonXuatHang.this, R.layout.custom_lv_hoadonxuat, hdlist);
        lv_hoadonxuat.setAdapter(listHoaDonXuatAdapter);
        return true;
    }
}