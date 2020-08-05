package cn.rubintry.chapters.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cn.rubintry.common.model.LoginModel;
import cn.rubintry.common.model.RegisterModel;
import cn.rubintry.common.model.BaseModel;
import cn.rubintry.common.utils.http.OkHttpUtils;
import cn.rubintry.common.utils.http.ResponseCallback;

/**
 * @author logcat
 */
public class LoginAndRegisterViewModel extends ViewModel {
    private MutableLiveData<BaseModel<LoginModel>> userData;
    private MutableLiveData<BaseModel<RegisterModel>> registerData;


    /**
     * 监听登录数据
     * @param userName
     * @param password
     * @return
     */
    public MutableLiveData<BaseModel<LoginModel>> login(String userName , String password) {
        if (userData == null) {
            userData = new MutableLiveData<>();
        }
        requestLogin(userName , password);
        return userData;
    }


    /**
     * 发起登录请求
     * @param userName
     * @param password
     */
    private void requestLogin(String userName , String password) {
        OkHttpUtils.getInstance()
                .post("/user/login")
                .addParameters("username" , userName)
                .addParameters("password" , password)
                .request(new ResponseCallback<BaseModel<LoginModel>>() {
                    @Override
                    protected void onSuccess(BaseModel<LoginModel> loginModelBaseModel) {
                        userData.postValue(loginModelBaseModel);
                    }

                    @Override
                    protected void onFailed(Exception e, BaseModel<LoginModel> loginModelBaseModel) {
                        userData.postValue(loginModelBaseModel);
                    }

                });
    }


    /**
     * 监听注册账号
     * @param username
     * @param password
     * @param rePassword
     * @return
     */
    public MutableLiveData<BaseModel<RegisterModel>> register(String username , String password , String rePassword) {

        if(registerData == null){
            registerData = new MutableLiveData<>();
        }

        requestRegister(username , password , rePassword);

        return registerData;
    }


    /**
     * 发起注册请求
     * @param username
     * @param password
     * @param rePassword
     */
    private void requestRegister(String username , String password , String rePassword) {
        OkHttpUtils.getInstance()
                .post("/user/register")
                .addParameters("username" , username)
                .addParameters("password" , password)
                .addParameters("repassword" , rePassword)
                .request(new ResponseCallback<BaseModel<RegisterModel>>() {
                    @Override
                    protected void onSuccess(BaseModel<RegisterModel> registerModelBaseModel) {
                        registerData.postValue(registerModelBaseModel);
                    }

                    @Override
                    protected void onFailed(Exception e, BaseModel<RegisterModel> registerModelBaseModel) {
                        registerData.postValue(registerModelBaseModel);
                    }
                });
    }
}
