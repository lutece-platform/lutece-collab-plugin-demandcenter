<jsp:useBean id="managedemandcenterContactMode" scope="session" class="fr.paris.lutece.plugins.demandcenter.web.ContactModeJspBean" />
<% String strContent = managedemandcenterContactMode.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
