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
package fr.paris.lutece.plugins.demandcenter.web;

import fr.paris.lutece.plugins.demandcenter.business.category.Category;
import fr.paris.lutece.plugins.demandcenter.business.category.CategoryType;
import fr.paris.lutece.plugins.demandcenter.business.demand.Demand;
import fr.paris.lutece.plugins.demandcenter.business.demand.DemandHome;
import fr.paris.lutece.plugins.demandcenter.business.demand.filter.PanelDemandFilter;
import fr.paris.lutece.plugins.demandcenter.business.demand.filter.RBACDemandFilter;
import fr.paris.lutece.plugins.demandcenter.rs.exception.JsonConvertToObjectException;
import fr.paris.lutece.plugins.demandcenter.service.category.CategoryService;
import fr.paris.lutece.plugins.demandcenter.service.category.CategoryTreeCacheService;
import fr.paris.lutece.plugins.demandcenter.service.tree.Tree;
import fr.paris.lutece.plugins.demandcenter.service.demand.DemandFilterService;
import fr.paris.lutece.plugins.demandcenter.service.demand.DemandService;
import fr.paris.lutece.plugins.demandcenter.web.panel.DemandPanel;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.prefs.AdminUserPreferencesService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.web.constants.Messages;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * This class provides the user interface to manage ContactMode features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageDemands.jsp", controllerPath = "jsp/admin/plugins/demandcenter/", right = "DEMANDS_MANAGEMENT" )
public class DemandJspBean extends ManageDemandcenterJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_DEMANDS = "/admin/plugins/demandcenter/manage_demands.html";
    private static final String TEMPLATE_DETAILS_DEMAND = "/admin/plugins/demandcenter/details_demand.html";

    // Parameters
    private static final String PARAMETER_ID_DEMAND = "id_demand";
    private static final String PARAMETER_PANEL = "selected_tab";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_DEMANDS = "demandcenter.manage_demands.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_DETAILS_DEMAND = "demandcenter.details_demand.pageTitle";

    // Markers
    private static final String MARK_DEMAND_LIST = "demand_list";
    private static final String MARK_DEMAND = "demand";
    private static final String MARK_TREE_CATEGORIES = "tree";
    private static final String MARK_SELECTED_TAB = "selected_tab";
    private static final String MARK_READ_ONLY_HTML_FIELDSETS = "read_only_html_fieldsets";
    private static final String MARK_TYPE_DISPLAY_DATE = "creation_date_display";

    //User preferences 
    private static final String USER_PREFERENCE_CREATION_DATE_DISPLAY = "user_creation_date_display_date";
    private static final String USER_PREFERENCE_CREATION_DATE_COUNTER = "counter";
    
    private static final String JSP_MANAGE_DEMANDS = "jsp/admin/plugins/demandcenter/ManageDemands.jsp";

    // Properties

    // Validations

    // Views
    private static final String VIEW_MANAGE_DEMANDS = "manageDemands";
    private static final String VIEW_DETAILS_DEMAND = "demandDetails";

    // Actions

    // Infos

    // Session variable to store working values
    private Demand _demand;
    private DemandFilterService _demandFilterService;

    @Override
    public void init( HttpServletRequest request, String strRight ) throws AccessDeniedException
    {
        super.init( request, strRight );
        _demandFilterService = new DemandFilterService( );

        // Add the rbac filter for demand view
        _demandFilterService.putFilter( new RBACDemandFilter( request ) );
    }

    /**
     * Build the Manage View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_DEMANDS, defaultView = true )
    public String getManageDemands( HttpServletRequest request )
    {
        _demand = null;
        // TODO maybe put this full demand list, computed with sql joins in a Lucene Index.
        List<Demand> listDemands = DemandHome.getFullDemandsList( );
        Tree<Category, CategoryType> treeCategory = CategoryTreeCacheService.getInstance( ).getResource( );

        // Add filter for panel
        DemandPanel panel = DemandPanel.getByName( request.getParameter( PARAMETER_PANEL ) );
        if ( panel == null )
        {
            panel = DemandPanel.GROUP;
        }
        _demandFilterService.putFilter( new PanelDemandFilter( panel, request ) );

        listDemands = _demandFilterService.processFilters( listDemands );

        Map<String, Object> model = getPaginatedListModel( request, MARK_DEMAND_LIST, listDemands, JSP_MANAGE_DEMANDS );

        model.put( MARK_TYPE_DISPLAY_DATE, AdminUserPreferencesService.instance().get( 
                Integer.toString( getUser().getUserId() ), 
                USER_PREFERENCE_CREATION_DATE_DISPLAY, 
                USER_PREFERENCE_CREATION_DATE_COUNTER ) );
        model.put( MARK_SELECTED_TAB, panel.getName( ) );
        model.put( MARK_TREE_CATEGORIES, treeCategory );
        
        return getPage( PROPERTY_PAGE_TITLE_MANAGE_DEMANDS, TEMPLATE_MANAGE_DEMANDS, model );
    }

    /**
     * Build the Details view of a demand
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_DETAILS_DEMAND )
    public String getDemandDetails( HttpServletRequest request )
    {
        _demand = null;
        String strDemandId = request.getParameter( PARAMETER_ID_DEMAND );
        if ( strDemandId != null )
        {
            try
            {
                _demand = DemandService.loadDemandById( Integer.parseInt( strDemandId ) );

                if ( _demand != null )
                {
                    // check user rights according to the demand category
                    if (  !CategoryService.isAutorized( _demand.getCategory(), Category.PERMISSION_VIEW_DETAILS, getUser( ) ) )
                    {
                        return redirect( request, AdminMessageService.getMessageUrl( request, Messages.USER_ACCESS_DENIED, AdminMessage.TYPE_STOP ) );
                    }
                    
                    //Get the read only fieldsets of responses attached to the demand
                    String htmlFieldsets = DemandService.getReadOnlyFieldsetsAsHtml( _demand, request.getLocale() );

                    Map<String, Object> model = getModel( );
                    model.put( MARK_DEMAND, _demand );
                    model.put( MARK_READ_ONLY_HTML_FIELDSETS, htmlFieldsets);
                    
                    return getPage( PROPERTY_PAGE_TITLE_DETAILS_DEMAND, TEMPLATE_DETAILS_DEMAND, model );
                }
            }
            catch ( JsonConvertToObjectException e )
            {
                return redirectView( request, VIEW_MANAGE_DEMANDS );
            }
        }
        return redirectView( request, VIEW_MANAGE_DEMANDS );
        
    }

}
