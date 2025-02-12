package com.example.demo.validate;

import com.example.demo.dto.request.CategoryRequestDao;
import org.springframework.util.StringUtils;

public class ValidateCategory {
    public static void validateCategory(CategoryRequestDao categoryRequestDao){
        if(StringUtils.isEmpty(categoryRequestDao.getName())){
            throw new IllegalArgumentException("el nombre es null o vacío");
        }
    }
}
