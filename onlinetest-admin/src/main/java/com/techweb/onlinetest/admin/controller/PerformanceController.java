package com.techweb.onlinetest.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by manishsanger on 15/10/16.
 */
@Controller
@RequestMapping(value = "admin/performance")
public class PerformanceController {

    public final static Logger logger = LoggerFactory
            .getLogger(PerformanceController.class);

}
