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

import fr.paris.lutece.plugins.demandcenter.business.service.IEntryTypeService;
import fr.paris.lutece.plugins.demandcenter.business.web.rs.dto.AnswersDto;
import fr.paris.lutece.plugins.demandcenter.business.web.rs.dto.EntryDto;
import fr.paris.lutece.plugins.demandcenter.business.web.rs.dto.QuestionDto;
import static fr.paris.lutece.plugins.demandcenter.service.entrytype.SimpleEntryType.DATE;
import static fr.paris.lutece.plugins.demandcenter.service.entrytype.SimpleEntryType.IMAGE;
import static fr.paris.lutece.plugins.demandcenter.service.entrytype.SimpleEntryType.TEXT;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class SimpleEntryTypeService implements IEntryTypeService
{

    private final String MARK_QUESTION = "question";
    private final String MARK_LIST_ANSWER = "list_answers";
    
    @Override
    public String getReadOnlyHtml( EntryDto entry, Locale locale )
    {
        QuestionDto question = entry.getQuestion();
        AnswersDto answers = entry.getAnswers();
        SimpleEntryType entryType = SimpleEntryType.findByType( question.getType( ) );
        if ( entryType != null )
        {
            Map<String,Object> model = new HashMap<>();
            

            model.put( MARK_QUESTION, question );
            model.put( MARK_LIST_ANSWER, answers.getListAnswers() );

            switch ( entryType )
            {
                //Used to put others values in model if necessary
                case TEXT:
                    break;
                case IMAGE:
                    break;
                case DATE:
                break;
            }
            return AppTemplateService.getTemplate( entryType.getTemplate(), locale, model ).getHtml( );   
        }
        return StringUtils.EMPTY;
    }
    
}
