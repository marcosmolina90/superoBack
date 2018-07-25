package br.com.supero.model.rest;

import javax.faces.bean.RequestScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
@RequestScoped
public class RestApplication extends Application {

}
