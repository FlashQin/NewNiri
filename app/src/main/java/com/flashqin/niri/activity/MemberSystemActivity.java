package com.flashqin.niri.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flashqin.niri.R;
import com.flashqin.niri.adapter.SpacesItemDecoration;
import com.flashqin.niri.base.BaseActivity;
import com.flashqin.niri.bean.MenberSystemBean;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberSystemActivity extends BaseActivity {

    @BindView(R.id.linback)
    LinearLayout linback;
    @BindView(R.id.linbar)
    LinearLayout linbar;
    @BindView(R.id.rec1)
    RecyclerView rec1;
    @BindView(R.id.dsf)
    LinearLayout dsf;
    private BaseQuickAdapter<MenberSystemBean, BaseViewHolder> mOneAdapter;
    String[] strlv = {"Level 0","Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6"};
    String[] stramout = {"100","100", "500", "2000", "8000", "20000", "100000"};
    String[] strcontent1 = {" The registration bonus is ₦100, but it can only be withdrawn if the member reaches Level 3 or above\n" +
            " Do order tasks only on the registration day\n" +
            " The profit is 0.3% of each order amount\n Members shall pay 15% of the withdrawal amount to the platform."
            , " Recharge ₦100 to become a valid member\n" +
            " The profit is 0.3% of each order amount\n" +
            " Members shall pay 15% of the withdrawal amount to the platform.\n", " Recharge ₦500 to become a valid member\n" +
            " The profit is 0.35% of each order amount\n" +
            " Members shall pay 12% of the withdrawal amount to the platform.\n", " Recharge ₦2000 to become a valid member\n" +
            " The profit is 0.4% of each order amount\n" +
            " Members shall pay 12% of the withdrawal amount to the platform.\n" +
            " You will receive a registration bonus of ₦100\n", " Recharge ₦8000 to become a valid member\n" +
            " The profit is 0.4% of each order amount\n" +
            " Members shall pay 10% of the withdrawal amount to the platform.\n", " Recharge ₦20000 to become a valid member\n" +
            " The profit is 0.5% of each order amount\n" +
            " Members shall pay 10% of the withdrawal amount to the platform.\n"," Recharge ₦100000 to become a valid member\n" +
            " The profit is 0.5% of each order amount\n" +
            " Members shall pay 6% of the withdrawal amount to the platform.\n"};
    String[] strlcontent2 = {"0.3%","0.3%", "0.35%", "0.4%", "0.4%", "0.5%", "0.5%"};

     List<MenberSystemBean> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_menbersystem;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(linbar) //可以为任意view，如果是自定义xml实现标题栏的话，标题栏根节点不能为RelativeLayout或者ConstraintLayout，以及其子类
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    public void initView() {

        // txttitle.setText("Member System");
        for (int i = 0; i < strlv.length; i++) {
            MenberSystemBean bean = new MenberSystemBean();
            bean.setLv(strlv[i]);
            bean.setAmount(stramout[i]);
            bean.setContentone(strcontent1[i]);
            bean.setContenttwo(strlcontent2[i]);
            list.add(bean);
        }
        initAdapter();

    }


    public void initAdapter() {
        rec1.setLayoutManager(new LinearLayoutManager(MemberSystemActivity.this));
        rec1.addItemDecoration(new SpacesItemDecoration(10));
        mOneAdapter = new BaseQuickAdapter<MenberSystemBean, BaseViewHolder>(R.layout.item_systtem,list) {
            @Override
            protected void convert(BaseViewHolder helper, MenberSystemBean item) {
                TextView txt11 = helper.itemView.findViewById(R.id.txt11);
                TextView txt22 = helper.itemView.findViewById(R.id.txt22);
                TextView txt33 = helper.itemView.findViewById(R.id.txt33);
                TextView txt34 = helper.itemView.findViewById(R.id.txt34);
                TextView txt1 = helper.itemView.findViewById(R.id.txt1);
                TextView txt3 = helper.itemView.findViewById(R.id.txtbaifen);
                TextView txt8 = helper.itemView.findViewById(R.id.txt8);
                LinearLayout txtup = helper.itemView.findViewById(R.id.txtup);
                LinearLayout lin4 = helper.itemView.findViewById(R.id.lin4);
                String[]  content=item.getContentone().split("\n");
                txt11.setText(content[0]);
                txt22.setText(content[1]);
                txt33.setText(content[2]);
                if (content.length==4){
                    lin4.setVisibility(View.VISIBLE);
                    txt34.setText(content[3]);
                }

                txt1.setText("*"+item.getLv());

                txt3.setText("Receitas de "+item.getContenttwo()+" por ordem");
                txt8.setText(item.getAmount() + "N");

                txtup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Goto(RechgerlActivity.class, "money", item.getAmount());
                    }
                });

            }
        };
        rec1.setAdapter(mOneAdapter);

//        View inflate = LayoutInflater.from(com.compy.check.Activity.MemberSystemActivity.this).inflate(R.layout.layout_empty_home2, null);
//        mOneAdapter.setEmptyView(inflate);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick(R.id.linback)
    public void onClick() {
        finish();
    }
}
