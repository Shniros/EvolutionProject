package ru.Shniros.web.controller;

import ru.Shniros.mappers.PersonMapper;
import ru.Shniros.service.SecurityService;
import ru.Shniros.service.exception.CommonServiceException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/auth")
public class SecurityController {
    @Inject
    SecurityService securityService;
    @Inject
    PersonMapper personMapper;

    @GET
    @Path("/login")
    @Produces({"application/json"})
    public Response loginPerson(@Context HttpServletRequest paramHttpServletRequest,
                                @HeaderParam("email") String email,
                                @HeaderParam("password") String password) {
        try {
            return Response.ok(personMapper.toPersonDto(securityService.login(email, password))).build();
        } catch (CommonServiceException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
