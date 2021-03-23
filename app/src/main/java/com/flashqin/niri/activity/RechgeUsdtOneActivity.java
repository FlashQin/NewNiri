package com.flashqin.niri.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flashqin.niri.R;
import com.flashqin.niri.adapter.SpacesItemDecoration;
import com.flashqin.niri.base.BaseActivity;
import com.flashqin.niri.bean.RechargBean;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.gyf.immersionbar.ImmersionBar;

import net.qiujuer.genius.ui.widget.Button;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RechgeUsdtOneActivity extends BaseActivity {

    @BindView(R.id.imgback)
    ImageView imgback;
    @BindView(R.id.txttitle)
    TextView txttitle;
    @BindView(R.id.txtmore)
    TextView txtmore;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imgercode)
    ImageView imgercode;
    @BindView(R.id.txtlink)
    TextView txtlink;
    @BindView(R.id.lincopy)
    LinearLayout lincopy;
    @BindView(R.id.linshare)
    LinearLayout linshare;
    @BindView(R.id.rec)
    RecyclerView rec;
    @BindView(R.id.edtamount)
    EditText edtamount;
    @BindView(R.id.txtrate)
    TextView txtrate;
    @BindView(R.id.btnpay)
    Button btnpay;
    @BindView(R.id.btnnext)
    Button btnnext;
    @BindView(R.id.linkefu)
    LinearLayout linkefu;
    private BaseQuickAdapter<RechargBean, BaseViewHolder> mOneAdapter;
    String ip = "", tongdao = "", amount = "500", bancode = "100501";
    List<RechargBean> rechargBeanList = new ArrayList<>();
    String[] strName = {"2000", "5000", "10000", "50000", "100000", "300000"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_usdtone;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(toolbar) //可以为任意view，如果是自定义xml实现标题栏的话，标题栏根节点不能为RelativeLayout或者ConstraintLayout，以及其子类
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void initView() {

        txttitle.setText("USDT  top-up");
        Bitmap bitmap = createQRCodeBitmap("http://wwww.baidu.com", 300, 300, "UTF-8", "H", "1", Color.BLACK, Color.WHITE);

        imgercode.setImageBitmap(bitmap);
        //ShowLoading();
        initAdapter();
    }

    public void initAdapter() {

        for (int i = 0; i < strName.length; i++) {
            RechargBean bean = new RechargBean();
            if (i == 0) {
                bean.setIscheck(true);

                bean.setName(strName[i]);
            } else {
                bean.setIscheck(false);
                bean.setName(strName[i]);
            }
            rechargBeanList.add(bean);

        }

        rec.setLayoutManager(new GridLayoutManager(RechgeUsdtOneActivity.this, 3));
        rec.addItemDecoration(new SpacesItemDecoration(20));
        mOneAdapter = new BaseQuickAdapter<RechargBean, BaseViewHolder>(R.layout.item_rechger, rechargBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, RechargBean item) {

                RelativeLayout txtname = helper.itemView.findViewById(R.id.rel);
                TextView txtmoney = helper.itemView.findViewById(R.id.txtmoney);
                txtmoney.setText(item.getName());
                if (item.isIscheck() == true) {
                    amount = item.getName();
                    edtamount.setText(item.getName() + "");
                    txtname.setBackgroundResource(R.drawable.shape_nobian_greenbac);
                    txtname.setSelected(true);
                } else {
                    txtname.setSelected(false);
                    txtname.setBackgroundResource(R.drawable.drawable_item_rechger);
                }
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (item.isIscheck() == false) {
                            for (int i = 0; i < rechargBeanList.size(); i++) {
                                if (rechargBeanList.get(i).getName().equals(item.getName())) {
                                    rechargBeanList.get(i).setIscheck(true);

                                } else {
                                    rechargBeanList.get(i).setIscheck(false);
                                }
                            }
                            mOneAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        };
        rec.setAdapter(mOneAdapter);
//
//        View inflate = LayoutInflater.from(RechargeActivity.this).inflate(R.layout.layout_empty_home2, null);
//        mOneAdapter.setEmptyView(inflate);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imgback, R.id.lincopy, R.id.linshare, R.id.btnpay, R.id.btnnext, R.id.linkefu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgback:
                finish();
                break;
            case R.id.lincopy:
                break;
            case R.id.linshare:
                break;
            case R.id.btnpay:
                break;
            case R.id.btnnext:
                Goto(ReUsdtTwoActivity.class);
                break;
            case R.id.linkefu:
                break;
        }
    }

    /**
     * 生成简单二维码
     *
     * @param content                字符串内容
     * @param width                  二维码宽度
     * @param height                 二维码高度
     * @param character_set          编码方式（一般使用UTF-8）
     * @param error_correction_level 容错率 L：7% M：15% Q：25% H：35%
     * @param margin                 空白边距（二维码与边框的空白区域）
     * @param color_black            黑色色块
     * @param color_white            白色色块
     * @return BitMap
     */
    public Bitmap createQRCodeBitmap(String content, int width, int height,
                                     String character_set, String error_correction_level,
                                     String margin, int color_black, int color_white) {
        // 字符串内容判空
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        // 宽和高>=0
        if (width < 0 || height < 0) {
            return null;
        }
        try {
            /** 1.设置二维码相关配置 */
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            // 字符转码格式设置
            if (!TextUtils.isEmpty(character_set)) {
                hints.put(EncodeHintType.CHARACTER_SET, character_set);
            }
            // 容错率设置
            if (!TextUtils.isEmpty(error_correction_level)) {
                hints.put(EncodeHintType.ERROR_CORRECTION, error_correction_level);
            }
            // 空白边距设置
            if (!TextUtils.isEmpty(margin)) {
                hints.put(EncodeHintType.MARGIN, margin);
            }
            /** 2.将配置参数传入到QRCodeWriter的encode方法生成BitMatrix(位矩阵)对象 */
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            /** 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    //bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = color_black;//黑色色块像素设置
                    } else {
                        pixels[y * width + x] = color_white;// 白色色块像素设置
                    }
                }
            }
            /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,并返回Bitmap对象 */
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);


            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
