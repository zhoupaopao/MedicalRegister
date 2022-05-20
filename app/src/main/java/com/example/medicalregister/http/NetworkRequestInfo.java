package com.example.medicalregister.http;

import android.os.Build;

import com.arch.demo.network_api.INetworkRequestInfo;

import java.util.HashMap;

/**
 *
 * @author Allen
 * @date 2017/7/20
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class NetworkRequestInfo implements INetworkRequestInfo {
    HashMap<String, String> headerMap = new HashMap<>();

    public NetworkRequestInfo(){
        headerMap.put("DeviceType", Build.MODEL);
        headerMap.put("versionT", "");
    }

    @Override
    public HashMap<String, String> getRequestHeaderMap() {
        return headerMap;
    }

    @Override
    public boolean isDebug() {
        return true;
    }
}
