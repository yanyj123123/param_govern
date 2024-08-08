package com.ghrk.common.utils;

import com.ghrk.common.exception.GlobalException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Validate {

    public static boolean validateUserId(Object userId) {
        if (userId != null  && !Objects.equals(userId, "")){
            Pattern pattern = Pattern.compile(Constant.USERID_REGEX);
            return pattern.matcher((String)userId).matches();
        }
        return true;
    }

}
