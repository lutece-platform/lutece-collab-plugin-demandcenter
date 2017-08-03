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
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for AttributeDemand objects
 */
public final class AttributeDemandDAO implements IAttributeDemandDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id ) FROM demandcenter_attribute_demand";
    private static final String SQL_QUERY_SELECT = "SELECT id, id_demand, id_attribute, value, filled_by_demand_content FROM demandcenter_attribute_demand WHERE id_attribute_user = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO demandcenter_attribute_demand ( id, id_demand, id_attribute, value, filled_by_demand_content ) VALUES ( ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM demandcenter_attribute_demand WHERE id = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE demandcenter_attribute_demand SET id = ?, id_demand = ?, id_attribute = ?, value = ?, filled_by_demand_content = ? WHERE id_attribute_user = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id, id_demand, id_attribute, value, filled_by_demand_content FROM demandcenter_attribute_demand";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id FROM demandcenter_attribute_demand";
    private static final String SQL_QUERY_SELECTALL_FULL_BY_ID_DEMAND = "SELECT attribute_demand.id, attribute_demand.id_demand, attribute_demand.id_attribute, attribute_demand.value, attribute_demand.filled_by_demand_content, "
            + " attribute.code, attribute.label "
            + " FROM demandcenter_attribute_demand attribute_demand "
            + " LEFT JOIN demandcenter_attribute attribute ON  attribute.id_attribute = attribute_demand.id_attribute " + " WHERE id_demand = ?";

    /**
     * Generates a new primary key
     * 
     * @param plugin
     *            The Plugin
     * @return The new primary key
     */
    public int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery( );
        int nKey = 1;

        if ( daoUtil.next( ) )
        {
            nKey = daoUtil.getInt( 1 ) + 1;
        }

        daoUtil.free( );
        return nKey;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( AttributeDemand attributeDemand, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        attributeDemand.setId( newPrimaryKey( plugin ) );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, attributeDemand.getId( ) );
        daoUtil.setInt( nIndex++, attributeDemand.getIdDemand( ) );
        daoUtil.setInt( nIndex++, attributeDemand.getAttribute( ).getId( ) );
        daoUtil.setString( nIndex++, attributeDemand.getValue( ) );
        daoUtil.setBoolean( nIndex++, attributeDemand.getFilledByDemandContent( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public AttributeDemand load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );
        AttributeDemand attributeDemand = null;

        if ( daoUtil.next( ) )
        {
            attributeDemand = new AttributeDemand( );
            int nIndex = 1;

            attributeDemand.setId( daoUtil.getInt( nIndex++ ) );
            attributeDemand.setIdDemand( daoUtil.getInt( nIndex++ ) );
            attributeDemand.getAttribute( ).setId( daoUtil.getInt( nIndex++ ) );
            attributeDemand.setValue( daoUtil.getString( nIndex++ ) );
            attributeDemand.setFilledByDemandContent( daoUtil.getBoolean( nIndex++ ) );
        }

        daoUtil.free( );
        return attributeDemand;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( AttributeDemand attributeDemand, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, attributeDemand.getId( ) );
        daoUtil.setInt( nIndex++, attributeDemand.getIdDemand( ) );
        daoUtil.setInt( nIndex++, attributeDemand.getAttribute( ).getId( ) );
        daoUtil.setString( nIndex++, attributeDemand.getValue( ) );
        daoUtil.setBoolean( nIndex++, attributeDemand.getFilledByDemandContent( ) );
        daoUtil.setInt( nIndex, attributeDemand.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<AttributeDemand> selectAttributeDemandsList( Plugin plugin )
    {
        List<AttributeDemand> attributeDemandList = new ArrayList<AttributeDemand>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            AttributeDemand attributeDemand = new AttributeDemand( );
            int nIndex = 1;

            attributeDemand.setId( daoUtil.getInt( nIndex++ ) );
            attributeDemand.setIdDemand( daoUtil.getInt( nIndex++ ) );
            attributeDemand.getAttribute( ).setId( daoUtil.getInt( nIndex++ ) );
            attributeDemand.setValue( daoUtil.getString( nIndex++ ) );
            attributeDemand.setFilledByDemandContent( daoUtil.getBoolean( nIndex++ ) );

            attributeDemandList.add( attributeDemand );
        }

        daoUtil.free( );
        return attributeDemandList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdAttributeDemandsList( Plugin plugin )
    {
        List<Integer> attributeDemandList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            attributeDemandList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return attributeDemandList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectAttributeDemandsReferenceList( Plugin plugin )
    {
        ReferenceList attributeDemandList = new ReferenceList( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            attributeDemandList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return attributeDemandList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<AttributeDemand> selectAttributeDemandsListFromIdDemand( int nIdDemand, Plugin plugin )
    {
        List<AttributeDemand> attributeDemandList = new ArrayList<AttributeDemand>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_FULL_BY_ID_DEMAND, plugin );
        daoUtil.setInt( 1, nIdDemand );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            AttributeDemand attributeDemand = new AttributeDemand( );
            int nIndex = 1;

            attributeDemand.setId( daoUtil.getInt( nIndex++ ) );
            attributeDemand.setIdDemand( daoUtil.getInt( nIndex++ ) );
            attributeDemand.getAttribute( ).setId( daoUtil.getInt( nIndex++ ) );
            attributeDemand.setValue( daoUtil.getString( nIndex++ ) );
            attributeDemand.setFilledByDemandContent( daoUtil.getBoolean( nIndex++ ) );
            attributeDemand.getAttribute( ).setCode( daoUtil.getString( nIndex++ ) );
            attributeDemand.getAttribute( ).setLabel( daoUtil.getString( nIndex++ ) );

            attributeDemandList.add( attributeDemand );
        }

        daoUtil.free( );
        return attributeDemandList;
    }
}
