package com.flashqin.niri.fragment;

import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flashqin.niri.R;
import com.flashqin.niri.activity.MemberSystemActivity;
import com.flashqin.niri.activity.MemberSystemBTCActivity;
import com.flashqin.niri.activity.RechgeBaseActivity;
import com.flashqin.niri.activity.RechgerlActivity;
import com.flashqin.niri.activity.WithdrawalActivity;
import com.flashqin.niri.activity.WithdrawalBaseActivity;
import com.flashqin.niri.adapter.SpacesItemNoHeader;
import com.flashqin.niri.base.BaseFragment;
import com.flashqin.niri.bean.BaseBean;
import com.flashqin.niri.bean.DanInfoBean;
import com.flashqin.niri.bean.NoticeListBean;
import com.flashqin.niri.bean.OderRecoderBean;
import com.flashqin.niri.bean.TaskBean;
import com.flashqin.niri.bean.UserMoneyDataBean;
import com.flashqin.niri.net.BaseObserver;
import com.flashqin.niri.utlis.UtilTool;
import com.marquee.dingrui.marqueeviewlib.MarqueeView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rxjava.rxlife.RxLife;

import net.qiujuer.genius.ui.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.param.RxHttp;

public class TaskBTCFragment extends BaseFragment {


    @BindView(R.id.txt1)
    TextView txt1;
    @BindView(R.id.txt2)
    TextView txt2;
    @BindView(R.id.imgtz)
    ImageView imgtz;
    @BindView(R.id.linsystem)
    LinearLayout linsystem;
    @BindView(R.id.con1)
    ConstraintLayout con1;
    @BindView(R.id.imgjb1)
    ImageView imgjb1;
    @BindView(R.id.txtre)
    TextView txtre;
    @BindView(R.id.txthb1)
    TextView txthb1;
    @BindView(R.id.txtrenum)
    TextView txtrenum;
    @BindView(R.id.imgjb2)
    ImageView imgjb2;
    @BindView(R.id.txtmo)
    TextView txtmo;
    @BindView(R.id.txthb2)
    TextView txthb2;
    @BindView(R.id.txtmonum)
    TextView txtmonum;
    @BindView(R.id.linaa)
    LinearLayout linaa;
    @BindView(R.id.linre)
    LinearLayout linrechage;
    @BindView(R.id.linwd)
    LinearLayout linwithdr;
    @BindView(R.id.con2)
    ConstraintLayout con2;
    @BindView(R.id.rec1)
    RecyclerView rec1;
    @BindView(R.id.marrr)
    MarqueeView marr;
    @BindView(R.id.txtxt)
    TextView txtxt;
    @BindView(R.id.txtlv)
    TextView txtlv;
    @BindView(R.id.connn)
    ConstraintLayout connn;
    @BindView(R.id.linn)
    LinearLayout linn;
    @BindView(R.id.txttabone)
    TextView txttabone;
    @BindView(R.id.txttabtwo)
    TextView txttabtwo;


    @BindView(R.id.linbacpic)
    LinearLayout linbacpic;
    @BindView(R.id.linbacbase)
    LinearLayout linbacbase;
    private BaseQuickAdapter<List<TaskBean.BodyBean.DataBean>, BaseViewHolder> mOneAdapter;
    private BaseQuickAdapter<TaskBean.BodyBean.DataBean, BaseViewHolder> twoAdapter;
    private DialogPlus dialog_chazhao, dialog_lock, dialog_fail, dialog_succ, dialog_over;
    //跑马灯数据
    List<String> messages = new ArrayList<>();
    TaskBean taskBean, taskBTCBean;
    int userlv = 0, oderNum = 0, oderShuaNum = 0;
    String resgerData = "";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_btctask;
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
        //getNoticeList();

    }

    @Override
    protected void initView(View view) {

        initAadpter();
        initOverDialog();

        //  initChazhaoDialog();
        initLockDialog();
        getMessageList();


    }

    public void getMenberInfoData() {//读取个人概要数据:
        RxHttp.get("/v1/members/" + SPUtils.getInstance().getString("id", "0") + "/info?type=1")
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {

                        if (baseBean.getHead().getCode() == 1) {
                            UserMoneyDataBean userMoneyDataBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), UserMoneyDataBean.class);

                            try {

                                userlv = userMoneyDataBean.getBody().getData().getLevel();
                                txtrenum.setText(userMoneyDataBean.getBody().getData().getTodayEarnings() + "");
                                oderShuaNum = userMoneyDataBean.getBody().getData().getTodayTradeTimes();
                                txtlv.setText("Level " + userMoneyDataBean.getBody().getData().getLevel());
                                resgerData = userMoneyDataBean.getBody().getData().getRegisterDate();
                                // resgerData="2021-03-15 17:40:11";
                                if (userlv >= 4) {
                                    txtmonum.setText("0");
                                }

                                getDataListBTC();
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

    public void initAadpter() {

        rec1.setLayoutManager(new LinearLayoutManager(getActivity()));
        rec1.addItemDecoration(new SpacesItemNoHeader(10));
        mOneAdapter = new BaseQuickAdapter<List<TaskBean.BodyBean.DataBean>, BaseViewHolder>(R.layout.item_task1) {
            @Override
            protected void convert(BaseViewHolder helper, List<TaskBean.BodyBean.DataBean> item) {
                TextView txtlv = helper.itemView.findViewById(R.id.txtlv);
                TextView txtbai = helper.itemView.findViewById(R.id.txtbaifen);
                RecyclerView recyclerView = helper.itemView.findViewById(R.id.rec2);
                txtlv.setText(item.get(0).getName());

                txtbai.setText("" + roundByScale(item.get(0).getIncomeRate() * 100, 2) + "%");
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recyclerView.addItemDecoration(new SpacesItemNoHeader(10));
                twoAdapter = new BaseQuickAdapter<TaskBean.BodyBean.DataBean, BaseViewHolder>(R.layout.item_task2, item) {
                    @Override
                    protected void convert(BaseViewHolder helper, TaskBean.BodyBean.DataBean item) {
                        TextView txtname = helper.itemView.findViewById(R.id.txtname);
                        TextView txtmoney = helper.itemView.findViewById(R.id.txtmoney);
                        ImageView imgpic = helper.itemView.findViewById(R.id.imgpic);
                        Glide.with(getActivity()).load(item.getImage()).into(imgpic);
                        Button btnsure = helper.itemView.findViewById(R.id.btnsure);
                        txtname.setText(item.getDesc());

                        txtmoney.setText(item.getBalanceLowerLimit() + "");
                        if (item.getId() == 0) {

                            txtmoney.setText("2000" + "");

                        }
                        if (userlv == item.getId()) {
                            oderNum = item.getTradeTimesLimit();
                            txtxt.setText(oderShuaNum + "/" + oderNum);

                            if (userlv == 0) {
                                //不用管用户等级，只要是0等级的商品，只有当天能刷
                                if (resgerData.substring(0, 10).equals(UtilTool.gettimenow().substring(0, 10))) {
                                    btnsure.setText("Get order");
                                    btnsure.setBackgroundResource(R.drawable.drawable_taskitem2);
                                } else {
                                    btnsure.setText("Locked");
                                    btnsure.setBackgroundResource(R.drawable.drawable_taskitem3);
                                }
                            } else {
                                btnsure.setText("Get order");
                                btnsure.setBackgroundResource(R.drawable.drawable_taskitem2);
                            }

                        } else {
                            btnsure.setText("Locked");
                            btnsure.setBackgroundResource(R.drawable.drawable_taskitem3);
                        }
                        btnsure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (userlv == item.getId()) {
                                    if (userlv == 0 && !resgerData.substring(0, 10).equals(UtilTool.gettimenow().substring(0, 10))) {
                                        //不用管用户等级，只要是0等级的商品，只有当天能刷
                                        dialog_lock.show();
                                        return;
                                    }
                                    if (oderShuaNum >= oderNum) {
                                        dialog_over.show();
                                        return;
                                    }
                                    //dialog_chazhao.show();
                                    initChazhaoDialog();

                                    new Handler().postDelayed(new Runnable() {
                                        public void run() {
                                            dialog_chazhao.dismiss();
                                            postXiadan(item.getId() + "");

                                        }
                                    }, 3000);
                                } else {

                                    dialog_lock.show();
                                }

                            }
                        });

                    }
                };
                recyclerView.setAdapter(twoAdapter);


            }
        };
        // mOneAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        rec1.setAdapter(mOneAdapter);


    }

    public void postXiadan(String lv) {//读取列表
        RxHttp.postForm("/v1/members/" + SPUtils.getInstance().getString("id", "0") + "/orders/" + lv+"?type=1")
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        //  HideLoading();
                        if (baseBean.getHead().getCode() == 1) {

                            getInfo();

                        } else
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    initFail(baseBean.getHead().getMessage());

                                }
                            }, 500);

                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        //   HideLoading();
                    }
                });
    }



    public void getDataListBTC() {//读取列表

        RxHttp.get("/v2/levels?type=1")
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        HideLoading();
                        if (baseBean.getHead().getCode() == 1) {
                            taskBTCBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), TaskBean.class);
                            mOneAdapter.setNewData(taskBTCBean.getBody().getData());

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

    public void getInfo() {//读取列表
        RxHttp.get("v1/members/" + SPUtils.getInstance().getString("id", "0") + "/latest-order?type=1")
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        //  HideLoading();
                        if (baseBean.getHead().getCode() == 1) {
                            getMenberInfoData();
                            //getNoticeList();
                            DanInfoBean danInfoBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), DanInfoBean.class);
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    initSuccDialog(danInfoBean);

                                }
                            }, 500);


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

    public void getNoticeList() {//读取列表
        RxHttp.get("/v1/members/" + SPUtils.getInstance().getString("id", "0") + "/orders?offset=0&limit=40&type=1")
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {

                        String content = "";
                        if (baseBean.getHead().getMessage().contains("There is no data")) {
                            // b.setEnabled(true);
                        }
                        if (baseBean.getHead().getCode() == 1) {
                            OderRecoderBean homeListBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), OderRecoderBean.class);

                            //mOneAdapter.setNewData(homeListBean.getBody().getData());
                            // txttodayorder.setText(homeListBean.getBody().getData().size() + "");//可用余额
                            if (homeListBean.getBody().getData().size() >= 20) {
                                // btnganble.setEnabled(false);
                            } else {
                                //  btnganble.setEnabled(true);
                            }
                        }

                        //  ToastUtils.showShort(baseBean.getHead().getMessage());
                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });
    }

    public void getMessageList() {//读取列表
        RxHttp.get("/v1/wallets/latest-withdraws?type=1")
                .asObject(BaseBean.class)
                .subscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {

                        String content = "";
                        if (baseBean.getHead().getCode() == 1) {
                            NoticeListBean homeListBean = JSONObject.parseObject(JSONObject.toJSONString(baseBean), NoticeListBean.class);

                            if (homeListBean.getBody().getData().size() == 0) {
                                return;
                            }
                            for (int i = 0; i < homeListBean.getBody().getData().size(); i++) {
                                messages.add(homeListBean.getBody().getData().get(i).getName() + " " + "Withdrawal" + " " + String.valueOf(homeListBean.getBody().getData().get(i).getAmount()).replace(".0", "") + " ₦ " + "Success;     ");
                            }
                            for (int i = 0; i < messages.size(); i++) {
                                content = content + "  " + messages.get(i);
                            }
                            marr.setContent(content);

                        }

                        // ToastUtils.showShort(baseBean.getHead().getMessage());
                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });
    }

    @OnClick({R.id.linsystem, R.id.linre, R.id.linwd, R.id.txttabone, R.id.txttabtwo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linsystem:
                Goto(MemberSystemBTCActivity.class);
                break;
            case R.id.linre:
                Goto(RechgeBaseActivity.class);
                break;
            case R.id.linwd:
                Goto(WithdrawalBaseActivity.class);
                break;
            case R.id.txttabone:
                txttabone.setBackgroundResource(R.drawable.drawable_wallone);
                txttabtwo.setBackgroundResource(0);
                linbacbase.setBackgroundColor(getResources().getColor(R.color.color_wall_btb));
                linbacpic.setBackgroundResource(R.drawable.bg_wallet);
                mOneAdapter.setNewData(taskBTCBean.getBody().getData());

                break;
            case R.id.txttabtwo:
                txttabtwo.setBackgroundResource(R.drawable.drawable_walltwo);
                txttabone.setBackgroundResource(0);
                linbacbase.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                linbacpic.setBackgroundResource(R.drawable.bg_wallet2);
                mOneAdapter.setNewData(taskBean.getBody().getData());

                break;
        }
    }

    private void initChazhaoDialog() {
        ViewHolder viewHolder = new ViewHolder(R.layout.dilog_taskgo);
        dialog_chazhao = DialogPlus.newDialog(getActivity())
                .setContentHolder(viewHolder)
                .setGravity(Gravity.CENTER)
                .setCancelable(false)
                .setContentBackgroundResource(R.color.transparent)
                .setOverlayBackgroundResource(R.color.dialog_overlay_bg)
                .create();

        AnimationSet set = new AnimationSet(true);
        ImageView img = (ImageView) viewHolder.getInflatedView().findViewById(R.id.imgmove);
        //初始化一个平移动画使用的是TranslateAnimation类
        //构造方法的参数分别是fromXDelta，toXDelta,fromYDelta,toYDelta
        Animation animation = new TranslateAnimation(0f, 70.0f, 0f, 20.0f);
        //动画的持续时间
        animation.setDuration(1000);
        //执行次数，不包括第一次
        animation.setRepeatCount(3);
        //设置加速器要实现的动画效果
        animation.setInterpolator(getActivity(), android.R.anim.bounce_interpolator);
        img.clearAnimation();
        img.startAnimation(animation);
        dialog_chazhao.show();


    }

    private void initOverDialog() {
        ViewHolder viewHolder = new ViewHolder(R.layout.dilog_workover);
        dialog_over = DialogPlus.newDialog(getActivity())
                .setContentHolder(viewHolder)
                .setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setContentBackgroundResource(R.color.transparent)
                .setOverlayBackgroundResource(R.color.dialog_overlay_bg)
                .create();


    }

    private void initSuccDialog(DanInfoBean danInfoBean) {
        ViewHolder viewHolder = new ViewHolder(R.layout.dilog_odersucc);
        dialog_succ = DialogPlus.newDialog(getActivity())
                .setContentHolder(viewHolder)
                .setGravity(Gravity.CENTER)
                .setCancelable(false)
                .setContentBackgroundResource(R.color.transparent)
                .setOverlayBackgroundResource(R.color.dialog_overlay_bg)
                .create();

        TextView txtall = viewHolder.getInflatedView().findViewById(R.id.txtrorle);
        TextView txtreturn = viewHolder.getInflatedView().findViewById(R.id.txtreturn);
        TextView txtodernum = viewHolder.getInflatedView().findViewById(R.id.txtodernum);
        TextView txttime = viewHolder.getInflatedView().findViewById(R.id.txttime);
        TextView txtfan = viewHolder.getInflatedView().findViewById(R.id.txtfan);
        Button btnok = viewHolder.getInflatedView().findViewById(R.id.btnok);
        Button btnno = viewHolder.getInflatedView().findViewById(R.id.btno);

        txtall.setText(danInfoBean.getBody().getData().getId() + "");
        txtreturn.setText(danInfoBean.getBody().getData().getBaseDate());
        txtodernum.setText(danInfoBean.getBody().getData().getTradeAmount() + "");
        txttime.setText("+" + danInfoBean.getBody().getData().getTradeIncome() + "");
        txtfan.setText(roundByScale(danInfoBean.getBody().getData().getIncomeRate() * 100, 2) + "%");
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_succ.dismiss();
                if (oderShuaNum >= oderNum) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {

                            dialog_over.show();

                        }
                    }, 600);

                }
            }
        });
        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_succ.dismiss();
                if (oderShuaNum >= oderNum) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {

                            dialog_over.show();

                        }
                    }, 600);
                    return;
                }
                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        //  dialog_chazhao.show();
                        initChazhaoDialog();

                    }
                }, 600);

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        dialog_chazhao.dismiss();
                        postXiadan(userlv + "");

                    }
                }, 3600);
            }
        });
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                System.out.println("okkkkkkkkkkkkkkkkkkkkk");
        dialog_succ.show();
//
//            }
//        }, 500);


    }

    private void initLockDialog() {
        ViewHolder viewHolder = new ViewHolder(R.layout.dilog_lock);
        dialog_lock = DialogPlus.newDialog(getActivity())
                .setContentHolder(viewHolder)
                .setGravity(Gravity.CENTER)
                .setCancelable(false)
                .setContentBackgroundResource(R.color.transparent)
                .setOverlayBackgroundResource(R.color.dialog_overlay_bg)
                .create();

        Button btnyes = viewHolder.getInflatedView().findViewById(R.id.btnsure);
        Button btncancle = viewHolder.getInflatedView().findViewById(R.id.btncancle);
        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_lock.dismiss();

            }
        });
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_lock.dismiss();
                Goto(RechgerlActivity.class);
            }
        });
    }

    private void initFail(String content) {
        ViewHolder viewHolder = new ViewHolder(R.layout.dilog_fail);
        dialog_fail = DialogPlus.newDialog(getActivity())
                .setContentHolder(viewHolder)
                .setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setContentBackgroundResource(R.color.transparent)
                .setOverlayBackgroundResource(R.color.dialog_overlay_bg)
                .create();

        Button btnok = viewHolder.getInflatedView().findViewById(R.id.btnok);
        TextView txtcontent = viewHolder.getInflatedView().findViewById(R.id.txtfan);
        if (content.length() > 0) {
            txtcontent.setText(content);
        }
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_fail.dismiss();
                //Goto(RechargeActivity.class);
            }
        });

        dialog_fail.show();
    }


}
