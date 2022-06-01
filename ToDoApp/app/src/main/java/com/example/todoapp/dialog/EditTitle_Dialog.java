package com.example.todoapp.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.todoapp.R;

public class EditTitle_Dialog extends DialogFragment {
    private EditText ed_title;
    private EditText ed_desc;
    private Button btn_edit;
    private Button btn_cancel;

    public EditTitle_Dialog() {
    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        getDialog().getWindow().setLayout(width, height/3);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_task_title, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String title = getArguments().getString("title", "");
        String desc = getArguments().getString("desc", "");

        ed_title = (EditText) view.findViewById(R.id.ed_title);
        ed_desc = (EditText) view.findViewById(R.id.ed_desc);
        btn_edit = (Button) view.findViewById(R.id.btn_edit);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

        ed_title.setText(title);
        ed_desc.setText(desc);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String update_title = ed_title.getText().toString();
                String update_desc = ed_desc.getText().toString();
                onSendUpdateData(update_title, update_desc);
                getDialog().dismiss();
            }
        });
    }

    public static interface OnCompleteListener {
        public abstract void onComplete(String title, String desc);

        void onComplete_date(String date);

        void onComplete_priority(Integer priority);


        void onComplete_category(String cate);
    }
    public void onSendUpdateData(String title, String desc) {
        this.mListener.onComplete(title, desc);
    }

    private OnCompleteListener mListener;

    // make sure the Activity implemented it
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }
}
