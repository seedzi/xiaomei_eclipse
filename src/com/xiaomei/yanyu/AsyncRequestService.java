
package com.xiaomei.yanyu;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.api.builder.NetResultBuilder;
import com.xiaomei.yanyu.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.yanyu.api.exception.XiaoMeiIOException;
import com.xiaomei.yanyu.api.exception.XiaoMeiJSONException;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.api.http.AbstractHttpApi;
import com.xiaomei.yanyu.api.http.HttpApi;
import com.xiaomei.yanyu.bean.NetResult;
import com.xiaomei.yanyu.util.Security;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.util.UserUtil;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.widget.Toast;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class AsyncRequestService extends IntentService {

    private static final String ACTION_NEW_POST = "com.xiaomei.yanyu.action.FOO";

    private static final String EXTRA_CONTENT = "com.xiaomei.yanyu.extra.CONTENT";

    private static final String EXTRA_ATTACHMENT_URIS = "com.xiaomei.yanyu.extra.ATTACHMENT_URIS";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startNewPost(Context context, String content, ArrayList<Uri> attachmentUris) {
        Intent intent = new Intent(context, AsyncRequestService.class);
        intent.setAction(ACTION_NEW_POST);
        intent.putExtra(EXTRA_CONTENT, content);
        intent.putParcelableArrayListExtra(EXTRA_ATTACHMENT_URIS, attachmentUris);
        context.startService(intent);
    }

    public AsyncRequestService() {
        super("AsyncRequestService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NEW_POST.equals(action)) {
                final ArrayList<Uri> attachmentUris = intent.<Uri>getParcelableArrayListExtra(EXTRA_ATTACHMENT_URIS);
                handleNewPost(intent.getStringExtra(EXTRA_CONTENT), intent.<Uri>getParcelableArrayListExtra(EXTRA_ATTACHMENT_URIS));
            }
        }
    }

    /**
     * Handle action New Post in the provided background thread with the provided
     * parameters.
     */
    private void handleNewPost(String content, List<Uri> attachmentUris) {
        JSONArray attahcmentArray = uploadAttachments(attachmentUris);
        requestNewPost(content, attahcmentArray);
    }

    private String requestNewPost(String content, JSONArray attahcmentArray) {
        HttpApi httpApi = XiaoMeiApplication.getInstance().getApi().getHttpApi();
        NameValuePair[] signedValuePairs = AbstractHttpApi.signValuePairs(
                new BasicNameValuePair("token", UserUtil.getUser().getToken()),
                new BasicNameValuePair("content", content),
                new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000)),
                new BasicNameValuePair("files", attahcmentArray.toString())
        );
        try {
            HttpPost request = httpApi.createHttpPost(HttpUrlManager.NEW_POST, signedValuePairs);
            NetResult response = httpApi.doHttpRequestObject(request, new NetResultBuilder());
            UiUtil.showToast(this, response.getMsg());
        } catch (XiaoMeiIOException e) {
            e.printStackTrace();
        } catch (XiaoMeiCredentialsException e) {
            e.printStackTrace();
        } catch (XiaoMeiJSONException e) {
            e.printStackTrace();
        } catch (XiaoMeiOtherException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONArray uploadAttachments(List<Uri> attachmentUris) {
        JSONArray jsonArray = new JSONArray();
        for (Uri uri : attachmentUris) {
           jsonArray.put(requestUploadAttachment(uri));
        }
        return jsonArray;
    }

    private String requestUploadAttachment(Uri uri) {
        HttpApi httpApi = XiaoMeiApplication.getInstance().getApi().getHttpApi();
        try {
            HttpPost httpPost = httpApi.createHttpPost(HttpUrlManager.UPLOAD_FILE);
            MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            NameValuePair[] signedValuePairs = AbstractHttpApi.signValuePairs(
                    new BasicNameValuePair("token", UserUtil.getUser().getToken()),
                    new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))
            );
            for (NameValuePair pair : signedValuePairs) {
                multipartEntity.addPart(pair.getName(), new StringBody(pair.getValue()));
            }
            AssetFileDescriptor fileDescriptor = getContentResolver().openAssetFileDescriptor(uri, "r");
            FileInputStream input = new FileInputStream(fileDescriptor.getFileDescriptor());
            byte[] data = readStream(input);
            input.close();
            multipartEntity.addPart("imgFile", new ByteArrayBody(data, "file", uri.getLastPathSegment()));
            httpPost.setEntity(multipartEntity);
            String response = httpApi.doHttpRequestString(httpPost);
            JSONObject json = new JSONObject(response);
            int code = json.getInt("code");
            if (code == 0) {
                return json.getJSONObject("msg").getString("file_url");
            } else {
                UiUtil.showToast(this, json.getString("msg"));
                return null;
            }
        } catch (XiaoMeiIOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XiaoMeiOtherException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        UiUtil.showToast(this, R.string.error_upload_file);
        return null;
    }
    
    public static byte[] readStream(InputStream in) throws IOException {
        int len;
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                os.write(buffer, 0, len);
            }
            return os.toByteArray();
        } finally {
            os.close();
        }
    }
}
