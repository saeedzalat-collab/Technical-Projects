package com.example.observabilitystarter.service;

import com.example.observabilitystarter.ui.model.RequestModel;
import com.example.observabilitystarter.ui.model.ResponseModel;

public interface NoteService {
    ResponseModel create(RequestModel req);
    ResponseModel get(String id);
    ResponseModel list();
}
