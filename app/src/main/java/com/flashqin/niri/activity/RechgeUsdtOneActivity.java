package com.flashqin.niri.activity;

import android.content.Intent;
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

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flashqin.niri.R;
import com.flashqin.niri.adapter.SpacesItemDecoration;
import com.flashqin.niri.base.BaseActivity;
import com.flashqin.niri.bean.RechargBean;
import com.flashqin.niri.utlis.CommonUtils;
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
    TextView edtamount;
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
    String[] strName = {"50000", "100000", "300000", "500000", "1000000"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_usdtone;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(toolbar) //???????????????view?????????????????????xml???????????????????????????????????????????????????RelativeLayout??????ConstraintLayout??????????????????
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
                    edtamount.setText(roundByScale(Double.valueOf(item.getName())*0.0026,0)+ "");
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
    private void shareContent() {
        String link = txtlink.getText().toString();

        Intent share_intent = new Intent();

        share_intent.setAction(Intent.ACTION_SEND);

        share_intent.setType("text/plain");

        share_intent.putExtra(Intent.EXTRA_SUBJECT, "Share");

        share_intent.putExtra(Intent.EXTRA_TEXT, link);

        share_intent = Intent.createChooser(share_intent, "Share");

        startActivity(share_intent);
    }
    @OnClick({R.id.imgback, R.id.lincopy, R.id.linshare, R.id.btnpay, R.id.btnnext, R.id.linkefu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgback:
                finish();
                break;
            case R.id.lincopy:
                ToastUtils.showShort("Copy Success");
                CommonUtils.copyToClipboard(RechgeUsdtOneActivity.this, txtlink.getText().toString());
                break;
            case R.id.linshare:

                shareContent();
                break;
            case R.id.btnpay:
                finish();
                break;
            case R.id.btnnext:
                Goto(ReUsdtTwoActivity.class,"amount",amount);
                break;
            case R.id.linkefu:
                break;
        }
    }

    /**
     * ?????????????????????
     *
     * @param content                ???????????????
     * @param width                  ???????????????
     * @param height                 ???????????????
     * @param character_set          ???????????????????????????UTF-8???
     * @param error_correction_level ????????? L???7% M???15% Q???25% H???35%
     * @param margin                 ???????????????????????????????????????????????????
     * @param color_black            ????????????
     * @param color_white            ????????????
     * @return BitMap
     */
    public Bitmap createQRCodeBitmap(String content, int width, int height,
                                     String character_set, String error_correction_level,
                                     String margin, int color_black, int color_white) {
        // ?????????????????????
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        // ?????????>=0
        if (width < 0 || height < 0) {
            return null;
        }
        try {
            /** 1.??????????????????????????? */
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            // ????????????????????????
            if (!TextUtils.isEmpty(character_set)) {
                hints.put(EncodeHintType.CHARACTER_SET, character_set);
            }
            // ???????????????
            if (!TextUtils.isEmpty(error_correction_level)) {
                hints.put(EncodeHintType.ERROR_CORRECTION, error_correction_level);
            }
            // ??????????????????
            if (!TextUtils.isEmpty(margin)) {
                hints.put(EncodeHintType.MARGIN, margin);
            }
            /** 2.????????????????????????QRCodeWriter???encode????????????BitMatrix(?????????)?????? */
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            /** 3.??????????????????,?????????BitMatrix(?????????)????????????????????????????????? */
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    //bitMatrix.get(x,y)????????????true??????????????????false???????????????
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = color_black;//????????????????????????
                    } else {
                        pixels[y * width + x] = color_white;// ????????????????????????
                    }
                }
            }
            /** 4.??????Bitmap??????,????????????????????????Bitmap???????????????????????????,?????????Bitmap?????? */
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);


            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
