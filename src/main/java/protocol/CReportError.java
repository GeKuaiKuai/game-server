package protocol;

public class CReportError extends GameProtocol {
    public String error;

    @Override
    public String toString() {
        return "CReportError{" +
                "error='" + error + '\'' +
                '}';
    }
}
