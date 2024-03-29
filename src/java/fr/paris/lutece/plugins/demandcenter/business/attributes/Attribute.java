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

import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import java.io.Serializable;

/**
 * This is the business class for the object Attribute
 */
public class Attribute implements Serializable
{
    private static final long serialVersionUID = 1L;

    // Variables declarations
    private int _nId;

    @NotEmpty( message = "#i18n{demandcenter.validation.attribute.Code.notEmpty}" )
    @Size( max = 255, message = "#i18n{demandcenter.validation.attribute.Code.size}" )
    private String _strCode;

    @NotEmpty( message = "#i18n{demandcenter.validation.attribute.Label.notEmpty}" )
    @Size( max = 255, message = "#i18n{demandcenter.validation.attribute.Label.size}" )
    private String _strLabel;

    private boolean _bMandatory;

    private boolean _bMayBeFilledByIdentityService;

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
     * Returns the Code
     * 
     * @return The Code
     */
    public String getCode( )
    {
        return _strCode;
    }

    /**
     * Sets the Code
     * 
     * @param strCode
     *            The Code
     */
    public void setCode( String strCode )
    {
        _strCode = strCode;
    }

    /**
     * Returns the Label
     * 
     * @return The Label
     */
    public String getLabel( )
    {
        return _strLabel;
    }

    /**
     * Sets the Label
     * 
     * @param strLabel
     *            The Label
     */
    public void setLabel( String strLabel )
    {
        _strLabel = strLabel;
    }

    /**
     * Returns the Mandatory
     * 
     * @return The Mandatory
     */
    public boolean getMandatory( )
    {
        return _bMandatory;
    }

    /**
     * Sets the Mandatory
     * 
     * @param bMandatory
     *            The Mandatory
     */
    public void setMandatory( boolean bMandatory )
    {
        _bMandatory = bMandatory;
    }

    /**
     * Returns the MayBeFilledByIdentityService
     * 
     * @return The MayBeFilledByIdentityService
     */
    public boolean getMayBeFilledByIdentityService( )
    {
        return _bMayBeFilledByIdentityService;
    }

    /**
     * Sets the MayBeFilledByIdentityService
     * 
     * @param bMayBeFilledByIdentityService
     *            The MayBeFilledByIdentityService
     */
    public void setMayBeFilledByIdentityService( boolean bMayBeFilledByIdentityService )
    {
        _bMayBeFilledByIdentityService = bMayBeFilledByIdentityService;
    }
}
