package com.example.networkinghw.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.networkinghw.R;
import com.example.networkinghw.model.Channel;

import java.util.ArrayList;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder> {
    private Context mContext;
    private ArrayList<Channel> mChannelList;
    private OnChannelClickListener mListener;


    public interface OnChannelClickListener{
        void onChannelClick(int position);
    }
    public void setOnItemClickListener(OnChannelClickListener listener){
         mListener = listener;
    }
    public ChannelAdapter(Context context, ArrayList<Channel> channelList){
        mContext = context;
        mChannelList = channelList;
    }

    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new ChannelViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder holder, int position) {
        Channel currentChannel = mChannelList.get(position);

        String titleChannel = currentChannel.getTitle();
        holder.mTextViewTitle.setText(titleChannel);
    }

    @Override
    public int getItemCount() {
        return mChannelList.size();
    }

    public class ChannelViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextViewTitle;

        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.textViewNameChannel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!= null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onChannelClick(position);
                        }
                    }
                }
            });
        }
    }
}
