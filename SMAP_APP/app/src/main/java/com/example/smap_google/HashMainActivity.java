package com.example.smap_google;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class HashMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final HashTagAutoCompleteTextView textView = (HashTagAutoCompleteTextView) findViewById(R.id.input_form);

        HashTagSuggestAdapter adapter = new HashTagSuggestAdapter(this, android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        adapter.setCursorPositionListener(new HashTagSuggestAdapter.CursorPositionListener() {
            @Override
            public int currentCursorPosition() {
                return textView.getSelectionStart();
            }
        });

        textView.setAdapter(adapter);
    }

    private static final String[] COUNTRIES = new String[]{
            "#빅맥송", "#호수", "#공원", "#회사 스트레스", "#공부 스트레스", "#아침 메뉴" , "#점심 메뉴","#저녁 메뉴","#당일치기","#분위기 좋은곳","#데이트 코스"
    };
}
