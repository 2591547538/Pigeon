package com.pigeon.communication;

public interface FFmpegh extends ResponseHandler {


     void onSuccess(String message);


     void onProgress(String message);
 void onFailure(String message);

}
