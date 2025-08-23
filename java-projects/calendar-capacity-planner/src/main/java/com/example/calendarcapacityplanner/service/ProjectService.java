package com.example.calendarcapacityplanner.service;

import com.example.calendarcapacityplanner.ui.model.RequestModel;
import com.example.calendarcapacityplanner.ui.model.ResponseModel;

public interface ProjectService {
    ResponseModel create(RequestModel req);
    ResponseModel get(String id);
    ResponseModel list();
}
