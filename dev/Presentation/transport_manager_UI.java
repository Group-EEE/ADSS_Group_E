package Presentation;

import Business.Transport_System;
import Business.Transport_system_controller;

public class transport_manager_UI {
    Transport_system_controller controller;
    public transport_manager_UI(){
        controller = new Transport_system_controller();
    }

    public void start(){
        if (Transport_system_controller.getLogistical_center() == null){

        }
    }
}
