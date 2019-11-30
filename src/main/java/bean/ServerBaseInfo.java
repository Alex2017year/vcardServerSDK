package bean;

import global.config.ConfigInfo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import utils.BufferUtil;
import utils.StringUtils;
import utils.TimeUtil;

// 一个服务器只有这样一个实例
public class ServerBaseInfo {

    public ServerBaseInfo() {
    }

    public ServerBaseInfo(short serverNo, int timestamp, short randomNum, String oemCode) {
        this.serverNo = serverNo;
        this.timestamp = timestamp;
        this.randomNum = randomNum;
        // this.signature = signature;
        if (oemCode == "") {
            oemCodeServer = ConfigInfo.getOEMCodeServer();
        }
    }

    private static int BYTES = 8;
    private static final int SIGNATURE_OFFSET_START = 4;
    private static final int SIGNATURE_LEN = 9;

    private short serverNo; // 服务器编号
    private int timestamp; // 秒
    private short randomNum; // 随机数
    private String oemCodeServer; // 签名字符串
    private String signature; // 服务端签名

    public short getServerNo() {
        return serverNo;
    }

    public void setServerNo(short serverNo) {
        this.serverNo = serverNo;
    }

    public void updateTimestamp() {
        timestamp = TimeUtil.getCurrentSec();
    }

    public void updateRandom() {
        randomNum = StringUtils.getRandShort();
    }

    public String getOemCodeServer() {
        return oemCodeServer;
    }

    public void setOemCodeServer(String oemCodeServer) {
        this.oemCodeServer = oemCodeServer;
    }

    private boolean calcSignature() {
        String oemCode;

        if (oemCodeServer == null || oemCodeServer == "") {
            oemCode = ConfigInfo.getOEMCodeServer();
        } else {
            oemCode = this.oemCodeServer;
        }

        byte[] byteArr = new byte[BYTES + oemCode.length()];
        BufferUtil.putShort(byteArr, this.serverNo, 0);
        BufferUtil.putInt(byteArr, this.timestamp, 2);
        BufferUtil.putShort(byteArr, this.randomNum, 6);
        BufferUtil.putString(byteArr, oemCode, 8);

        byte[] signatureBytes = BufferUtil.subBytes(byteArr, SIGNATURE_OFFSET_START, SIGNATURE_LEN);
        if (signatureBytes != null) {
            this.signature = new String(signatureBytes);
            return true;
        }

        return false;
    }

    // 构造一个ByteBuf
    public ByteBuf toByteBuf() {
        if (!calcSignature()) {
            return null;
        }
        ByteBuf appData = ByteBufAllocator.DEFAULT.buffer();
        appData.writeShort(serverNo);
        appData.writeInt(timestamp);
        appData.writeShort(randomNum);
        appData.writeBytes(signature.getBytes());
        return appData;
    }
}
