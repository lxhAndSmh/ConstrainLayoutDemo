package com.botpy.constrainlayoutexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.botpy.constrainlayoutexample.model.MultiModel;
import com.botpy.constrainlayoutexample.view.MultiTypeView;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * @author liuxuhui 
 * @date 2019-07-24
 */
public class MultiTypeActivity extends AppCompatActivity {

    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        for(int i = 0; i < 10; i++) {
            MultiModel multiModel = new MultiModel();
            if(i % 3 == 0){
                multiModel.isShowHeadTitle = true;
                multiModel.headTitle = "投保标题" + i;
            }
            multiModel.contentTitle = "内容标题" + i;
            multiModel.contentHint = "请输入内容" + i;
            multiModel.type = MultiTypeView.MultiType.STRING;
            if(i == 3) {
                multiModel.type = MultiTypeView.MultiType.INTEGER;
                multiModel.contentHint = "请输入整数";
            }else if(i == 5) {
                multiModel.type = MultiTypeView.MultiType.FLOAT;
                multiModel.contentHint = "请输入小数";
            }else if(i == 7) {
                multiModel.type = MultiTypeView.MultiType.SINGLE_CHOICE;
            }else if(i == 8) {
                multiModel.type = MultiTypeView.MultiType.DATE;
            }

            MultiTypeView multiTypeView = new MultiTypeView(this);
            multiTypeView.setData(multiModel);
            multiTypeView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(multiTypeView);
        }
    }
}
