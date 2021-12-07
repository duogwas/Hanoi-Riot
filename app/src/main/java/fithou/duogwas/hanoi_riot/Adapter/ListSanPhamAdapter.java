package fithou.duogwas.hanoi_riot.Adapter;//
// Created by duogwas on 08/12/2021.
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

import fithou.duogwas.hanoi_riot.Class.SanPham;
import fithou.duogwas.hanoi_riot.R;

public class ListSanPhamAdapter extends ArrayAdapter<SanPham> {
    Activity context;
    ArrayList<SanPham> list;

    public ListSanPhamAdapter(Activity context, int resource, ArrayList<SanPham> list) {
        super(context, resource, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.custom_lv_sanpham, null);
        }

        SanPham sanPham = getItem(position);
        if (sanPham != null) {
            TextView tvTenGiay = (TextView) view.findViewById(R.id.tv_tengiay);
            tvTenGiay.setText(sanPham.TenSp);

            TextView tvSlSp = (TextView) view.findViewById(R.id.tv_slsp);
            tvSlSp.setText(Integer.toString(sanPham.SoLuong));

            ImageView imgHinhSp = (ImageView) view.findViewById(R.id.iv_anhgiay);
            Bitmap bitmap = BitmapFactory.decodeByteArray(sanPham.HinhSp, 0, sanPham.HinhSp.length);
            imgHinhSp.setImageBitmap(bitmap);
        }
        return view;
    }
}
