<jsp:useBean id="managedemandcenterCategory" scope="session" class="fr.paris.lutece.plugins.demandcenter.web.CategoryJspBean" />
<% String strContent = managedemandcenterCategory.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
