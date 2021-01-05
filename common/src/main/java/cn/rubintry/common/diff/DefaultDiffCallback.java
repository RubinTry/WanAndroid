package cn.rubintry.common.diff;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

/**
 * @author logcat
 */
public class DefaultDiffCallback<T> extends DiffUtil.Callback {
    private List<T> oldData;
    private List<T> newData;

    public DefaultDiffCallback(List<T> oldData, List<T> newData) {
        this.oldData = oldData;
        this.newData = newData;
    }

    @Override
    public int getOldListSize() {
        return oldData == null ? 0 : oldData.size();
    }

    @Override
    public int getNewListSize() {
        return newData == null ? 0 : newData.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        T oldItem = oldData.get(oldItemPosition);
        T newItem = newData.get(newItemPosition);
        return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        T oldItem = oldData.get(oldItemPosition);
        T newItem = newData.get(newItemPosition);
        return oldItem == newItem;
    }
}
