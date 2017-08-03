/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
package fr.paris.lutece.plugins.demandcenter.service.demand;

import fr.paris.lutece.plugins.demandcenter.business.attributes.AttributeDemand;
import fr.paris.lutece.plugins.demandcenter.business.attributes.AttributeDemandHome;
import fr.paris.lutece.plugins.demandcenter.business.category.Category;
import fr.paris.lutece.plugins.demandcenter.business.channel.Channel;
import fr.paris.lutece.plugins.demandcenter.business.contactmode.ContactMode;
import fr.paris.lutece.plugins.demandcenter.business.demand.Demand;
import fr.paris.lutece.plugins.demandcenter.business.demand.DemandHome;
import fr.paris.lutece.plugins.demandcenter.business.web.rs.dto.FormSubmitDto;
import fr.paris.lutece.plugins.demandcenter.rs.exception.CategoryNotFoundException;
import fr.paris.lutece.plugins.demandcenter.rs.exception.ChannelNotFoundException;
import fr.paris.lutece.plugins.demandcenter.rs.exception.ContactModeNotFoundException;
import fr.paris.lutece.plugins.demandcenter.rs.exception.IdentityNotFoundException;
import fr.paris.lutece.plugins.demandcenter.rs.exception.MissingAttributesException;
import fr.paris.lutece.plugins.demandcenter.service.demand.attributes.DemandAttributeService;
import fr.paris.lutece.portal.service.workflow.WorkflowService;
import java.sql.Timestamp;

public class DemandService
{
    public static WorkflowService _workflowService = WorkflowService.getInstance( );

    /**
     * Get a demand java obj from a form submit obj
     * 
     * @param formSubmit
     * @param strJsonFormSubmit
     * @return a Demand java obj
     */
    public static Demand getDemandFromFormSubmit( FormSubmitDto formSubmit, String strJsonFormSubmit )
    {
        Demand demand = new Demand( );
        demand.setGuid( formSubmit.getUsername( ) );
        // Get the demand user attached to the form submit and set it to the current demand;
        demand.setListAttributeDemand( DemandAttributeService.getAttributesDemandFromFormSubmit( formSubmit ) );

        // Set the category found in form submit
        Category category = new Category( );
        category.setCode( formSubmit.getCategory( ) );
        demand.setCategory( category );

        // Set the creation date
        demand.setDateCreate( new Timestamp( System.currentTimeMillis( ) ) );

        // Set the channel
        Channel channel = new Channel( );
        channel.setCode( formSubmit.getChannel( ) );
        demand.setChannel( channel );

        // Set the contact mode
        ContactMode contactMode = new ContactMode( );
        contactMode.setCode( formSubmit.getContactMode( ) );
        demand.setContactMode( contactMode );

        // Set the comment
        demand.setComment( formSubmit.getComment( ) );

        // Set the code form
        demand.setCodeForm( formSubmit.getCodeForm( ) );

        // Set the demand content (the json corresponding to the form submit )
        demand.setDemandContent( strJsonFormSubmit );

        return demand;
    }

    /**
     * Create the demand
     * 
     * @param demand
     *            the demand to create
     * @throws MissingAttributesException
     *             if all the mandatory attributes are not provided
     * @throws CategoryNotFoundException
     *             if the given category isnt found
     * @throws ChannelNotFoundException
     *             if the given channel isn't found
     * @throws ContactModeNotFoundException
     *             if the given contact mode isn't found
     * @throws IdentityNotFoundException
     *             if the given identity doesn't exists in identity service
     */
    public static void createDemand( Demand demand ) throws MissingAttributesException, CategoryNotFoundException, ChannelNotFoundException,
            ContactModeNotFoundException, IdentityNotFoundException
    {
        int nIdWorkflow = demand.getCategory( ).getIdWorkflow( );

        // Store the demand, user and user attr if no exceptions
        demand = DemandHome.create( demand );

        // Store attributes
        for ( AttributeDemand attribute : demand.getListAttributeDemand( ) )
        {
            attribute.setIdDemand( demand.getId( ) );
            AttributeDemandHome.create( attribute );
        }

        // Initialize the workflow resource
        _workflowService.getState( demand.getId( ), Demand.DEMAND_RESOURCE_TYPE, nIdWorkflow, -1 );

    }

}
