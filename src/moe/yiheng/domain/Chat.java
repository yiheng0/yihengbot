package moe.yiheng.domain;

public class Chat {
    private long chatId;

    private int shouldReplyPrpr;

    public Chat(long chatId, int shouldReplyPrpr) {
        this.chatId = chatId;
        this.shouldReplyPrpr = shouldReplyPrpr;
    }

    public Chat() {
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public int getShouldReplyPrpr() {
        return shouldReplyPrpr;
    }

    public void setShouldReplyPrpr(int shouldReplyPrpr) {
        this.shouldReplyPrpr = shouldReplyPrpr;
    }
}
