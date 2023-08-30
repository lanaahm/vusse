package com.example.vusse.model.register;

import com.google.gson.annotations.SerializedName;

public class RegisterApiModelError {

    @SerializedName("data")
    private ResgiterApiError resgiterApiError;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public void setData(ResgiterApiError resgiterApiError){
        this.resgiterApiError = resgiterApiError;
    }

    public ResgiterApiError getData(){
        return resgiterApiError;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public boolean isSuccess(){
        return success;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
