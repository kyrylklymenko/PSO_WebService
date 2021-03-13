$(document).ready(function () {
    $("#myselect").change(function () {
        var action = "canonical/" + $("#myselect option:selected").attr('value');
        $("#form").attr("action", action);
        $("#functionImage").attr('src', '/images/' + $("#myselect option:selected").attr('value') + '.PNG');
    });
    $("#hybridselect").change(function () {
        var action = "hybrid/" + $("#hybridselect option:selected").attr('value');
        console.log(action);
        $("#form").attr("action", action);
        $("#functionImage").attr('src', '/images/' + $("#hybridselect option:selected").attr('value') + '.PNG');
    });
    $("#adaptiveselect").change(function () {
        var action = "adaptive/" + $("#adaptiveselect option:selected").attr('value');
        console.log(action);
        $("#form").attr("action", action);
        $("#functionImage").attr('src', '/images/' + $("#adaptiveselect option:selected").attr('value') + '.PNG');
    });

    $(window).on('load', function () {
        $('input[type=number]' ).each(function() {
            var l = $(this).val();
            $(this).attr("step", getStep(l));
        });
    });

    $('input[type=number]').on('keyup', function () {
        var l =$(this).val();
        $(this).attr("step", getStep(l));
    });

    function getStep(val) {
        if(val.toString().split(".")[1]!=null)
            return 1/Math.pow(10,val.toString().split(".")[1].length);
        else{
            if(val.toString().indexOf("-")>0){
                if(val.toString().indexOf("e")>0){
                    return Math.pow(10,Number(val.toString().split("e")[1]));
                }
                if(val.toString().indexOf("E")>0){
                    return Math.pow(10,Number(val.toString().split("E")[1]));
                }
            }
        }
        return 0;
    }
});