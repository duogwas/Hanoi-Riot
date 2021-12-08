package fithou.duogwas.hanoi_riot.Adapter;//
// Created by duogwas on 30/11/2021.
//


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fithou.duogwas.hanoi_riot.Class.Hoa_Don;
import fithou.duogwas.hanoi_riot.R;

public class ListChiTietHoaDonAdapter extends ArrayAdapter<Hoa_Don> {
    Activity context;
    ArrayList<Hoa_Don> list;

    public ListChiTietHoaDonAdapter(Activity context, int resource, ArrayList<Hoa_Don> items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.custom_lv_chitiethoadon, null);
        }

        Hoa_Don hoaDon = getItem(position);
        if (hoaDon != null) {
            TextView tvTenGiay = (TextView) view.findViewById(R.id.tv_tensp);
            tvTenGiay.setText(hoaDon.ten);

            TextView tvGia = (TextView) view.findViewById(R.id.tv_giachitiet);
            tvGia.setText(Integer.toString(hoaDon.gia));

            TextView tvSlSp = (TextView) view.findViewById(R.id.tv_slchitiet);
            tvSlSp.setText(Integer.toString(hoaDon.soluong));

            ImageView imgHinhSp = (ImageView) view.findViewById(R.id.iv_anhsp);
            Bitmap bitmap = BitmapFactory.decodeByteArray(hoaDon.anh, 0, hoaDon.anh.length);
            imgHinhSp.setImageBitmap(bitmap);
        }
        return view;
    }
}
