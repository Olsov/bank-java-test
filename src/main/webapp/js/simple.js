/**
 * Created by Icomp on 19/06/2016.
 */
$(document).ready(function(){
    $("input[name='putMoney']").on("mouseover",function(){
        $("input[name='view']").val("add");
        $("input[name='moneycalc']").show(500);
        $("input[name='money']").animate({width:"12%"},500);
    });

    $("input[name='putMoney']").on("click",function(){
        if($("input[name='moneycalc']").val()==""){
            $("input[name='moneycalc']").css("border","1px solid red");

            return false;
        }
        else
        {
            $("input[name='moneycalc']").css("border","1px solid green");
        }

    });


    $("input[name='getMoney']").on("mouseover",function(){
        $("input[name='view']").val("get");
        beHide();
    });


    $("input[name='add_for_shure']").on("mouseover",function(){
        $("input[name='view']").val("add_for_shure");
        beHide();
    });
    $("input[name='getZip']").on("mouseover",function(){
        $("input[name='view']").val("download");
        beHide();
    });

    if(($("input[name='view']").val()=="add_for_shure")){
        $("input[name='add_for_shure']").css("visibility","visible");
        $("#accept").css("visibility","visible");

    }
    $("input[type='submit']").on("click",function(){

        if($("input[name='money']").val()=="")
        {
            $("input[name='money']").css("border","1px solid red");
            return false;
        }
        else
        {
            $("input[name='money']").css("border","1px solid green");
        }
    });
    function beHide()
    {
        $("input[name='moneycalc']").hide(500);
        $("input[name='money']").animate({width:"30%"},500);
    }

});