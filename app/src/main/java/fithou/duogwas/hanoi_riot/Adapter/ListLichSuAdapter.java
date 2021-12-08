package fithou.duogwas.hanoi_riot.Adapter;//
// Created by duogwas on 04/12/2021.
//


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fithou.duogwas.hanoi_riot.Class.Hoa_Don;
import fithou.duogwas.hanoi_riot.R;

public class ListLichSuAdapter extends ArrayAdapter<Hoa_Don> {
    Activity context;
    ArrayList<Hoa_Don> list;

    public ListLichSuAdapter(Activity context, int resource, ArrayList<Hoa_Don> items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.custom_lv_lichsu, null);
        }

        Hoa_Don hoaDon = getItem(position);
        if (hoaDon != null) {
            TextView tv_idhd = (TextView) view.findViewById(R.id.tv_mahd);
            tv_idhd.setText(hoaDon.idhd);

            TextView tv_gia = (TextView) view.findViewById(R.id.tv_gia);
            tv_gia.setText(Integer.toString(hoaDon.gia));

            TextView tv_soluong = (TextView) view.findViewById(R.id.tv_soluong);
            tv_soluong.setText(Integer.toString(hoaDon.soluong));

            TextView tv_thanhtien = (TextView) view.findViewById(R.id.tv_thanhtien);
            tv_thanhtien.setText(Integer.toString(hoaDon.thanhtien));

            TextView tv_thoigian = (TextView) view.findViewById(R.id.tv_thoigian);
            tv_thoigian.setText(hoaDon.ngay);
        }
        return view;
    }
}
