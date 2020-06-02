package com.soil.service.cloudNio;

import org.springframework.stereotype.Service;

/**
 * @author ymj
 * @Date： 2020/4/28 21:00
 */
@Service("NioServer")
public class StartNio {
    StartNio(){
        System.out.println("开启多线程*********************");
        new Thread(new NioServer()).start();
    }
}
