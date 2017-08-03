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
package fr.paris.lutece.plugins.demandcenter.business.attributes;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;

/**
 * This class provides instances management methods (create, find, ...) for AttributeDemand objects
 */
public final class AttributeDemandHome
{
    // Static variable pointed at the DAO instance
    private static IAttributeDemandDAO _dao = SpringContextService.getBean( "demandcenter.attributeDemandDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "demandcenter" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private AttributeDemandHome( )
    {
    }

    /**
     * Create an instance of the attributeDemand class
     * 
     * @param attributeDemand
     *            The instance of the AttributeDemand which contains the informations to store
     * @return The instance of attributeDemand which has been created with its primary key.
     */
    public static AttributeDemand create( AttributeDemand attributeDemand )
    {
        _dao.insert( attributeDemand, _plugin );

        return attributeDemand;
    }

    /**
     * Update of the attributeDemand which is specified in parameter
     * 
     * @param attributeDemand
     *            The instance of the AttributeDemand which contains the data to store
     * @return The instance of the attributeDemand which has been updated
     */
    public static AttributeDemand update( AttributeDemand attributeDemand )
    {
        _dao.store( attributeDemand, _plugin );

        return attributeDemand;
    }

    /**
     * Remove the attributeDemand whose identifier is specified in parameter
     * 
     * @param nKey
     *            The attributeDemand Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a attributeDemand whose identifier is specified in parameter
     * 
     * @param nKey
     *            The attributeDemand primary key
     * @return an instance of AttributeDemand
     */
    public static AttributeDemand findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Load the data of all the attributeDemand objects and returns them as a list
     * 
     * @return the list which contains the data of all the attributeDemand objects
     */
    public static List<AttributeDemand> getAttributeDemandsList( )
    {
        return _dao.selectAttributeDemandsList( _plugin );
    }

    /**
     * Load the id of all the attributeDemand objects and returns them as a list
     * 
     * @return the list which contains the id of all the attributeDemand objects
     */
    public static List<Integer> getIdAttributeDemandsList( )
    {
        return _dao.selectIdAttributeDemandsList( _plugin );
    }

    /**
     * Load the data of all the attributeDemand objects and returns them as a referenceList
     * 
     * @return the referenceList which contains the data of all the attributeDemand objects
     */
    public static ReferenceList getAttributeDemandsReferenceList( )
    {
        return _dao.selectAttributeDemandsReferenceList( _plugin );
    }

    /**
     * Load the data of all the attributeDemand objects for a given demand id ans return them as a list
     * 
     * @param nIdDemand
     *            the id of the demand
     * @return the list of all the attributeDemand attached to given id demand
     */
    public static List<AttributeDemand> getAttributeDemandsListFromIdDemand( int nIdDemand )
    {
        return _dao.selectAttributeDemandsListFromIdDemand( nIdDemand, _plugin );
    }
}
