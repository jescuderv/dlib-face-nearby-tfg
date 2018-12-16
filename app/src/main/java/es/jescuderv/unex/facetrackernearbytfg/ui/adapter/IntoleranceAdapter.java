package es.jescuderv.unex.facetrackernearbytfg.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jescuderv.unex.facetrackernearbytfg.R;
import es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel.UserViewModel;

public class IntoleranceAdapter extends RecyclerView.Adapter<IntoleranceAdapter.ViewHolder> {

    private List<UserViewModel.Intolerance> mDataSetList;

    public IntoleranceAdapter(List<UserViewModel.Intolerance> dateSetList) {
        mDataSetList = dateSetList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IntoleranceAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_staggered_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(mDataSetList.get(i));
    }

    @Override
    public int getItemCount() {
        return mDataSetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_staggered_text)
        TextView mText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(UserViewModel.Intolerance intolerance) {
            mText.setText(intolerance.getName());
        }
    }
}
