/*
 * Copyright (c) 2002-2015, Mairie de Paris
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
package fr.paris.lutece.plugins.demandcenter.web.user;

import fr.paris.lutece.plugins.demandcenter.web.util.ModelUtils;
import fr.paris.lutece.portal.service.prefs.AdminUserPreferencesService;
import fr.paris.lutece.portal.service.prefs.IUserPreferencesService;
import fr.paris.lutece.portal.util.mvc.admin.MVCAdminJspBean;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;

import org.apache.commons.lang.StringUtils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * This class provides the user interface to manage user preferences
 *
 */
@Controller( controllerJsp = "UserPreferences.jsp", controllerPath = "jsp/admin/plugins/demandcenter/", right = "USER_PREFERENCES_MANAGEMENT" )
public class UserPreferencesJspBean extends MVCAdminJspBean
{
    protected static final String CONTROLLER_JSP = "UserPreferences.jsp";
    protected static final String CONTROLLER_RIGHT = "TICKETING_USER_PREFERENCES_MANAGEMENT";

     /**
     * Generated serial id
     */
    private static final long serialVersionUID = 591571470516993886L;
    
    //Markers
    private static final String MARK_CREATION_DATE_AS_DATE = "creation_date_as_date";
    private static final String MARK_USER_SIGNATURE = "user_signature";
    
    // Parameters
    private static final String PARAMETER_CREATION_DATE_DISPLAY = "creation_date_display";
    private static final String PARAMETER_USER_SIGNATURE = "user_signature";
    
    // templates
    private static final String TEMPLATE_MANAGE_USER_PREFERENCES = "/admin/plugins/demandcenter/user/manage_user_preferences.html";

    // Messages
    private static final String PROPERTY_PAGE_TITLE_MANAGE_USER_PREFERENCES = "demandcenter.manage_user_preferences.pageTitle";

    // Infos
    private static final String INFO_USER_PREFERENCES_SAVED = "demandcenter.info.user.preferences.saved";

    // Views
    private static final String VIEW_MANAGE_USER_PREFERENCES = "manageUserPreferences";

    // Actions
    private static final String ACTION_MODIFY_USER_PREFERENCES = "modifyUserPreferences";

    //User preferences
    private static final String USER_PREFERENCE_CREATION_DATE_DISPLAY = "user_creation_date_display_date";
    private static final String USER_PREFERENCE_CREATION_DATE_DISPLAY_DATE = "date";
    private static final String USER_PREFERENCE_SIGNATURE = "user_signature";
    
    // Services
    private static IUserPreferencesService _userPreferencesService = AdminUserPreferencesService.instance( );

    /**
     * Gives the page to manage user preferences
     * 
     * @param request
     *            the request
     * @return the page
     */
    @View( value = VIEW_MANAGE_USER_PREFERENCES, defaultView = true )
    public String getManageUserPreferences( HttpServletRequest request )
    {
        Map<String, Object> model = getModel( );
        int nUserId = getUser( ).getUserId( );

        String strCreationDateDisplay = _userPreferencesService.get( String.valueOf( nUserId ), USER_PREFERENCE_CREATION_DATE_DISPLAY,
                StringUtils.EMPTY );
        model.put( MARK_CREATION_DATE_AS_DATE, USER_PREFERENCE_CREATION_DATE_DISPLAY_DATE.equals( strCreationDateDisplay ) );

        String strUserSignature = _userPreferencesService.get( String.valueOf( nUserId ), USER_PREFERENCE_SIGNATURE, StringUtils.EMPTY );
        model.put( MARK_USER_SIGNATURE, strUserSignature );

        ModelUtils.storeRichText( request, model );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_USER_PREFERENCES, TEMPLATE_MANAGE_USER_PREFERENCES, model );
    }

    /**
     * Modifies the user preferences
     * 
     * @param request
     *            the request
     * @return the page after modification
     */
    @Action( ACTION_MODIFY_USER_PREFERENCES )
    public String doModifyUserPreferences( HttpServletRequest request )
    {
        int nUserId = getUser( ).getUserId( );

        String strCreationDateDisplay = request.getParameter( PARAMETER_CREATION_DATE_DISPLAY );
        _userPreferencesService.put( String.valueOf( nUserId ), USER_PREFERENCE_CREATION_DATE_DISPLAY, strCreationDateDisplay );

        String strUserSignature = request.getParameter( PARAMETER_USER_SIGNATURE );
        _userPreferencesService.put( String.valueOf( nUserId ), USER_PREFERENCE_SIGNATURE, strUserSignature );

        addInfo( INFO_USER_PREFERENCES_SAVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_USER_PREFERENCES );
    }
}
