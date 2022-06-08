package com.example.todoapp.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.todoapp.R;

public class EditCategory_Dialog extends DialogFragment {
    private RadioButton r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11;
    private Button btn_edit;

    public EditCategory_Dialog() {
    }
    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = 100 + getResources().getDisplayMetrics().heightPixels ;
        getDialog().getWindow().setLayout(width, height);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.editcategory_layout, container);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        r1 = (RadioButton) view.findViewById(R.id.radio_grocery);
        r2 = (RadioButton) view.findViewById(R.id.radio_work);
        r3 = (RadioButton) view.findViewById(R.id.radio_sport);
        r4 = (RadioButton) view.findViewById(R.id.radio_design);
        r5 = (RadioButton) view.findViewById(R.id.radio_uni);
        r6 = (RadioButton) view.findViewById(R.id.radio_social);
        r7 = (RadioButton) view.findViewById(R.id.radio_music);
        r8 = (RadioButton) view.findViewById(R.id.radio_health);
        r9 = (RadioButton) view.findViewById(R.id.radio_movie);
        r10 = (RadioButton) view.findViewById(R.id.radio_home);
        r11 = (RadioButton) view.findViewById(R.id.radio_other);

        btn_edit = (Button) view.findViewById(R.id.btn_edit);

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendUpdateData("Grocery");
                getDialog().dismiss();
            }
        });

        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendUpdateData("Work");
                getDialog().dismiss();
            }
        });
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendUpdateData("Sport");
                getDialog().dismiss();
            }
        });
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendUpdateData("Design");
                getDialog().dismiss();
            }
        });
        r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendUpdateData("University");
                getDialog().dismiss();
            }
        });
        r6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendUpdateData("Social");
                getDialog().dismiss();
            }
        });
        r7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendUpdateData("Music");
                getDialog().dismiss();
            }
        });
        r8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendUpdateData("Health");
                getDialog().dismiss();
            }
        });
        r9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendUpdateData("Movie");
                getDialog().dismiss();
            }
        });
        r10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendUpdateData("Home");
                getDialog().dismiss();
            }
        });
        r11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendUpdateData("Other");
                getDialog().dismiss();
            }
        });


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendUpdateData("Category");
                getDialog().dismiss();
            }
        });



    }
    public static interface OnCompleteListener_Category {
        public abstract void onComplete_category(String cate );
    }
    public void onSendUpdateData(String cate) {
        this.mListener.onComplete_category(cate);
    }

    private EditTitle_Dialog.OnCompleteListener mListener;

    // make sure the Activity implemented it
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (EditTitle_Dialog.OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }
}
