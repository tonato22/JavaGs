package org.example.controller;

import org.example.dtos.ClienteDto;
import org.example.exceptions.ClienteNotFoundException;
import org.example.exceptions.ClienteNotSavedException;
import org.example.exceptions.UnsupportedServiceOperationException;
import org.example.models.Cliente;
import org.example.service.ClienteService;
import org.example.service.ClienteServiceFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Logger;

@Path("/rest/cliente")
public class ClienteController {

    private final ClienteService clienteService = ClienteServiceFactory.create();
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(ClienteDto input) throws UnsupportedServiceOperationException {
        if (input.getId() == null) {
            try {
                Cliente cliente = this.clienteService.create(new Cliente(null, input.getNome(), input.getEmail(), input.getTelefone(), input.getValorContaMensal()));
                return Response
                        .status(Response.Status.CREATED)
                        .entity(cliente)
                        .build();
            } catch (SQLException | ClienteNotSavedException e) {
                logger.severe(e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(Map.of("mensagem", "erro inesperado ao tentar inserir cliente")).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(
                            Map.of(
                                    "mensagem",
                                    "esse método só permite a criação de novos clientes"))
                    .build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        return Response.status(Response.Status.OK)
                .entity(this.clienteService.findAll()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, ClienteDto input) {
        try {
            Cliente updated = this.clienteService.update(new Cliente(id, input.getNome(), input.getEmail(), input.getTelefone(), input.getValorContaMensal()));
            return Response.status(Response.Status.OK).entity(updated).build();
        } catch (ClienteNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (SQLException s) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "erro inesperado ao tentar atualizar cliente")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            this.clienteService.deleteById(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ClienteNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (SQLException s) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "erro inesperado ao tentar deletar cliente")).build();
        }
    }


}


