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

public class DangNhap extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton btn_dangnhap, btn_dangkyngay;
    TextInputEditText username_log, pass_log;
    HRDBHelper hrdbHelper;
    String userlog, passlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dang_nhap);
        AnhXa();
        setOnClick();

        userlog = getIntent().getStringExtra("userreg");
        username_log.setText(userlog);
        passlog = getIntent().getStringExtra("passreg");
        pass_log.setText(passlog);
    }

    public void AnhXa() {
        btn_dangnhap = findViewById(R.id.btn_dangnhap);
        btn_dangkyngay = findViewById(R.id.btn_dangkyngay);
        username_log = findViewById(R.id.username_log);
        pass_log = findViewById(R.id.pass_log);
        hrdbHelper = new HRDBHelper(this);
    }

    private void setOnClick() {
        btn_dangnhap.setOnClickListener(this);
        btn_dangkyngay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_dangkyngay:
                intent = new Intent(DangNhap.this, DangKy.class);
                startActivity(intent);
                break;

            case R.id.btn_dangnhap:
                String user = username_log.getText().toString();
                String pass = pass_log.getText().toString();
                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(DangNhap.this, "Vui lòng điền tất cả thông tin", Toast.LENGTH_LONG).show();
                } else {
                    Boolean checkuserpassword = hrdbHelper.checkUserPassword(user, pass);
                    if (checkuserpassword == true) {
                        intent = new Intent(DangNhap.this, MainActivity.class);
                        intent.putExtra("username", user);
                        startActivity(intent);
                        Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DangNhap.this, "Tên tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            default:
                break;
        }
    }
}