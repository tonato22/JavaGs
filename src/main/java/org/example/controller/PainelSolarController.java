package org.example.controller;



import org.example.dtos.PainelSolarDto;
import org.example.exceptions.PainelSolarNotFoundException;
import org.example.exceptions.PainelSolarNotSavedException;
import org.example.exceptions.UnsupportedServiceOperationException;
import org.example.models.PainelSolar;
import org.example.service.PainelSolarService;
import org.example.service.PainelSolarServiceFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Logger;

@Path("/rest/painelSolar")
public class PainelSolarController {

    private final PainelSolarService painelSolarService = PainelSolarServiceFactory.create();
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(PainelSolarDto input) throws UnsupportedServiceOperationException {
        if (input.getId() == null) {
            try {
                PainelSolar painelSolar = this.painelSolarService.create(new PainelSolar(null, input.getNome(), input.getPreco(), input.getPercentualDeEconomia()));
                return Response
                        .status(Response.Status.CREATED)
                        .entity(painelSolar)
                        .build();
            } catch (SQLException | PainelSolarNotSavedException e) {
                logger.severe(e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(Map.of("mensagem", "erro inesperado ao tentar inserir o PainelSolar")).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(
                            Map.of(
                                    "mensagem",
                                    "esse método só permite a criação de novos PaineisSolares"))
                    .build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        return Response.status(Response.Status.OK)
                .entity(this.painelSolarService.findAll()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, PainelSolarDto input) {
        try {
            PainelSolar updated = this.painelSolarService.update(new PainelSolar(id, input.getNome(), input.getPreco(), input.getPercentualDeEconomia()));
            return Response.status(Response.Status.OK).entity(updated).build();
        } catch (PainelSolarNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (SQLException s) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "erro inesperado ao tentar atualizar o PainelSolar")).build();
        }
    }
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            this.painelSolarService.deleteById(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (PainelSolarNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (SQLException s) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "erro inesperado ao tentar deletar o painelSolar")).build();
        }
    }
}
