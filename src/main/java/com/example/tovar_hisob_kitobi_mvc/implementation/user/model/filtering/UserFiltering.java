package com.example.tovar_hisob_kitobi_mvc.implementation.user.model.filtering;

import com.example.tovar_hisob_kitobi_mvc.base.model.filtering.BaseFiltering;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.enums.Lavozim;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFiltering extends BaseFiltering {
    private Lavozim lavozim;
}
