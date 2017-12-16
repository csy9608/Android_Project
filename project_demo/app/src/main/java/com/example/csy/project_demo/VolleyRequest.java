package com.example.csy.project_demo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Created by csy on 2017-11-30.
 */

public class VolleyRequest extends StringRequest{
    public static enum MODE{
        DETAIL, LOGIN, MAINPAGE, MYPAGE, SIGNUP, UPLOAD, CHECKID, ULIKE, MMYINFO, MBOARD , SEARCHPAGE , GETFREQTAGS};
    private Map<String, String> parameters;

    public static String getAdd(MODE mode){
        switch (mode){
            case DETAIL:
                return PhpAddress.Detail;
            case LOGIN:
                return PhpAddress.Login;
            case MAINPAGE:
                return PhpAddress.MainPage;
            case MYPAGE:
                return PhpAddress.MyPage;
            case SIGNUP:
                return PhpAddress.SignUp;
            case UPLOAD:
                return PhpAddress.Upload;
            case CHECKID:
                return PhpAddress.CheckId;
            case ULIKE:
                return PhpAddress.UpdateLike;
            case MMYINFO:
                return PhpAddress.ModifyMyInfo;
            case MBOARD:
                return PhpAddress.ModifyBoard;
            case SEARCHPAGE:
                return PhpAddress.SearchPage;
            case GETFREQTAGS:
                return PhpAddress.GetFreqTags;
            default:
                break;
        }
      return null;
    }

    public VolleyRequest(MODE mode, Map<String,String> params ,Response.Listener<String> listener){
        super(Method.POST, VolleyRequest.getAdd(mode), listener, null);
        this.parameters = params;
    }

    @Override
    protected Map<String, String> getParams(){
        return parameters;
    }
}
