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

import fr.paris.lutece.plugins.demandcenter.service.category.CategoryService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for Demand objects
 */
public final class DemandDAO implements IDemandDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_demand ) FROM demandcenter_demand";
    private static final String SQL_QUERY_SELECT = "SELECT id_demand, reference, is_read , comment, date_create, date_close, demand_content, code_form, guid, id_category, id_assignee_user, id_assignee_unit, id_channel, id_contact_mode, date_last_update FROM demandcenter_demand WHERE id_demand = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO demandcenter_demand ( id_demand, reference, is_read, comment, date_create, date_close, demand_content, code_form, guid, id_category, id_assignee_user, id_assignee_unit, id_channel, id_contact_mode, date_last_update ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM demandcenter_demand WHERE id_demand = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE demandcenter_demand SET id_demand = ?, reference = ?, is_read = ?, comment = ?, date_create = ?, date_close = ?, demand_content = ?, code_form = ?, guid = ?, id_category = ?, id_assignee_user = ?, id_assignee_unit = ?, id_channel = ?, id_contact_mode = ?, date_last_update = ? WHERE id_demand = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_demand, reference, is_read, comment, date_create, date_close, demand_content, code_form, guid, id_category, id_assignee_user, id_assignee_unit, id_channel, id_contact_mode, date_last_update FROM demandcenter_demand";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_demand FROM demandcenter_demand";
    // For manage demand_list and full indexing
    private static final String SQL_QUERY_SELECT_FULL_ALL = "SELECT demand.id_demand, demand.reference, demand.code_form, demand.is_read, demand.comment, demand.date_create, demand.date_close, demand.date_last_update, demand.id_category, demand.guid, "
            + "assigneeuser.last_name, assigneeuser.first_name, assigneeuser.email, "
            + "assigneeunit.id_unit, assigneeunit.label, "
            + "channel.label, channel.icon_font, "
            + "contactmode.label, contactmode.icon_font, "
            + "workflow_state.name "
            + " FROM demandcenter_demand demand "
            + " LEFT JOIN core_admin_user assigneeuser ON demand.id_assignee_user = assigneeuser.id_user "
            + " LEFT JOIN unittree_unit assigneeunit ON demand.id_assignee_unit = assigneeunit.id_unit "
            + " LEFT JOIN demandcenter_channel channel ON demand.id_channel = channel.id_channel "
            + " LEFT JOIN demandcenter_contactmode contactmode ON demand.id_contact_mode = contactmode.id_contact_mode "
            + " LEFT JOIN workflow_resource_workflow ON workflow_resource_workflow.resource_type = 'demandcenter_demand' AND workflow_resource_workflow.id_resource = demand.id_demand "
            + " LEFT JOIN workflow_state workflow_state ON workflow_resource_workflow.id_state = workflow_state.id_state";

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
    public void insert( Demand demand, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        demand.setId( newPrimaryKey( plugin ) );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, demand.getId( ) );
        daoUtil.setString( nIndex++, demand.getReference( ) );
        daoUtil.setBoolean( nIndex++, demand.getRead( ) );
        daoUtil.setString( nIndex++, demand.getComment( ) );
        daoUtil.setTimestamp( nIndex++, demand.getDateCreate( ) );
        daoUtil.setTimestamp( nIndex++, demand.getDateClose( ) );
        daoUtil.setString( nIndex++, demand.getDemandContent( ) );
        daoUtil.setString( nIndex++, demand.getCodeForm( ) );
        daoUtil.setString( nIndex++, demand.getGuid( ) );
        daoUtil.setInt( nIndex++, demand.getCategory( ).getId( ) );
        daoUtil.setInt( nIndex++, demand.getAssigneeUser( ).getUserId( ) );
        daoUtil.setInt( nIndex++, demand.getAssigneeUnit( ).getIdUnit( ) );
        daoUtil.setInt( nIndex++, demand.getChannel( ).getId( ) );
        daoUtil.setInt( nIndex++, demand.getContactMode( ).getId( ) );
        daoUtil.setTimestamp( nIndex++, demand.getDateLastUpdate( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Demand load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );
        Demand demand = null;

        if ( daoUtil.next( ) )
        {
            demand = new Demand( );
            int nIndex = 1;

            demand.setId( daoUtil.getInt( nIndex++ ) );
            demand.setReference( daoUtil.getString( nIndex++ ) );
            demand.setRead( daoUtil.getBoolean( nIndex++ ) );
            demand.setComment( daoUtil.getString( nIndex++ ) );
            demand.setDateCreate( daoUtil.getTimestamp( nIndex++ ) );
            demand.setDateClose( daoUtil.getTimestamp( nIndex++ ) );
            demand.setDemandContent( daoUtil.getString( nIndex++ ) );
            demand.setCodeForm( daoUtil.getString( nIndex++ ) );
            demand.setGuid( daoUtil.getString( nIndex++ ) );
            demand.getCategory( ).setId( daoUtil.getInt( nIndex++ ) );
            demand.getAssigneeUser( ).setUserId( daoUtil.getInt( nIndex++ ) );
            demand.getAssigneeUnit( ).setIdUnit( daoUtil.getInt( nIndex++ ) );
            demand.getChannel( ).setId( daoUtil.getInt( nIndex++ ) );
            demand.getContactMode( ).setId( daoUtil.getInt( nIndex++ ) );
            demand.setDateLastUpdate( daoUtil.getTimestamp( nIndex++ ) );
        }

        daoUtil.free( );
        return demand;
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
    public void store( Demand demand, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, demand.getId( ) );
        daoUtil.setString( nIndex++, demand.getReference( ) );
        daoUtil.setBoolean( nIndex++, demand.getRead( ) );
        daoUtil.setString( nIndex++, demand.getComment( ) );
        daoUtil.setTimestamp( nIndex++, demand.getDateCreate( ) );
        daoUtil.setTimestamp( nIndex++, demand.getDateClose( ) );
        daoUtil.setString( nIndex++, demand.getDemandContent( ) );
        daoUtil.setString( nIndex++, demand.getCodeForm( ) );
        daoUtil.setString( nIndex++, demand.getGuid( ) );
        daoUtil.setInt( nIndex++, demand.getCategory( ).getId( ) );
        daoUtil.setInt( nIndex++, demand.getAssigneeUser( ).getUserId( ) );
        daoUtil.setInt( nIndex++, demand.getAssigneeUnit( ).getIdUnit( ) );
        daoUtil.setInt( nIndex++, demand.getChannel( ).getId( ) );
        daoUtil.setInt( nIndex++, demand.getContactMode( ).getId( ) );
        daoUtil.setTimestamp( nIndex++, demand.getDateLastUpdate( ) );
        daoUtil.setInt( nIndex, demand.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Demand> selectDemandsList( Plugin plugin )
    {
        List<Demand> demandList = new ArrayList<Demand>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            Demand demand = new Demand( );
            int nIndex = 1;

            demand.setId( daoUtil.getInt( nIndex++ ) );
            demand.setReference( daoUtil.getString( nIndex++ ) );
            demand.setRead( daoUtil.getBoolean( nIndex++ ) );
            demand.setComment( daoUtil.getString( nIndex++ ) );
            demand.setDateCreate( daoUtil.getTimestamp( nIndex++ ) );
            demand.setDateClose( daoUtil.getTimestamp( nIndex++ ) );
            demand.setDemandContent( daoUtil.getString( nIndex++ ) );
            demand.setCodeForm( daoUtil.getString( nIndex++ ) );
            demand.setGuid( daoUtil.getString( nIndex++ ) );
            demand.getCategory( ).setId( daoUtil.getInt( nIndex++ ) );
            demand.getAssigneeUser( ).setUserId( daoUtil.getInt( nIndex++ ) );
            demand.getAssigneeUnit( ).setIdUnit( daoUtil.getInt( nIndex++ ) );
            demand.getChannel( ).setId( daoUtil.getInt( nIndex++ ) );
            demand.getContactMode( ).setId( daoUtil.getInt( nIndex++ ) );
            demand.setDateLastUpdate( daoUtil.getTimestamp( nIndex++ ) );

            demandList.add( demand );
        }

        daoUtil.free( );
        return demandList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdDemandsList( Plugin plugin )
    {
        List<Integer> demandList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            demandList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return demandList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectDemandsReferenceList( Plugin plugin )
    {
        ReferenceList demandList = new ReferenceList( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            demandList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return demandList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Demand> selectFullDemandsList( Plugin plugin )
    {
        List<Demand> demandList = new ArrayList<Demand>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_FULL_ALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            Demand demand = new Demand( );
            int nIndex = 1;

            demand.setId( daoUtil.getInt( nIndex++ ) );
            demand.setReference( daoUtil.getString( nIndex++ ) );
            demand.setCodeForm( daoUtil.getString( nIndex++ ) );
            demand.setRead( daoUtil.getBoolean( nIndex++ ) );
            demand.setComment( daoUtil.getString( nIndex++ ) );
            demand.setDateCreate( daoUtil.getTimestamp( nIndex++ ) );
            demand.setDateClose( daoUtil.getTimestamp( nIndex++ ) );
            demand.setDateLastUpdate( daoUtil.getTimestamp( nIndex++ ) );
            demand.setDemandContent( "" );
            demand.setCategory( CategoryService.getInstance( ).findById( daoUtil.getInt( nIndex++ ) ) );
            demand.setGuid( daoUtil.getString( nIndex++ ) );
            demand.getAssigneeUser( ).setLastName( daoUtil.getString( nIndex++ ) );
            demand.getAssigneeUser( ).setFirstName( daoUtil.getString( nIndex++ ) );
            demand.getAssigneeUser( ).setEmail( daoUtil.getString( nIndex++ ) );
            demand.getAssigneeUnit( ).setIdUnit( daoUtil.getInt( nIndex++ ) );
            demand.getAssigneeUnit( ).setLabel( daoUtil.getString( nIndex++ ) );
            demand.getChannel( ).setLabel( daoUtil.getString( nIndex++ ) );
            demand.getChannel( ).setIconFont( daoUtil.getString( nIndex++ ) );
            demand.getContactMode( ).setLabel( daoUtil.getString( nIndex++ ) );
            demand.getContactMode( ).setIconFont( daoUtil.getString( nIndex++ ) );
            demand.getState( ).setName( daoUtil.getString( nIndex++ ) );

            demandList.add( demand );
        }

        daoUtil.free( );
        return demandList;
    }

}
