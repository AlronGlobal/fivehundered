package com.example.allenthomasjoy.fivehundredpx.singleton;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class SingletonRequestQueue {
    private static SingletonRequestQueue mInstance;
    private RequestQueue mRequestQueue;
    private Context mContext;
    private RetryPolicy mRetryPolicy;

    private SingletonRequestQueue(Context context){
        mContext = context;
        mRequestQueue = getRequestQueue();

        mRetryPolicy = new DefaultRetryPolicy(
                0,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    public static synchronized SingletonRequestQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SingletonRequestQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setRetryPolicy(mRetryPolicy);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(final Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    boolean cancel = request.getTag() == tag;
                    if(cancel) {
                        request.getErrorListener().onErrorResponse(new VolleyError(tag.toString()));
                    }
                    return cancel;
                }
            });
            //mRequestQueue.cancelAll(tag);
        }
    }

}
