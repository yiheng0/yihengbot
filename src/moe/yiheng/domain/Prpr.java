package moe.yiheng.domain;

public class Prpr {
    private int id;
    private String username;
    private long chatId;
    private int prprstat;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Prpr{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", chatId=").append(chatId);
        sb.append(", prprstat=").append(prprstat);
        sb.append('}');
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public int getPrprstat() {
        return prprstat;
    }

    public void setPrprstat(int prprstat) {
        this.prprstat = prprstat;
    }

    public Prpr() {

    }

    public Prpr(String username, long chatId, int prprstat) {

        this.username = username;
        this.chatId = chatId;
        this.prprstat = prprstat;
    }
}
