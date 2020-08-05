package cn.rubintry.chapters.fragment

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import butterknife.BindView
import cn.gorouter.annotation.Route
import cn.rubintry.chapters.R
import cn.rubintry.chapters.R2
import cn.rubintry.chapters.adapters.HomePageAdapter
import cn.rubintry.common.base.BaseFragment
import com.google.android.material.tabs.TabLayout


@Route(url = "chapters/HomeFragment")
class HomeFragment : BaseFragment(){

    @BindView(R2.id.vpHome)
    lateinit var vpHome: ViewPager2

    @BindView(R2.id.tbHome)
    lateinit var tbHome: TabLayout

    private lateinit var pageAdapter: HomePageAdapter
    private var pageList: ArrayList<Fragment>? = null
    private var titleList: ArrayList<String>? = null
    override fun attachedLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun initViews() {
        pageList = ArrayList()
        pageList?.add(HomeTabFragment())
        pageList?.add(HomeTabFragment())
        pageList?.add(HomeTabFragment())

        titleList = ArrayList()
        titleList?.add("tab1")
        titleList?.add("tab2")
        titleList?.add("tab3")
        pageAdapter = HomePageAdapter(
            getDefaultContext()?.supportFragmentManager!!,
            lifecycle,
            pageList
        )
        vpHome.adapter = pageAdapter
        pageAdapter.notifyDataSetChanged()

        vpHome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                tbHome.getTabAt(position)?.select()
            }
        })
        tbHome.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                vpHome.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


        for (i in titleList?.indices!!) {
            if (i == 0) {
                tbHome.addTab(tbHome.newTab().setText(titleList?.get(i)), true)
            } else {
                tbHome.addTab(tbHome.newTab().setText(titleList?.get(i)))
            }
        }
    }





}