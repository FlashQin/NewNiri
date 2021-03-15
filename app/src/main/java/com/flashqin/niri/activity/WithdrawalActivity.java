package com.flashqin.niri.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flashqin.niri.R;
import com.flashqin.niri.base.BaseActivity;
import com.flashqin.niri.bean.BaseBean;
import com.flashqin.niri.bean.CanShuBean;
import com.flashqin.niri.bean.IFSListBean;
import com.flashqin.niri.bean.IPBean;
import com.flashqin.niri.bean.RechargBean;
import com.flashqin.niri.bean.WalletMoneyBean;
import com.flashqin.niri.net.BaseObserver;
import com.flashqin.niri.utlis.CommonUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.rxjava.rxlife.RxLife;

import net.qiujuer.genius.ui.widget.Button;

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

public class WithdrawalActivity extends BaseActivity {

    @BindView(R.id.imgback)
    ImageView imgback;
    @BindView(R.id.txttitle)
    TextView txttitle;
    @BindView(R.id.txtmore)
    TextView txtmore;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txttotlenum)
    TextView txttotlenum;
    @BindView(R.id.edtname)
    EditText edtname;
    @BindView(R.id.txtbank)
    TextView txtbank;
    @BindView(R.id.relchoosebank)
    RelativeLayout relchoosebank;
    @BindView(R.id.edtmoney)
    EditText edtmoney;
    @BindView(R.id.btnall)
    Button btnall;
    @BindView(R.id.brnwd)
    Button brnwd;
    @BindView(R.id.spinner_kinds)
    NiceSpinner spinnerKinds;
    @BindView(R.id.edtcard)
    EditText edtcard;
    @BindView(R.id.edtphone)
    EditText edtphone;
    @BindView(R.id.edtemail)
    EditText edtemail;
    @BindView(R.id.lindocuid)
    LinearLayout lindocuid;
    @BindView(R.id.edtdocumentId)
    EditText edtdocumentId;
    @BindView(R.id.txttab1)
    TextView txttab1;
    @BindView(R.id.txttab2)
    TextView txttab2;
    @BindView(R.id.lintab)
    LinearLayout lintab;
    @BindView(R.id.edtaccount913)
    EditText edtaccount913;
    @BindView(R.id.edtaccountType)
    EditText edtaccountType;
    @BindView(R.id.txtBranchCode)
    TextView txtBranchCode;
    @BindView(R.id.spinner_kindsBranchCode)
    NiceSpinner spinnerKindsBranchCode;
    @BindView(R.id.relBranchCode)
    RelativeLayout relBranchCode;
    @BindView(R.id.edtDocumentId)
    EditText edtDocumentId;
    @BindView(R.id.edtAccountDigit)
    EditText edtAccountDigit;
    @BindView(R.id.linAccountDigit)
    LinearLayout linAccountDigit;
    @BindView(R.id.lin913)
    LinearLayout lin913;
    @BindView(R.id.lin914)
    LinearLayout lin914;
    @BindView(R.id.spinner_kindstype)
    NiceSpinner spinnerKindstype;
    @BindView(R.id.btnlook)
    Button btnlook;
    @BindView(R.id.lintype)
    LinearLayout lintype;
    private BaseQuickAdapter<RechargBean, BaseViewHolder> mOneAdapter;
    String ip = "", tongdao = "913", amount = "500", typeString = "CPF", bankString = "Itaú", leixingString = "Corrente";
    IFSListBean ifsListBean;
    BasePopupView popupView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_withrd;
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

        txttitle.setText("Withdrawal");

        ShowLoading();
        getUserMoney();
        initSpinner();
        getCanshu();
        getIP();

    }

    public void initAask() {
        popupView = new XPopup.Builder(getContext())
                .dismissOnBackPressed(true)
                .dismissOnTouchOutside(true)
                .hasNavigationBar(false)
                .isDestroyOnDismiss(true)
                .asConfirm("não", "Você deve ter PIX para receber pagamentos.",
                        "Não", "Sim",
                        new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                tongdao = "914";
                                txttab2.setBackgroundResource(R.drawable.drawable_txttab1);
                                txttab1.setBackgroundResource(R.drawable.drawable_txttab2);
                                txttab2.setTextColor(getResouseColor(R.color.white));
                                txttab1.setTextColor(getResouseColor(R.color.black));
                                lin914.setVisibility(View.VISIBLE);
                                lin913.setVisibility(View.GONE);
                            }
                        }, new OnCancelListener() {
                            @Override
                            public void onCancel() {

                                popupView.dismiss();
                            }
                        }, false);
        popupView.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.imgback, R.id.btnall, R.id.brnwd, R.id.txttab1, R.id.txttab2, R.id.btnlook})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgback:
                finish();
                break;
            case R.id.btnall:
                edtmoney.setText(txttotlenum.getText().toString());
                break;
            case R.id.brnwd:
                postMoney();



                break;
            case R.id.txttab1:
                tongdao = "913";
                txttab1.setBackgroundResource(R.drawable.drawable_txttab1);
                txttab2.setBackgroundResource(R.drawable.drawable_txttab2);
                txttab1.setTextColor(getResouseColor(R.color.white));
                txttab2.setTextColor(getResouseColor(R.color.black));
                lin913.setVisibility(View.VISIBLE);
                lin914.setVisibility(View.GONE);
                break;
            case R.id.txttab2:
                initAask();

                break;
            case R.id.btnlook:

                Goto(LookActivity.class);
                break;
        }
    }

    private void initSpinner() {
        List<String> strs = new ArrayList<>();

        RxHttp.get("https://pay.kaymu.vip/v1/orfeyt/bankcode/list")
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {

                        if (baseBean.getHead().getCode() == 1) {
                            ifsListBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), IFSListBean.class);

                            for (int i = 0; i < ifsListBean.getBody().getData().size(); i++) {
                                strs.add(ifsListBean.getBody().getData().get(i).getName() );
                            }
                            spinnerKinds.attachDataSource(strs);
                            spinnerKinds.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
                                @Override
                                public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                                    // This example uses String, but your type can be any
                                    bankString = parent.getItemAtPosition(position).toString();


                                }
                            });

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

                            txttotlenum.setText(homeListBean.getBody().getData().getBalance() + "");

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

                            tongdao = homeListBean.getBody().getData().getPayoutChannel();
                            if (tongdao.equals("913")) {//只显示913
                                lin913.setVisibility(View.VISIBLE);
                                txttab1.setVisibility(View.VISIBLE);
                                lin914.setVisibility(View.GONE);
                                txttab2.setVisibility(View.GONE);
                            }
                            if (tongdao.equals("914")) {//只显示914
                                lin914.setVisibility(View.VISIBLE);
                                lin913.setVisibility(View.GONE);
                                txttab1.setVisibility(View.GONE);
                                txttab2.setVisibility(View.VISIBLE);
                            }
                            if (tongdao.equals("900")) {//同时显示913和914
                                lin913.setVisibility(View.VISIBLE);
                                lin914.setVisibility(View.VISIBLE);
                                txttab1.setVisibility(View.VISIBLE);
                                txttab2.setVisibility(View.VISIBLE);
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

                            brnwd.setEnabled(true);

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

    public void postMoney() {//发起体现
        ShowLoading();
        if (edtemail.getText().toString().length() == 0 || edtname.getText().toString().length() == 0 || edtmoney.getText().toString().length() == 0) {
            ToastUtils.showShort("Please enter the data");
            return;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        String json = createJsonString(map, "walletId", SPUtils.getInstance().getString("id", "0"));
        json = createJsonString(map, "amount", edtmoney.getText().toString().trim());

        json = createJsonString(map, "acc_no", edtemail.getText().toString());
        json = createJsonString(map, "acc_name", edtname.getText().toString().trim());
        json = createJsonString(map, "bank_code", bankString);

        System.out.println("json----" + json);
        RxHttp.postJson("https://pay.amazoncash.vip/v1/WebPayToPay/applyWithdraw")
                .setJsonParams(json)
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        HideLoading();
                        if (baseBean.getHead().getCode() == 1) {
                            //  IPBean homeListBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), IPBean.class);

                            finish();
                            Goto(WithdrawalRecordActivity.class);

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
}
