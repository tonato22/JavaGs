package org.example.controller;
import org.example.dtos.PedidoDto;
import org.example.exceptions.*;
import org.example.models.Cliente;
import org.example.models.PainelSolar;
import org.example.models.Pedido;
import org.example.service.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Logger;

@Path("/rest/pedido")
public class PedidoController {
    private final PedidoService pedidoService = PedidoServiceFactory.create();
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final ClienteService clienteService = ClienteServiceFactory.create();
    private final PainelSolarService painelSolarService = PainelSolarServiceFactory.create();

   @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        return Response.status(Response.Status.OK)
                .entity(this.pedidoService.findAll()).build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(PedidoDto input) {
            try {
                Pedido pedido = this.pedidoService.create(input);
                return Response
                        .status(Response.Status.CREATED)
                        .entity(pedido)
                        .build();
            } catch (SQLException | PedidoNotSavedException  | PedidoNotFoundException | UnsupportedServiceOperationException  |ClienteNotFoundException | PainelSolarNotFoundException e) {
                logger.severe(e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(Map.of("mensagem", "Erro inesperado ao tentar inserir pedido")).build();
            }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, PedidoDto input) {
        try {
            Cliente cliente = clienteService.findById(input.getClienteid());
            PainelSolar painelSolar = painelSolarService.findById(input.getPainelSolarid());
            Pedido updated = this.pedidoService.update(
                    new Pedido(
                            id,
                            cliente,
                            painelSolar,
                            input.getQuantindadePainelSolar()
                    )
            );
            return Response.status(Response.Status.OK).entity(updated).build();
        } catch (PedidoNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensagem", "Pedido não encontrado")).build();
        } catch (SQLException s) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "Erro inesperado ao tentar atualizar pedido")).build();
        } catch (ClienteNotFoundException e) {
            throw new RuntimeException(e);
        } catch (PainelSolarNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            this.pedidoService.deleteById(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (PedidoNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensagem", "Pedido não encontrado")).build();
        } catch (SQLException s) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("mensagem", "Erro inesperado ao tentar deletar pedido")).build();
        }
    }



}
