package sample.tencent.matrix.util;

import android.text.TextUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import sample.tencent.matrix.api.BizServiceException;
import sample.tencent.matrix.api.ResultCode;

/**
 * 参数校验辅助类
 * @author tf
 * @date 2018-12-19 14:55
 */
public class ParamUtils {

    /**
     * 检查不能为空
     * @param object
     */
    public static void validateNotEmpty(Object object){
        validateNotEmpty(object, "参数");
    }

    /**
     * 检查不能为空
     * @param object
     * @param name
     */
    public static void validateNotEmpty(Object object, String name){
        if(object instanceof String){
            checkStr(object.toString(), name);
        }else if(object instanceof List || object instanceof Map){
            checkList((List) object, name);
        }else{
            if(null == object){
                throw new BizServiceException(name + "不能为空", ResultCode.Base.Param.EMPTY);
            }
        }
    }

    private static void checkStr(String str, String name){
        if(TextUtils.isEmpty(str)){
            throw new BizServiceException(name + "值不能为空", ResultCode.Base.Param.EMPTY);
        }
    }

    private static void checkList(Collection collection, String name){
        if(CollectionUtils.isEmpty(collection)){
            throw new BizServiceException(name + "值不能为空", ResultCode.Base.Param.EMPTY);
        }
    }

    /**
     * 检查人名是否有效
     * @param personName
     * @return
     */
    public static boolean checkPersonName(String personName){
        boolean valid = true;
        if(!TextUtils.isEmpty(personName)) {
            //去掉空格
            String newPersonName = personName.trim().replace(" ", "");
            System.out.println(newPersonName);
            if (newPersonName.endsWith("先生")
                    || newPersonName.endsWith("小姐")
                    || newPersonName.endsWith("女士")
                    || newPersonName.endsWith("主任")
                    || newPersonName.endsWith("经理")
                    || newPersonName.endsWith("总监")
                    || newPersonName.endsWith("师傅")
                    || newPersonName.endsWith("老师")
                    || newPersonName.endsWith("会计")
                    || newPersonName.endsWith("总")) {
                valid = false;
            } else if (newPersonName.startsWith("小")) {
                valid = false;
            } else if (newPersonName.length() == 1) {
                valid = false;
            } else if (newPersonName.equals("前台")
                    ||  newPersonName.equals("采购")
                    || newPersonName.equals("商务")) {
                valid = false;
            }
        }

        return valid;
    }

    public static void main(String[] args) {
        System.out.println(checkPersonName("张三"));
    }
}
