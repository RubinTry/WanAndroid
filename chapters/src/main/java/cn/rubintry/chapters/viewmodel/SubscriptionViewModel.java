package cn.rubintry.chapters.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cn.rubintry.chapters.model.SubscriptionModel;
import cn.rubintry.common.model.BaseModel;
import cn.rubintry.common.utils.http.OkHttpUtils;
import cn.rubintry.common.utils.http.ResponseCallback;

/**
 * @author logcat
 */
public class SubscriptionViewModel extends ViewModel {
    private MutableLiveData<BaseModel<List<SubscriptionModel>>> subscriptionData;

    public MutableLiveData<BaseModel<List<SubscriptionModel>>> getSubscriptionData(){
        if(subscriptionData == null){
            subscriptionData = new MutableLiveData<>();
        }
        loadData();
        return subscriptionData;
    }

    private void loadData() {
        OkHttpUtils.getInstance()
                .get("/wxarticle/chapters/json")
                .request(new ResponseCallback<BaseModel<List<SubscriptionModel>>>() {
                             @Override
                             protected void onSuccess(BaseModel<List<SubscriptionModel>> listBaseModel) {
                                 subscriptionData.postValue(listBaseModel);
                             }

                             @Override
                             protected void onFailed(Exception e, BaseModel<List<SubscriptionModel>> listBaseModel) {
                                 subscriptionData.postValue(listBaseModel);
                             }
                         }

                );
    }
}
