package com.flashqin.niri.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.flashqin.niri.R;
import com.flashqin.niri.adapter.SpacesItemNoHeader;
import com.flashqin.niri.base.BaseActivity;
import com.flashqin.niri.bean.BaseBean;
import com.flashqin.niri.bean.PayListRecorder;
import com.flashqin.niri.net.BaseObserver;
import com.gyf.immersionbar.ImmersionBar;
import com.orhanobut.dialogplus.DialogPlus;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;

public class RechageRecordActivity extends BaseActivity implements OnLoadMoreListener, OnRefreshListener {
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
    @BindView(R.id.smallLabel)
    SmartRefreshLayout smallLabel;
    private BaseQuickAdapter<PayListRecorder.BodyBean.DataBean, BaseViewHolder> mOneAdapter;
    private DialogPlus dialog_spec;
    int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rechagerecord;
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

        txttitle.setText("Recharge record");
        smallLabel.setOnLoadMoreListener(this);
        smallLabel.setOnRefreshListener(this);



        initAdapter();
        getDataLiST();
    }

    public void initAdapter() {


        rec.setLayoutManager(new LinearLayoutManager(RechageRecordActivity.this));
        rec.addItemDecoration(new SpacesItemNoHeader(20));
        mOneAdapter = new BaseQuickAdapter<PayListRecorder.BodyBean.DataBean, BaseViewHolder>(R.layout.item_newrecharrecoder) {
            @Override
            protected void convert(BaseViewHolder helper, PayListRecorder.BodyBean.DataBean item) {

                TextView txtname = helper.itemView.findViewById(R.id.txttime);
                TextView txtamount = helper.itemView.findViewById(R.id.txtamount);
                TextView txttype = helper.itemView.findViewById(R.id.txttype);
//                JSONObject json1 = JSONObject.parseObject(JSONObject.toJSONString(item.getCreatedAt().getDate()));
               String time1 = "";
//                for (Map.Entry<String, Object> entry : json1.entrySet()) {
//
//                    time1=time1+entry.getValue().toString();
//                }
//                JSONObject json2 = JSONObject.parseObject(JSONObject.toJSONString(item.getCreatedAt().getTime()));
              String time2 = "";
//                for (Map.Entry<String, Object> entry2 : json2.entrySet()) {
//
//                    time2=time2+entry2.getValue().toString();
//                }
                time1=item.getCreatedAt().getDate().getMonth()+"-"+item.getCreatedAt().getDate().getDay()+"-"+item.getCreatedAt().getDate().getYear();
                time2=item.getCreatedAt().getTime().getHour()+":"+item.getCreatedAt().getTime().getMinute()+":"+item.getCreatedAt().getTime().getSecond();

                txtname.setText(time1+" "+time2);
                txtamount.setText(item.getAmount()+"R$");

                switch (item.getStatus()){
                    case 0:
                        txttype.setText("Revisão pendente");
                        break;
                    case 1:
                        txttype.setText("Sucesso");
                        break;
                    case 3:
                        txttype.setText("Falhar");
                        break;
                }

            }
        };
        rec.setAdapter(mOneAdapter);



    }

    public void getDataLiST() {//客户端IP地址
        ShowLoading();
        Map<String, Object> map = new HashMap<String, Object>();
        String json = createJsonString(map, "type", -1);
        RxHttp.get("/v1/wallets/" + SPUtils.getInstance().getString("id", "0") + "/recharge-journals?limit=10")
                .setJsonParams(json)
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        HideLoading();
                        if (baseBean.getHead().getCode() == 1) {
                            PayListRecorder homeListBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), PayListRecorder.class);


                            mOneAdapter.setNewData(homeListBean.getBody().getData());


                        }

                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        HideLoading();
//                        View inflate = LayoutInflater.from(RechageRecordActivity.this).inflate(R.layout.layout_empty_home2, null);
//                        mOneAdapter.setEmptyView(inflate);
                    }
                });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getDataLiST();
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
