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
package fr.paris.lutece.plugins.demandcenter.business.demand;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;

/**
 * This class provides instances management methods (create, find, ...) for Demand objects
 */
public final class DemandHome
{
    // Static variable pointed at the DAO instance
    private static IDemandDAO _dao = SpringContextService.getBean( "demandcenter.demandDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "demandcenter" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private DemandHome( )
    {
    }

    /**
     * Create an instance of the demand class
     * 
     * @param demand
     *            The instance of the Demand which contains the informations to store
     * @return The instance of demand which has been created with its primary key.
     */
    public static Demand create( Demand demand )
    {
        _dao.insert( demand, _plugin );

        return demand;
    }

    /**
     * Update of the demand which is specified in parameter
     * 
     * @param demand
     *            The instance of the Demand which contains the data to store
     * @return The instance of the demand which has been updated
     */
    public static Demand update( Demand demand )
    {
        _dao.store( demand, _plugin );

        return demand;
    }

    /**
     * Remove the demand whose identifier is specified in parameter
     * 
     * @param nKey
     *            The demand Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a demand whose identifier is specified in parameter
     * 
     * @param nKey
     *            The demand primary key
     * @return an instance of Demand
     */
    public static Demand findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }
    
    /**
     * Returns an instance of a full demand whose identifier is specified in parameter
     * 
     * @param nKey
     *            The demand primary key
     * @return a full instance of Demand
     */
    public static Demand findFullByPrimaryKey( int nKey )
    {
        return _dao.loadFull( nKey, _plugin );
    }

    /**
     * Load the data of all the demand objects and returns them as a list
     * 
     * @return the list which contains the data of all the demand objects
     */
    public static List<Demand> getDemandsList( )
    {
        return _dao.selectDemandsList( _plugin );
    }

    /**
     * Load the full data of all the demand objects and returns them as a list
     * 
     * @return the list which contains the full data of all the demand objects
     */
    public static List<Demand> getFullDemandsList( )
    {
        return _dao.selectFullDemandsList( _plugin );
    }

    /**
     * Load the id of all the demand objects and returns them as a list
     * 
     * @return the list which contains the id of all the demand objects
     */
    public static List<Integer> getIdDemandsList( )
    {
        return _dao.selectIdDemandsList( _plugin );
    }

    /**
     * Load the data of all the demand objects and returns them as a referenceList
     * 
     * @return the referenceList which contains the data of all the demand objects
     */
    public static ReferenceList getDemandsReferenceList( )
    {
        return _dao.selectDemandsReferenceList( _plugin );
    }
}
