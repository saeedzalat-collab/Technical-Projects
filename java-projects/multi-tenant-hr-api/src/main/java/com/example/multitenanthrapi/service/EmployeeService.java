package com.example.multitenanthrapi.service;

import com.example.multitenanthrapi.ui.model.RequestModel;
import com.example.multitenanthrapi.ui.model.ResponseModel;

public interface EmployeeService {
    ResponseModel create(RequestModel req);
    ResponseModel get(String id);
    ResponseModel list();
}
