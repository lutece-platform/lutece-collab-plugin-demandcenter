/*
 * Copyright (c) 2002-2016, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.demandcenter.rs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.paris.lutece.plugins.demandcenter.business.demand.Demand;
import fr.paris.lutece.plugins.demandcenter.business.web.rs.dto.FormSubmitDto;
import fr.paris.lutece.plugins.demandcenter.business.web.rs.dto.ResponseDto;
import fr.paris.lutece.plugins.demandcenter.rs.exception.AbstractRestException;
import fr.paris.lutece.plugins.demandcenter.rs.exception.ObjectConvertToJsonException;
import fr.paris.lutece.plugins.demandcenter.service.demand.DemandService;
import fr.paris.lutece.plugins.demandcenter.service.demand.DemandValidator;
import fr.paris.lutece.portal.service.util.AppLogService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * DemandRest
 */
@Path( "/rest/demandcenter/demand" )
public class DemandRestService
{
    // Messages
    public static final String SUCCESS_MESSAGE = "success";

    private final ObjectMapper _mapper;

    /**
     * private constructor
     */
    private DemandRestService( )
    {
        _mapper = new ObjectMapper( );
        _mapper.enable( SerializationFeature.WRAP_ROOT_VALUE );
        _mapper.enable( DeserializationFeature.UNWRAP_ROOT_VALUE );
    }

    /**
     * webservice to create demad
     * 
     * @param strJsonFormSubmit
     *            the form submit
     * @return the response to send to the client
     * @throws ObjectConvertToJsonException
     *             if the response cant be mapped to an object
     */
    @POST
    @Path( "/create" )
    @Consumes( MediaType.APPLICATION_JSON )
    public Response createDemand( String strJsonFormSubmit ) throws ObjectConvertToJsonException
    {
        try
        {
            FormSubmitDto formSubmit = DemandService.fetchFormSubmit( strJsonFormSubmit );
            Demand demand = DemandService.getDemandFromFormSubmit( formSubmit, strJsonFormSubmit );

            // Test validity of the demand
            DemandValidator.validate( demand );

            // try to create demand, and create/update demand attributes attached to the demand
            DemandService.createDemand( demand );

            return getSuccessResponse( );

        }
        catch( AbstractRestException e )
        {
            AppLogService.error( e.getResponse( ).getMessage( ), e );
            return buildResponse( e.getResponse( ), e.getStatus( ) );
        }
    }

    /**
     * Builds a {@code Response} object from the specified message and status
     * 
     * @param strMessage
     *            the message
     * @param status
     *            the status
     * @return the {@code Response} object
     */
    private Response buildResponse( ResponseDto response, Status status ) throws ObjectConvertToJsonException
    {
        try
        {
            return Response.status( status ).type( MediaType.APPLICATION_JSON ).entity( _mapper.writeValueAsString( response ) ).build( );
        }
        catch( JsonProcessingException e )
        {
            throw new ObjectConvertToJsonException( e );
        }
    }

    /**
     * Get the response for a success
     * 
     * @return a successful response
     */
    private Response getSuccessResponse( ) throws ObjectConvertToJsonException
    {
        ResponseDto successResponse = new ResponseDto( );
        successResponse.setMessage( SUCCESS_MESSAGE );
        successResponse.setStatus( Integer.toString( Status.OK.getStatusCode( ) ) );

        return buildResponse( successResponse, Status.OK );
    }

}
