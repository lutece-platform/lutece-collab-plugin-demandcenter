<jsp:useBean id="managedemandcenterCategoryType" scope="session" class="fr.paris.lutece.plugins.demandcenter.web.CategoryTypeJspBean" />
<% String strContent = managedemandcenterCategoryType.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
