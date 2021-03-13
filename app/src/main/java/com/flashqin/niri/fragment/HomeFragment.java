package com.flashqin.niri.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.flashqin.niri.R;
import com.flashqin.niri.base.BaseFragment;
import com.flashqin.niri.utlis.CommonUtils;


import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {


    @BindView(R.id.txt1)
    TextView txt1;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.view5)
    View view5;
    @BindView(R.id.txt2)
    TextView txt2;
    @BindView(R.id.txt3)
    TextView txt3;
    @BindView(R.id.txttt)
    TextView txttt;
    @BindView(R.id.btnlink)
    Button btnlink;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.txt34)
    TextView txt34;
    @BindView(R.id.brnwd)
    Button brnwd;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.txt44)
    TextView txt44;
    @BindView(R.id.con4)
    ConstraintLayout con4;

    @BindView(R.id.frame)
    ConstraintLayout frame;
    @BindView(R.id.btn1more)
    Button btn1more;
    @BindView(R.id.txtt1)
    TextView txtt1;
    @BindView(R.id.txtt2)
    TextView txtt2;
    @BindView(R.id.txtt3)
    TextView txtt3;
    @BindView(R.id.txtt4)
    TextView txtt4;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one_home;
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

        //getMenberInfoData();
        //getNoticeList();
    }

    @Override
    protected void initView(View view) {


    }

    private void shareContent() {
        String link = "https://h5.amazoncash.vip/register?code=" + CommonUtils.ShareCode;

        Intent share_intent = new Intent();

        share_intent.setAction(Intent.ACTION_SEND);

        share_intent.setType("text/plain");

        share_intent.putExtra(Intent.EXTRA_SUBJECT, "compartilhar");

        share_intent.putExtra(Intent.EXTRA_TEXT, link);

        share_intent = Intent.createChooser(share_intent, "compartilhar");

        startActivity(share_intent);
    }

    @OnClick({R.id.btnlink, R.id.brnwd, R.id.btn1more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnlink:
                shareContent();
                break;
            case R.id.brnwd:
                txt34.setMaxLines(15);
                brnwd.setVisibility(View.GONE);
                break;
            case R.id.btn1more:
                txttt.setMaxLines(15);
                btn1more.setVisibility(View.GONE);
                break;




        }
    }

}
