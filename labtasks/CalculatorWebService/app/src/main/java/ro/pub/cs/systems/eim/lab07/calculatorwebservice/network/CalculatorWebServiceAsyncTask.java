package ro.pub.cs.systems.eim.lab07.calculatorwebservice.network;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;
import ro.pub.cs.systems.eim.lab07.calculatorwebservice.general.Constants;

public class CalculatorWebServiceAsyncTask extends AsyncTask<String, Void, String> {

    private TextView resultTextView;

    public CalculatorWebServiceAsyncTask(TextView resultTextView) {
        this.resultTextView = resultTextView;
    }

    @Override
    protected String doInBackground(String... params) {
        String operator1 = params[0];
        String operator2 = params[1];
        String operation = params[2];
        int method = Integer.parseInt(params[3]);
        String error = null;

        // TODO exercise 4
        // signal missing values through error messages
        if (operator1 == null || operator2 == null || operator1.equals("") || operator2.equals("")) {
            error = "Add operators";
        }
        if (error != null) {
            return error;
        }

        // create an instance of a HttpClient object
        try {
            HttpClient httpClient = new DefaultHttpClient();
            if (method == Constants.GET_OPERATION) {
                HttpGet httpGet = new HttpGet("https://wi-fi.cs.pub.ro/~dniculescu/didactic/eim/expr/expr_get.php?operation="
                        + operation + "&t1=" + operator1 + "&t2=" + operator2);
                /*HttpResponse httpGetResponse = httpClient.execute(httpGet);
                HttpEntity httpGetEntity = httpGetResponse.getEntity();
                if (httpGetEntity != null) {
                    // do something with the response
                    Log.i(Constants.TAG, EntityUtils.toString(httpGetEntity));
                }*/
                ResponseHandler<String> responseHandlerGet = new BasicResponseHandler();
                try {
                    return httpClient.execute(httpGet, responseHandlerGet);
                } catch (ClientProtocolException clientProtocolException) {
                    Log.e(Constants.TAG, clientProtocolException.getMessage());
                    if (Constants.DEBUG) {
                        clientProtocolException.printStackTrace();
                    }
                } catch (IOException ioException) {
                    Log.e(Constants.TAG, ioException.getMessage());
                    if (Constants.DEBUG) {
                        ioException.printStackTrace();
                    }
                }
            } else {
                HttpPost httpPost = new HttpPost("https://wi-fi.cs.pub.ro/~dniculescu/didactic/eim/expr/expr_post.php");

                List<NameValuePair> paramsPost = new ArrayList<NameValuePair>();
                paramsPost.add(new BasicNameValuePair("operation", operation));
                paramsPost.add(new BasicNameValuePair("t1", operator1));
                paramsPost.add(new BasicNameValuePair("t2", operator2));

                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(paramsPost, HTTP.UTF_8);
                httpPost.setEntity(urlEncodedFormEntity);

                /*HttpResponse httpPostResponse = httpClient.execute(httpPost);
                HttpEntity httpPostEntity = httpPostResponse.getEntity();
                if (httpPostEntity != null) {
                    // do something with the response
                    Log.i(Constants.TAG, EntityUtils.toString(httpPostEntity));
                }*/
                ResponseHandler<String> responseHandlerGet = new BasicResponseHandler();
                try {
                    return httpClient.execute(httpPost, responseHandlerGet);
                } catch (ClientProtocolException clientProtocolException) {
                    Log.e(Constants.TAG, clientProtocolException.getMessage());
                    if (Constants.DEBUG) {
                        clientProtocolException.printStackTrace();
                    }
                } catch (IOException ioException) {
                    Log.e(Constants.TAG, ioException.getMessage());
                    if (Constants.DEBUG) {
                        ioException.printStackTrace();
                    }
                }
            }
        } catch (Exception exception) {
            Log.e(Constants.TAG, exception.getMessage());
            if (Constants.DEBUG) {
                exception.printStackTrace();
            }
        }

        // get method used for sending request from methodsSpinner

        // 1. GET
        // a) build the URL into a HttpGet object (append the operators / operations to the Internet address)
        // b) create an instance of a ResultHandler object
        // c) execute the request, thus generating the result

        // 2. POST
        // a) build the URL into a HttpPost object
        // b) create a list of NameValuePair objects containing the attributes and their values (operators, operation)
        // c) create an instance of a UrlEncodedFormEntity object using the list and UTF-8 encoding and attach it to the post request
        // d) create an instance of a ResultHandler object
        // e) execute the request, thus generating the result

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // display the result in resultTextView
        resultTextView.setText(result);
    }

}
