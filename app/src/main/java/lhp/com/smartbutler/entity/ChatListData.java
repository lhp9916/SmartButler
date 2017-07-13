package lhp.com.smartbutler.entity;

/**
 * Created by lhp on 2017/7/13.
 * description: 对话内容实体
 */

public class ChatListData {
    //文本
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;
}
