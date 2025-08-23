package com.example.leavemanagementservice.service;

import com.example.leavemanagementservice.ui.model.RequestModel;
import com.example.leavemanagementservice.ui.model.ResponseModel;

public interface LeaveRequestService {
    ResponseModel create(RequestModel req);
    ResponseModel get(String id);
    ResponseModel list();
}
