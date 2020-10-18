package com.echo.backend.utils;

import com.echo.backend.domain.Property;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class PagingUtil {

    public static List afterPaging(List result, Integer page, Integer dataNum) {

        if (CollectionUtils.isEmpty(result))
            return result;
        if (result.size() <= dataNum){
            return result;
        }
        else{
            int index = (page - 1) * dataNum;
            int end = page * dataNum - 1;
            end = result.size()-1 < end ? result.size()-1 : end;

            List<Object> ret = new ArrayList<>();
            for (int i=index; i<=end; i++){
                ret.add(result.get(i));
            }

            return ret;
        }

    }
}
