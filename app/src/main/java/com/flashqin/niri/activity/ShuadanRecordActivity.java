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
import com.flashqin.niri.base.BaseActivity;
import com.flashqin.niri.bean.BaseBean;
import com.flashqin.niri.bean.OderRecoderBean;
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

public class ShuadanRecordActivity extends BaseActivity {
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
    private BaseQuickAdapter<OderRecoderBean.BodyBean.DataBean, BaseViewHolder> mOneAdapter;
    int page = 0;
    @Override
    public int getLayoutId() {
        return R.layout.activity_shuadan;
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
        txttitle.setText("Brush sheet income details ");
        initAdapter();
        getList();
    }

    public void initAdapter() {


        rec.setLayoutManager(new LinearLayoutManager(ShuadanRecordActivity.this));
        //rec.addItemDecoration(new SpacesItemDecoration(20));
        mOneAdapter = new BaseQuickAdapter<OderRecoderBean.BodyBean.DataBean, BaseViewHolder>(R.layout.item_shuadanrecoder) {
            @Override
            protected void convert(BaseViewHolder helper, OderRecoderBean.BodyBean.DataBean item) {
                TextView txtname = helper.itemView.findViewById(R.id.txttime);
                TextView txtamount = helper.itemView.findViewById(R.id.txtamount);
                TextView txttype = helper.itemView.findViewById(R.id.txttype);

                txtname.setText(item.getBaseDate());
                txtamount.setText(item.getTradeIncome() + "₦");
                txttype.setText(item.getTradeIncome() + " ");
                txttype.setText(item.getLevel()+"");
//                switch (item.getStatus()) {
//                    case 0:
//                        txttype.setText("Pending review");
//                        break;
//                    case 1:
//                        txttype.setText("Sucess");
//                        break;
//                    case 3:
//                        txttype.setText("Fail");
//                        break;
//                }

            }
        };
        rec.setAdapter(mOneAdapter);

        View inflate = LayoutInflater.from(ShuadanRecordActivity.this).inflate(R.layout.layout_empty_home2, null);
        mOneAdapter.setEmptyView(inflate);

    }

    public void getList() {//客户端IP地址
        Map<String, Object> map = new HashMap<String, Object>();
       // String json = createJsonString(map, "type", -1);

        RxHttp.get("/v1/members/" + SPUtils.getInstance().getString("id", "0") + "/order-journals?offset="+page+"&limit=40")
                //.setJsonParams(json)
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        HideLoading();
                        if (baseBean.getHead().getCode() == 1) {
                            OderRecoderBean homeListBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), OderRecoderBean.class);

                            if (page == 0) {

                                mOneAdapter.setNewData(homeListBean.getBody().getData());

                            } else {
                                mOneAdapter.addData(homeListBean.getBody().getData());
                            }
                            page = page + 20;


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
