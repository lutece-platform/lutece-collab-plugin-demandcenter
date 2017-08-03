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
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for ContactMode objects
 */
public final class ContactModeDAO implements IContactModeDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_contact_mode ) FROM demandcenter_contactmode";
    private static final String SQL_QUERY_SELECT = "SELECT id_contact_mode, code, label, icon_font FROM demandcenter_contactmode WHERE id_contact_mode = ?";
    private static final String SQL_QUERY_SELECT_BY_CODE = "SELECT id_contact_mode, code, label, icon_font FROM demandcenter_contactmode WHERE code = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO demandcenter_contactmode ( id_contact_mode, code, label, icon_font ) VALUES ( ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM demandcenter_contactmode WHERE id_contact_mode = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE demandcenter_contactmode SET id_contact_mode = ?, code = ?, label = ?, icon_font = ? WHERE id_contact_mode = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_contact_mode, code, label, icon_font FROM demandcenter_contactmode";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_contact_mode FROM demandcenter_contactmode";

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
    public void insert( ContactMode contactMode, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        contactMode.setId( newPrimaryKey( plugin ) );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, contactMode.getId( ) );
        daoUtil.setString( nIndex++, contactMode.getCode( ) );
        daoUtil.setString( nIndex++, contactMode.getLabel( ) );
        daoUtil.setString( nIndex++, contactMode.getIconFont( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ContactMode load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );
        ContactMode contactMode = null;

        if ( daoUtil.next( ) )
        {
            contactMode = new ContactMode( );
            int nIndex = 1;

            contactMode.setId( daoUtil.getInt( nIndex++ ) );
            contactMode.setCode( daoUtil.getString( nIndex++ ) );
            contactMode.setLabel( daoUtil.getString( nIndex++ ) );
            contactMode.setIconFont( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return contactMode;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ContactMode loadByCode( String strCode, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_CODE, plugin );
        daoUtil.setString( 1, strCode );
        daoUtil.executeQuery( );
        ContactMode contactMode = null;

        if ( daoUtil.next( ) )
        {
            contactMode = new ContactMode( );
            int nIndex = 1;

            contactMode.setId( daoUtil.getInt( nIndex++ ) );
            contactMode.setCode( daoUtil.getString( nIndex++ ) );
            contactMode.setLabel( daoUtil.getString( nIndex++ ) );
            contactMode.setIconFont( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return contactMode;
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
    public void store( ContactMode contactMode, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, contactMode.getId( ) );
        daoUtil.setString( nIndex++, contactMode.getCode( ) );
        daoUtil.setString( nIndex++, contactMode.getLabel( ) );
        daoUtil.setString( nIndex++, contactMode.getIconFont( ) );
        daoUtil.setInt( nIndex, contactMode.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<ContactMode> selectContactModesList( Plugin plugin )
    {
        List<ContactMode> contactModeList = new ArrayList<ContactMode>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            ContactMode contactMode = new ContactMode( );
            int nIndex = 1;

            contactMode.setId( daoUtil.getInt( nIndex++ ) );
            contactMode.setCode( daoUtil.getString( nIndex++ ) );
            contactMode.setLabel( daoUtil.getString( nIndex++ ) );
            contactMode.setIconFont( daoUtil.getString( nIndex++ ) );

            contactModeList.add( contactMode );
        }

        daoUtil.free( );
        return contactModeList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdContactModesList( Plugin plugin )
    {
        List<Integer> contactModeList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            contactModeList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return contactModeList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectContactModesReferenceList( Plugin plugin )
    {
        ReferenceList contactModeList = new ReferenceList( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            contactModeList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return contactModeList;
    }
}
