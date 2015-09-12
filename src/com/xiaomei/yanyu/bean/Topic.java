package com.xiaomei.yanyu.bean;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class Topic {

    public static final String TYPE_DETAIL = "0";

    public static final String TYPE_DISPLAY = "1";

    private String title;
    private String image_6;
    private int viewcount;
    private String type;
    private String url;
    private transient Info list;

    public static Gson newGson() {
        return new GsonBuilder().registerTypeAdapter(Topic.class, new JsonDeserializer<Topic>() {
            @Override
            public Topic deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                Gson gson = new Gson();
                Topic topic = gson.fromJson(json, Topic.class);
                JsonElement list = json.getAsJsonObject().get("list");
                // list may be emyty array. Bad design...
                topic.list = (list.isJsonObject() ? gson.fromJson(list, Info.class) : null);
                return topic;
            }
        }).create();
    }

    public String getTitle() {
        return title;
    }

    public String getImageLarge() {
        return image_6;
    }

    public int getViewCount() {
        return viewcount;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public Info getInfo() {
        return list;
    }

    public static class Info {

        private String title;
        private String des;
        private Segment[] images;

        public String getDes() {
            return des;
        }
        public Segment[] getSegments() {
            return images;
        }
    }

    public static class Segment {

        private String title;
        private String des;
        private String image_6;
        private String price_xm;
        private String price_market;
        private String hosp_name;
        private String link;
    }
}
