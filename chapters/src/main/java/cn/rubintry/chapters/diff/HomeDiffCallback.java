package cn.rubintry.chapters.diff;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import cn.rubintry.chapters.model.SubscriptionModel;

/**
 * @author logcat
 */
public class HomeDiffCallback extends DiffUtil.Callback {
    private List<SubscriptionModel> current;
    private List<SubscriptionModel> next;

    public HomeDiffCallback(List<SubscriptionModel> current, List<SubscriptionModel> next) {
        this.current = current;
        this.next = next;
    }

    @Override
    public int getOldListSize() {
        return current.size();
    }

    @Override
    public int getNewListSize() {
        return next.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        SubscriptionModel currentSubscriptionModel = current.get(oldItemPosition);
        SubscriptionModel nextSubscriptionModel = next.get(newItemPosition);

        return currentSubscriptionModel.equals(nextSubscriptionModel);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        SubscriptionModel currentSubscriptionModel = current.get(oldItemPosition);
        SubscriptionModel nextSubscriptionModel = next.get(newItemPosition);

        return currentSubscriptionModel.equals(nextSubscriptionModel);
    }
}
