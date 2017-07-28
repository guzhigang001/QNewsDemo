package com.example.administrator.ggcode.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.example.administrator.ggcode.Adapter.MsgReceivedtemDelagate;
import com.example.administrator.ggcode.Adapter.MsgSendItemDelagate;
import com.example.administrator.ggcode.Adapter.RobotAdapter;
import com.example.administrator.ggcode.Bean.RobotBean;
import com.example.administrator.ggcode.Bean.RobotMSGBean;
import com.example.administrator.ggcode.Commons.ActivityUtils;
import com.example.administrator.ggcode.Commons.Constants;
import com.example.administrator.ggcode.Commons.LogUtils;
import com.example.administrator.ggcode.R;
import com.example.administrator.ggcode.net.QClitent;
import com.example.administrator.ggcode.net.QNewsService;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class RobotFragment extends Fragment implements TextView.OnEditorActionListener{
    @BindView(R.id.tb_robot)
    Toolbar tbRobot;
    @BindView (R.id.rv_robot)
    RecyclerView rvRobot;
    @BindView (R.id.et_input)
    EditText etInput;
    @BindView(R.id.ll_robot)
    LinearLayout ll_robot;
    private RobotAdapter adapter;

    private List<RobotMSGBean> datas = new ArrayList<>();

    private ActivityUtils utils;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_robot, container, false);
        ButterKnife.bind(this,view);
        utils = new ActivityUtils(getActivity());

        // 设置软键盘的操作 (更改键盘右下角为发送)
        etInput.setImeOptions(EditorInfo.IME_ACTION_SEND);
        //设置软键盘的操作事件监听              （在我们编辑完之后点击软键盘上的回车键才会触发）
        etInput.setOnEditorActionListener(this);
        // 设置输入的类型： 文本类型
        etInput.setInputType(EditorInfo.TYPE_CLASS_TEXT);




        adapter=new RobotAdapter(getActivity(),datas);
        adapter.addItemViewDelegate(new MsgReceivedtemDelagate());
        adapter.addItemViewDelegate(new MsgSendItemDelagate());

        rvRobot.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRobot.setAdapter(adapter);

        RobotMSGBean receiverBean = new RobotMSGBean();
        receiverBean.setMsg("您好！我是图灵机器人，有什么可以帮助您吗？");
        receiverBean.setType(RobotMSGBean.MSG_RECEIVED);
        adapter.addDataToAdapter(receiverBean);
        adapter.notifyDataSetChanged();
        rvRobot.smoothScrollToPosition(adapter.getItemCount() - 1);

        return view;
    }

    @OnClick(R.id.bt_send)
    public void onClick(View view) {
        sendMsg();
        closeKeyBoard();

    }


    private void sendMsg() {
        String msg=etInput.getText().toString();
        if (msg.isEmpty()||("").equals(msg)){
            etInput.setError("内容不能为空");
            return;
        }
        RobotMSGBean robot=new RobotMSGBean();
        robot.setMsg(msg);
        robot.setType(RobotMSGBean.MSG_SEND);
        adapter.addDataToAdapter(robot);
        adapter.notifyDataSetChanged();

        QClitent.getInstance()
                .create(QNewsService.class, Constants.BASE_Q_A_ROBOT_URL)
                .getQARobotData(msg)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RobotBean>() {
                    @Override
                    public void accept(RobotBean s) throws Exception {
                        String       text         = s.getResult().getText();
                        RobotMSGBean receiverBean = new RobotMSGBean();
                        receiverBean.setMsg(text);
                        receiverBean.setType(RobotMSGBean.MSG_RECEIVED);
                        adapter.addDataToAdapter(receiverBean);
                        adapter.notifyDataSetChanged();
                        rvRobot.smoothScrollToPosition(adapter.getItemCount() - 1);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.i("error: " + throwable.getMessage());
                    }
                });
    }



    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId==EditorInfo.IME_ACTION_SEND){
            sendMsg();
            etInput.setText("");
            return true;
        }else {

        }
        closeKeyBoard();
        return false;
    }
    private void closeKeyBoard() {
        etInput.setText("");//清空内容
        InputMethodManager imm= (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etInput.getWindowToken(), 0);

    }

}
