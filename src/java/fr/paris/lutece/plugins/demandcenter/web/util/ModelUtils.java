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
package fr.paris.lutece.plugins.demandcenter.web.util;

import fr.paris.lutece.plugins.demandcenter.business.demand.Demand;
import fr.paris.lutece.plugins.demandcenter.service.demand.DemandIdService;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.prefs.AdminUserPreferencesService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.util.AppPathService;

import org.apache.commons.lang.StringUtils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Class providing utility methods for for Model
 */
public final class ModelUtils
{
    //Markers
    private static final String MARK_TICKET_MODIFICATION_RIGHT = "demand_modification_right";
    private static final String MARK_TICKET_DELETION_RIGHT = "demand_deletion_right";
    private static final String MARK_WEBAPP_URL = "webapp_url";
    private static final String MARK_LOCALE = "locale";
    private static final String MARK_USER_SIGNATURE = "user_signature";
    
    //User Preferences Keys
    private static final String USER_PREFERENCE_SIGNATURE = "user_signature";
   
    /**
     * Default constructor
     */
    private ModelUtils( )
    {
    }

    /**
     * Completes the specified model for user rights
     * 
     * @param model
     *            the model to complete
     * @param adminUser
     *            user the user
     */
    public static void storeTicketRights( Map<String, Object> model, AdminUser adminUser )
    {
        if ( RBACService.isAuthorized( new Demand( ), Demand.PERMISSION_DELETE, adminUser ) )
        {
            model.put( MARK_TICKET_DELETION_RIGHT, Boolean.TRUE );
        }

        if ( RBACService.isAuthorized( new Demand( ), Demand.PERMISSION_MODIFY, adminUser ) )
        {
            model.put( MARK_TICKET_MODIFICATION_RIGHT, Boolean.TRUE );
        }
    }

    /**
     * Completes the specified model for rich text
     * 
     * @param request
     *            the request
     * @param model
     *            the model to complete
     */
    public static void storeRichText( HttpServletRequest request, Map<String, Object> model )
    {
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, AdminUserService.getLocale( request ).getLanguage( ) );
    }

    /**
     * Completes the specified model for the user signature
     * 
     * @param request
     *            the request
     * @param model
     *            the model to complete
     */
    public static void storeUserSignature( HttpServletRequest request, Map<String, Object> model )
    {
        String strUserSignature = AdminUserPreferencesService.instance( ).get( String.valueOf( AdminUserService.getAdminUser( request ).getUserId( ) ),
                USER_PREFERENCE_SIGNATURE, StringUtils.EMPTY );

        model.put( MARK_USER_SIGNATURE, strUserSignature );
    }
}
