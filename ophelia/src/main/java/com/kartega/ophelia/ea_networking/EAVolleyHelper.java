package com.kartega.ophelia.ea_networking;

import android.content.Context;

import com.google.gson.JsonObject;
import com.kartega.ophelia.ea_utilities.enums.LogType;
import com.kartega.ophelia.ea_utilities.interfaces.LogListener;
import com.kartega.ophelia.ea_utilities.tools.StringUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Ahmet Kılıç on 18.12.2018.
 * Copyright © 2018, Kartega. All rights reserved.
 * <p>
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
@SuppressWarnings("All")
public class EAVolleyHelper {

    /**
     * Base helper class for volley.
     * Settings and common headers are stored in this class.
     * You can use use one instance for all builders or you use separate instances for separate settings.
     */

    private Context context;
    private LogListener logListener;
    private DefaultRetryPolicy defaultRetryPolicy;
    private HashMap<String, String> headers;
    private boolean stop_listening_response;
    private final String VOLLEY_REQUEST_TAG;
    private boolean VOLLEY_SHOULD_CACHE;

    private boolean SENT_PARAMS_LOG_ENABLED;
    private boolean URL_LOG_ENABLED;
    private boolean RESPONSE_LOG_ENABLED;
    private boolean HEADERS_LOG_ENABLED;

    private String bodyContentType;
    private String paramsEncoding;
    private JSONObject jsonBody;

    private int timeOutMillis, retryCount;

    public EAVolleyHelper(Context context) {
        this.context = context;
        if (context instanceof LogListener)
            logListener = (LogListener) context;
        VOLLEY_REQUEST_TAG = context.getClass().getSimpleName();
        stop_listening_response = false;
        setShouldCache(false);
        setHeadersLogEnabled(true);
        setResponseLogEnabled(true);
        setSentParamsLogEnabled(true);
        setUrlLogEnabled(true);

        retryCount = DefaultRetryPolicy.DEFAULT_MAX_RETRIES;
        timeOutMillis = 21000;

        updateRetryPolicy();

        headers = new HashMap<>();
        jsonBody = new JSONObject();
    }

    /**
     * Add Json Body
     *
     * @param key       json Body Key
     * @param value     json Body
     * @return
     */

    public EAVolleyHelper addJsonBody(String key, String value){
        if(!StringUtils.isEmptyString(key) && !StringUtils.isEmptyString(value)) {
            try {
                this.jsonBody.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    /**
     * Sets json body, clear other bodies
     *
     * @param jsonBody  json body map
     * @return
     */

    public EAVolleyHelper setJsonBody(JSONObject jsonBody){
        if(jsonBody != null)
            this.jsonBody = jsonBody;
        return this;
    }

    /**
     * Create a string request and add it to queue.
     *
     * @param URL           request url
     * @param params        request parameters
     * @param method        request method
     * @param listener      response listener
     * @param errorListener error listener
     */
    void requestEngine(boolean isJsonRequest,
                       String URL,
                       HashMap<String, String> params,
                       String jsonBody,
                       int method,
                       Response.Listener<String> listener,
                       Response.ErrorListener errorListener) {

        if (params != null && SENT_PARAMS_LOG_ENABLED)
            doLog(LogType.DEBUG, "Sent Params: " + params.toString());

        Request request;
        if (isJsonRequest)
            request = createJsonRequest(method, URL, jsonBody, listener, errorListener);
        else
            request = createStringRequest(method, URL, params, listener, errorListener);

        addCustomRequest(request);
    }

    /**
     * Creqte a String request
     *
     * @param URL           request url
     * @param params        request parameters
     * @param method        request method
     * @param listener      response listener
     * @param errorListener error listener
     * @return
     */
    private Request createStringRequest(final int method,
                                        final String URL,
                                        final HashMap<String, String> params,
                                        Response.Listener<String> listener,
                                        Response.ErrorListener errorListener) {
        if(params != null && params.size() > 0)
            doLog(LogType.DEBUG, "Original Params:" + params.toString());
        final String mRequestBody;
        if(jsonBody != null)
            mRequestBody = jsonBody.toString();
        else
            mRequestBody = null;
        return new StringRequest(method, URL, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headers != null) {
                    if (HEADERS_LOG_ENABLED)
                        doLog(LogType.DEBUG, "HEADERS: " + headers.toString());
                    return headers;
                }
                return super.getHeaders();
            }

            @Override
            public String getBodyContentType() {
                if (!StringUtils.isEmptyString(bodyContentType))
                    return bodyContentType;
                return super.getBodyContentType();
            }

            @Override
            protected String getParamsEncoding() {
                if (!StringUtils.isEmptyString(paramsEncoding))
                    return paramsEncoding;
                return super.getParamsEncoding();
            }

            @Override
            public String getUrl() {
                String url = URL;
                if(params != null) {
                    StringBuilder stringBuilder = new StringBuilder(url);
                    List<String> paramKeys = new ArrayList<>(params.keySet());
                    for(int i = 0; i < paramKeys.size(); i++){
                        String key = paramKeys.get(i);
                        if(i == 0)
                            stringBuilder.append("?" + key + "=" + params.get(key));
                        else
                            stringBuilder.append("&" + key + "=" + params.get(key));
                    }
                    url = stringBuilder.toString();
                }
                return url;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }
        };
    }

    /**
     * Creqte a String request
     *
     * @param URL           request url
     * @param params        request parameters
     * @param method        request method
     * @param listener      response listener
     * @param errorListener error listener
     * @return
     */
    private Request createJsonRequest(int method,
                                      String URL,
                                      String params,
                                      Response.Listener<String> listener,
                                      Response.ErrorListener errorListener) {
        if (params != null) {
            if (SENT_PARAMS_LOG_ENABLED)
                doLog(LogType.DEBUG, "PARAMS:" + params);
        }

        return new JsonRequest(method, URL, StringUtils.setEmptyStringIfNull(params), listener, errorListener) {

            @Override
            protected Response parseNetworkResponse(NetworkResponse response) {
                String parsed;
                try {
                    parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                } catch (UnsupportedEncodingException e) {
                    parsed = new String(response.data);
                }
                return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headers != null) {
                    if (HEADERS_LOG_ENABLED)
                        doLog(LogType.DEBUG, "HEADERS: " + headers.toString());
                    return headers;
                }

                return super.getHeaders();
            }

            @Override
            public String getBodyContentType() {
                if (!StringUtils.isEmptyString(bodyContentType))
                    return bodyContentType;
                return super.getBodyContentType();
            }

            @Override
            protected String getParamsEncoding() {
                if (!StringUtils.isEmptyString(paramsEncoding))
                    return paramsEncoding;
                return super.getParamsEncoding();
            }
        };
    }

    /**
     * Add a custom request to queue.
     *
     * @param request request to add
     */
    public void addCustomRequest(Request request) {
        if (request == null) {
            doLog(LogType.ERROR, "Request can not be NULL");
            return;
        }

        if (URL_LOG_ENABLED)
            doLog(LogType.DEBUG, "URL: " + request.getUrl());

        request.setTag(VOLLEY_REQUEST_TAG);
        request.setRetryPolicy(defaultRetryPolicy);
        request.setShouldCache(VOLLEY_SHOULD_CACHE);
        try {
            VolleySingleton.getInstance().addToRequestQueue(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBodyContentType(String bodyContentType) {
        this.bodyContentType = bodyContentType;
    }

    public void setParamsEncoding(String paramsEncoding) {
        this.paramsEncoding = paramsEncoding;
    }

    /**
     * Show log for request
     *
     * @param message message to log
     */
    void doLog(@LogType int type, String message) {
        if (logListener != null)
            logListener.onLogRequired(type, message);
    }

    boolean isStopListeningResponse() {
        return stop_listening_response;
    }

    /**
     * Set the default config for cache
     *
     * @param shouldCache enable cache
     */
    public void setShouldCache(boolean shouldCache) {
        this.VOLLEY_SHOULD_CACHE = shouldCache;
    }

    /**
     * Set the default config for logging of parameters
     *
     * @param sentParamsLogEnabled log is enabled if true
     */
    public void setSentParamsLogEnabled(boolean sentParamsLogEnabled) {
        SENT_PARAMS_LOG_ENABLED = sentParamsLogEnabled;
    }

    /**
     * Set the log listener
     *
     * @param logListener
     */
    public void setLogListener(LogListener logListener) {
        this.logListener = logListener;
    }

    /**
     * Set the default config for logging of the URL
     *
     * @param urlLogEnabled log is enabled if true
     */
    public void setUrlLogEnabled(boolean urlLogEnabled) {
        URL_LOG_ENABLED = urlLogEnabled;
    }

    /**
     * Set the default config for logging of the response
     *
     * @param responseLogEnabled log is enabled if true
     */
    public void setResponseLogEnabled(boolean responseLogEnabled) {
        RESPONSE_LOG_ENABLED = responseLogEnabled;
    }

    /**
     * Set the default config for logging of headers
     *
     * @param headersLogEnabled log is enabled if true
     */
    public void setHeadersLogEnabled(boolean headersLogEnabled) {
        HEADERS_LOG_ENABLED = headersLogEnabled;
    }

    /**
     * Set the default config for time out millis
     *
     * @param timeOutMillis milliseconds for timeout of the request
     */
    public void setTimeOutMillis(int timeOutMillis) {
        this.timeOutMillis = timeOutMillis;
        updateRetryPolicy();
    }


    /**
     * Check if the response log is enabled
     *
     * @return response log enabled value
     */
    public boolean isResponseLogEnabled() {
        return RESPONSE_LOG_ENABLED;
    }

    /**
     * Set maximum amount of retries for a request
     *
     * @param retryCount amount of retries
     */
    public void setMaxRetries(int retryCount) {
        this.retryCount = retryCount;
        updateRetryPolicy();
    }

    /**
     * Set the default retry policy
     *
     * @param defaultRetryPolicy retryPolicy
     */
    public void setDefaultRetryPolicy(DefaultRetryPolicy defaultRetryPolicy) {
        this.defaultRetryPolicy = defaultRetryPolicy;
    }

    private void updateRetryPolicy() {
        defaultRetryPolicy = new DefaultRetryPolicy(timeOutMillis, retryCount, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    /**
     * Call this on your activity's onDestroy
     */
    public void stopResponseListening() {
        stop_listening_response = true;
        try {
            VolleySingleton.getInstance().cancelRequest(VOLLEY_REQUEST_TAG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the tag of the helper class
     *
     * @return request tag
     */
    public String getVolleyRequestTag() {
        return VOLLEY_REQUEST_TAG;
    }


    /**
     * Get the headers of the helper class
     *
     * @return headers
     */
    public HashMap<String, String> getHeaders() {
        return headers;
    }

    /**
     * Get the context
     *
     * @return context
     */
    public Context getContext() {
        return context;
    }
}
