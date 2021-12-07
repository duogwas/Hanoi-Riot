package fithou.duogwas.hanoi_riot.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import fithou.duogwas.hanoi_riot.Class.Utils;
import fithou.duogwas.hanoi_riot.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    DrawerLayout drawerLayout;
    NavigationView nav_view;
    Toolbar toolbar;
    CardView cv_khohang, cv_nhaphang, cv_xuathang, cv_hoadon;
    TextView tv_userlogin;
    String userlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        AnhXa();
        setOnClick();

        if (Utils.userLogin.equals("")) {
            userlogin = getIntent().getStringExtra("username");
            Utils.userLogin = userlogin;
            tv_userlogin.setText(Utils.userLogin);
        }

        /********Toolbar********/
        setSupportActionBar(toolbar);

        /********Navigation Drawer Menu********/
        nav_view.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav_view.setCheckedItem(R.id.nav_home);

        /********Hide or show menu items********/
        Menu menu = nav_view.getMenu();
        menu.findItem(R.id.nav_login).setVisible(false);
        menu.findItem(R.id.nav_register).setVisible(false);
    }

    private void AnhXa() {
        drawerLayout = findViewById(R.id.drawerlayout);
        nav_view = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        tv_userlogin = findViewById(R.id.tv_userlogin);
        cv_hoadon = findViewById(R.id.cv_hoadon);
        cv_khohang = findViewById(R.id.cv_khohang);
        cv_nhaphang = findViewById(R.id.cv_nhaphang);
        cv_xuathang = findViewById(R.id.cv_xuathang);
    }

    private void setOnClick() {
        nav_view.setNavigationItemSelectedListener(this);
        cv_khohang.setOnClickListener(this);
        cv_nhaphang.setOnClickListener(this);
        cv_xuathang.setOnClickListener(this);
        cv_hoadon.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //navigation item onclick
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Menu menu = nav_view.getMenu();
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                break;

            case R.id.nav_khohang:
                intent = new Intent(MainActivity.this, KhoHang.class);
                startActivity(intent);
                break;

            case R.id.nav_register:
                intent = new Intent(MainActivity.this, DangKy.class);
                startActivity(intent);
                menu.findItem(R.id.nav_logout).setVisible(true);
                menu.findItem(R.id.nav_login).setVisible(false);
                menu.findItem(R.id.nav_register).setVisible(false);
                break;

            case R.id.nav_login:
                intent = new Intent(MainActivity.this, DangNhap.class);
                startActivity(intent);
                menu.findItem(R.id.nav_logout).setVisible(true);
                menu.findItem(R.id.nav_login).setVisible(false);
                menu.findItem(R.id.nav_register).setVisible(false);
                break;

            case R.id.nav_logout:
                if (!Utils.userLogin.equals("")) {
                    tv_userlogin.setVisibility(View.GONE);
                    menu.findItem(R.id.nav_login).setVisible(true);
                    menu.findItem(R.id.nav_register).setVisible(true);
                    menu.findItem(R.id.nav_logout).setVisible(false);
                }
                break;

            case R.id.nav_thongtin:
                intent = new Intent(MainActivity.this, ThongTinUngDung.class);
                startActivity(intent);
                break;

            case R.id.nav_hotro:
                intent = new Intent(MainActivity.this, HoTro.class);
                startActivity(intent);
                break;

            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.cv_khohang:
                intent = new Intent(MainActivity.this, KhoHang.class);
                startActivity(intent);
                break;

            case R.id.cv_nhaphang:
                intent = new Intent(MainActivity.this, NhapGiay.class);
                startActivity(intent);
                break;

            case R.id.cv_xuathang:
                intent = new Intent(MainActivity.this, XuatGiay.class);
                startActivity(intent);
                break;

            case R.id.cv_hoadon:
                intent = new Intent(MainActivity.this, HoaDon.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}