package com.swufe.scoresforapp.FourMain;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.swufe.scoresforapp.R;

public class MoreFragment extends Fragment {

    public static final String CONTENT = "content";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_more_fragment, container, false);

        Button btn = view.findViewById(R.id.frag4_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "第四个Fragment", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
