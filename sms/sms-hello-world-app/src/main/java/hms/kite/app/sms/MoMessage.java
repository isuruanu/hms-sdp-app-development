package hms.kite.app.sms;

import com.google.common.base.Optional;

public class MoMessage {
    private String keyword;
    private Optional<String> message;

    public MoMessage(String keyword, Optional<String> message) {
        this.keyword = keyword;
        this.message = message;
    }

    public String getKeyword() {
        return keyword;
    }

    public Optional<String> getMessage() {
        return message;
    }
}
