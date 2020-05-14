package com.ominext.cms.utils;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class DateUtils {
    public static Timestamp currentTimestamp() {
        return new Timestamp(now().getTime());
    }

    public static Date now() {
        return new Date();
    }
}
