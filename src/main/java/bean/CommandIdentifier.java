package bean;

import protocol.ControlCode;

import java.util.Objects;

public class CommandIdentifier {

    public CommandIdentifier() {
    }

    public CommandIdentifier(ControlCode cmdType, short cmdSequence) {
        this.cmdType = cmdType;
        this.cmdSequence = cmdSequence;
    }

    private ControlCode cmdType;
    private short cmdSequence;

    public ControlCode getCmdType() {
        return cmdType;
    }

    public void setCmdType(ControlCode cmdType) {
        this.cmdType = cmdType;
    }

    public short getCmdSequence() {
        return cmdSequence;
    }

    public void setCmdSequence(short cmdSequence) {
        this.cmdSequence = cmdSequence;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandIdentifier)) return false;
        CommandIdentifier that = (CommandIdentifier) o;
        return getCmdSequence() == that.getCmdSequence() &&
                Objects.equals(getCmdType(), that.getCmdType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCmdType(), getCmdSequence());
    }

    @Override
    public String toString() {
        return "CommandIdentifier{" +
                "cmdType=" + cmdType +
                ", cmdSequence=" + cmdSequence +
                '}';
    }
}
