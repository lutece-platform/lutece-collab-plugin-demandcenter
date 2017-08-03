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

import fr.paris.lutece.plugins.demandcenter.business.attributes.Attribute;
import fr.paris.lutece.plugins.demandcenter.business.attributes.AttributeDemand;
import fr.paris.lutece.plugins.demandcenter.business.attributes.AttributeHome;
import fr.paris.lutece.plugins.demandcenter.business.category.Category;
import fr.paris.lutece.plugins.demandcenter.business.channel.Channel;
import fr.paris.lutece.plugins.demandcenter.business.channel.ChannelHome;
import fr.paris.lutece.plugins.demandcenter.business.contactmode.ContactMode;
import fr.paris.lutece.plugins.demandcenter.business.contactmode.ContactModeHome;
import fr.paris.lutece.plugins.demandcenter.business.demand.Demand;
import fr.paris.lutece.plugins.demandcenter.rs.exception.CategoryNotFoundException;
import fr.paris.lutece.plugins.demandcenter.rs.exception.ChannelNotFoundException;
import fr.paris.lutece.plugins.demandcenter.rs.exception.ContactModeNotFoundException;
import fr.paris.lutece.plugins.demandcenter.rs.exception.IdentityNotFoundException;
import fr.paris.lutece.plugins.demandcenter.rs.exception.MissingAttributesException;
import fr.paris.lutece.plugins.demandcenter.rs.exception.UnableToAssignateException;
import fr.paris.lutece.plugins.demandcenter.service.category.CategoryService;
import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import java.util.List;
import java.util.stream.Collectors;

public class DemandValidator
{
    public static void validate( Demand demand ) throws MissingAttributesException, CategoryNotFoundException, ChannelNotFoundException,
            ContactModeNotFoundException, IdentityNotFoundException, UnableToAssignateException
    {
        // Check if mandatory attributes are not provided
        List<Attribute> listMandatoryAttributes = AttributeHome.getAttributesMandatoryList( );
        List<Integer> listIdAttributesMandatory = listMandatoryAttributes.stream( ).map( Attribute::getId ).collect( Collectors.toList( ) );

        List<Integer> listIdAttributes = demand.getListAttributeDemand( ).stream( ).map( AttributeDemand::getAttribute ).map( Attribute::getId )
                .collect( Collectors.toList( ) );

        listIdAttributesMandatory.removeAll( listIdAttributes );
        if ( listIdAttributesMandatory.size( ) > 0 )
        {
            StringBuilder strMissingCodes = new StringBuilder( );
            for ( int idAttribute : listIdAttributesMandatory )
            {
                for ( Attribute attribute : listMandatoryAttributes )
                {
                    if ( attribute.getId( ) == idAttribute )
                    {
                        strMissingCodes.append( attribute.getCode( ) );
                        strMissingCodes.append( " " );
                    }
                }
            }

            // Send the non provided mandatory attributes to the client
            throw new MissingAttributesException( null, strMissingCodes.toString( ) );
        }

        // Check if given category exists; look in cached tree of categories instead of database
        Category category = CategoryService.getInstance( ).findByCode( demand.getCategory( ).getCode( ) );
        if ( category == null )
        {
            throw new CategoryNotFoundException( null );
        }
        else
        {
            demand.setCategory( category );
        }

        // Check if the given category has at least one parent which have an assignee unit
        Unit unit = CategoryService.getInstance( ).findAssigneeUnit( category );
        if ( unit == null )
        {
            throw new UnableToAssignateException( null );
        }
        else
        {
            demand.setAssigneeUnit( unit );
        }

        // Check if given channel exists
        Channel channel = ChannelHome.findByCode( demand.getChannel( ).getCode( ) );
        if ( channel == null )
        {
            throw new ChannelNotFoundException( null );
        }
        else
        {
            demand.setChannel( channel );
        }

        // Check if contact mode exists
        ContactMode contactMode = ContactModeHome.findByCode( demand.getContactMode( ).getCode( ) );
        if ( contactMode == null )
        {
            throw new ContactModeNotFoundException( null );
        }
        else
        {
            demand.setContactMode( contactMode );
        }

        // If the demand is set as an authenticated one, check if identity exists
        String strGuid = demand.getGuid( );
        if ( strGuid != null ) // The client called the demand center for an authenticated demand
        {
            // TODO check to identitystore
            if ( false )
            {
                throw new IdentityNotFoundException( null, strGuid );
            }
        }
    }

}
