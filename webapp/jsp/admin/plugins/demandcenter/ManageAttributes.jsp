<jsp:useBean id="managedemandcenterAttribute" scope="session" class="fr.paris.lutece.plugins.demandcenter.web.AttributeJspBean" />
<% String strContent = managedemandcenterAttribute.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
