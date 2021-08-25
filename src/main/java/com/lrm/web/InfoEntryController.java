package com.lrm.web;

import com.lrm.po.InfoEntry;
import com.lrm.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class InfoEntryController {
    private final static Logger logger = LoggerFactory.getLogger(InfoEntryController.class);

    @PostMapping("/createEntry")
    public Result create(@RequestBody @Valid InfoEntry infoEntry, BindingResult bindingResult) {

        return null;
    }

    @PostMapping("/updateEntry")
    public Result update(@RequestBody InfoEntry infoEntry) {

        return null;
    }


}
