package com.example.doan.GiaoDien;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.Adapter.DichVuHightAdapter;
import com.example.doan.DataBase.DBKhachHang;
import com.example.doan.DataBase.DBPhatSinh;
import com.example.doan.DataBase.DBPhatSinhChiTiet;
import com.example.doan.DataBase.DBPhieuThu;
import com.example.doan.Model.PhatSinh;
import com.example.doan.Model.PhatSinhChiTiet;
import com.example.doan.Model.PhieuThu;
import com.example.doan.R;

import java.util.ArrayList;

public class PhieuSuaChua extends AppCompatActivity {
    Button btnInHoaDon;
    EditText txtMaKHPhieuSuaChua, txtTenKH, txtNgaySinh, txtDiaChi;
    ImageView imgGioiTinh;
    ListView lvPhatSinh;
    ArrayList<PhatSinh> dataPhatSinh = new ArrayList<>();
    ArrayList<PhatSinhChiTiet> dataPhatSinhChiTiet = new ArrayList<>();
    ArrayList<PhieuThu> dataPhieuThu = new ArrayList<>();
    ArrayAdapter adapter_phatsinh;
    ArrayList<com.example.doan.Model.KhachHang> arrKH = new ArrayList<>();

    int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_sua_chua);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setControl();
        setEvent();
    }

    private void setEvent() {
//        try {
//            DBPhatSinh dbPhatSinh = new DBPhatSinh(this);
//            dataPhatSinh = dbPhatSinh.LayDL();
//            adapter_phatsinh = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dataPhatSinh);
//
//            lvPhatSinh.setAdapter(adapter_phatsinh);
//
//        } catch (Exception e) {
//
//        }
        btnInHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PhieuSuaChua.this, InHoaDon.class);
                startActivity(intent);
            }
        });
        lvPhatSinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {

                    DBPhatSinhChiTiet dbPhatSinhChiTiet = new DBPhatSinhChiTiet(getApplication());
                    DBPhatSinh dbKhachHang = new DBPhatSinh(getApplication());
                    //ki????m ma?? s???? phi????u cu??a danh sa??nh khi mi??nh click, -> position
                    dataPhatSinh = dbKhachHang.TimKiem(txtMaKHPhieuSuaChua.getText() + "");
                    //b????t ??????u ti??m ki????m, in ra danh sa??ch theo s???? phi????u
                    dataPhatSinhChiTiet = dbPhatSinhChiTiet.TimKiem(dataPhatSinh.get(position).getSoPhieu() + "");

                    index = position;
                    DBPhieuThu dbPhieuThu = new DBPhieuThu(getApplication());
                    dataPhieuThu = dbPhieuThu.TimKiem(dataPhatSinh.get(position).getSoPhieu() + "");

                    int tinhTrang = 0;
                    int tongTien = 0;
                    String c = "";
                    String b = "";
                    for (int i = 0; i < dataPhatSinhChiTiet.size(); i++) {
                        b += i + 1 + ". Ma??Dv:" + dataPhatSinhChiTiet.get(i).getMaDV() +
                                "   SL:" + dataPhatSinhChiTiet.get(i).getSoLuong() +
                                "   Ti????n:" + dataPhatSinhChiTiet.get(i).getSoTien() + "\n";
                        tongTien += dataPhatSinhChiTiet.get(i).getSoTien();
                    }


                       if(dataPhieuThu.get(0).isTinhTrang() == 0)
                       {
                           c += "Ch??a thanh toa??n!!!!";
                       }else
                       {
                           c += "??a?? thanh toa??n";
                       }


                    AlertDialog.Builder builder = new AlertDialog.Builder(PhieuSuaChua.this);
                    String a = "(S???? phi????u: " + dataPhatSinh.get(position).getSoPhieu() + ")";
                    builder.setTitle("Phi????u pha??t sinh chi ti????t" + a);

                    builder.setMessage(b + "\t\t\t\tT????ng ti????n: " + tongTien + "\t\t\t\t" + c);


                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } catch (Exception e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PhieuSuaChua.this);
                    builder.setTitle("Phi????u pha??t sinh chi ti????t");
                    builder.setMessage("Kh??ng co?? di??ch vu??");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }

            }
        });

        lvPhatSinh.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final CharSequence[] items = {"Thanh toa??n", "Thoa??t"};
                AlertDialog.Builder builder = new AlertDialog.Builder(PhieuSuaChua.this);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(PhieuSuaChua.this);
                            builder.setTitle("Xa??c nh????n thanh toa??n");
                            builder.setMessage("Ba??n co?? th????c s???? mu????n thanh toa??n kh??ng?");
                            builder.setPositiveButton("Thanh toa??n", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DBPhatSinh dbKhachHang = new DBPhatSinh(getApplication());
                                    //ki????m ma?? s???? phi????u cu??a danh sa??nh khi mi??nh click, -> position
                                    dataPhatSinh = dbKhachHang.TimKiem(txtMaKHPhieuSuaChua.getText() + "");
                                    //b????t ??????u ti??m ki????m, in ra danh sa??ch theo s???? phi????u

                                    DBPhieuThu dbPhieuThu = new DBPhieuThu(getApplication());
                                    dataPhieuThu = dbPhieuThu.TimKiem(dataPhatSinh.get(index).getSoPhieu() + "");
                                    PhieuThu phieuThu = new PhieuThu();

                                    phieuThu.setSoPhieu(dataPhieuThu.get(0).getSoPhieu() + "");
                                    phieuThu.setTinhTrang(1);

                                    dbPhieuThu.sua(phieuThu);
                                }
                            });
                            builder.setNegativeButton("Thoa??t", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        }
                    }
                });

                builder.show();
                //Toast.makeText(getApplicationContext(), "concac", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void setControl() {
        btnInHoaDon = findViewById(R.id.btnInHoaDon);
        lvPhatSinh = findViewById(R.id.lvPhatSinh);

        imgGioiTinh = findViewById(R.id.imgGioiTinh);

        txtMaKHPhieuSuaChua = findViewById(R.id.txtMaKHPhieuSuaChua);
        txtTenKH = findViewById(R.id.txtTenKH);
        txtNgaySinh = findViewById(R.id.txtNgaySinh);
        txtDiaChi = findViewById(R.id.txtDiaChi);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_phieusuachua, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuTimKiem:
                if (txtMaKHPhieuSuaChua.length() == 0) {
                    break;
                } else {
                    try {
                        DBPhatSinh dbKhachHang = new DBPhatSinh(getApplication());
                        String key = txtMaKHPhieuSuaChua.getText() + "";
                        //ArrayList<KhachHang> khachHangs = new ArrayList<>();
                        dataPhatSinh = dbKhachHang.TimKiem(txtMaKHPhieuSuaChua.getText() + "");
                        Toast.makeText(getApplication(), key, Toast.LENGTH_SHORT).show();


                        DBKhachHang dbKhachHang1 = new DBKhachHang(getApplication());
                        arrKH = dbKhachHang1.TimKiemMa(txtMaKHPhieuSuaChua.getText() + "");
                        txtTenKH.setText(arrKH.get(0).getTen());
                        txtNgaySinh.setText(arrKH.get(0).getNgaySinh());
                        txtDiaChi.setText(arrKH.get(0).getDiaChi());
                        if(arrKH.get(0).getGioiTinh() == 1)
                        {
                            imgGioiTinh.setImageResource(R.drawable.male);
                        }else
                        {
                            imgGioiTinh.setImageResource(R.drawable.female);
                        }

                        adapter_phatsinh = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dataPhatSinh);
                        // dataKh.sort(new TenSort());
                        lvPhatSinh.setAdapter(adapter_phatsinh);
                        adapter_phatsinh.notifyDataSetChanged();
                    } catch (Exception e) {
                        Toast.makeText(getApplication(), "Kh??ng ti??m th????y", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    int TimKiem(ArrayList<PhatSinh> arr, String key) {
        for (int i = 0; i < dataPhatSinh.size(); i++) {
            if (arr.get(i).getMaKH() == key) {
                return i;
            }
        }
        return -1;
    }
}
