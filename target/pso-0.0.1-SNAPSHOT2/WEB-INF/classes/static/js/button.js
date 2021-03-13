$(document).ready(function () {
    $( 'button[type=button]' ).on('click', function(){
        var cursorPos = $('#function').prop('selectionStart');
        var v = $('#function').val();
        var textBefore = v.substring(0,  cursorPos );
        var textAfter  = v.substring( cursorPos, v.length );
        $('#function').val( textBefore+ $(this).text() +textAfter );

    });
});
