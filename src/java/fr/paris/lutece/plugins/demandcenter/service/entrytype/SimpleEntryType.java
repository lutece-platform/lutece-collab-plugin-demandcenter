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
package fr.paris.lutece.plugins.demandcenter.service.entrytype;

public enum SimpleEntryType
{
    TEXT( "text","/admin/plugins/demandcenter/entry/read_only_entry_type_text.html"), 
    DATE( "date","/admin/plugins/demandcenter/entry/read_only_entry_type_date.html" ),
    IMAGE( "image","/admin/plugins/demandcenter/entry/read_only_entry_type_image.html" );
    
    private String _strType;
    private String _strTemplate;
    
    SimpleEntryType( String strType, String strTemplate )
    {
        _strType = strType;
        _strTemplate = strTemplate;
    }

    public String getType()
    {
        return _strType;
    }

    public void setType( String strType )
    {
        _strType = strType;
    }

    public String getTemplate()
    {
        return _strTemplate;
    }

    public void setTemplate( String strTemplate )
    {
        _strTemplate = strTemplate;
    }
    
    public static SimpleEntryType findByType( String strType )
    {
        for ( SimpleEntryType entryType : SimpleEntryType.values() )
        {
            if ( entryType.getType().equals( strType ) )
            {
                return entryType;
            }
        }
        return null;        
    }
   
}
