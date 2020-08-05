package cn.rubintry.chapters.fragment

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import cn.rubintry.chapters.R
import cn.rubintry.chapters.R2
import cn.rubintry.chapters.adapters.HomeFragmentListAdapter
import cn.rubintry.chapters.model.SubscriptionModel
import cn.rubintry.chapters.viewmodel.SubscriptionViewModel
import cn.rubintry.common.base.BaseFragment
import cn.rubintry.common.utils.ToastUtils

class HomeTabFragment : BaseFragment(){
    private val TAG: String? = this.javaClass.simpleName

    @BindView(R2.id.rvHome)
    lateinit var rvHome : RecyclerView

    lateinit var homeFragmentListAdapter: HomeFragmentListAdapter
    override fun attachedLayoutRes(): Int {
        return R.layout.fragment_tab_home
    }

    override fun initViews() {
        rvHome.layoutManager = LinearLayoutManager(getDefaultContext())
        rvHome.isNestedScrollingEnabled = false
        homeFragmentListAdapter = HomeFragmentListAdapter(ArrayList<SubscriptionModel>(), R.layout.item_home)
        rvHome.adapter = homeFragmentListAdapter
        homeFragmentListAdapter.setOnItemClickListener{
            data ->
            ToastUtils.showShort("点击了" + data.name )
            Log.d(TAG,"点击了" + data.name )
        }


    }


    override fun onResume() {
        super.onResume()
        val subscriptionViewModel: SubscriptionViewModel by viewModels()
        subscriptionViewModel.getSubscriptionData().observe(this , Observer { result ->
            if(result != null && result.errorCode == 0){
                val data = result.data
                if(homeFragmentListAdapter.isEmpty){
                    homeFragmentListAdapter.setNewData(data)
                }else{
                    homeFragmentListAdapter.appendData(data)
                }

            }else{
                ToastUtils.showShort("请求失败：" + result.errorMsg)
            }
        })
    }
}