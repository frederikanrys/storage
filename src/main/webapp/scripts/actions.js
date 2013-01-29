$(function ()
{

    function sleep()
    {
        $.ajax({
            url:'/sleep',
            success:function (result)
            {
                $('#actionresult').text("Server is put to sleep ").addClass("success");
            },
            error:function (result)
            {
                $('#actionresult').text("Sleeping the server FAILED " + result.responseText).addClass("error");
            },
            complete:function(result)
            {
                $('#sleepbutton').toggle();
            }
        });

    }
    function browse()
    {
       window.location.href = "/data";

    }

    $('#sleepbutton').button().click(sleep);
    $('#browsebutton').button().click(browse);

});