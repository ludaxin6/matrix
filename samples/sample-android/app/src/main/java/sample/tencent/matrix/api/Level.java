package sample.tencent.matrix.api;

import java.util.HashMap;
import java.util.Map;

public enum Level {
    /**
     * --> currentTime , method, uri , userId , sessionId ,stackTrace
     * <-- currentTime, httpStatus, method, uri
     */
    INFO,

    /**
     * --> currentTime , method , uri , userId , sessionId , headers , body ,stackTrace
     * <-- currentTime , httpStatus , method , uri , headers , body
     */
    DEBUG,
    /**
     * No logs.
     */
    NONE,
    /**
     * --> currentTime , method, uri , userId , sessionId ,stackTrace
     * <-- currentTime, httpStatus, method, uri
     */
    BASIC,

    /**
     * --> currentTime , method , uri , userId , sessionId , headers , body ,stackTrace
     * <-- currentTime , httpStatus , method , uri , headers , body
     */
    BODY;

    static Map<String, Level> map = new HashMap<String, Level>() {
        private static final long serialVersionUID = -2873590923344392083L;

        {
            put("INFO",INFO);
            put("DEBUG",DEBUG);
            put("NONE",NONE);
            put("BASIC",BASIC);
            put("BODY",BODY);
        }
    };

    public static Level nameOf(String name) {
        return map.get(name);
    }
}