package be.heh.gourmet.adapter.in.web;

import be.heh.gourmet.application.port.in.IManageOrderUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api")
public class OrderController {
    @Autowired
    @Qualifier("getManageOrderUseCase")
    IManageOrderUseCase orderManager;

}
