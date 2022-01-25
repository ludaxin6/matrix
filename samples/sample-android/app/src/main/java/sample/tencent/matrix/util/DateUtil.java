package sample.tencent.matrix.util;

import static java.math.BigDecimal.ROUND_HALF_UP;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 方法名称 : com.deshine.common.util.DateUtil 作 者 : Administrator 创建时间 : 2017-02-07
 * 13:44 方法描述 : 时间工具类
 * <p>
 * 修改作者 : 修改时间 : 修改描述 :
 */
public class DateUtil {
    private final static String[] CN_Digits =
            {"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
    public static final long daySpan = 24 * 60 * 60 * 1000;
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String TIME_FORMAT_NORMAL1 = "yyyy-MM-dd HH:mm";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String TIME_FORMAT_NORMAL = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd
     */
    public static final String DATE_FORMAT_NORMAL = "yyyy-MM-dd";
    public static final String DATE_FORMAT_NORMAL_CN = "yyyy年MM月dd日";
    /**
     * yyyy.MM.dd
     */
    public static final String DATE_FORMAT_DOT = "yyyy.MM.dd";
    /**
     * yyyyMMdd
     */
    public static final String DATE_FORMAT_NO_MINUS = "yyyyMMdd";
    /**
     * yyMMdd
     */
    public static final String DATE_FORMAT_NO_MINUS_SHORT = "yyMMdd";
    /**
     * yyyy-MM
     */
    public static final String MONTH_FORMAT_NORMAL = "yyyy-MM";
    /**
     * MM-dd
     */
    public static final String MONTH_DAY_FORMAT = "MM-dd";
    /**
     * yyyyMMdd
     */
    public static final String DATE_FORMAT_SHORT = "yyyyMMdd";
    /**
     * yyyyMM
     */
    public static final String MONTH_FORMAT = "yyyyMM";
    /**
     * yyyy.MM
     */
    public static final String MONTH_FORMAT_DOT = "yyyy.MM";
    /**
     * yyyyMMddHHmm
     */
    public static final String DATE_FORMAT_MINUTE = "yyyyMMddHHmm";
    /**
     * yyyyMMddHHmmss
     */
    private static final String TIME_FORMAT_SHORT = "yyyyMMddHHmmss";
    /**
     * MM/dd/yyyy HH:mm:ss
     **/
    public static final String MONTH_DAY_YEAR_HOUR_MINUTE = "MM/dd/yyyy HH:mm:ss";

    public static final String YYMMDD = "yyMMdd";

    /**
     * 判断参数year、month、day能否组成一个合法的日期。
     *
     * @param month 月份，合法月份范围是 1-12
     * @param day   日数
     * @param year  年份，必须大于1900
     * @return boolean
     */
    public static boolean isDate(int month, int day, int year) {
        if (year < 1900) {
            return false;
        }
        if (month < 1 || month > 12) {
            return false;
        }
        if (day < 1 || day > 31) {
            return false;
        }

        // 判断年份是否为闰年
        @SuppressWarnings("unused")
        boolean leapYear = isLeapYear(year);
        // 获得该年该月的最大日期
        int maxD = getMaxDay(year, month);
        if (day > maxD) {
            return false;
        } else {
            return true;
        }
    }

    public static Date addDays(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    public static Date addMonth(Date date, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    public static String getAmPm(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int amPm = cal.get(Calendar.AM_PM);
        if(amPm ==0 ) return "上午";
        if(amPm == 1) return "下午";
        return "";
    }

    /**
     * 给定一个年份和月份，可以得到该月的最大日期。 例如 2009年1月，最大日期为31。
     *
     * @param year  年份，必须大于1900
     * @param month 月份，合法月份范围是 1-12
     * @return i
     */
    public static int getMaxDay(int year, int month) {
        if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
            return 30;
        }
        if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 31;
    }

    /**
     * 判断年份是否为闰年。
     *
     * @param year 年份，必须大于1900
     * @return
     */
    public static boolean isLeapYear(int year) {
        boolean leapYear = ((year % 400) == 0);
        if (!leapYear) {
            leapYear = ((year % 4) == 0) && ((year % 100) != 0);
        }
        return leapYear;
    }

    public static String Date4FormatStr(Date date, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        String formatDate = format.format(date);
        return formatDate;
    }

    public static String getCurrentStringTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = format.format(date);
        return formatDate;
    }

    public static String getCurrentTimeSecond() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDate = format.format(date);
        return formatDate;
    }

    /**
     * yyyy-MM-dd HH:mm:ss格式串转换为日期
     *
     * @param formatDate yyyy-MM-dd HH:mm:ss 格式日期
     * @return Date日期
     */
    public static Date paseDate(String formatDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     */
    public static Date paseDate(String formatDate, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = format.parse(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getCurrentTimeMilliSecond() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String formatDate = format.format(date);
        return formatDate;
    }

    public static String getCurrentMonth() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String formatDate = format.format(date);
        return formatDate;
    }

    /**
     * 获取当前日期（格式为20110802）
     *
     * @return
     */
    public static String getCurrentDay() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String formatDate = format.format(date);
        return formatDate;
    }

    /**
     * 获取当前时间
     *
     * @param format 时间格式，例如：yyyy-MM-dd
     * @param count  增加或减少的天数
     * @return
     */
    public static String getCurrentDate(String format, Integer count) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, count);// 增加或减少的天数
        String currentDate = df.format(cal.getTime());
        return currentDate;
    }
    public static Date getCurrentDate(){
        return new Date();
    }

    /**
     * 增加月份后的日期数
     *
     * @param dateStr 时间格式 yyyy-MM-dd
     * @param m       增加的月数
     * @return
     */
    public static String getDateAddMoney(String dateStr, int m) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = df.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, m);
            return df.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取下个月的第一天
     *
     * @param format
     * @return
     */
    public static String getNextMonthFirstDay(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);// 当前月＋1，即下个月
        cal.set(Calendar.DATE, 1);// 将下个月1号作为日期初始值
        String currentMonth = df.format(cal.getTime());
        return currentMonth;
    }

    /**
     * 获取下个月的最后一天
     *
     * @param format
     * @return
     */
    public static String getNextMonthLastDay(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);// 将下2个月1号作为日期初始值
        cal.add(Calendar.MONTH, 2);// 当前月＋2，即下2个月
        cal.add(Calendar.DATE, -1);// 下2个月1号减去一天，即得到下1一个月最后一天
        String currentMonth = df.format(cal.getTime());
        return currentMonth;
    }

    public static String format(Date date) {
        if (null == date) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDate = format.format(date);
        return formatDate;
    }

    public static String format(Date date, String formatStr) {
        if(date == null) return "";
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        String formatDate = format.format(date);
        return formatDate;
    }

    public static Date getDateWithZeroHMS(Date date) {
        Date result = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s = sdf.format(date);
        try {
            result = sdf.parse(s);
        } catch (ParseException e) {

        }
        return result;
    }


    public static String format4Null(Date date, String formatStr) {

        if (date == null) {
            return null;
        } else {

            return format(date, formatStr);
        }
    }

    /**
     * 得到2个字符串日期之间的日期差,返回结果以秒为单位
     *
     * @param beginTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public static Long getOffTime(String beginTime, String endTime) {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date begin = dfs.parse(beginTime);
            Date end = dfs.parse(endTime);
            long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
            return between;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getYesterday() {
        return getCurrentDate("yyyy-MM-dd", -1) + " 00:00:00";
    }

    /**
     * 获取指定某天的23点59分59秒
     *
     * @return
     */
    public static Date getLastTimeByFixDate(Date date) {
        Calendar cal = Calendar.getInstance();
        String dateStr = Date4FormatStr(date, DATE_FORMAT_NORMAL);
        dateStr = dateStr + "  23:59:59";
        return paseDate(dateStr);
    }

    public static String getToday() {
        return getCurrentDate("yyyy-MM-dd", 0) + " 00:00:00";
    }

    public static long getTodayLong() {
        String today = getCurrentDate("yyyy-MM-dd", 0) + " 00:00:00";
        return paseDate(today).getTime();
    }


    /**
     * 昨天的起始终止 &gt;=起始 &lt;终止
     *
     * @param nowTime
     * @return
     */
    // public static Pair<Long, Long> getLastDay(long nowTime){
    // try{
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    // String s = sdf.format(new Date(nowTime));
    // Date yesdEnd = sdf.parse(s);
    //
    // return new Pair<Long, Long>(yesdEnd.getTime() - daySpan,
    // yesdEnd.getTime());
    // }catch (Exception e) {
    // throw new RuntimeException(e);
    // }
    // }

    /**
     * 取得上一个自然周起始（返回最近的周日0点为结束，之前一个周日0点为开始） &gt;=起始 &lt;终止
     *
     * @param nowTime
     * @return
     */
    // public static Pair<Long, Long> getLastNativeWeek(long nowTime){
    // try{
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    // String s = sdf.format(new Date(nowTime));
    // Date yesdEnd = sdf.parse(s); // 今天0点
    //
    // Calendar c = Calendar.getInstance();
    // c.setTime(yesdEnd);
    // c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    // long weekEnd = c.getTimeInMillis();
    // return new Pair<Long, Long>(weekEnd - daySpan * 7, weekEnd);
    // }catch (Exception e) {
    // throw new RuntimeException(e);
    // }
    // }

    /**
     * 取得上一个月的起始 &gt;=起始 &lt;终止
     *
     * @param nowTime
     * @return
     */
    // public static Pair<Long, Long> getLastMonth(long nowTime){
    // try{
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01 00:00:00");
    // String s = sdf.format(new Date(nowTime));
    // Date lmEnd = sdf.parse(s); // 本月1号0点
    //
    // Calendar c = Calendar.getInstance();
    // c.setTime(lmEnd);
    // c.add(Calendar.MONTH, -1);
    //
    // return new Pair<Long, Long>(c.getTimeInMillis(), lmEnd.getTime());
    // }catch (Exception e) {
    // throw new RuntimeException(e);
    // }
    // }

    /**
     * 计算两个日期之间相差的月数
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 两个日期之间相差的月数
     */
    public static int dateIntervalMonth(Date startDate, Date endDate) {

        int intervalMonth = 0;
        try {
            if (startDate == null || endDate == null) {
                return intervalMonth;
            }
            Calendar starCal = Calendar.getInstance();
            starCal.setTime(startDate);

            int sYear = starCal.get(Calendar.YEAR);
            int sMonth = starCal.get(Calendar.MONTH);

            Calendar endCal = Calendar.getInstance();
            endCal.setTime(endDate);
            int eYear = endCal.get(Calendar.YEAR);
            int eMonth = endCal.get(Calendar.MONTH);

            intervalMonth = ((eYear - sYear) * 12 + (eMonth - sMonth));
        } catch (Exception e) {
            intervalMonth = 0;
        }

        return intervalMonth;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int dateIntervalDay(Date startDate, Date endDate) {
        if (null == startDate || null == endDate) return 0;

        long nd = 1000 * 24 * 60 * 60;
        long diff = endDate.getTime() - startDate.getTime();

        //计算天
        long day = diff / nd +1;

        return Integer.parseInt(day + "");
    }

    /***
     * 根据所选格式将字符串转换为日期类型
     *
     * @param dateStr
     *            待转换的字符串
     * @param formateRule
     *            转换格式
     * @return 日期类型结果
     */
    public static Date convertAsDate(String dateStr, String formateRule) {
        DateFormat fmt = new SimpleDateFormat(formateRule);
        try {
            if (dateStr == null) {
                return null;
            }
            return fmt.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
    // /**
    // * 自动把String时间转成Date类型
    // * @param dateStr
    // * @return
    // */
    // public static Date autoConvertAsDate(String dateStr){
    // try {
    // if(null ==dateStr){
    // return null;
    // }
    // Object obj = StringUtils.convert(dateStr, Date.class);
    // if(null!=obj){
    // return (Date)obj;
    // }else{
    // return null;
    // }
    // } catch (Exception e) {
    // return null;
    // }
    // }

    public static SimpleDateFormat newTimeFormater() {
        return new SimpleDateFormat(TIME_FORMAT_NORMAL);
    }

    /**
     * 计算时间差，返回秒
     *
     * @param start
     * @param end
     * @return
     */
    public static long timeDifference(long start, long end) {
        return (end - start) / 1000;
    }

    /*
     * 判断输入的字符串是否是合法的生日 生日不能大于当前日期,支持 yyyy-MM-dd ,yyyyMMdd MM-dd-yyyy
     * ,yyyy年MM月dd日等
     *
     * @param birthday 一个日期格式的字符串
     *
     * @param formats 期望转换后的日期格式数组
     *
     * @return
     */
    public static boolean isRightDate(String birthday, String[] formats) {

        // 记录传入的日期字符串，转换成日期类型
        Date birth = null;

        // 判断格式是否正确，默认值为false
        boolean isRight = false;
        for (String f : formats) {
            try {
                birth = new SimpleDateFormat(f).parse(birthday);
                // 校验日期转换后是和传入的值不相同，说明日期传入有问题
                if (!new SimpleDateFormat(f).format(birth).equals(birthday)) {
                    return false;
                }
                isRight = true;
                break;
            } catch (ParseException e) {
            }
        }

        if (isRight) {
            // 获取当前日期的毫秒数
            long now = new Date().getTime();
            // 获取生日的毫秒数
            long birthTime = birth.getTime();
            // 如果当前时间小于生日，生日不合法。反之合法
            return birthTime <= now;
        } else {
            // 输入的参数类型不是日期类型，或者类型和过滤中设置的类型不匹配
            return false;
        }
    }

    /**
     * 获取本月第一天
     */
    public static Date getCurrentMonFirstDay() {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        // 将小时至0
        cale.set(Calendar.HOUR_OF_DAY, 0);
        // 将分钟至0
        cale.set(Calendar.MINUTE, 0);
        // 将秒至0
        cale.set(Calendar.SECOND, 0);
        // 将毫秒至0
        cale.set(Calendar.MILLISECOND, 0);
        return cale.getTime();
    }
    // /***
    // * 计算时间差，返回天数
    // * @param b_date 大时间
    // * @param s_date 小时间
    // * @return 天数
    // */
    // public static long dateSubtract(Date b_date,Date s_date){
    // if(StringUtils.blank(s_date) || StringUtils.blank(b_date)){
    // return 0;
    // }else{
    // long dateLong1 = b_date.getTime();
    // long dateLong2 = s_date.getTime();
    // long day = (dateLong1-dateLong2)/1000/60/60/24;
    // return day;
    // }
    // }


    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static Date getTomorrow() {
        Date date = new Date();//取时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);//把日期+1天
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取上午/下午 时分
     */
    public static String getAhh(Date dt) {
        return new SimpleDateFormat("ahh:mm").format(new Date());
    }

    /**
     * 获得某个月最大天数
     * @param monthDate yyyy-MM
     * @return 某个月最大天数
     */
    public static int getMaxDayByYearMonth(String monthDate) {
        String[] arr = monthDate.split("-");
        int year = Integer.valueOf(arr[0]);
        int month = Integer.valueOf(arr[1]) - 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year - 1);
        calendar.set(Calendar.MONTH, month);
        return calendar.getActualMaximum(Calendar.DATE);
    }
    public static Date getTomorrow(Date date) {
        if(date == null){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_MONTH, +1);//明天
        //calendar.add(Calendar.DAY_OF_MONTH, 0);//今天
        //calendar.add(Calendar.DAY_OF_MONTH, -1);//昨天
        return calendar.getTime();
    }
    public static Date getYesterday(Date date) {
        if(date == null){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_MONTH, -1);//昨天
        return calendar.getTime();
    }
    public static class DayCompare{
        private int year;
        private int month;
        private int day;

        public DayCompare() {
        }

        public DayCompare(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }
    }
    public static String dateCompareToToday(Date fromDate){
        if(fromDate == null) return "";
        StringBuilder sb = new StringBuilder();
        int days = subDays(fromDate, new Date());
        if(days>365){
            sb.append(days/365).append("年");
            days = days%365;
        }
        if(days>30){
            sb.append(days/30).append("个月");
            days = days%30;
        }
        if(days>0){
            sb.append(days).append("天");
        }
        if(days ==0){
            sb.append("今天");
        }
        return sb.toString();
    }
    public static String dateCompareToToday1(Date fromDate){
        if(fromDate == null) return "";
        StringBuilder sb = new StringBuilder();
        int days = subDays(fromDate, new Date(), false);
        if(days>365){
            sb.append(days/365).append("年");
            days = days%365;
        }
        if(days>30){
            sb.append(days/30).append("个月");
            days = days%30;
        }
        if(days>0){
            sb.append(days).append("天");
        }
        if(days ==0){
            sb.append("今天");
        }
        return sb.toString();
    }
    public static String dateCompareToTodayStr(Date fromDate){
        if(fromDate == null) return "";
        DayCompare dayCompare = dayComparePrecise(fromDate, new Date());
        StringBuilder sb = new StringBuilder();
        if(dayCompare.year>0){
            sb.append(dayCompare.year).append("年前");
        }else if(dayCompare.month>0){
            sb.append(dayCompare.month).append("个月前");
        }else if(dayCompare.day>0){
            sb.append(dayCompare.day).append("天前");
        }
        return sb.toString();
    }
    /**
     * 计算2个日期之间相差的  相差多少年月日
     * 比如：2011-02-02 到  2017-03-02 相差 6年，1个月，0天
     * @param fromDate
     * @param toDate
     * @return
     */
    public static DayCompare dayComparePrecise(Date fromDate,Date toDate){
        Calendar  from  =  Calendar.getInstance();
        from.setTime(fromDate);
        Calendar  to  =  Calendar.getInstance();
        to.setTime(toDate);

        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);

        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);

        int year = toYear  -  fromYear -1;
        int month = toYear *  12  + toMonth  -  (fromYear  *  12  +  fromMonth);
        int day = (int) ((to.getTimeInMillis()  -  from.getTimeInMillis())  /  (24  *  3600  *  1000));
        return new DayCompare(year,month,day);
    }
    public static int subDays(Date fromDate,Date toDate){
        return subDays(fromDate, toDate,true);
    }
    public static int subDays(Date fromDate,Date toDate, boolean isUp){
        long len = toDate.getTime()-fromDate.getTime();
        BigDecimal v = new BigDecimal(len).divide(new BigDecimal(1000*60*60*24), 20, ROUND_HALF_UP);
        if(isUp) {
            v = v.add(new BigDecimal("0.5"));//+0.99天
        }
        v = v.setScale(0, ROUND_HALF_UP);
        int k = v.intValue();
        if(k==0) k=1;
        return k;
    }
    /**
     * 计算2个日期之间相差的  以年、月、日为单位，各自计算结果是多少
     * 比如：2011-02-02 到  2017-03-02
     *                                以年为单位相差为：6年
     *                                以月为单位相差为：73个月
     *                                以日为单位相差为：2220天
     * @param fromDate
     * @param toDate
     * @return
     */
    public static DayCompare dayCompare(Date fromDate,Date toDate){
        Calendar  from  =  Calendar.getInstance();
        from.setTime(fromDate);
        Calendar  to  =  Calendar.getInstance();
        to.setTime(toDate);
        //只要年月
        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);

        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);

        int year = toYear  -  fromYear;
        int month = toYear *  12  + toMonth  -  (fromYear  *  12  +  fromMonth);
        int day = (int) ((to.getTimeInMillis()  -  from.getTimeInMillis())  /  (24  *  3600  *  1000));
        return new DayCompare(year,month,day);
    }

    /**
     * 计算2个日期相差多少年
     * 列：2011-02-02  ~  2017-03-02 大约相差 6.1 年
     * @param fromDate
     * @param toDate
     * @return
     */
    public static String yearCompare(Date fromDate,Date toDate){
        DayCompare result = dayComparePrecise(fromDate, toDate);
        double month = result.getMonth();
        double year = result.getYear();
        //返回2位小数，并且四舍五入
        DecimalFormat df = new DecimalFormat("######0.0");
        return df.format(year + month / 12);
    }

    public static void main(String[] args) {
//        System.out.println(getCurrentStringTime());
//        Date monthFirstDate = DateUtil.getCurrentMonFirstDay();
//        int days = DateUtil.dateIntervalDay(monthFirstDate,DateUtil.paseDate(DateUtil.format(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd"));
//        System.out.println(days);
        String s = "2021-03-16 16:24:00";
        Date start = paseDate(s);
        System.out.println(subDays(paseDate("2021-03-16 00:00:00"),paseDate("2021-03-16 16:24:00")));
    }

}
