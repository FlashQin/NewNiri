package com.flashqin.niri.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flashqin.niri.R;
import com.flashqin.niri.adapter.SpacesItemDecoration;
import com.flashqin.niri.base.BaseActivity;
import com.flashqin.niri.bean.RebaseBean;
import com.gyf.immersionbar.ImmersionBar;

import net.qiujuer.genius.ui.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RechgeBaseActivity extends BaseActivity {


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
    @BindView(R.id.btnsure)
    Button btnsure;
    private BaseQuickAdapter<RebaseBean, BaseViewHolder> mOneAdapter;
    String ip = "", tongdao = "", amount = "500", bancode = "100501";
    List<RebaseBean> rechargBeanList = new ArrayList<>();
    String[] strName = {"Bank-online", "Bank-card", "Bitcoin", "USDT", "Partner"};
    String[] strContent = {"", "", "Get an additional 10% bonus amount", "Get an additional 5% bonus amount", "Get an additional 5% bonus amount"};
    int[] picbac = {R.drawable.icon_bo, R.drawable.icon_bc, R.drawable.icon_bt, R.drawable.icon_us, R.drawable.icon_pt};

    int position = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rergerbase;
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

        txttitle.setText("Recharge");


        initAdapter();
    }

    public void initAdapter() {

        for (int i = 0; i < strName.length; i++) {
            RebaseBean bean = new RebaseBean();
            if (i == 0) {
                bean.setChecked(true);
            } else {
                bean.setChecked(false);
            }
            bean.setPic(picbac[i]);
            bean.setName(strName[i]);
            bean.setIndex(i);
            bean.setContent(strContent[i]);
            rechargBeanList.add(bean);

        }

        rec.setLayoutManager(new LinearLayoutManager(RechgeBaseActivity.this));
        rec.addItemDecoration(new SpacesItemDecoration(20));
        mOneAdapter = new BaseQuickAdapter<RebaseBean, BaseViewHolder>(R.layout.item_rebase, rechargBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, RebaseBean item) {

                RelativeLayout txtname = helper.itemView.findViewById(R.id.rel);
                TextView txtmoney = helper.itemView.findViewById(R.id.txtname);
                TextView txtcontent = helper.itemView.findViewById(R.id.txtcontent);

                ImageView imgbac = helper.itemView.findViewById(R.id.imgbac);
                ImageView imgchoose = helper.itemView.findViewById(R.id.imgchoose);

                txtmoney.setText(item.getName());
                txtcontent.setText(item.getContent());
                imgbac.setBackgroundResource(item.getPic());
                if (item.getContent().equals("")) {
                    txtcontent.setVisibility(View.GONE);
                } else {
                    txtcontent.setVisibility(View.VISIBLE);
                }
                if (item.isChecked() == true) {
                    position = item.getIndex();
                    amount = item.getName();
                    txtname.setBackgroundResource(R.drawable.drawable_rebaseyes);
                    txtname.setSelected(true);
                    imgchoose.setBackgroundResource(R.drawable.ion_check);
                } else {
                    txtname.setSelected(false);
                    txtname.setBackgroundResource(R.drawable.drawable_rebaseno);
                    imgchoose.setBackgroundResource(R.drawable.icon_check_no);
                }
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (item.isChecked() == false) {
                            for (int i = 0; i < rechargBeanList.size(); i++) {
                                if (rechargBeanList.get(i).getName().equals(item.getName())) {
                                    rechargBeanList.get(i).setChecked(true);
                                } else {
                                    rechargBeanList.get(i).setChecked(false);
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


    @OnClick({R.id.imgback, R.id.btnsure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgback:
                finish();
                break;
            case R.id.btnsure:
                switch (position) {
                    case 0:
                        Goto(RechgerlActivity.class);
                        break;
                    case 1:
                        break;
                    case 2:
                        Goto(RechgerlBTBActivity.class);
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
                break;
        }
    }
}
