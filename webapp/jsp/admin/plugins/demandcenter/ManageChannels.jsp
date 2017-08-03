<jsp:useBean id="managedemandcenterChannel" scope="session" class="fr.paris.lutece.plugins.demandcenter.web.ChannelJspBean" />
<% String strContent = managedemandcenterChannel.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
