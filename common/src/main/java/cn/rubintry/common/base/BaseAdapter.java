package cn.rubintry.common.base;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.rubintry.common.diff.DefaultDiffCallback;


/**
 * @author logcat
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {

    protected List<T> data;
    protected int resId;
    protected OnItemClickListener onItemClickListener;


    public void setNewData(List<T> data) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DefaultDiffCallback<T>(this.data , data) , true);
        diffResult.dispatchUpdatesTo(this);
        this.data = data;
    }


    public void appendData(List<T> newData){
        List<T> oldData = new ArrayList<>();
        oldData.addAll(this.data);
        this.data.addAll(newData);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DefaultDiffCallback<T>(oldData , this.data) , true);
        diffResult.dispatchUpdatesTo(this);
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public BaseAdapter(List<T> data, int resId) {
        this.data = data;
        this.resId = resId;
    }


    public boolean isEmpty(){
        return data == null || data.size() == 0;
    }



    @NonNull
    @Override
    public BaseAdapter<T>.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, viewType);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.BaseViewHolder holder, int position) {
        bindBaseViewHolder(holder, position);
    }

    protected abstract BaseViewHolder createBaseViewHolder(@NonNull ViewGroup parent, int viewType);

    protected abstract void bindBaseViewHolder(BaseViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class BaseViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;

        public BaseViewHolder(@NonNull ViewDataBinding viewBinding) {
            super(viewBinding.getRoot());
            binding = viewBinding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }



}
