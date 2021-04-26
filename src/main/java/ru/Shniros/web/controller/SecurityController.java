package ru.Shniros.web.controller;

import ru.Shniros.service.SecurityService;
import ru.Shniros.service.ServiceFactory;
import ru.Shniros.service.dto.PersonDto;
import ru.Shniros.service.exception.CommonServiceException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/auth")
public class SecurityController {
    @Path("/login/{email}/{password}")
    @GET
    public String loginPerson(@PathParam("email")String email, @PathParam("password")String password)
            throws CommonServiceException {
       // ServiceFactory.getSecurityService().login(email,password);
        return email + "/" + ServiceFactory.getDigestService().getMd5("12345");
    }
   /* @GET
    @Path("/{pass}")
    public String get(@PathParam("pass")String pass) {
        return pass;
        //return ServiceFactory.getDigestService().getMd5("12345");
    }*/
}
