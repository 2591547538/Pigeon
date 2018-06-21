package com.pigeon.communication;

import java.util.Map;

import com.pigeon.communication.exceptions.FFmpegc;
import com.pigeon.communication.exceptions.FFmpegm;

@SuppressWarnings("unused")
interface FFmpegInterface {


    public void loadBinary(FFmpegg ffmpegLoadBinaryResponseHandler) throws FFmpegm;


    public void execute(Map<String, String> environvenmentVars, String[] cmd, FFmpegh ffmpegExecuteResponseHandler) throws FFmpegc;


    public void execute(String[] cmd, FFmpegh ffmpegExecuteResponseHandler) throws FFmpegc;


    public String getDeviceFFmpegVersion() throws FFmpegc;


    public String getLibraryFFmpegVersion();


    public boolean isFFmpegCommandRunning();


    public boolean killRunningProcesses();


    public void setTimeout(long timeout);

}
