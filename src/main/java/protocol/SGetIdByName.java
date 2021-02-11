package protocol;

import java.util.ArrayList;
import java.util.List;

public class SGetIdByName extends GameProtocol {
    public List<String> id = new ArrayList<>();

    @Override
    public String toString() {
        return "SGetIdByName{" +
                "id=" + id +
                '}';
    }
}
