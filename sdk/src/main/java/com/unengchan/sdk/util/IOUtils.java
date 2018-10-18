package com.unengchan.sdk.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by unengchan on 2017/11/10.
 * IO帮助类
 */

public class IOUtils {

    private IOUtils() {
    }

    public static void close(Closeable os){
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                LogUtils.d(IOUtils.class.getSimpleName(),e.getMessage());
            }
        }
    }

}
