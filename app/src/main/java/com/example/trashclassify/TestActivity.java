package com.example.trashclassify;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trashclassify.model.Trash;
import com.example.trashclassify.util.TrashService;

import java.util.ArrayList;
import java.util.Random;

public class TestActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    ArrayList<Trash> trashes;
    ArrayList<Trash> questionTrashes = new ArrayList<>();
    Trash.TrashType[] answer = new Trash.TrashType[5];
    TextView q1, q2, q3, q4, q5;
    RadioGroup q1a, q2a, q3a, q4a, q5a;
    Button submit, again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        TrashService service = new TrashService();
        trashes = service.readCSV(getAssets());
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            questionTrashes.add(trashes.get(rand.nextInt(trashes.size())));
            answer[i] = Trash.TrashType.unknown;
        }
        q1 = findViewById(R.id.q1);
        q2 = findViewById(R.id.q2);
        q3 = findViewById(R.id.q3);
        q4 = findViewById(R.id.q4);
        q5 = findViewById(R.id.q5);

        q1a = findViewById(R.id.q1a);
        q1a.setOnCheckedChangeListener(this);
        q2a = findViewById(R.id.q2a);
        q2a.setOnCheckedChangeListener(this);
        q3a = findViewById(R.id.q3a);
        q3a.setOnCheckedChangeListener(this);
        q4a = findViewById(R.id.q4a);
        q4a.setOnCheckedChangeListener(this);
        q5a = findViewById(R.id.q5a);
        q5a.setOnCheckedChangeListener(this);

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);

        again = findViewById(R.id.again);
        again.setOnClickListener(this);

        q1.setText(questionTrashes.get(0).getName());
        q2.setText(questionTrashes.get(1).getName());
        q3.setText(questionTrashes.get(2).getName());
        q4.setText(questionTrashes.get(3).getName());
        q5.setText(questionTrashes.get(4).getName());

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit) {
            int correct = 0;
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < 5; i++) {
                if (answer[i] == questionTrashes.get(i).getType()) {
                    correct += 1;
                    result.append(questionTrashes.get(i).toString()).append("，答对").append("\n");
                } else {
                    result.append(questionTrashes.get(i).getName()).append("不是").append(answer[i].toString())
                            .append("，答错，").append(questionTrashes.get(i).toString()).append("\n");
                }
            }
            AlertDialog.Builder dialog = new AlertDialog.Builder(TestActivity.this);
            dialog.setTitle("您答对了" + correct + "题");
            dialog.setMessage(result.toString());
            dialog.show();
        } else if (v.getId() == R.id.again) {
            Random rand = new Random();
            questionTrashes = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                questionTrashes.add(trashes.get(rand.nextInt(trashes.size())));
                answer[i] = Trash.TrashType.unknown;
            }
            q1.setText(questionTrashes.get(0).getName());
            q2.setText(questionTrashes.get(1).getName());
            q3.setText(questionTrashes.get(2).getName());
            q4.setText(questionTrashes.get(3).getName());
            q5.setText(questionTrashes.get(4).getName());
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.q1a1) {
            answer[0] = Trash.TrashType.recyclable;
        } else if (checkedId == R.id.q1a2) {
            answer[0] = Trash.TrashType.harmful;
        } else if (checkedId == R.id.q1a3) {
            answer[0] = Trash.TrashType.wet;
        } else if (checkedId == R.id.q1a4) {
            answer[0] = Trash.TrashType.dry;
        } else if (checkedId == R.id.q1a5) {
            answer[0] = Trash.TrashType.bulky;
        } else if (checkedId == R.id.q2a1) {
            answer[1] = Trash.TrashType.recyclable;
        } else if (checkedId == R.id.q2a2) {
            answer[1] = Trash.TrashType.harmful;
        } else if (checkedId == R.id.q2a3) {
            answer[1] = Trash.TrashType.wet;
        } else if (checkedId == R.id.q2a4) {
            answer[1] = Trash.TrashType.dry;
        } else if (checkedId == R.id.q2a5) {
            answer[1] = Trash.TrashType.bulky;
        } else if (checkedId == R.id.q3a1) {
            answer[2] = Trash.TrashType.recyclable;
        } else if (checkedId == R.id.q3a2) {
            answer[2] = Trash.TrashType.harmful;
        } else if (checkedId == R.id.q3a3) {
            answer[2] = Trash.TrashType.wet;
        } else if (checkedId == R.id.q3a4) {
            answer[2] = Trash.TrashType.dry;
        } else if (checkedId == R.id.q3a5) {
            answer[2] = Trash.TrashType.bulky;
        } else if (checkedId == R.id.q4a1) {
            answer[3] = Trash.TrashType.recyclable;
        } else if (checkedId == R.id.q4a2) {
            answer[3] = Trash.TrashType.harmful;
        } else if (checkedId == R.id.q4a3) {
            answer[3] = Trash.TrashType.wet;
        } else if (checkedId == R.id.q4a4) {
            answer[3] = Trash.TrashType.dry;
        } else if (checkedId == R.id.q4a5) {
            answer[3] = Trash.TrashType.bulky;
        } else if (checkedId == R.id.q5a1) {
            answer[4] = Trash.TrashType.recyclable;
        } else if (checkedId == R.id.q5a2) {
            answer[4] = Trash.TrashType.harmful;
        } else if (checkedId == R.id.q5a3) {
            answer[4] = Trash.TrashType.wet;
        } else if (checkedId == R.id.q5a4) {
            answer[4] = Trash.TrashType.dry;
        } else if (checkedId == R.id.q5a5) {
            answer[4] = Trash.TrashType.bulky;
        }
    }
}
