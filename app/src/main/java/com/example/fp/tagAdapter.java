package com.example.fp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class tagAdapter extends RecyclerView.Adapter<tagAdapter.ViewHolder>{

    Context mContext;
    List<String> mTags;
    List<String> mTagsCount;

    public tagAdapter(Context mContext, List<String> mTags, List<String> mTagsCount) {
        this.mContext = mContext;
        this.mTags = mTags;
        this.mTagsCount = mTagsCount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(mContext).inflate(R.layout.tag_item,parent,false);
        return new tagAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
           holder.tag.setText("#"+mTags.get(position));
           holder.noOfPosts.setText(mTagsCount.get(position) +" Posts");
    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tag;
        public TextView noOfPosts;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tag = itemView.findViewById(R.id.hashtag);
            noOfPosts = itemView.findViewById(R.id.no_of_posts);

        }
    }
    public void filter(List<String>filterTags,List<String> filtertagsCount)
    {
        this.mTags = filterTags;
        this.mTagsCount = filtertagsCount;
        notifyDataSetChanged();
    }
}
