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

import fr.paris.lutece.plugins.demandcenter.business.attributes.AttributeDemand;
import fr.paris.lutece.plugins.demandcenter.business.attributes.AttributeDemandHome;
import fr.paris.lutece.plugins.demandcenter.business.category.Category;
import fr.paris.lutece.plugins.demandcenter.business.channel.Channel;
import fr.paris.lutece.plugins.demandcenter.business.contactmode.ContactMode;
import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import fr.paris.lutece.plugins.workflowcore.business.state.State;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * This is the business class for the object Demand
 */
public class Demand implements Serializable
{
    private static final long serialVersionUID = 1L;
    // For resource workflow
    public static final String DEMAND_RESOURCE_TYPE = "demandcenter_demand";

    // Variables declarations
    private int _nId;
    private String _strReference;
    private boolean _bRead;
    private String _strComment;
    private Timestamp _tDateCreate;
    private Timestamp _tDateLastUpdate;
    private Timestamp _tDateClose;
    private String _strDemandContent;
    private String _strCodeForm;
    private String _strGuid;

    private Category _category;
    private AdminUser _assigneeUser;
    private Unit _assigneeUnit;
    private Channel _channel;
    private ContactMode _contactMode;
    private List<AttributeDemand> _listAttributeDemand;

    private State _state;
    private List<Action> _listActions;

    public Demand( )
    {
        _strReference = StringUtils.EMPTY;

        _category = new Category( );

        _assigneeUnit = new Unit( );
        _assigneeUnit.setIdUnit( -1 );

        _assigneeUser = new AdminUser( );
        _assigneeUser.setUserId( -1 );

        _channel = new Channel( );
        _contactMode = new ContactMode( );

        _state = new State( );
    }

    /**
     * Returns the Id
     * 
     * @return The Id
     */
    public int getId( )
    {
        return _nId;
    }

    /**
     * Sets the Id
     * 
     * @param nId
     *            The Id
     */
    public void setId( int nId )
    {
        _nId = nId;
    }

    /**
     * Returns the Reference
     * 
     * @return The Reference
     */
    public String getReference( )
    {
        return _strReference;
    }

    /**
     * Sets the Reference
     * 
     * @param strReference
     *            The Reference
     */
    public void setReference( String strReference )
    {
        _strReference = strReference;
    }

    /**
     * Returns the DemandContent
     * 
     * @return The DemandContent
     */
    public String getDemandContent( )
    {
        return _strDemandContent;
    }

    /**
     * Sets the DemandContent
     * 
     * @param strDemandContent
     *            The DemandContent
     */
    public void setDemandContent( String strDemandContent )
    {
        _strDemandContent = strDemandContent;
    }

    /**
     * Returns the Read
     * 
     * @return The Read
     */
    public boolean getRead( )
    {
        return _bRead;
    }

    /**
     * Sets the Read
     * 
     * @param bRead
     *            The Read
     */
    public void setRead( boolean bRead )
    {
        _bRead = bRead;
    }

    /**
     * The map of attribute demand : key of attribute ==> AttributeDemand
     * 
     * @return the map of attribute demand, with the key equals the the attribute key and the value equals to the attribute demand
     */
    public Map<String, AttributeDemand> getMapAttributeDemand( )
    {
        List<AttributeDemand> listDemandAttributes = AttributeDemandHome.getAttributeDemandsListFromIdDemand( getId( ) );
        Map<String, AttributeDemand> mapAttributes = new HashMap<>( );
        listDemandAttributes.stream( ).forEach( ( attributeDemand ) -> mapAttributes.put( attributeDemand.getAttribute( ).getCode( ), attributeDemand ) );
        return mapAttributes;
    }

    /**
     * Get the category of the demand
     * 
     * @return the category
     */
    public Category getCategory( )
    {
        return _category;
    }

    /**
     * Set the category of the demand
     * 
     * @param category
     *            the category
     */
    public void setCategory( Category category )
    {
        _category = category;
    }

    /**
     * Get the assignee user of the demand
     * 
     * @return the assignee user of the demand
     */
    public AdminUser getAssigneeUser( )
    {
        return _assigneeUser;
    }

    /**
     * Set the assignee user of the demand
     * 
     * @param assigneeUser
     *            the assignee user of the demand
     */
    public void setAssigneeUser( AdminUser assigneeUser )
    {
        _assigneeUser = assigneeUser;
    }

    /**
     * Get the assignee unit of the demand
     * 
     * @return the assignee unit of the demand
     */
    public Unit getAssigneeUnit( )
    {
        return _assigneeUnit;
    }

    /**
     * Set the assignee unit of the demadn
     * 
     * @param assigneeUnit
     *            the assignee unit
     */
    public void setAssigneeUnit( Unit assigneeUnit )
    {
        _assigneeUnit = assigneeUnit;
    }

    /**
     * Get the comment attached to the demand
     * 
     * @return the comment attached to the demand
     */
    public String getComment( )
    {
        return _strComment;
    }

    /**
     * Set the comment
     * 
     * @param strComment
     *            the comment
     */
    public void setComment( String strComment )
    {
        _strComment = strComment;
    }

    /**
     * Get the channel of the demand
     * 
     * @return the channel of the demand
     */
    public Channel getChannel( )
    {
        return _channel;
    }

    /**
     * Set the channel of the demand
     * 
     * @param Channel
     */
    public void setChannel( Channel Channel )
    {
        _channel = Channel;
    }

    /**
     * Get the contact mode of the demand
     * 
     * @return the contact mode
     */
    public ContactMode getContactMode( )
    {
        return _contactMode;
    }

    /**
     * Set the contact mode of the demand
     * 
     * @param contactMode
     *            the contact mode of the demand
     */
    public void setContactMode( ContactMode contactMode )
    {
        _contactMode = contactMode;
    }

    /**
     * Get the creation date of the demand
     * 
     * @return the creation date of the demand
     */
    public Timestamp getDateCreate( )
    {
        return _tDateCreate;
    }

    /**
     * Set the creation date of the demand
     * 
     * @param tDateCreate
     *            the creation date of the demand
     */
    public void setDateCreate( Timestamp tDateCreate )
    {
        _tDateCreate = tDateCreate;
    }

    /**
     * Get the last update of the demand
     * 
     * @return the last update of the demand
     */
    public Timestamp getDateLastUpdate( )
    {
        return _tDateLastUpdate;
    }

    /**
     * Set the last update of the demand
     * 
     * @param tDateLastUpdate
     *            the last update of the demand
     */
    public void setDateLastUpdate( Timestamp tDateLastUpdate )
    {
        _tDateLastUpdate = tDateLastUpdate;
    }

    /**
     * get the closure date of the demand
     * 
     * @return the closure date of the demand
     */
    public Timestamp getDateClose( )
    {
        return _tDateClose;
    }

    /**
     * Set the closure date of the demand
     * 
     * @param tDateClose
     *            the closure date
     */
    public void setDateClose( Timestamp tDateClose )
    {
        _tDateClose = tDateClose;
    }

    /**
     * Get the code form of the demand
     * 
     * @return the code form of the demand
     */
    public String getCodeForm( )
    {
        return _strCodeForm;
    }

    /**
     * Set the code form of the demand
     * 
     * @param strCodeForm
     *            the code form of the demand
     */
    public void setCodeForm( String strCodeForm )
    {
        _strCodeForm = strCodeForm;
    }

    /**
     * Get the guid attached to the demand
     * 
     * @return the guid of the demand
     */
    public String getGuid( )
    {
        return _strGuid;
    }

    /**
     * Set the guid attached to the demand
     * 
     * @param strGuid
     *            the guid attached to the demand
     */
    public void setGuid( String strGuid )
    {
        _strGuid = strGuid;
    }

    /**
     * Get the state attached to the demand
     * 
     * @return the state of the demand
     */
    public State getState( )
    {
        return _state;
    }

    /**
     * Set the state of the demand
     * 
     * @param state
     *            the state of the demand
     */
    public void setState( State state )
    {
        _state = state;
    }

    /**
     * Get the list of actions for the demand
     * 
     * @return the list of workflow actions
     */
    public List<Action> getListActions( )
    {
        return _listActions;
    }

    /**
     * Set the list of actions for the demand
     * 
     * @param listActions
     *            the list of workflow actions
     */
    public void setListActions( List<Action> listActions )
    {
        _listActions = listActions;
    }

    /**
     * Get the attribute demand list of the demand
     * 
     * @return the attribute demand list
     */
    public List<AttributeDemand> getListAttributeDemand( )
    {
        return _listAttributeDemand;
    }

    /**
     * Set the attribute demand list of the demand
     * 
     * @param listAttributeDemand
     *            the attributeDemand list of the demand
     */
    public void setListAttributeDemand( List<AttributeDemand> listAttributeDemand )
    {
        _listAttributeDemand = listAttributeDemand;
    }

}
