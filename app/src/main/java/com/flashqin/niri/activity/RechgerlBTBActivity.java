package com.flashqin.niri.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flashqin.niri.R;
import com.flashqin.niri.adapter.SpacesItemDecoration;
import com.flashqin.niri.base.BaseActivity;
import com.flashqin.niri.bean.BaseBean;
import com.flashqin.niri.bean.CanShuBean;
import com.flashqin.niri.bean.IPBean;
import com.flashqin.niri.bean.NewPayBean;
import com.flashqin.niri.bean.NewPaySuccess;
import com.flashqin.niri.bean.RechargBean;
import com.flashqin.niri.bean.WalletMoneyBean;
import com.flashqin.niri.net.BaseObserver;
import com.gyf.immersionbar.ImmersionBar;
import com.rxjava.rxlife.RxLife;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;

public class RechgerlBTBActivity extends BaseActivity {

    @BindView(R.id.imgback)
    ImageView imgback;
    @BindView(R.id.txttitle)
    TextView txttitle;
    @BindView(R.id.txtmore)
    TextView txtmore;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtall)
    TextView txtall;
    @BindView(R.id.rec)
    RecyclerView rec;
    @BindView(R.id.edtaccount)
    EditText edtaccount;
    @BindView(R.id.edtname)
    EditText edtname;
    @BindView(R.id.edtemail)
    EditText edtemail;
    @BindView(R.id.btnsure)
    Button btnsure;
    @BindView(R.id.edtphone)
    EditText edtphone;
    @BindView(R.id.txtbank)
    TextView txtbank;
    @BindView(R.id.spinner_kinds)
    NiceSpinner spinnerKinds;
    @BindView(R.id.relchoosebank)
    RelativeLayout relchoosebank;
    @BindView(R.id.imgg)
    ImageView imgg;
    @BindView(R.id.txtlern)
    TextView txtlern;
    @BindView(R.id.btncreat)
    net.qiujuer.genius.ui.widget.Button btncreat;
    private BaseQuickAdapter<RechargBean, BaseViewHolder> mOneAdapter;
    String ip = "", tongdao = "", amount = "500", bancode = "100501";
    List<RechargBean> rechargBeanList = new ArrayList<>();
    String[] strName = {"2000", "5000", "10000", "50000", "100000", "300000"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_rechargbtb;
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
        getUserMoney();
    }

    @Override
    public void initView() {

        txttitle.setText("Bitcoin");

        ShowLoading();
        initAdapter();
    }

    public void initAdapter() {
//        List<String> strs = new ArrayList<>();
//        strs.add("Bank");
//        strs.add("Opay");
//        spinnerKinds.attachDataSource(strs);
//        spinnerKinds.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
//            @Override
//            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
//                // This example uses String, but your type can be any
//                // bankString = parent.getItemAtPosition(position).toString();
//
//                if (position == 0) {
//                    bancode = "100501";
//                } else {
//                    bancode = "100502";
//                }
//
//            }
//        });

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

        rec.setLayoutManager(new GridLayoutManager(RechgerlBTBActivity.this, 3));
        rec.addItemDecoration(new SpacesItemDecoration(20));
        mOneAdapter = new BaseQuickAdapter<RechargBean, BaseViewHolder>(R.layout.item_rechger, rechargBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, RechargBean item) {

                RelativeLayout txtname = helper.itemView.findViewById(R.id.rel);
                TextView txtmoney = helper.itemView.findViewById(R.id.txtmoney);
                TextView txtfuhao = helper.itemView.findViewById(R.id.txtfuhao);
                txtmoney.setText(item.getName());
                if (item.isIscheck() == true) {
                    amount = item.getName();

                    txtmoney.setTextColor(getResouseColor(R.color.white));
                    txtfuhao.setTextColor(getResouseColor(R.color.white));
                    txtname.setBackgroundResource(R.drawable.shape_nobian_bulebac);
                    txtname.setSelected(true);

                } else {
                    txtname.setSelected(false);
                    txtname.setBackgroundResource(R.drawable.drawable_item_rechger);
                    txtmoney.setTextColor(getResouseColor(R.color.black));
                    txtfuhao.setTextColor(getResouseColor(R.color.black));
                }
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edtaccount.setText(item.getName());
                        if (item.isIscheck() == false) {
                            for (int i = 0; i < rechargBeanList.size(); i++) {
                                if (rechargBeanList.get(i).getName().equals(item.getName())) {
                                    rechargBeanList.get(i).setIscheck(true);
                                    edtaccount.setText(item.getName() + "");
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

    public void getUserMoney() {//读取列表
        RxHttp.get("/v1/wallets/" + SPUtils.getInstance().getString("id", "0") + "/balances")
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        HideLoading();
                        if (baseBean.getHead().getCode() == 1) {
                            WalletMoneyBean homeListBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), WalletMoneyBean.class);

                            txtall.setText(homeListBean.getBody().getData().getBalance() + "");

                        } else

                            ToastUtils.showShort(baseBean.getHead().getMessage());
                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        HideLoading();
                    }
                });
    }

    public void getCanshu() {//读取平台参数
        RxHttp.get("/v1/admin/platform/parameters")
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {

                        if (baseBean.getHead().getCode() == 1) {
                            CanShuBean homeListBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), CanShuBean.class);

                            tongdao = homeListBean.getBody().getData().getPayChannel();
                            if (tongdao.equals("910")) {
                                //linname.setVisibility(View.VISIBLE);
                            }
                            getIP();

                        } else

                            ToastUtils.showShort(baseBean.getHead().getMessage());
                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        HideLoading();
                    }
                });
    }

    public void getIP() {//客户端IP地址
        RxHttp.get("/v1/common/ip")
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        HideLoading();
                        if (baseBean.getHead().getCode() == 1) {
                            IPBean homeListBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), IPBean.class);
                            ip = homeListBean.getBody().getData();
                            btnsure.setEnabled(true);
                            btnsure.setEnabled(true);

                        } else

                            ToastUtils.showShort(baseBean.getHead().getMessage());
                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        HideLoading();
                    }
                });
    }

    public void getPayLink() {//获取这个支付链接
        ShowLoading();
        Map<String, Object> map = new HashMap<String, Object>();
        String json = createJsonString(map, "walletId", SPUtils.getInstance().getString("id", "0"));
        json = createJsonString(map, "amount", edtaccount.getText().toString());
        json = createJsonString(map, "pemail", edtemail.getText().toString());
        json = createJsonString(map, "pname", edtname.getText().toString());
        json = createJsonString(map, "phone", edtphone.getText().toString());
        json = createJsonString(map, "obcode", bancode);
        // json = createJsonString(map, "email", edtemail.getText().toString().trim());//910，911通道毕传
        // if (tongdao.equals("910")) {
        // json = createJsonString(map, "contactName", edtname.getText().toString().trim());//910通道毕传
        // }
        System.out.println("------------" + json);
        RxHttp.postJson("https://pay.kaymu.vip/v1/orfeyt/preRecharge")
                .setJsonParams(json)
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        HideLoading();
                        if (baseBean.getHead().getCode() == 1) {
                            NewPaySuccess homeListBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), NewPaySuccess.class);
//                            Map<String, Object> map = new HashMap<String, Object>();
//                            String json = createJsonString(map, "amount", homeListBean.getBody().getData().getAmount());
//                            json = createJsonString(map, "appId", homeListBean.getBody().getData().getAppId());
//                            json = createJsonString(map, "applyDate", homeListBean.getBody().getData().getApplyDate());
//                            json = createJsonString(map, "channel", homeListBean.getBody().getData().getChannel());
//                            json = createJsonString(map, "clientIp", homeListBean.getBody().getData().getClientIp());
//                            json = createJsonString(map, "clientSn", homeListBean.getBody().getData().getClientSn());
//                            json = createJsonString(map, "notifyUrl", homeListBean.getBody().getData().getNotifyUrl());
//                            json = createJsonString(map, "outOrderNo", homeListBean.getBody().getData().getOutOrderNo());
//                            json = createJsonString(map, "sign", homeListBean.getBody().getData().getSign());
//                            json = createJsonString(map, "userId", homeListBean.getBody().getData().getUserId());
                            // json = createJsonString(map, "email", homeListBean.getBody().getData().getEmail());
//                            if (tongdao.equals("910")) {
//                                //  json = createJsonString(map, "contactName", edtname.getText().toString());
//                            }
                            //  json = createJsonString(map, "contactName", edtname.getText().toString());
//                            json = "https://h5.kaymu.vip/pay.html?channel=" + homeListBean.getBody().getData().getChannel() + "&data=" + json;
//
//                            System.out.println("111111111///" + json);
                            Goto(WebActivity.class, "json", homeListBean.getBody().getData().getOrder_data());
                            ToastUtils.showShort("Succse");

                        } else

                            ToastUtils.showShort(baseBean.getHead().getMessage());
                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        HideLoading();
                    }
                });
    }

    public void getBTCLink() {//获取这个支付链接
        ShowLoading();
        Map<String, Object> map = new HashMap<String, Object>();
        String json = createJsonString(map, "fiatAmount", Double.parseDouble(amount));
        json = createJsonString(map, "email", edtemail.getText().toString().trim());//910，911通道毕传
        json = createJsonString(map, "name", edtname.getText().toString().trim());//910通道毕传
        System.out.println("------------" + json);
        RxHttp.postJson("https://pay.zxm88.net/v1/members/" + SPUtils.getInstance().getString("id", "0") + "/bitcoin/paxful")
                .setJsonParams(json)
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        HideLoading();
                        if (baseBean.getHead().getCode() == 1) {
                            NewPayBean homeListBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), NewPayBean.class);
                            Goto(WebActivity.class, "json", homeListBean.getBody().getData().toString());

                        } else

                            ToastUtils.showShort(baseBean.getHead().getMessage());
                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        HideLoading();
                    }
                });
    }

    @OnClick({R.id.imgback, R.id.btnsure,R.id.txtlern, R.id.btncreat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgback:
                finish();
                break;
            case R.id.btnsure:
                if (edtname.getText().toString().length() == 0) {

                    ToastUtils.showShort("please input your name");
                    return;
                }
                if (edtemail.getText().toString().length() == 0) {
                    ToastUtils.showShort("please input your email");
                    return;
                }

                if (edtphone.getText().toString().length() == 0) {
                    ToastUtils.showShort("please input right phone number");
                    return;
                }
                if (edtaccount.getText().toString().trim().length() != 0) {
                    double money = Double.parseDouble(edtaccount.getText().toString().trim());
                    if (money < 2000) {

                        ToastUtils.showShort("Withdrawal of at least 100N");
                        return;
                    }
                    if (money > 500000) {

                        ToastUtils.showShort("The single maximum load is 50000N");
                        return;
                    }
                }
                getPayLink();
                break;
            case R.id.txtlern:
                break;
            case R.id.btncreat:
                break;
        }
    }


}
