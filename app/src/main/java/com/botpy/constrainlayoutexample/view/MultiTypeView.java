package com.botpy.constrainlayoutexample.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.botpy.constrainlayoutexample.R;
import com.botpy.constrainlayoutexample.model.MultiModel;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author liuxuhui
 * @date 2019-07-15
 */
public class MultiTypeView extends LinearLayout implements View.OnClickListener {

    @BindView(R.id.multi_type_tv_head)
    TextView mHeadTv;
    @BindView(R.id.multi_type_tv_content)
    TextView mContentTv;
    @BindView(R.id.multi_type_et)
    EditText mEditText;
    @BindView(R.id.multi_type_tv_select)
    TextView mSelectTv;

    private String mInputStr;

    private MultiModel mMultiModel;

    @Override
    public void onClick(View v) {
        if(mMultiModel == null) {
            return;
        }
        mInputStr = mMultiModel.contentTitle;
        Toast.makeText(getContext(), mMultiModel.contentTitle, Toast.LENGTH_SHORT).show();
    }

    public enum MultiType {
        /** 字符串 */
        STRING,
        /** 整数 */
        INTEGER,
        /** 浮点数 */
        FLOAT,
        /** 单选 */
        SINGLE_CHOICE,
        /** 日期 */
        DATE;
    }

    public MultiTypeView(Context context) {
        this(context, null);
    }

    public MultiTypeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTypeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(context, R.layout.linear_layout_multi_type, this);
        ButterKnife.bind(this, view);
    }

    public void setData(@NonNull MultiModel multiModel) {
        mMultiModel = multiModel;

        if(multiModel.isShowHeadTitle) {
            mHeadTv.setVisibility(VISIBLE);
            mHeadTv.setText(multiModel.headTitle);
        }else {
            mHeadTv.setVisibility(GONE);
        }

        mContentTv.setText(multiModel.contentTitle);
        switch (multiModel.type) {
            case STRING:
                showEditText();
                mEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case INTEGER:
                showEditText();
                mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case FLOAT:
                showEditText();
                mEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                mEditText.addTextChangedListener(new MyFloatTextWatcher());
                break;
            case SINGLE_CHOICE:
                showSelectTv();
                break;
            case DATE:
                showSelectTv();
                break;
            default:
                break;
        }
    }

    class MyFloatTextWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().trim().equals(".")) {
                s = "0" + s;
                mEditText.setText(s);
                mEditText.setSelection(2);
            }

            if (s.toString().startsWith("0")
                    && s.toString().trim().length() > 1) {
                if (!s.toString().substring(1, 2).equals(".")) {
                    s = s.subSequence(0, 1);
                    mEditText.setText(s);
                    mEditText.setSelection(1);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    /**
     * 显示输入框
     */
    private void showEditText() {
        mEditText.setVisibility(VISIBLE);
        mSelectTv.setVisibility(GONE);
        if(TextUtils.isEmpty(mMultiModel.content)) {
            mEditText.setHint(mMultiModel.contentHint);
        }else {
            mEditText.setText(mMultiModel.content);
        }
    }

    /**
     * 显示单选框
     */
    private void showSelectTv() {
        mEditText.setVisibility(GONE);
        mSelectTv.setVisibility(VISIBLE);
        mSelectTv.setText(mMultiModel.content);
        mSelectTv.setOnClickListener(this);
    }

    /**
     * 输入的值
     * @return
     */
    public String getmInputStr() {
        switch (mMultiModel.type) {
            case STRING:
            case INTEGER:
            case FLOAT:
                String inputContent = mEditText.getText().toString();
                if(TextUtils.isEmpty(inputContent)) {
                    Toast.makeText(getContext(), mMultiModel.contentHint, Toast.LENGTH_SHORT).show();
                }
                return inputContent;
            case SINGLE_CHOICE:
            case DATE:
                return mInputStr;
            default:
                break;
        }
        return mInputStr;
    }
}
