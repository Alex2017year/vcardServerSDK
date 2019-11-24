package protocol;

import java.util.Objects;

public class ControlCode {
    private byte categoryCode; // 分类
    private byte commandCode; // 命令

    public ControlCode() { }

    public ControlCode(byte categoryCode, byte commandCode) {
        this.categoryCode = categoryCode;
        this.commandCode = commandCode;
    }

    public byte getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(byte categoryCode) {
        this.categoryCode = categoryCode;
    }

    public byte getCommandCode() {
        return commandCode;
    }

    public void setCommandCode(byte commandCode) {
        this.commandCode = commandCode;
    }

    @Override
    public String toString() {
        return "ControlCode{" +
                "categoryCode=" + categoryCode +
                ", commandCode=" + commandCode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ControlCode)) return false;
        ControlCode that = (ControlCode) o;
        return getCategoryCode() == that.getCategoryCode() &&
                getCommandCode() == that.getCommandCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategoryCode(), getCommandCode());
    }
}
