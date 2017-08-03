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

import fr.paris.lutece.plugins.demandcenter.business.category.Category;
import fr.paris.lutece.plugins.demandcenter.business.demand.Demand;
import fr.paris.lutece.plugins.demandcenter.service.category.CategoryService;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import java.util.ArrayList;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class RBACDemandFilter implements IDemandFilter
{
    private final AdminUser _user;

    private static final String RBAC_DEMAND_FILTER = "rbac_demand_filter";

    /**
     * Constructor of RBAC demand filter
     * 
     * @param request
     *            the HttpServletRequest
     */
    public RBACDemandFilter( HttpServletRequest request )
    {
        _user = AdminUserService.getAdminUser( request );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Demand> processFilter( List<Demand> listDemands )
    {
        List<Demand> listFilteredDemand = new ArrayList<Demand>( );
        for ( Demand demand : listDemands )
        {
            List<Category> listCategory = CategoryService.getInstance( ).getBranchOfCategory(
                    CategoryService.getInstance( ).findById( demand.getCategory( ).getId( ) ) );
            for ( Category category : listCategory )
            {
                if ( RBACService.isAuthorized( category, Category.PERMISSION_VIEW, _user ) )
                {
                    listFilteredDemand.add( demand );
                    break;
                }
            }
        }
        return listFilteredDemand;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFilterName( )
    {
        return RBAC_DEMAND_FILTER;
    }

}
