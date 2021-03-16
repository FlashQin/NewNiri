package com.flashqin.niri.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.flashqin.niri.R;
import com.flashqin.niri.adapter.SpacesItemDecoration;
import com.flashqin.niri.base.BaseActivity;
import com.flashqin.niri.bean.BaseBean;
import com.flashqin.niri.bean.TixianRecoderBean;
import com.flashqin.niri.net.BaseObserver;
import com.gyf.immersionbar.ImmersionBar;
import com.rxjava.rxlife.RxLife;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;

public class WithdrawalRecordActivity extends BaseActivity {
    @BindView(R.id.imgback)
    ImageView imgback;
    @BindView(R.id.txttitle)
    TextView txttitle;
    @BindView(R.id.txtmore)
    TextView txtmore;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rec)
    RecyclerView rec;
    private BaseQuickAdapter<TixianRecoderBean.BodyBean.DataBean, BaseViewHolder> mOneAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdrawalrecord;
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

        ShowLoading();
        txttitle.setText("Withdrawal record ");
        initAdapter();
        getList();
    }

    public void initAdapter() {


        rec.setLayoutManager(new LinearLayoutManager(WithdrawalRecordActivity.this));
        rec.addItemDecoration(new SpacesItemDecoration(20));
        mOneAdapter = new BaseQuickAdapter<TixianRecoderBean.BodyBean.DataBean, BaseViewHolder>(R.layout.item_wdrecoder) {
            @Override
            protected void convert(BaseViewHolder helper, TixianRecoderBean.BodyBean.DataBean item) {
                TextView txtname = helper.itemView.findViewById(R.id.txttime);
                TextView txtamount = helper.itemView.findViewById(R.id.txtamount);
                TextView txttype = helper.itemView.findViewById(R.id.txttype);
                String time1 = item.getCreatedAt().getDate().getMonth() + "-" + item.getCreatedAt().getDate().getDay() + "-" + item.getCreatedAt().getDate().getYear();
                String time2 = item.getCreatedAt().getTime().getHour() + ":" + item.getCreatedAt().getTime().getMinute() + ":" + item.getCreatedAt().getTime().getSecond();

                txtname.setText(time1 + " " + time2);
                txtamount.setText(item.getAmount() + "₦");
                switch (item.getStatus()) {
                    case 0:
                        txttype.setText("Applied");
                        break;
                    case 10:
                        txttype.setText("Processing");
                        break;
                    case 11:
                        txttype.setText("succeeded");
                        break;
                    case 12:
                        txttype.setText("failed");
                        break;
                }

            }
        };
        rec.setAdapter(mOneAdapter);

        View inflate = LayoutInflater.from(WithdrawalRecordActivity.this).inflate(R.layout.layout_empty_home2, null);
        mOneAdapter.setEmptyView(inflate);

    }

    public void getList() {//客户端IP地址
        Map<String, Object> map = new HashMap<String, Object>();
        String json = createJsonString(map, "type", -1);

        RxHttp.get("/v1/wallets/" + SPUtils.getInstance().getString("id", "0") + "/withdraw-journals?limit=10")
                .setJsonParams(json)
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        HideLoading();
                        if (baseBean.getHead().getCode() == 1) {
                            TixianRecoderBean homeListBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), TixianRecoderBean.class);

                            mOneAdapter.addData(homeListBean.getBody().getData());

                        }
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

    @OnClick(R.id.imgback)
    public void onClick() {
        finish();
    }
}
