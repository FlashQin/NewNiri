package com.flashqin.niri.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.flashqin.niri.R;
import com.flashqin.niri.base.BaseFragment;
import com.flashqin.niri.utlis.CommonUtils;


import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {


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

    @BindView(R.id.txtt3)
    LinearLayout txtt3;
    @BindView(R.id.txtt4)
    LinearLayout txtt4;


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
        String link = "https://h5.kaymu.vip/register?code=" + CommonUtils.ShareCode;

        Intent share_intent = new Intent();

        share_intent.setAction(Intent.ACTION_SEND);

        share_intent.setType("text/plain");

        share_intent.putExtra(Intent.EXTRA_SUBJECT, "Share");

        share_intent.putExtra(Intent.EXTRA_TEXT, link);

        share_intent = Intent.createChooser(share_intent, "Share");

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
