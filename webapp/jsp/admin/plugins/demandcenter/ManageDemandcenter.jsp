<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="managedemandcenter" scope="session" class="fr.paris.lutece.plugins.demandcenter.web.ManageDemandcenterJspBean" />

<% managedemandcenter.init( request, managedemandcenter.RIGHT_MANAGEDEMANDCENTER ); %>
<%= managedemandcenter.getManageDemandcenterHome ( request ) %>

<%@ include file="../../AdminFooter.jsp" %>
