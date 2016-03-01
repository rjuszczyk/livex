package com.mygdx.livex.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Radek on 2016-02-27.
 */
public abstract class BaseDialog extends DialogFragment {
    protected View mContentView;

    public abstract int getLayoutId();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(getLayoutId(), container);

        initView(inflater, container, savedInstanceState);
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(mContentView);
        super.onViewCreated(view, savedInstanceState);
    }

    public abstract void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
