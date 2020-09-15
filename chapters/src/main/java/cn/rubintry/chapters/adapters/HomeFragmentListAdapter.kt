package cn.rubintry.chapters.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import cn.rubintry.chapters.BR

import cn.rubintry.chapters.model.SubscriptionModel
import cn.rubintry.common.base.BaseAdapter

class HomeFragmentListAdapter(data: List<SubscriptionModel>, resId: Int) :
    BaseAdapter<SubscriptionModel>(data, resId) {
    override fun createBaseViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                resId,
                parent,
                false
            )
        )
    }

    override fun bindBaseViewHolder(holder: BaseViewHolder, position: Int) {
        val viewDataBinding: ViewDataBinding = holder.binding
        viewDataBinding.setVariable(BR.data , data.get(position))
        holder.itemView.setOnClickListener{
            onItemClickListener?.onItemClick(data[position])
        }
    }



}