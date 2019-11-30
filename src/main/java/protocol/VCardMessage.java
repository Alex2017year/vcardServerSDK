package protocol;

import java.util.Objects;

public class VCardMessage {
    private MessageHeader header; // 消息头
    private Object appData; // 消息体

    public VCardMessage() { }

    public VCardMessage(MessageHeader header, Object appData) {
        this.header = header;
        this.appData = appData;
    }

    public MessageHeader getHeader() {
        return header;
    }

    public void setHeader(MessageHeader header) {
        this.header = header;
    }

    public Object getAppData() {
        return appData;
    }

    public void setAppData(Object appData) {
        this.appData = appData;
    }

    @Override
    public String toString() {
        return "VCardMessage{" +
                "header=" + header +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VCardMessage)) return false;
        VCardMessage that = (VCardMessage) o;
        return getHeader().equals(that.getHeader()) &&
                Objects.equals(getAppData(), that.getAppData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHeader(), getAppData());
    }
}
