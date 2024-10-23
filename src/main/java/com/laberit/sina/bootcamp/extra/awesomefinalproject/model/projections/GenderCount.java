package com.laberit.sina.bootcamp.extra.awesomefinalproject.model.projections;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.enums.Gender;

public interface GenderCount {
    Gender getGender();
    Long getCount();
}
