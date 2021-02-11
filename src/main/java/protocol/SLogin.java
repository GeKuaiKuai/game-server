package protocol;

import utils.ResponseResult;

public class SLogin extends GameProtocol{
    public String result = ResponseResult.SUCCESS.toString();
    public int lastSaveTime;
    public int version;
    public boolean over;

    @Override
    public String toString() {
        return "SLogin{" +
                "result='" + result + '\'' +
                ", lastSaveTime=" + lastSaveTime +
                ", version=" + version +
                ", over=" + over +
                '}';
    }
}
