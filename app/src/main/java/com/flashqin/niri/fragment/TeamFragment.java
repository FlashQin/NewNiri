package com.flashqin.niri.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.flashqin.niri.R;
import com.flashqin.niri.activity.LoginActivity;
import com.flashqin.niri.base.BaseFragment;
import com.flashqin.niri.bean.BaseBean;
import com.flashqin.niri.bean.NewTeamBean;
import com.flashqin.niri.bean.TeamMoneyBean;
import com.flashqin.niri.bean.UserMoneyDataBean;
import com.flashqin.niri.net.BaseObserver;
import com.flashqin.niri.utlis.ACache;
import com.flashqin.niri.utlis.CommonUtils;
import com.rxjava.rxlife.RxLife;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class TeamFragment extends BaseFragment {


    @BindView(R.id.linbar)
    LinearLayout linbar;
    @BindView(R.id.imgyou)
    ImageView imgyou;
    @BindView(R.id.txtamount)
    TextView txtamount;
    @BindView(R.id.txtamountnum)
    TextView txtamountnum;
    @BindView(R.id.txtperson)
    TextView txtperson;
    @BindView(R.id.txtpersonnum)
    TextView txtpersonnum;
    @BindView(R.id.txta2)
    TextView txta2;
    @BindView(R.id.txta1)
    TextView txta1;
    @BindView(R.id.txta3)
    TextView txta3;
    @BindView(R.id.txtb2)
    TextView txtb2;
    @BindView(R.id.txtb1)
    TextView txtb1;
    @BindView(R.id.txtb3)
    TextView txtb3;
    @BindView(R.id.txtc2)
    TextView txtc2;
    @BindView(R.id.txtc1)
    TextView txtc1;
    @BindView(R.id.txtc3)
    TextView txtc3;
    @BindView(R.id.txtd2)
    TextView txtd2;
    @BindView(R.id.txtd1)
    TextView txtd1;
    @BindView(R.id.txtd3)
    TextView txtd3;
    @BindView(R.id.txte2)
    TextView txte2;
    @BindView(R.id.txte1)
    TextView txte1;
    @BindView(R.id.txte3)
    TextView txte3;
    @BindView(R.id.btninvite)
    Button btninvite;
    @BindView(R.id.txtlink)
    TextView txtlink;
    @BindView(R.id.lin2)
    LinearLayout lin2;
    @BindView(R.id.txt6)
    TextView txt6;
    @BindView(R.id.txtjiangnum)
    TextView txtjiangnum;
    @BindView(R.id.dsf)
    TextView dsf;
    @BindView(R.id.dfsgsd)
    ConstraintLayout dfsgsd;
    @BindView(R.id.linexit)
    LinearLayout linexit;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_team;
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

        //
        getDataList();
    }

    @Override
    protected void initView(View view) {
        //getMenberInfoData();

        getMoney();
        String link = "https://h5.kaymu.vip\n/register?code=" + CommonUtils.ShareCode;
        txtlink.setText(link);
    }

    public void getDataList() {//读取列表

        RxHttp.get("/v1/members/" + SPUtils.getInstance().getString("id", "0") + "/team")
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        HideLoading();
                        if (baseBean.getHead().getCode() == 1) {
                            NewTeamBean homeListBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), NewTeamBean.class);

                            txta1.setText(homeListBean.getBody().getData().getPrimaryShareAmount() + "");
                            txta3.setText(homeListBean.getBody().getData().getPrimaryMemberCount() + "");

                            txtb1.setText(homeListBean.getBody().getData().getSecondaryShareAmount() + "");
                            txtb3.setText(homeListBean.getBody().getData().getSecondaryMemberCount() + "");

                            txtc1.setText(homeListBean.getBody().getData().getRecessiveShareAmount() + "");
                            txtc3.setText(homeListBean.getBody().getData().getRecessiveMemberCount() + "");

                            txtd1.setText(homeListBean.getBody().getData().getRecessive2ShareAmount() + "");
                            txtd3.setText(homeListBean.getBody().getData().getRecessive2MemberCount() + "");

                            txte1.setText(homeListBean.getBody().getData().getRecessive3ShareAmount() + "");
                            txte3.setText(homeListBean.getBody().getData().getRecessive3MemberCount() + "");
                            double totleamount = homeListBean.getBody().getData().getPrimaryShareAmount()
                                    + homeListBean.getBody().getData().getSecondaryShareAmount()
                                    + homeListBean.getBody().getData().getRecessiveShareAmount()
                                    + homeListBean.getBody().getData().getRecessive2ShareAmount()
                                    + homeListBean.getBody().getData().getRecessive3ShareAmount();
                            int totleperson = homeListBean.getBody().getData().getPrimaryMemberCount()
                                    + homeListBean.getBody().getData().getSecondaryMemberCount()
                                    + homeListBean.getBody().getData().getRecessiveMemberCount()
                                    + homeListBean.getBody().getData().getRecessive2MemberCount()
                                    + homeListBean.getBody().getData().getRecessive3MemberCount();

                            txtamountnum.setText(totleamount + "");
                            txtpersonnum.setText(totleperson + "");

                        } else

                            ToastUtils.showShort(baseBean.getHead().getMessage());
                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        //   HideLoading();
                    }
                });
    }

    public void getMenberInfoData() {//读取个人概要数据:
        RxHttp.get("/v1/members/" + SPUtils.getInstance().getString("id", "0") + "/info")
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {

                        if (baseBean.getHead().getCode() == 1) {
                            UserMoneyDataBean userMoneyDataBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), UserMoneyDataBean.class);

                            CommonUtils.ShareCode = userMoneyDataBean.getBody().getData().getShareCode();

                            try {


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

    public void getMoney() {//读取个人概要数据:
        RxHttp.get("/v1/members/" + SPUtils.getInstance().getString("id", "0") + "/referee-bonus")
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {

                        if (baseBean.getHead().getCode() == 1) {
                            TeamMoneyBean userMoneyDataBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), TeamMoneyBean.class);


                            txtjiangnum.setText(userMoneyDataBean.getBody().getData() + "");
                            try {


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

    private void shareContent() {
        String link = "https://h5.kaymu.vip/register?code=" + CommonUtils.ShareCode;

        Intent share_intent = new Intent();

        share_intent.setAction(Intent.ACTION_SEND);

        share_intent.setType("text/plain");

        share_intent.putExtra(Intent.EXTRA_SUBJECT, "Share");

        share_intent.putExtra(Intent.EXTRA_TEXT, link);

        share_intent = Intent.createChooser(share_intent, "Share");

        startActivity(share_intent);
    }

    @OnClick({R.id.btninvite, R.id.txtlink, R.id.linexit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btninvite:
                shareContent();
                break;
            case R.id.txtlink:
                ToastUtils.showShort("Copy Success");
                CommonUtils.copyToClipboard(getActivity(), txtlink.getText().toString());
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
        }
    }
}
