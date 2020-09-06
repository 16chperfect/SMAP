package com.example.smap_google;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.Query;

import java.util.Arrays;
import java.util.List;

public class SearchView extends AppCompatActivity {
    private List<String> items = items = Arrays.asList("어벤져스","배트맨","배트맨2","배구","수퍼맨");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item03_java);

        SearchView searchView = findViewById(R.id.search_View);
        TextView resultTextView = findViewById(R.id.textView);
        resultTextView.setText(getResult());
        searchView.setOnQueryTextListener(new SearchView().OnQueryTextListener()
        {
           @Override
           public  boolean onQueryTextSubmit(String Query){
               return false;
            }
            @Override
                    public boolean onQueryTextChange(String newText)
            {
                resultTextView.setText(search(newText));
                return ture;
            }
        });


    }
    private String search(String query){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<items.size();i++)
        {
            String item = items.get(i);
            if(item.toLowerCase().contains(query.toLowerCase())){
                sb.append(item);
                if( i != items.size()-1){
                    sb.append("\n");
                }
            }
        }
    }
    private String getResult() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<items.size();i++)
        {
            String item = items.get(i);
            sb.append(item);
            if(i!=items.size()-1) {
            } else{
                sb.append("\n");
            }
        }
        return sb.toString();
    }

}
