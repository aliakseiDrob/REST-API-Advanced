package com.epam.esm.validator.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.TagValidationException;
import com.epam.esm.validator.TagValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class TagValidatorImpl implements TagValidator {
    private static final int MAX_TAG_NAME_LENGTH = 64;

    @Override
    public void validateTag(TagDto tag) {
      if (StringUtils.isBlank(tag.getName())){
          throw  new TagValidationException("Tag name can't be empty",40001);
      }
        if (tag.getName().length() > MAX_TAG_NAME_LENGTH) {
            throw  new TagValidationException("Tag name can't be more then 64 characters",40002);
        }
    }
}
