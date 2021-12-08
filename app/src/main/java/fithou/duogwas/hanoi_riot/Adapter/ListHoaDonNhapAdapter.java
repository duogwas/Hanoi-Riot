package fithou.duogwas.hanoi_riot.Adapter;//
// Created by duogwas on 27/11/2021.
//


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;

import fithou.duogwas.hanoi_riot.Activity.ChiTietDonNhap;
import fithou.duogwas.hanoi_riot.Class.Hoa_Don;
import fithou.duogwas.hanoi_riot.R;

public class ListHoaDonNhapAdapter extends ArrayAdapter<Hoa_Don> {
    Activity context;
    ArrayList<Hoa_Don> list;

    public ListHoaDonNhapAdapter(Activity context, int resource, ArrayList<Hoa_Don> items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.custom_lv_hoadonnhap, null);
        }

        Hoa_Don hoaDon = getItem(position);
        if (hoaDon != null) {
            TextView tv_idhd = (TextView) view.findViewById(R.id.tv_idhd);
            tv_idhd.setText(hoaDon.idhd);

            TextView tv_datehd = (TextView) view.findViewById(R.id.tv_datehd);
            tv_datehd.setText(hoaDon.ngay);

            ImageView imgHoadon = (ImageView) view.findViewById(R.id.iv_bill);
            imgHoadon.setImageResource(R.drawable.ic_lv_hoadon);

            AppCompatButton btn_chitiet = (AppCompatButton) view.findViewById(R.id.btn_chitiet);
            btn_chitiet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietDonNhap.class);
                    intent.putExtra("idhd",hoaDon.getIdhd());
                    intent.putExtra("ngaynhap",hoaDon.getNgay());
                    context.startActivity(intent);
                }
            });
        }
        return view;
    }
}
