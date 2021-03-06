package com.flashqin.niri.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flashqin.niri.R;
import com.flashqin.niri.activity.GongziRecordActivity;
import com.flashqin.niri.activity.LoginActivity;
import com.flashqin.niri.activity.RechageRecordActivity;
import com.flashqin.niri.activity.RechgeBaseActivity;
import com.flashqin.niri.activity.RechgerlActivity;
import com.flashqin.niri.activity.ShuadanRecordActivity;
import com.flashqin.niri.activity.WithdrawalActivity;
import com.flashqin.niri.activity.WithdrawalBaseActivity;
import com.flashqin.niri.activity.WithdrawalRecordActivity;
import com.flashqin.niri.activity.YongJinRecordActivity;
import com.flashqin.niri.adapter.SpacesItemDecoration;
import com.flashqin.niri.base.BaseFragment;
import com.flashqin.niri.bean.BaseBean;
import com.flashqin.niri.bean.UserMoneyDataBean;
import com.flashqin.niri.bean.WallDataBean;
import com.flashqin.niri.bean.WalletBaseBean;
import com.flashqin.niri.net.BaseObserver;
import com.flashqin.niri.utlis.ACache;
import com.rxjava.rxlife.RxLife;

import net.qiujuer.genius.kit.handler.Run;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class WalletFragment extends BaseFragment {


    @BindView(R.id.txt1)
    TextView txt1;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.txt3)
    TextView txt3;
    @BindView(R.id.txthi)
    TextView txthi;
    @BindView(R.id.txtid)
    TextView txtid;
    @BindView(R.id.txtblance)
    TextView txtblance;
    @BindView(R.id.linwd)
    LinearLayout linwd;
    @BindView(R.id.linre)
    LinearLayout linre;
    @BindView(R.id.rec1)
    RecyclerView rec1;
    @BindView(R.id.txtlv)
    TextView txtlv;
    @BindView(R.id.btnexit)
    LinearLayout btnexit;
    @BindView(R.id.linexit)
    LinearLayout linexit;
    @BindView(R.id.txttabone)
    TextView txttabone;
    @BindView(R.id.txttabtwo)
    TextView txttabtwo;
    @BindView(R.id.conbacpic)
    ConstraintLayout conbacpic;
    @BindView(R.id.linbacpic)
    LinearLayout linbacpic;
    @BindView(R.id.linbacbase)
    LinearLayout linbacbase;
    private BaseQuickAdapter<WallDataBean, BaseViewHolder> mOneAdapter;
    String[] wallitem = {"Total\n top-up", "Total\n withdrawal", "Revenue\n amounts", "Commission\n amounts", "salary"};//, "salary"

    List<String> moneylist = new ArrayList<>();
    List<WallDataBean> wallDataBeanList = new ArrayList<>();
    UserMoneyDataBean userMoneyDataBean, userMoneyDataBeanBTC;
    WalletBaseBean danInfoBean, danInfoBeanBTC;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wallet;
    }


    @Override
    protected void fetchData() {

    }

    @Override
    protected void initData() {

    }


    @Override
    public void onResume() {
        super.onResume();

        getMenberInfoData();
        getMenberInfoDataBTC();
        getInfo();
        getInfoBTC();
        // getNoticeList();
    }

    @Override
    protected void initView(View view) {

        initAadpter();


    }

    public void initAadpter() {

        rec1.setLayoutManager(new LinearLayoutManager(getActivity()));
        rec1.addItemDecoration(new SpacesItemDecoration(10));
        mOneAdapter = new BaseQuickAdapter<WallDataBean, BaseViewHolder>(R.layout.item_wallet) {
            @Override
            protected void convert(BaseViewHolder helper, WallDataBean item) {

                TextView txtname = helper.itemView.findViewById(R.id.txtname);
                TextView txtnum = helper.itemView.findViewById(R.id.txtnum);

                txtname.setText(item.getName());
                txtnum.setText("??? " + item.getMoney());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (item.getName()) {
                            case "Total\n top-up":
                                Goto(RechageRecordActivity.class);
                                break;
                            case "Total\n withdrawal":
                                Goto(WithdrawalRecordActivity.class);
                                break;
                            case "Revenue\n amounts":
                                Goto(ShuadanRecordActivity.class);
                                break;
                            case "Commission\n amounts":
                                Goto(YongJinRecordActivity.class);
                                break;
                            case "salary":
                                Goto(GongziRecordActivity.class);
                                break;
                        }

                    }
                });
            }
        };


        // mOneAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        rec1.setAdapter(mOneAdapter);


    }

    public void getMenberInfoData() {//????????????????????????:
        RxHttp.get("/v1/members/" + SPUtils.getInstance().getString("id", "0") + "/info")
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {

                        if (baseBean.getHead().getCode() == 1) {
                            userMoneyDataBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), UserMoneyDataBean.class);

                            try {
//                                txthi.setText("Hi " + userMoneyDataBean.getBody().getData().getName());
//                                txtblance.setText(userMoneyDataBean.getBody().getData().getBalance() + "");
//
//                                txtid.setText("ID???" + userMoneyDataBean.getBody().getData().getMobile() + "");
//                                txtlv.setText("Level " + userMoneyDataBean.getBody().getData().getLevel());
                            } catch (NullPointerException e) {

                            }

                        } else

                            ToastUtils.showShort(baseBean.getHead().getMessage());
                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });
    }

    public void getMenberInfoDataBTC() {//????????????????????????:
        RxHttp.get("/v1/members/" + SPUtils.getInstance().getString("id", "0") + "/info?type=1")
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {

                        if (baseBean.getHead().getCode() == 1) {
                            userMoneyDataBeanBTC = JSONObject.parseObject(JSONObject.toJSONString(baseBean), UserMoneyDataBean.class);

                            try {
                                txthi.setText("Hi " + userMoneyDataBean.getBody().getData().getName());
                                txtblance.setText(userMoneyDataBean.getBody().getData().getBalance() + "");

                                txtid.setText("ID???" + userMoneyDataBean.getBody().getData().getMobile() + "");
                                txtlv.setText("Level " + userMoneyDataBean.getBody().getData().getLevel());
                            } catch (NullPointerException e) {

                            }

                        } else

                            ToastUtils.showShort(baseBean.getHead().getMessage());
                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });
    }

    public void getInfo() {//????????????
        RxHttp.get("v1/members/" + SPUtils.getInstance().getString("id", "0") + "/earnings")
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        //  HideLoading();
                        if (baseBean.getHead().getCode() == 1) {
                            // getMenberInfoData();
                            //getNoticeList();
                            //wallDataBeanList.clear();

                            danInfoBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), WalletBaseBean.class);

//                            moneylist.add(danInfoBean.getBody().getData().getTotalRecharge() + "");
//                            moneylist.add(danInfoBean.getBody().getData().getTotalWithdraw() + "");
//                            moneylist.add(danInfoBean.getBody().getData().getTotalRevenue() + "");
//                            moneylist.add(danInfoBean.getBody().getData().getTotalCommission() + "");
//                            moneylist.add(danInfoBean.getBody().getData().getTotalSalary() + "");
//                            for (int i = 0; i < wallitem.length; i++) {
//                                WallDataBean wallDataBean = new WallDataBean();
//                                wallDataBean.setName(wallitem[i]);
//                                wallDataBean.setMoney(moneylist.get(i));
//                                wallDataBeanList.add(wallDataBean);
//                            }
//
//                            mOneAdapter.setNewData(wallDataBeanList);

                        }
                        // ToastUtils.showShort(baseBean.getHead().getMessage());
                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        //   HideLoading();
                    }
                });
    }

    public void getInfoBTC() {//????????????
        RxHttp.get("v1/members/" + SPUtils.getInstance().getString("id", "0") + "/earnings?type=1")
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        //  HideLoading();
                        if (baseBean.getHead().getCode() == 1) {
                            // getMenberInfoData();
                            //getNoticeList();
                            wallDataBeanList.clear();
                            moneylist.clear();
                            danInfoBeanBTC = JSONObject.parseObject(JSONObject.toJSONString(baseBean), WalletBaseBean.class);

                            moneylist.add(danInfoBeanBTC.getBody().getData().getTotalRecharge() + "");
                            moneylist.add(danInfoBeanBTC.getBody().getData().getTotalWithdraw() + "");
                            moneylist.add(danInfoBeanBTC.getBody().getData().getTotalRevenue() + "");
                            moneylist.add(danInfoBeanBTC.getBody().getData().getTotalCommission() + "");
                            moneylist.add(danInfoBeanBTC.getBody().getData().getTotalSalary() + "");
                            for (int i = 0; i < wallitem.length; i++) {
                                WallDataBean wallDataBean = new WallDataBean();
                                wallDataBean.setName(wallitem[i]);
                                wallDataBean.setMoney(moneylist.get(i));
                                wallDataBeanList.add(wallDataBean);
                            }

                            mOneAdapter.setNewData(wallDataBeanList);

                        }
                        // ToastUtils.showShort(baseBean.getHead().getMessage());
                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        //   HideLoading();
                    }
                });
    }

    @OnClick({R.id.linwd, R.id.linre, R.id.linexit, R.id.txttabone, R.id.txttabtwo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linwd:
                Goto(WithdrawalBaseActivity.class);
                break;
            case R.id.linre:
                Goto(RechgeBaseActivity.class);
                break;
            case R.id.linexit:

                Run.onUiAsync(() -> {
                    ACache.get(Utils.getApp()).remove("USER_BEAN");
                    SPUtils.getInstance().remove("TOKEN");
                    SPUtils.getInstance().remove("id");
                });
                // RxBus.getDefault().post("", "Exit");
                Goto(LoginActivity.class, FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().finish();
                break;
            case R.id.txttabone:
                txttabone.setBackgroundResource(R.drawable.drawable_wallone);
                txttabtwo.setBackgroundResource(0);
                linbacbase.setBackgroundColor(getResources().getColor(R.color.color_wall_btb));
                linbacpic.setBackgroundResource(R.drawable.bg_wallet);
                conbacpic.setBackgroundResource(R.drawable.bg_qb_ngn);
                img2.setBackgroundResource(R.drawable.icon_rsbtc);
                txt3.setText("Bitcoin wallet is equivalent to ");
                try {
                    txthi.setText("Hi " + userMoneyDataBean.getBody().getData().getName());
                    txtblance.setText(userMoneyDataBean.getBody().getData().getBalance() + "");

                    txtid.setText("ID???" + userMoneyDataBean.getBody().getData().getMobile() + "");
                    txtlv.setText("Level " + userMoneyDataBean.getBody().getData().getLevel());

                    wallDataBeanList.clear();
                    moneylist.clear();
                    moneylist.add(danInfoBeanBTC.getBody().getData().getTotalRecharge() + "");
                    moneylist.add(danInfoBeanBTC.getBody().getData().getTotalWithdraw() + "");
                    moneylist.add(danInfoBeanBTC.getBody().getData().getTotalRevenue() + "");
                    moneylist.add(danInfoBeanBTC.getBody().getData().getTotalCommission() + "");
                    moneylist.add(danInfoBeanBTC.getBody().getData().getTotalSalary() + "");
                    for (int i = 0; i < wallitem.length; i++) {
                        WallDataBean wallDataBean = new WallDataBean();
                        wallDataBean.setName(wallitem[i]);
                        wallDataBean.setMoney(moneylist.get(i));
                        wallDataBeanList.add(wallDataBean);
                    }

                    mOneAdapter.setNewData(wallDataBeanList);
                } catch (NullPointerException e) {

                }
                break;
            case R.id.txttabtwo:
                txttabtwo.setBackgroundResource(R.drawable.drawable_walltwo);
                txttabone.setBackgroundResource(0);
                linbacbase.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                linbacpic.setBackgroundResource(R.drawable.bg_wallet2);
                conbacpic.setBackgroundResource(R.drawable.btb);

                img2.setBackgroundResource(R.drawable.icon_rs);
                txt3.setText("NGN credit balance");

                try {
                    txthi.setText("Hi " + userMoneyDataBeanBTC.getBody().getData().getName());
                    txtblance.setText(userMoneyDataBeanBTC.getBody().getData().getBalance() + "");

                    txtid.setText("ID???" + userMoneyDataBeanBTC.getBody().getData().getMobile() + "");
                    txtlv.setText("Level " + userMoneyDataBeanBTC.getBody().getData().getLevel());

                    wallDataBeanList.clear();
                    moneylist.clear();
                    moneylist.add(danInfoBean.getBody().getData().getTotalRecharge() + "");
                    moneylist.add(danInfoBean.getBody().getData().getTotalWithdraw() + "");
                    moneylist.add(danInfoBean.getBody().getData().getTotalRevenue() + "");
                    moneylist.add(danInfoBean.getBody().getData().getTotalCommission() + "");
                    moneylist.add(danInfoBean.getBody().getData().getTotalSalary() + "");
                    for (int i = 0; i < wallitem.length; i++) {
                        WallDataBean wallDataBean = new WallDataBean();
                        wallDataBean.setName(wallitem[i]);
                        wallDataBean.setMoney(moneylist.get(i));
                        wallDataBeanList.add(wallDataBean);
                    }

                    mOneAdapter.setNewData(wallDataBeanList);
                } catch (NullPointerException e) {

                }
                break;
        }
    }

}
