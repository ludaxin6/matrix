package sample.tencent.matrix.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GesonUtil {
    public static Gson getGson(){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                try{
                    JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
                    if("\"\"".equals(jsonPrimitive.getAsString())|| TextUtils.isEmpty(jsonPrimitive.getAsString())){
                        return null;
                    }else if(jsonPrimitive.isNumber()){
                        return new Date(jsonPrimitive.getAsLong());
                    }else{
                        try{
                            return new Date(jsonPrimitive.getAsString());
                        }catch (Exception e){
                            if(jsonPrimitive.getAsString().indexOf(":")>0 && jsonPrimitive.getAsString().indexOf("-")>0){
                                return DateUtil.paseDate(jsonPrimitive.getAsString(), DateUtil.TIME_FORMAT_NORMAL);
                            }else if(jsonPrimitive.getAsString().indexOf("-")>0){
                                return DateUtil.paseDate(jsonPrimitive.getAsString(), DateUtil.DATE_FORMAT_NORMAL);
                            }else if(jsonPrimitive.getAsString().indexOf(":")>0){
                                Date date = DateUtil.paseDate(jsonPrimitive.getAsString(), "HH:mm:ss");
                                if (date == null) {
                                    date = DateUtil.paseDate(jsonPrimitive.getAsString(), "HH:mm");
                                }
                                return date;
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        });
        builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>(){
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                try{
                return new JsonPrimitive(src.getTime());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        });
        builder.registerTypeAdapter(Time.class, new JsonDeserializer<Time>() {
            public Time deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                if(json.getAsJsonPrimitive().getAsString().indexOf(":")>0){
                    try {
                        Date date = DateUtil.paseDate(json.getAsJsonPrimitive().getAsString(), "HH:mm:ss");
                        if (date == null) {
                            date = DateUtil.paseDate(json.getAsJsonPrimitive().getAsString(), "HH:mm");
                        }
                        if (date == null) {
                            return null;
                        }
                        return new Time(date.getTime());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return new Time(new Date().getTime());
                }else{
                    try {
                        if (json.getAsJsonPrimitive().isNumber() && json.getAsJsonPrimitive().getAsLong()>0) {
                            return new Time(json.getAsJsonPrimitive().getAsLong());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return new Time(new Date().getTime());
                }
            }
        });
        builder.registerTypeAdapter(Time.class, new JsonSerializer<Time>(){
            @Override
            public JsonElement serialize(Time src, Type typeOfSrc, JsonSerializationContext context) {
                try {
                    return new JsonPrimitive(src.getTime());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        });
        return builder.create();
    }
    /**
     * 转成json
     */
    public static String beanToString(Object object) {
        return getGson().toJson(object);
    }

    /**
     * 转成bean
     */
    public static <T> T stringToBean(String gsonString, Class<T> cls) {
        return getGson().fromJson(gsonString, cls);
    }

    /**
     * 转成list
     */
    public static <T> ArrayList<T> stringToList(String gsonString, Class<T> cls) {
        ArrayList<T> list = new ArrayList<>();
        JsonArray array = new JsonParser().parse(gsonString).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(getGson().fromJson(elem, cls));
        }
        return list;
    }

    /**
     * 转成list, 有可能造成类型擦除
     */
    public static <T> ArrayList<T> stringToList(String gsonString) {
        return getGson().fromJson(gsonString, new TypeToken<ArrayList<T>>() {
        }.getType());
    }

    /**
     * 转成map的
     */
    public static <T> Map<String, T> stringToMaps(String gsonString, Class<T> cls) {
        return getGson().fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
    }
    public static  <T> List<T> parseString2List(String json, Class clazz) {
        Type type = new ParameterizedTypeImpl(clazz);
        return new Gson().fromJson(json, type);
    }
    /**
     * 拷贝集合
     */
    public static <T> List<T> copyList(List source, Class<T> klass){
        List target = null;
        if(!CollectionUtils.isEmpty(source)){
            target = stringToList(getGson().toJson(source), klass);
        }else if(null != source){
            target = new ArrayList();
        }
        return target;
    }

    /**
     * 拷贝对象
     */
    public static <T> T copyObject(Object source, Class<T> klass){
        if(null == source){
            return null;
        }
        Gson gson = getGson();
        return gson.fromJson(gson.toJson(source), klass);
//        return JSON.parseArray("[" + JSON.toJSONString(source) + "]", klass).get(0);
    }

    public static   class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }
        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

    /**
     * 格式化json字符串
     *
     * @param jsonStr 需要格式化的json串
     * @return 格式化后的json串
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            //遇到{ [换行，且下一行缩进
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                //遇到} ]换行，当前行缩进
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                //遇到,换行
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }
        return sb.toString();
    }

    /**
     * 添加space
     *
     * @param sb
     * @param indent
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
    /**
     * http 请求数据返回 json 中中文字符为 unicode 编码转汉字转码
     *
     * @return 转化后的结果.
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuilder outBuffer = new StringBuilder(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }
}
