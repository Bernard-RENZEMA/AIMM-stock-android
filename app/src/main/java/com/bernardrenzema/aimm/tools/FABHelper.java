package com.bernardrenzema.aimm.tools;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bernardrenzema.aimm.R;
import com.bernardrenzema.aimm.entities.OnFABClick;

public class FABHelper {

    private Activity                    mContext;

    private FloatingActionButton        mFAB;
    public FloatingActionButton        mFABCancel;
    private FloatingActionButton        mFABOpt1;
    private FloatingActionButton        mFABOpt2;
    private FloatingActionButton        mFABOpt3;

    private TextView                    mFABOpt1Title;
    private TextView                    mFABOpt2Title;
    private TextView                    mFABOpt3Title;

    private RelativeLayout              mBackground;

    public FABHelper(Activity context,
                     final OnFABClick fabCallback,
                     final OnFABClick fabCancelCallback,
                     final OnFABClick fabOpt1Callback,
                     final OnFABClick fabOpt2Callback,
                     final OnFABClick fabOpt3Callback) {
        mContext = context;
        mFAB = (FloatingActionButton) context.findViewById(R.id.fab);
        mFABCancel = (FloatingActionButton) context.findViewById(R.id.fabCancel);
        mFABOpt1 = (FloatingActionButton) context.findViewById(R.id.fabOpt1);
        mFABOpt2 = (FloatingActionButton) context.findViewById(R.id.fabOpt2);
        mFABOpt3 = (FloatingActionButton) context.findViewById(R.id.fabOpt3);
        mFABOpt1Title = (TextView) context.findViewById(R.id.fabOpt1Title);
        mFABOpt2Title = (TextView) context.findViewById(R.id.fabOpt2Title);
        mFABOpt3Title = (TextView) context.findViewById(R.id.fabOpt3Title);
        mBackground = (RelativeLayout) context.findViewById(R.id.background);

        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(R.id.fabCancel, -1, -1);
                fabCallback.onClick();
            }
        });
        mFABCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(R.id.fab, -1, -1);
                fabCancelCallback.onClick();
            }
        });
        mFABOpt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabOpt1Callback.onClick();
            }
        });
        mFABOpt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabOpt2Callback.onClick();
            }
        });
        mFABOpt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabOpt3Callback.onClick();
            }
        });
        mBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(R.id.fab, -1, -1);
            }
        });

        mFAB.setVisibility(View.GONE);
        mFABCancel.setVisibility(View.GONE);
        mFABOpt1.setVisibility(View.GONE);
        mFABOpt2.setVisibility(View.GONE);
        mFABOpt3.setVisibility(View.GONE);
        mFABOpt1Title.setVisibility(View.GONE);
        mFABOpt2Title.setVisibility(View.GONE);
        mFABOpt3Title.setVisibility(View.GONE);
        mBackground.setVisibility(View.GONE);
    }

    public void show(int wich, int drawable, int text) {
        switch (wich) {
            case R.id.fab:
                if (drawable > 0) {
                    mFAB.setImageResource(drawable);
                }
                mFAB.setVisibility(View.VISIBLE);
                mFABCancel.setVisibility(View.GONE);
                mFABOpt1.setVisibility(View.GONE);
                mFABOpt2.setVisibility(View.GONE);
                mFABOpt3.setVisibility(View.GONE);
                mFABOpt1Title.setVisibility(View.GONE);
                mFABOpt2Title.setVisibility(View.GONE);
                mFABOpt3Title.setVisibility(View.GONE);
                mBackground.setVisibility(View.GONE);
                break;
            case R.id.fabCancel:
                mFAB.setVisibility(View.GONE);
                mFABCancel.setVisibility(View.VISIBLE);
                mBackground.setVisibility(View.VISIBLE);
                break;
            case R.id.fabOpt1:
                if (drawable > 0) {
                    mFABOpt1.setImageResource(drawable);
                }
                if (text > 0) {
                    mFABOpt1Title.setText(text);
                }
                mFABOpt1.setVisibility(View.VISIBLE);
                mFABOpt1Title.setVisibility(View.VISIBLE);
                break;
            case R.id.fabOpt2:
                if (drawable > 0) {
                    mFABOpt2.setImageResource(drawable);
                }
                if (text > 0) {
                    mFABOpt2Title.setText(text);
                }
                mFABOpt2.setVisibility(View.VISIBLE);
                mFABOpt2Title.setVisibility(View.VISIBLE);
                break;
            case R.id.fabOpt3:
                if (drawable > 0) {
                    mFABOpt3.setImageResource(drawable);
                }
                if (text > 0) {
                    mFABOpt3Title.setText(text);
                }
                mFABOpt3.setVisibility(View.VISIBLE);
                mFABOpt3Title.setVisibility(View.VISIBLE);
                break;

        }
    }
}
