package lhp.com.smartbutler.entity;

/**
 * Created by lhp on 2017/7/16.
 * description: 微信精选实体类
 */

public class WeChatData {
    //标题
    private String title;
    //出处
    private String source;
    //图片的url
    private String imgUrl;
    //新闻地址
    private String newsUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}
