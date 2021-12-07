package fithou.duogwas.hanoi_riot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import fithou.duogwas.hanoi_riot.Database.HRDBHelper;
import fithou.duogwas.hanoi_riot.R;

public class DangKy extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText username_reg, sdt_reg, pass_reg, repass_reg;
    AppCompatButton btn_dangky, btn_dangnhap;
    HRDBHelper hrdbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dang_ky);
        AnhXa();
        setOnClick();
    }

    public void AnhXa() {
        username_reg = findViewById(R.id.username_reg);
        sdt_reg = findViewById(R.id.sdt_reg);
        pass_reg = findViewById(R.id.pass_reg);
        repass_reg = findViewById(R.id.repass_reg);
        btn_dangky = findViewById(R.id.btn_dangky);
        btn_dangnhap = findViewById(R.id.btn_dangnhapngay);
        hrdbHelper = new HRDBHelper(this);
    }

    public void setOnClick() {
        btn_dangky.setOnClickListener(this);
        btn_dangnhap.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_dangnhapngay:
                intent = new Intent(DangKy.this, DangNhap.class);
                startActivity(intent);
                break;

            case R.id.btn_dangky:
                String user = username_reg.getText().toString();
                String phone = sdt_reg.getText().toString();
                String pass = pass_reg.getText().toString();
                String repass = repass_reg.getText().toString();

                if (user.equals("") || phone.equals("") || pass.equals("") || repass.equals("")) {
                    Toast.makeText(DangKy.this, "Vui lòng điền tất cả thông tin", Toast.LENGTH_LONG).show();
                } else {
                    if (pass.equals(repass)) {
                        Boolean checkusername = hrdbHelper.checkUser(user);
                        if (checkusername == false) {
                            Boolean checksdt = hrdbHelper.checkPhoneNumber(phone);
                            if (checksdt == false) {
                                Boolean insertdata = hrdbHelper.insertUser(user, pass, phone);
                                if (insertdata == true) {
                                    intent = new Intent(DangKy.this, DangNhap.class);
                                    intent.putExtra("userreg", user);
                                    intent.putExtra("passreg", pass);
                                    startActivity(intent);
                                    Toast.makeText(DangKy.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(DangKy.this, "Đăng ký thất bại", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Toast.makeText(DangKy.this, "Số điện thoại này đã được sử dụng để đăng ký một tài khoản khác", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(DangKy.this, "Tên người dùng này đã tồn tại", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(DangKy.this, "Mật khẩu và Xác nhận mật khẩu không trùng khớp", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            default:
                break;
        }
    }
}