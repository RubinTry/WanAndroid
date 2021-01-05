package cn.rubintry.lib_common.base

data class BaseModel<T>(val data : T , val errorCode : Int , val errorMsg: String)