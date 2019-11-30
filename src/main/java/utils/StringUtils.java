package utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class StringUtils {
    private final static Log logger = LogFactory.getLog(StringUtils.class);

    private static final Random rand = new Random();

    /**
     * 获取系统目录
     *
     * @return 系统目录
     */
    public static String getRealPath() {
        String realPath = StringUtils.class.getClassLoader().getResource("").getFile();

        File file = new File(realPath);
        if (realPath.indexOf("bin") > 0) {
            realPath = file.getParentFile().getAbsolutePath();
        } else {
            realPath = file.getAbsolutePath();
        }

        logger.info("[getRealPath] realPath: " + realPath);

        return realPath;
    }

    public static String long2DateString(Long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分ss秒");
        if (time != null) {
            if (time.longValue() != 0) {
                return format.format(new Date(time.longValue()));
            }
        }

        return null;
    }

    /**
     * 生成uuid
     *
     * @return
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成随机数
     *
     * @return
     */
    public static int getRandInt() {
        return rand.nextInt(Integer.MAX_VALUE);
    }

    public static short getRandShort() {
        return (short) rand.nextInt(Short.MAX_VALUE);
    }


    public static String changeToVersion(short originData) {
        StringBuffer version = new StringBuffer(Integer.toHexString(originData));
        if (version.length() % 2 != 0) {
            version.insert(0, '0');
        }

        // 每隔两位插入 .
        int oldLen = version.length();
        for (int i = oldLen - 2; i > 0; i -= 2) {
            version.insert(i, '.');
        }

        version.insert(0, "Ver ");

        return version.toString();
    }

}
