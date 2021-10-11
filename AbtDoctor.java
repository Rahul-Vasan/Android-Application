package com.example.fbans.projecthm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class AbtDoctor extends Fragment {
    View vi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        vi = inflater.inflate(R.layout.layout_abtdoctorl,container,false);

        return vi;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView wv = vi.findViewById(R.id.webview2);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("https://www.google.co.in/?gfe_rd=cr&dcr=0&ei=4BdzWv7uH4Tj8wex2q_QDQ");
    }
}
