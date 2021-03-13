google.charts.load('current', {packages: ['corechart', 'line']});
google.charts.setOnLoadCallback(draw);

var a;
function getData(array) {
    a = new Array(array.length);
    array.forEach(function(item, i) {
        a[i]=item;
    });
}

function draw() {
    var MAX = a.length;
    var options = {
        width: 600,
        height: 300,
        animation: {
            duration: 1000,
            easing: 'in'
        },
        series: {
            1: {curveType: 'function'}
        },
        hAxis: {
            viewWindow: {min:0, max:MAX},
            viewWindowMode:'explicit',
            title: 'Ітерації'
        },
        vAxis: {
            viewWindow: { min: 0 },
            viewWindowMode: "explicit"
        }
    };
    var chart = new google.visualization.LineChart(
        document.getElementById('visualization'));
    var data = new google.visualization.DataTable();
    data.addColumn('number', 'X');
    data.addColumn('number', 'f(x)');
    a.forEach(function(item, i) {
        data.addRow([i,item])
    });
    var prevButton = document.getElementById('prev');
    var nextButton = document.getElementById('next');
    var changeZoomButton = document.getElementById('change');
    function drawChart() {
        prevButton.disabled = true;
        nextButton.disabled = true;
        changeZoomButton.disabled = true;
        google.visualization.events.addListener(chart, 'ready',
            function() {
                prevButton.disabled = options.hAxis.viewWindow.min <= 0;
                nextButton.disabled = options.hAxis.viewWindow.max >= MAX;
                changeZoomButton.disabled = false;
            });
        chart.draw(data, options);
    }

    prevButton.onclick = function() {
        options.hAxis.viewWindow.min -= 10;
        options.hAxis.viewWindow.max -= 10;
        drawChart();
    }
    nextButton.onclick = function() {
        options.hAxis.viewWindow.min += 10;
        options.hAxis.viewWindow.max += 10;
        drawChart();
    }
    var zoomed = true;
    changeZoomButton.onclick = function() {
        if (zoomed) {
            options.hAxis.viewWindow.min = 0;
            options.hAxis.viewWindow.max = 20;
            $(this).html('-');
        } else {
            options.hAxis.viewWindow.min = 0;
            options.hAxis.viewWindow.max = MAX;
            $(this).html('+');
        }
        zoomed = !zoomed;
        drawChart();
    }
    drawChart();
}
