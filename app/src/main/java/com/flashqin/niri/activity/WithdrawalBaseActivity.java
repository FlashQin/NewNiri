package com.flashqin.niri.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flashqin.niri.R;
import com.flashqin.niri.base.BaseActivity;
import com.flashqin.niri.bean.BaseBean;
import com.flashqin.niri.bean.WalletMoneyBean;
import com.flashqin.niri.net.BaseObserver;
import com.gyf.immersionbar.ImmersionBar;
import com.rxjava.rxlife.RxLife;

import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;

public class WithdrawalBaseActivity extends BaseActivity {

    @BindView(R.id.imgback)
    ImageView imgback;
    @BindView(R.id.txttitle)
    TextView txttitle;
    @BindView(R.id.txtmore)
    TextView txtmore;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edtmoney)
    EditText edtmoney;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.txt3)
    TextView txt3;
    @BindView(R.id.txtblance)
    TextView txtblance;
    @BindView(R.id.conbacpic)
    ConstraintLayout conbacpic;
    @BindView(R.id.imgcheck)
    ImageView imgcheck;
    @BindView(R.id.relngn)
    RelativeLayout relngn;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.txt4)
    TextView txt4;
    @BindView(R.id.txtblancebtc)
    TextView txtblancebtc;
    @BindView(R.id.conbtcbacpic)
    ConstraintLayout conbtcbacpic;
    @BindView(R.id.imgcheckbtc)
    ImageView imgcheckbtc;
    @BindView(R.id.relbtc)
    RelativeLayout relbtc;
    @BindView(R.id.brnwd)
    Button brnwd;

    @Override
    public int getLayoutId() {
        return R.layout.activity_withrdbase;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(toolbar) //可以为任意view，如果是自定义xml实现标题栏的话，标题栏根节点不能为RelativeLayout或者ConstraintLayout，以及其子类
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    public void initView() {

        txttitle.setText("Withdrawal Type");

        getUserMoney();
        getUserMoneyBTC();


    }

    public void getUserMoney() {//读取列表
        RxHttp.get("/v1/wallets/" + SPUtils.getInstance().getString("id", "0") + "/balances")
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {

                        if (baseBean.getHead().getCode() == 1) {
                            WalletMoneyBean homeListBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), WalletMoneyBean.class);

                            txtblance.setText(homeListBean.getBody().getData().getBalance() + "");

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
    public void getUserMoneyBTC() {//读取列表
        RxHttp.get("/v1/wallets/" + SPUtils.getInstance().getString("id", "0") + "/balances?type=1")
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {

                        if (baseBean.getHead().getCode() == 1) {
                            WalletMoneyBean homeListBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), WalletMoneyBean.class);

                            txtblancebtc.setText(homeListBean.getBody().getData().getBalance() + "");

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.imgback, R.id.relngn, R.id.relbtc, R.id.brnwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgback:
                finish();
                break;
            case R.id.relngn:
                imgcheck.setBackgroundResource(R.drawable.ion_check);
                imgcheckbtc.setBackgroundResource(R.drawable.icon_check_no);
                break;
            case R.id.relbtc:
                imgcheckbtc.setBackgroundResource(R.drawable.ion_check);
                imgcheck.setBackgroundResource(R.drawable.icon_check_no);
                break;
            case R.id.brnwd:
                if (edtmoney.getText().toString().length()==0){
                    ToastUtils.showShort("please enter withdrawal amount");
                    return;
                }
                Goto(WithdrawalActivity.class);
                break;
        }
    }
}
