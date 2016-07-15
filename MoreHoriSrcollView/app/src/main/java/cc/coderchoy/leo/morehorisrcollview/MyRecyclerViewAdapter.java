package cc.coderchoy.leo.morehorisrcollview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * author ChenCHaoXue
 * Created by supercard on 2016/7/15 15:19
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private List<String> mData;
    private Context mContext;
    private LayoutInflater inflater;

    public MyRecyclerViewAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mData = data;
        this.inflater=LayoutInflater.from(context);
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        holder.tv_content.setText(mData.get(position));
        holder.lly_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,mData.get(position)+"  top",Toast.LENGTH_SHORT).show();
                if(topListener!=null){
                    bottomListener.bottomOnClick(v,position);
                }
            }
        });
        holder.tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,mData.get(position),Toast.LENGTH_SHORT).show();
                if(bottomListener!=null){
                    bottomListener.bottomOnClick(v,position);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        MyViewHolder holder=null;
        if(view==null){
            view = inflater.inflate(R.layout.item, parent, false);
        }
        if(holder==null){
            holder = new MyViewHolder(view);
        }

        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lly_detail;
        TextView tv_content;

        public MyViewHolder(View itemView) {
            super(itemView);
            lly_detail = (LinearLayout) itemView.findViewById(R.id.lly_detail);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }

    TopListener topListener;

    public void setTopListener(TopListener topListener) {
        this.topListener = topListener;
    }

    public interface TopListener {
        void topOnClick(View v,int Position);
    }

    BottomListener bottomListener;

    public void setBottomListener(BottomListener bottomListener) {
        this.bottomListener = bottomListener;
    }

    public interface BottomListener {
        void bottomOnClick(View v,int Position);
    }
}
