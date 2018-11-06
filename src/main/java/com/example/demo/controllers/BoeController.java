package com.example.demo.controllers;


import com.example.demo.db.DbManager;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoeController {

    private DbManager dbManager;

    public List<String> latest(int n) {

    }
}
