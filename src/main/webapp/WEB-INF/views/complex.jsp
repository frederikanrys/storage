<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Page Title</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.0/jquery.mobile-1.3.0.min.css" />
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
    <script src="http://code.jquery.com/mobile/1.3.0/jquery.mobile-1.3.0.min.js"></script>

    <link href="/photoswipe/3.0.5/photoswipe.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="/photoswipe/3.0.5/lib/klass.min.js"></script>
    <script type="text/javascript" src="/photoswipe/3.0.5/code.photoswipe-3.0.5.js"></script>


    <script src="/scripts/photoswipe.js"></script>

</head>
    <script>
        $(document).on('panelcreate', function(){
            $( "#nav-panel" ).panel( "open");
        });

    </script>

<body>

<div data-role="page" id="page">

    <div data-role="header" data-position="fixed">
        <h1>Fixed header</h1>
        <a href="#nav-panel" data-icon="bars" data-iconpos="notext">Menu</a>
    </div><!-- /header -->
    <div data-role="content" >
        <ul id="Gallery" class="gallery" data-role="listview" data-inset="true">
            <c:forEach var="resource" items="${resources}">
                <c:if test="${resource.type == 'file'}">


                        <c:set var="image" value="${fn:startsWith(resource.contentType, 'image') ? 'image' : ''}"/>

                    <%--<li><a href="${resource.url}" rel="external"> ${resource.name} </a></li>--%>
                    <li>
                        <a href="${resource.url}" rel="external" class="${image}" >
                            <%--<img src="/images/mime_icons/${resource.extension}.png">--%>
                            ${resource.name}
                        </a>
                    </li>
                </c:if>
            </c:forEach>
        </ul>

    </div><!-- /content -->
    <div data-role="footer" data-position="fixed" >
        <h4>Fixed footer</h4>
    </div><!-- /footer -->
    <div data-role="panel" data-position="left" data-position-fixed="false" data-display="reveal" data-theme="a" id="nav-panel" data-dismissible="false">
        <ul data-role="listview" data-theme="a" class="nav-search">


            <li><a href="${parent.url}" rel="external">..</a></li>

            <c:forEach var="resource" items="${resources}">
                <c:if test="${resource.type == 'directory'}">
                    <li><a href="${resource.url}" rel="external"> ${resource.name} </a></li>
                </c:if>
            </c:forEach>



        </ul>
    </div><!-- /panel -->
</div><!-- /page -->

</body>
</html>