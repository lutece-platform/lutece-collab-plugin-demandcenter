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
package fr.paris.lutece.plugins.demandcenter.business.demand.filter;

import fr.paris.lutece.plugins.demandcenter.business.demand.Demand;
import fr.paris.lutece.plugins.demandcenter.web.panel.DemandPanel;
import fr.paris.lutece.plugins.unittree.business.unit.UnitHome;
import fr.paris.lutece.plugins.unittree.service.unit.UnitService;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class PanelDemandFilter implements IDemandFilter
{
    public static final String PANEL_DEMAND_FILTER = "panel_demand_filter";

    private DemandPanel _panel;
    private AdminUser _user;

    /**
     * Constructor of panel demand filter
     * 
     * @param demandPanel
     *            the demand panel
     * @param request
     *            the request
     */
    public PanelDemandFilter( DemandPanel demandPanel, HttpServletRequest request )
    {
        _panel = demandPanel;
        _user = AdminUserService.getAdminUser( request );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Demand> processFilter( List<Demand> listDemands )
    {
        List<Demand> listDemandAfterFilter = new ArrayList<>( );
        for ( Demand demand : listDemands )
        {
            switch( _panel )
            {
                case AGENT:
                    if ( demand.getAssigneeUser( ).getUserId( ) == _user.getUserId( ) )
                    {
                        listDemandAfterFilter.add( demand );
                        break;
                    }
                    break;
                case GROUP:
                    if ( UnitHome.isUserInUnit( _user.getUserId( ), demand.getAssigneeUnit( ).getIdUnit( ) ) )
                    {
                        listDemandAfterFilter.add( demand );

                    }
                    break;
            }
        }
        return listDemandAfterFilter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFilterName( )
    {
        return PANEL_DEMAND_FILTER;
    }

}
