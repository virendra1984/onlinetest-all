package com.techweb.onlinetest.admin.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.onlinetest.model.SheetUploadHistory;
import com.techweb.onlinetest.admin.service.SheetHistoryService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "admin/dashboard")
public class DashBoardController {

    private final static Logger logger = LoggerFactory.getLogger(DashBoardController.class);

    private final static String DASHBOARD_VIEW_NAME = "admin/dashboard";


    @Autowired
    private SheetHistoryService sheetHistoryService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView login(Principal principal) {

        if (principal == null) {
            logger.info("Cannot provide access to unauthenticated user");
            throw new AccessDeniedException("Cannot provide access to unauthenticated user");
        }

        ModelAndView mav = new ModelAndView(DASHBOARD_VIEW_NAME);
        List<SheetUploadHistory> sheetUploadHistoryList = null;
        try {
            //sheetUploadHistoryList = sheetHistoryService.getAllSheetUploadHistory();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        mav.addObject("history", sheetUploadHistoryList);
        return mav;

        //	return new ModelAndView(DASHBOARD_VIEW_NAME);
    }

}
