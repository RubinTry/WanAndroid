package cn.rubintry.common.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import butterknife.ButterKnife
import cn.rubintry.common.FragmentMonitor

abstract class BaseFragment : Fragment(){

    lateinit var currentContext: FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView : View = layoutInflater.inflate(attachedLayoutRes() , container , false)
        ButterKnife.bind(this , rootView)
        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        currentContext = this!!.activity!!
        initViews()
    }



    abstract fun attachedLayoutRes() : Int

    abstract fun initViews()

    fun getDefaultContext() : FragmentActivity?{
        return currentContext
    }

    fun finish(){
        FragmentMonitor.instance?.finish(this)
    }


    protected fun jumpToFragment(fragment: Fragment){
        FragmentMonitor.instance?.jump(fragment)
    }


}