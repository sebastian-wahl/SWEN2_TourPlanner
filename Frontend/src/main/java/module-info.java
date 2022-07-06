module at.fhtw.swen2_tourplanner.frontend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jdk8;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.module.paramnames;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires org.apache.logging.log4j.slf4j;
    requires retrofit2;
    requires retrofit2.converter.jackson;
    requires okhttp3;
    requires de.saxsys.mvvmfx;

    opens at.fhtw.swen2_tourplanner.frontend.controller;
    exports at.fhtw.swen2_tourplanner.frontend.controller;
    opens at.fhtw.swen2_tourplanner.frontend.viewmodel;
    exports at.fhtw.swen2_tourplanner.frontend.viewmodel;
    opens at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects;
    exports at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects;
    opens at.fhtw.swen2_tourplanner.frontend.cellObject;
    exports at.fhtw.swen2_tourplanner.frontend.cellObject;
    opens at.fhtw.swen2_tourplanner.frontend.cellObject.converter;
    exports at.fhtw.swen2_tourplanner.frontend.cellObject.converter;
    opens at.fhtw.swen2_tourplanner.frontend.cellObject.exception;
    exports at.fhtw.swen2_tourplanner.frontend.cellObject.exception;
    opens at.fhtw.swen2_tourplanner.frontend.observer;
    exports at.fhtw.swen2_tourplanner.frontend.observer;
    opens at.fhtw.swen2_tourplanner.frontend.service;
    exports at.fhtw.swen2_tourplanner.frontend.service;
    opens at.fhtw.swen2_tourplanner.frontend.service.exceptions;
    exports at.fhtw.swen2_tourplanner.frontend.service.exceptions;
    opens at.fhtw.swen2_tourplanner.frontend.listener;
    exports at.fhtw.swen2_tourplanner.frontend.listener;
    opens at.fhtw.swen2_tourplanner.frontend to javafx.fxml;
    exports at.fhtw.swen2_tourplanner.frontend;
    exports at.fhtw.swen2_tourplanner.frontend.service.tour;
    opens at.fhtw.swen2_tourplanner.frontend.service.tour;
    exports at.fhtw.swen2_tourplanner.frontend.service.tourlog;
    opens at.fhtw.swen2_tourplanner.frontend.service.tourlog;
    exports at.fhtw.swen2_tourplanner.frontend.service.mapquest;
    opens at.fhtw.swen2_tourplanner.frontend.service.mapquest;
}