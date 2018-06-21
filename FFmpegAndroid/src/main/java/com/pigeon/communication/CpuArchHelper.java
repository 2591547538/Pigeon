package com.pigeon.communication;

import android.os.Build;
import android.util.Log;

class CpuArchHelper {
    
    static CpuArch getCpuArch() {
      //  Log.d("Build.CPU_ABI : " + Build.CPU_ABI);
        if (Build.CPU_ABI.equals(getx86CpuAbi()) || Build.CPU_ABI.equals(getx86_64CpuAbi())) {
            return CpuArch.x86;
        } else {
            if (Build.CPU_ABI.equals(getArmeabiv7CpuAbi())) {
                ArmArchHelper cpuNativeArchHelper = new ArmArchHelper();
                String archInfo = cpuNativeArchHelper.cpuArchFromJNI();
                if (cpuNativeArchHelper.isARM_v7_CPU(archInfo)) {
                    return CpuArch.ARMv7;
                }
            } else if (Build.CPU_ABI.equals(getArm64CpuAbi())) {
                return CpuArch.ARMv7;
            }
        }
        return CpuArch.NONE;
    }
    
    static String getx86CpuAbi() {
        return "x86";
    }
    
    static String getx86_64CpuAbi() {
        return "x86_64";
    }
    
    static String getArm64CpuAbi() {
        return "arm64-v8a";
    }

    static String getArmeabiv7CpuAbi() {
        return "armeabi-v7a";
    }
}
