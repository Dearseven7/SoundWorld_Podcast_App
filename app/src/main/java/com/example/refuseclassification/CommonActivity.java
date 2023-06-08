package com.example.refuseclassification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.refuseclassification.Database.Knowledge;

import org.litepal.LitePal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CommonActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView question_num;
    private TextView question;
    private Button submit;
    private RadioGroup radiogroup;
    private RadioButton answer1;
    private RadioButton answer2;
    private RadioButton answer3;
    private RadioButton answer4;
    private List<Knowledge> knowledges = new ArrayList<>();
    private List<String> answers = new ArrayList<>();
    private String answer = "";
    private int score = 0;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        toolbar = findViewById(R.id.test_toolbar);
        toolbar.setTitle("什么垃圾小游戏");
        count = 0;
        new setTitleCenter().setTitleCenter(toolbar); // 初始化ToolBar

        // 初始化随机数列表，10个1~100的数
        Set<Integer> hashSet = new HashSet<Integer>();
        while (hashSet.size() != 10) {
            int number = (int) (Math.random() * 100);
            hashSet.add(number);
        }
        // 初始化问题列表
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            int id = Integer.parseInt(it.next().toString());
            Knowledge knowledge = LitePal.find(Knowledge.class, id);
            knowledges.add(knowledge);
        }

        // 设置题目
        question = findViewById(R.id.question);
        question_num = findViewById(R.id.question_num);
        radiogroup = findViewById(R.id.radioGroup);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        submit = findViewById(R.id.submit);

        submit.setText("提交答案");
        question_num.setText("1");
        question.setText(knowledges.get(0).getName());

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 选中文字显示红色，没有选中显示黑色
                if(answer1.isChecked()) {
                    answer = "可回收物";
                    answer1.setTextColor(Color.parseColor("#FF0033"));
                }else{
                    answer1.setTextColor(Color.parseColor("#000000"));
                }
                if(answer2.isChecked()) {
                    answer = "有害垃圾";
                    answer2.setTextColor(Color.parseColor("#FF0033"));
                }else{
                    answer2.setTextColor(Color.parseColor("#000000"));
                }
                if(answer3.isChecked()) {
                    answer = "湿垃圾";
                    answer3.setTextColor(Color.parseColor("#FF0033"));
                }else{
                    answer3.setTextColor(Color.parseColor("#000000"));
                }
                if(answer4.isChecked()) {
                    answer = "干垃圾";
                    answer4.setTextColor(Color.parseColor("#FF0033"));
                }else{
                    answer4.setTextColor(Color.parseColor("#000000"));
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radiogroup.clearCheck();

                if (!answer.equals("")) {
                    if (answer.equals(knowledges.get(count).getKind())) {
                        score += 10;
                    }
                    Knowledge knowledge = knowledges.get(count);
                    knowledge.setAnswer(answer);
                    knowledges.set(count, knowledge);
                }

                count++;

                if (count <= 10) {
                    if (count != 10) {
                        question_num.setText(String.valueOf(count + 1));
                        question.setText(knowledges.get(count).getName());
                    } else {
                        submit.setText("查看结果");
                    }
                }

                if (count == 10) {
                    Intent intent = new Intent(CommonActivity.this, AnswerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("knowledges", (Serializable) knowledges);
                    bundle.putInt("score", score);
                    intent.putExtra("message", bundle);
                    startActivity(intent);
                    finish(); // 销毁活动
                }
            }
        });
    }



}
