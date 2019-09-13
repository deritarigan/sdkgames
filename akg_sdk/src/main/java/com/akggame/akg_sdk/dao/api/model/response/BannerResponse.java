package com.akggame.akg_sdk.dao.api.model.response;

import java.util.List;

public class BannerResponse extends BaseResponse{


    private MetaBean meta;
    private List<DataBean> data;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private String id;
        private String type;
        private AttributesBean attributes;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public AttributesBean getAttributes() {
            return attributes;
        }

        public void setAttributes(AttributesBean attributes) {
            this.attributes = attributes;
        }

        public static class AttributesBean {

            private String title;
            private String image_link_url;
            private CoverImageBean cover_image;
            private GameBean game;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImage_link_url() {
                return image_link_url;
            }

            public void setImage_link_url(String image_link_url) {
                this.image_link_url = image_link_url;
            }

            public CoverImageBean getCover_image() {
                return cover_image;
            }

            public void setCover_image(CoverImageBean cover_image) {
                this.cover_image = cover_image;
            }

            public GameBean getGame() {
                return game;
            }

            public void setGame(GameBean game) {
                this.game = game;
            }

            public static class CoverImageBean {

                private String url;
                private ThumbBean thumb;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public ThumbBean getThumb() {
                    return thumb;
                }

                public void setThumb(ThumbBean thumb) {
                    this.thumb = thumb;
                }

                public static class ThumbBean {

                    private String url;

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }
                }
            }

            public static class GameBean {

                private String id;
                private String name;
                private String platform;
                private String product_code;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getPlatform() {
                    return platform;
                }

                public void setPlatform(String platform) {
                    this.platform = platform;
                }

                public String getProduct_code() {
                    return product_code;
                }

                public void setProduct_code(String product_code) {
                    this.product_code = product_code;
                }
            }
        }
    }
}
