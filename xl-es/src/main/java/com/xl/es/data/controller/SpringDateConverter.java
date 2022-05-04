package com.xl.es.data.controller;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class SpringDateConverter implements WebBindingInitializer {
  public SpringDateConverter() {
  }

  public void initBinder(WebDataBinder binder, WebRequest request) {
	  binder.registerCustomEditor(Date.class, new DateConvertEditor());
  }
}