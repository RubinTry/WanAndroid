package cn.rubintry.chapters.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.rubintry.chapters.model.SubscriptionModel
import cn.rubintry.common.model.BaseModel
import cn.rubintry.common.utils.http.OkHttpUtils
import cn.rubintry.common.utils.http.ResponseCallback

/**
 * @author logcat
 */
class SubscriptionViewModel : ViewModel() {
    private var subscriptionData: MutableLiveData<BaseModel<List<SubscriptionModel>>> = MutableLiveData()

    fun getSubscriptionData(): MutableLiveData<BaseModel<List<SubscriptionModel>>> {
        loadData()
        return subscriptionData
    }

    fun loadData() {

        OkHttpUtils.instance
            ?.get("/wxarticle/chapters/json")
            ?.request(object  : ResponseCallback<BaseModel<List<SubscriptionModel>>>(){
                override fun onSuccess(t: BaseModel<List<SubscriptionModel>>) {
                    subscriptionData.postValue(t)
                }

                override fun onFailed(e: Exception?, t: BaseModel<List<SubscriptionModel>>?) {
                    subscriptionData.postValue(t)
                }

            })
    }
}