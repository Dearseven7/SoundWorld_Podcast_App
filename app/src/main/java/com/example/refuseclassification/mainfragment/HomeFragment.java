package com.example.refuseclassification.mainfragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.EventManager;
import com.baidu.speech.asr.SpeechConstant;
import com.example.refuseclassification.ASRresponse;
import com.example.refuseclassification.GarbageInfoActivity;
import com.example.refuseclassification.KnowledgeDatabase;
import com.example.refuseclassification.R;
import com.example.refuseclassification.SearchActivity;
import com.example.refuseclassification.StartQuestionActivity;
import com.example.refuseclassification.setTitleCenter;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements EventListener{

    private Toolbar toolbar;

    private ImageButton information_button;

    private ImageButton common_button;

    private EditText search;
    private EventManager asr;//语音识别核心库
    private String result;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.home_toolbar);
        toolbar.setTitle("首页");
        new setTitleCenter().setTitleCenter(toolbar);// 初始化ToolBar
        new KnowledgeDatabase().setKnowledgeDatabase();// 初始化数据库
        // 绑定按钮以及事件

        information_button = (ImageButton) view.findViewById(R.id.information_button);
        information_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GarbageInfoActivity.class);
                startActivity(intent);
            }
        });

        common_button = (ImageButton) view.findViewById(R.id.common_button);
        common_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StartQuestionActivity.class);
                startActivity(intent);
            }
        });

        search = (EditText) view.findViewById(R.id.searchHome);
        search.setFocusable(false);//失去焦点
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        // 初始化权限
        initPermission();


        //初始化EventManager对象
        asr = EventManagerFactory.create(getContext(), "asr");
        //注册自己的输出事件类
        asr.registerListener(this); // EventListener 中 onEvent方法

        return view;
    }

    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), perm)) {
                toApplyList.add(perm);
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), toApplyList.toArray(tmpList), 123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }

    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
            // 识别相关的结果都在这里
            if (params == null || params.isEmpty()) {
                return;
            }
            if (params.contains("\"final_result\"")) {
                // 一句话的最终识别结果
                Gson gson = new Gson();
                ASRresponse asRresponse = gson.fromJson(params, ASRresponse.class);//数据解析转实体bean
                if(asRresponse.getBest_result().contains("，")){
                    // 包含逗号  则将逗号替换为空格
                    // 替换为空格之后，通过trim去掉字符串的首尾空格
                    setResult(asRresponse.getBest_result().replace('，',' ').trim());
                }else {// 不包含
                    setResult(asRresponse.getBest_result().trim());
                }
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                if (result.contains("。")) {
                    setResult(result.replaceAll("。", ""));
                }
                intent.putExtra("record", result);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //发送取消事件
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        //退出事件管理器
        // 必须与registerListener成对出现，否则可能造成内存泄露
        asr.unregisterListener(this);
    }

    public void setResult(String result) {
        this.result = result;
    }
}
