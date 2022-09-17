package kz.jaguars.hackathon.controllers;

import kz.jaguars.hackathon.services.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final StaffService staffService;

}
