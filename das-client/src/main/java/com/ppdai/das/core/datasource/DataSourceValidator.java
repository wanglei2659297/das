package com.ppdai.das.core.datasource;

import com.ichangtou.IctDataSourceValidator;
import com.ichangtou.util.IctValidatorUtil;

import java.sql.Connection;

public class DataSourceValidator {

    private IctDataSourceValidator ictDataSourceValidator;

    public DataSourceValidator() {
        this(IctValidatorUtil.createValidator());
    }

    public DataSourceValidator(IctDataSourceValidator ictDataSourceValidator) {
        this.ictDataSourceValidator = ictDataSourceValidator;
    }

    public boolean validate(Connection connection, int validateAction) {
        if (ictDataSourceValidator == null) {
            return true;
        }
        return ictDataSourceValidator.validate(connection, validateAction);
    }
}
