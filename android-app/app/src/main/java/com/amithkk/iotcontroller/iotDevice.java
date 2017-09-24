package com.amithkk.iotcontroller;

/**
 * Created by Amith on 18-08-2017.
 */

public class iotDevice {

    String type;
    String ipAddr;
    String mDnsName;

    public iotDevice(String type, String ipAddr, String mDnsName) {
        this.type = type;
        this.ipAddr = ipAddr;
        this.mDnsName = mDnsName;
    }
}
