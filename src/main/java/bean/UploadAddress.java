package bean;

public class UploadAddress {
    private byte funId;
    private String address;

    public UploadAddress() {
    }

    public UploadAddress(byte funId, String address) {
        this.funId = funId;
        this.address = address;
    }

    public byte getFunId() {
        return funId;
    }

    public void setFunId(byte funId) {
        this.funId = funId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
