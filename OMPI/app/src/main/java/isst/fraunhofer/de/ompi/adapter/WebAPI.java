package isst.fraunhofer.de.ompi.adapter;


import android.content.Context;

import isst.fraunhofer.de.ompi.Constants;
import retrofit.client.OkClient;
import retrofit.RestAdapter;
/**
 * Created by durin on 22/12/2014.
 */
public class WebAPI {



    Context mContext;
    WebInterface service;

    public WebAPI(Context pContext) {
        mContext = pContext.getApplicationContext();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.REST_ENDPOINT)
                .setClient(new OkClient())
               // .setRequestInterceptor(new AuthInterceptor(mContext))
                .setLogLevel(Constants.REST_LOGLEVEVL)
                .build();

        service = restAdapter.create(WebInterface.class);
    }

    public WebInterface getService(){
        return service;
    }
}
