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
package fr.paris.lutece.plugins.demandcenter.service.demand.attributes;

import fr.paris.lutece.plugins.demandcenter.business.attributes.Attribute;
import fr.paris.lutece.plugins.demandcenter.business.attributes.AttributeDemand;
import fr.paris.lutece.plugins.demandcenter.business.attributes.AttributeHome;
import fr.paris.lutece.plugins.demandcenter.business.web.rs.dto.AnswersDto;
import fr.paris.lutece.plugins.demandcenter.business.web.rs.dto.FormSubmitDto;
import fr.paris.lutece.plugins.demandcenter.business.web.rs.dto.QuestionDto;
import fr.paris.lutece.plugins.demandcenter.business.web.rs.service.EntriesDtoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alexandre
 */
public class DemandAttributeService
{
    /**
     * Get demand attributs from formSubmit
     * 
     * @param formSubmit
     * @return the demand attributes list of the demand
     */
    public static List<AttributeDemand> getAttributesDemandFromFormSubmit( FormSubmitDto formSubmit )
    {
        List<AttributeDemand> listAttributeDemand = new ArrayList<AttributeDemand>( );

        // Recursively find all questions/answers of the form submit
        Map<QuestionDto, AnswersDto> mapQuestionAnswers = EntriesDtoService.getRecursiveMapQuestionAnswers( formSubmit.getEntries( ) );

        // Find all attributes codes defined in BO as user attributes
        List<Attribute> listAttribute = AttributeHome.getAttributesList( );

        for ( Map.Entry<QuestionDto, AnswersDto> entry : mapQuestionAnswers.entrySet( ) )
        {
            QuestionDto question = entry.getKey( );
            AnswersDto answers = entry.getValue( );
            for ( Attribute attribute : listAttribute )
            {
                if ( attribute.getCode( ).equals( question.getIdentityAttrCode( ) ) )
                {
                    AttributeDemand attributeDemand = new AttributeDemand( );
                    attributeDemand.setAttribute( attribute );
                    if ( answers.getListAnswers( ).size( ) == 1 )
                    {
                        attributeDemand.setValue( answers.getListAnswers( ).get( 0 ).getValue( ) );
                        listAttributeDemand.add( attributeDemand );
                    }

                }
            }
        }

        return listAttributeDemand;

    }

    /**
     * Check if identity exists with given guid
     * 
     * @param strGuid
     *            the guid of the identity to check
     * @return true if exists, false otherwise.
     */
    public static boolean checkIdentityExists( String strGuid )
    {
        return true;
        // TODO
    }

}
