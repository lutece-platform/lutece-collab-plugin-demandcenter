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

package fr.paris.lutece.plugins.demandcenter.business.channel;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for Channel objects
 */
public final class ChannelDAO implements IChannelDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_channel ) FROM demandcenter_channel";
    private static final String SQL_QUERY_SELECT = "SELECT id_channel, label, icon_font, code FROM demandcenter_channel WHERE id_channel = ?";
    private static final String SQL_QUERY_SELECT_BY_CODE = "SELECT id_channel, label, icon_font, code FROM demandcenter_channel WHERE code = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO demandcenter_channel ( id_channel, label, icon_font, code ) VALUES ( ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM demandcenter_channel WHERE id_channel = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE demandcenter_channel SET id_channel = ?, label = ?, icon_font = ?, code = ? WHERE id_channel = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_channel, label, icon_font, code FROM demandcenter_channel";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_channel FROM demandcenter_channel";

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
    public void insert( Channel channel, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        channel.setId( newPrimaryKey( plugin ) );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, channel.getId( ) );
        daoUtil.setString( nIndex++, channel.getLabel( ) );
        daoUtil.setString( nIndex++, channel.getIconFont( ) );
        daoUtil.setString( nIndex++, channel.getCode( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Channel load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );
        Channel channel = null;

        if ( daoUtil.next( ) )
        {
            channel = new Channel( );
            int nIndex = 1;

            channel.setId( daoUtil.getInt( nIndex++ ) );
            channel.setLabel( daoUtil.getString( nIndex++ ) );
            channel.setIconFont( daoUtil.getString( nIndex++ ) );
            channel.setCode( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return channel;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Channel loadByCode( String strCode, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_CODE, plugin );
        daoUtil.setString( 1, strCode );
        daoUtil.executeQuery( );
        Channel channel = null;

        if ( daoUtil.next( ) )
        {
            channel = new Channel( );
            int nIndex = 1;

            channel.setId( daoUtil.getInt( nIndex++ ) );
            channel.setLabel( daoUtil.getString( nIndex++ ) );
            channel.setIconFont( daoUtil.getString( nIndex++ ) );
            channel.setCode( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return channel;
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
    public void store( Channel channel, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, channel.getId( ) );
        daoUtil.setString( nIndex++, channel.getLabel( ) );
        daoUtil.setString( nIndex++, channel.getIconFont( ) );
        daoUtil.setString( nIndex++, channel.getCode( ) );
        daoUtil.setInt( nIndex, channel.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Channel> selectChannelsList( Plugin plugin )
    {
        List<Channel> channelList = new ArrayList<Channel>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            Channel channel = new Channel( );
            int nIndex = 1;

            channel.setId( daoUtil.getInt( nIndex++ ) );
            channel.setLabel( daoUtil.getString( nIndex++ ) );
            channel.setIconFont( daoUtil.getString( nIndex++ ) );
            channel.setCode( daoUtil.getString( nIndex++ ) );

            channelList.add( channel );
        }

        daoUtil.free( );
        return channelList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdChannelsList( Plugin plugin )
    {
        List<Integer> channelList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            channelList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return channelList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectChannelsReferenceList( Plugin plugin )
    {
        ReferenceList channelList = new ReferenceList( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            channelList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return channelList;
    }
}
