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
package fr.paris.lutece.plugins.demandcenter.business.contactmode;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;

/**
 * This class provides instances management methods (create, find, ...) for ContactMode objects
 */
public final class ContactModeHome
{
    // Static variable pointed at the DAO instance
    private static IContactModeDAO _dao = SpringContextService.getBean( "demandcenter.contactModeDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "demandcenter" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private ContactModeHome( )
    {
    }

    /**
     * Create an instance of the contactMode class
     * 
     * @param contactMode
     *            The instance of the ContactMode which contains the informations to store
     * @return The instance of contactMode which has been created with its primary key.
     */
    public static ContactMode create( ContactMode contactMode )
    {
        _dao.insert( contactMode, _plugin );

        return contactMode;
    }

    /**
     * Update of the contactMode which is specified in parameter
     * 
     * @param contactMode
     *            The instance of the ContactMode which contains the data to store
     * @return The instance of the contactMode which has been updated
     */
    public static ContactMode update( ContactMode contactMode )
    {
        _dao.store( contactMode, _plugin );

        return contactMode;
    }

    /**
     * Remove the contactMode whose identifier is specified in parameter
     * 
     * @param nKey
     *            The contactMode Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a contactMode whose identifier is specified in parameter
     * 
     * @param nKey
     *            The contactMode primary key
     * @return an instance of ContactMode
     */
    public static ContactMode findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Returns a instance of a contact mode with the the code is specified in parameter
     * 
     * @param strCode
     * @return an instance of ContactMode
     */
    public static ContactMode findByCode( String strCode )
    {
        return _dao.loadByCode( strCode, _plugin );
    }

    /**
     * Load the data of all the contactMode objects and returns them as a list
     * 
     * @return the list which contains the data of all the contactMode objects
     */
    public static List<ContactMode> getContactModesList( )
    {
        return _dao.selectContactModesList( _plugin );
    }

    /**
     * Load the id of all the contactMode objects and returns them as a list
     * 
     * @return the list which contains the id of all the contactMode objects
     */
    public static List<Integer> getIdContactModesList( )
    {
        return _dao.selectIdContactModesList( _plugin );
    }

    /**
     * Load the data of all the contactMode objects and returns them as a referenceList
     * 
     * @return the referenceList which contains the data of all the contactMode objects
     */
    public static ReferenceList getContactModesReferenceList( )
    {
        return _dao.selectContactModesReferenceList( _plugin );
    }
}
