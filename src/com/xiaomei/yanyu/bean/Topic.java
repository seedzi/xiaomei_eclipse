package com.xiaomei.yanyu.bean;

public class Topic {

    public static final String TYPE_DETAIL = "0";

    public static final String TYPE_DISPLAY = "1";

    private String title;
    private String image_6;
    private int viewcount;
    private String type;
    private String url;
    private Info list;

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
