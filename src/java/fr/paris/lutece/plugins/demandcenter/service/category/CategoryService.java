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
package fr.paris.lutece.plugins.demandcenter.service.category;

import fr.paris.lutece.plugins.demandcenter.service.tree.Tree;
import fr.paris.lutece.plugins.demandcenter.business.category.Category;
import fr.paris.lutece.plugins.demandcenter.business.category.CategoryHome;
import fr.paris.lutece.plugins.demandcenter.business.category.CategoryType;
import fr.paris.lutece.plugins.demandcenter.business.category.CategoryTypeHome;
import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import java.util.List;
import java.util.Locale;

public class CategoryService
{

    private final String KEY_DEFAULT_CATEGORY_TYPE_LABEL = "demandcenter.create_categorytype.default.label";
    private static CategoryTree _treeCategories;

    private static CategoryService _instance;

    private CategoryService( )
    {
    };

    /**
     * Get the instance of CategoryService
     * 
     * @return the instance of categoryService
     */
    public static CategoryService getInstance( )
    {
        if ( _instance == null )
        {
            _instance = new CategoryService( );
        }
        _treeCategories = CategoryTreeCacheService.getInstance( ).getResource( );
        return _instance;
    }

    /**
     * Return the category tree
     * 
     * @return the category tree
     */
    public Tree<Category, CategoryType> getCategoriesTree( )
    {
        return _treeCategories;
    }

    /**
     * Find a category by code in the category tree
     * 
     * @param strCode
     *            the category code
     * @return the category coresponding to given code
     */
    public Category findByCode( String strCode )
    {
        return _treeCategories.findByCode( strCode );
    }

    /**
     * Find a category by id in the category tree
     * 
     * @param nId
     *            the category id
     * @return the category corresponding to given id
     */
    public Category findById( int nId )
    {
        return _treeCategories.findNodeById( nId );
    }

    /**
     * Find the assignee unit of given category (recursively found in parent)
     * 
     * @param category
     *            the category
     * @return the assignee unit
     */
    public Unit findAssigneeUnit( Category category )
    {
        Unit unit = category.getDefaultAssignUnit( );
        if ( unit.getIdUnit( ) != -1 )
        {
            return unit;
        }
        while ( unit.getIdUnit( ) == -1 && category.getCategoryType( ).getNbDepth( ) > 0 )
        {
            category = category.getParent( );
            unit = category.getDefaultAssignUnit( );
            if ( unit.getIdUnit( ) != -1 )
            {
                return unit;
            }
        }
        return null;
    }

    /**
     * Create a sub category (based on his parent id)
     * 
     * @param subCategory
     *            the subcategory to create
     * @return the created category
     */
    public Category createSubCategory( Category subCategory )
    {
        if ( subCategory.getParent( ) == null )
        {
            // Try to get root category type
            CategoryType childCategoryType = CategoryTypeService.getInstance( ).findByNbDepth( 0 );
            if ( childCategoryType == null )
            {
                CategoryType newCategoryType = new CategoryType( );
                newCategoryType.setDepth( 1 );
                newCategoryType.setLabel( I18nService.getLocalizedString( KEY_DEFAULT_CATEGORY_TYPE_LABEL, Locale.getDefault( ) ) );
                CategoryTypeHome.create( childCategoryType );
                subCategory.setCategoryType( newCategoryType );
            }
            else
            {
                subCategory.setCategoryType( childCategoryType );
            }
        }
        else
        {
            // Try to get child category type
            Category parentCategory = subCategory.getParent( );
            CategoryType categoryTypeParent = parentCategory.getCategoryType( );
            CategoryType childCategoryType = CategoryTypeService.getInstance( ).findByNbDepth( categoryTypeParent.getDepth( ) + 1 );

            if ( childCategoryType == null )
            {
                CategoryType newCategoryType = new CategoryType( );
                newCategoryType.setDepth( categoryTypeParent.getDepth( ) + 1 );
                newCategoryType.setLabel( I18nService.getLocalizedString( KEY_DEFAULT_CATEGORY_TYPE_LABEL, Locale.getDefault( ) ) );
                CategoryTypeHome.create( newCategoryType );
                subCategory.setCategoryType( newCategoryType );
            }
            else
            {
                subCategory.setCategoryType( childCategoryType );
            }
        }
        CategoryHome.create( subCategory );

        return subCategory;
    }

    /**
     * Get the branch of a category (from root to category)
     * 
     * @param category
     * @return the branch of categories from root to category
     */
    public List<Category> getBranchOfCategory( Category category )
    {
        return _treeCategories.getBranch( category );
    }
    
    /**
     * 
     */
    public static boolean isAutorized ( Category category, String strPermission,  AdminUser user )
    {
        List<Category> listCategory = CategoryService.getInstance( ).getBranchOfCategory(
                    CategoryService.getInstance( ).findById( category.getId( ) ) );
            for ( Category categoryBranch : listCategory )
            {
                if ( RBACService.isAuthorized( categoryBranch, strPermission, user ) )
                {
                    return true;
                }
            }
        return false;
    }
}
