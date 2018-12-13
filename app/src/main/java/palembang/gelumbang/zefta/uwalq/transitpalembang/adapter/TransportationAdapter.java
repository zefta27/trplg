package palembang.gelumbang.zefta.uwalq.transitpalembang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import palembang.gelumbang.zefta.uwalq.transitpalembang.R;
import palembang.gelumbang.zefta.uwalq.transitpalembang.model.Transportation;

/**
 * Created by root on 16/10/18.
 */

public class TransportationAdapter extends RecyclerView.Adapter<TransportationAdapter.ViewHolder> {
    private Context ctx;
    private List<Transportation> items = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public TransportationAdapter(Context ctx, List<Transportation> items) {
        this.ctx = ctx;
        this.items = items;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, Transportation obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener)
    {
        this.onItemClickListener = mItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_option_route,parent,false);
        RecyclerView.ViewHolder vh = new ViewHolder(v);
        return (ViewHolder) vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Transportation r = items.get(position);
        Picasso.with(ctx).load(r.image).into(holder.image);
        holder.class_name.setText(r.class_name);
        holder.price.setText(r.price);
        holder.pax.setText(r.pax);
        holder.duration.setText(r.duration);

        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener !=null)
                {
                    onItemClickListener.onItemClick(v,r, position );
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView class_name;
        public TextView price;
        public TextView pax;
        public TextView duration;
        public View lyt_parent;

        public ViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            class_name = (TextView) v.findViewById(R.id.class_name);
            price = (TextView) v.findViewById(R.id.price);
            pax = (TextView) v.findViewById(R.id.pax);
            duration = (TextView) v.findViewById(R.id.duration);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);


        }
    }
}
