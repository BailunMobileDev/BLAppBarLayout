package com.wangjing.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView =  findViewById(R.id.rv);
        recyclerView.setAdapter(new TvAdapter());
    }

    public static class TvAdapter extends RecyclerView.Adapter<TvViewHolder>{

        @NonNull
        @Override
        public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TvViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
            holder.textView.setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }

    public static class TvViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
        }
    }
}
