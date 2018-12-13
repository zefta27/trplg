package palembang.gelumbang.zefta.uwalq.transitpalembang.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import palembang.gelumbang.zefta.uwalq.transitpalembang.R;
import palembang.gelumbang.zefta.uwalq.transitpalembang.TimeLine;

/**
 * Created by root on 29/09/18.
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {
   private  List<TimeLine> mDataset;

    public class TimelineViewHolder extends RecyclerView.ViewHolder{
        public TextView mSimpulAwal, mSimpulTujuan, mEstimasiWaktu, mEstimasiJam;
        public TimelineViewHolder(View itemView) {
            super(itemView);
            mSimpulAwal = (TextView) itemView.findViewById(R.id.tvShowSimpulAwal);
            mSimpulTujuan = (TextView) itemView.findViewById(R.id.tvShowSimpulTujuan);
            mEstimasiWaktu = (TextView) itemView.findViewById(R.id.tvShowEstimated);
            mEstimasiJam = (TextView) itemView.findViewById(R.id.tvShowEstimasiJam);

        }
    }

    public TimelineAdapter(List<TimeLine> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public TimelineAdapter.TimelineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline, parent, false);
        TimelineViewHolder tv = new TimelineViewHolder(v);
        return tv;
    }

    @Override
    public void onBindViewHolder(TimelineAdapter.TimelineViewHolder holder, int position) {
        TimeLine item =  mDataset.get(position);
        holder.mSimpulAwal.setText(item.getSimpulAwal());
        holder.mSimpulTujuan.setText(item.getSimpulAkhir());
        holder.mEstimasiWaktu.setText(item.getEstimasiRute());
        holder.mEstimasiJam.setText(item.getEstimasiJam());

    }

    @Override
    public int getItemCount() {
     return mDataset.size();
//        return (null != mDataset ? mDataset.size() : 0);

    }

}
