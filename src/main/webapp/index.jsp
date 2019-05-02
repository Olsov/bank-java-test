<%--
  Created by IntelliJ IDEA.
  User: Icomp
  Date: 16/06/2016
  Time: 22:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <script src="js/jquery.js" type="text/javascript"></script>
    <script src="js/simple.js" type="text/javascript"></script>
    <title>${adress}</title>
</head>
<body>
<div class="wrapper">

    <div class="name_top">
      <a href="${adress}">  <h3>Your Home not bank</h3></a>
        <hr>
    </div>
    <div class="mid">
        <div class="info">
            <span>${name}</span>
        </div>
        <form action="" method="get">

            <input type="hidden" name="view" value="${viewname}"/>


            <div class="left">
                <input type="submit"  name="add_for_shure" value=""/><label id="accept">Accept Withdraw </label>
                <input type="submit"  name="getMoney" value=""/><label>Withdraw Money</label>
            </div>
            <div id="writeArea">
                <input name="moneycalc" placeholder="Amount money to put "/>
                <input name="money" placeholder="Write here"/>
            </div>
            <div class="right">

                <label>Only if you're admin</label><input type="submit"  name="getZip" value=""/>
                <p>
                    <label> Put money</label><input type="submit"  name="putMoney" value="" />
                </p>
            </div>
        </form>

    </div>

</div>
</body>
</html>
