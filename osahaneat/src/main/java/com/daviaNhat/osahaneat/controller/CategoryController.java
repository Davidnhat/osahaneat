package com.daviaNhat.osahaneat.controller;

import com.daviaNhat.osahaneat.payload.ResponseData;
import com.daviaNhat.osahaneat.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryServiceImp categoryServiceImp;

    @GetMapping()
    public ResponseEntity<?> getHomeCategory(){
        ResponseData responseData = new ResponseData();
        responseData.setData(categoryServiceImp.getCategoryHomePage());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
