<jsp:useBean id="managedemandcenterDemand" scope="session" class="fr.paris.lutece.plugins.demandcenter.web.DemandJspBean" />
<% String strContent = managedemandcenterDemand.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
