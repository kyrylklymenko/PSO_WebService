google.charts.load('current', {packages: ['corechart', 'line']});
google.charts.setOnLoadCallback(drawCurveTypes);

var a;
function getData(array) {
    a = new Array(array.length);
    array.forEach(function(item, i) {
        a[i]=item;
    });
}

function drawCurveTypes() {
    var data = new google.visualization.DataTable();
    data.addColumn('number', 'X');
    data.addColumn('number', 'f(x)');
    a.forEach(function(item, i) {
        data.addRow([i,item])
        console.log("x["+ i + "] = " + item);
    });

    var options = {
        hAxis: {
            title: 'Ітерації'
        },
        vAxis: {
            title: '',
            0: {side: 'top'}
        },
        series: {
            1: {curveType: 'function'}
        },
        width: 600,
        height: 300
    };
    var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
    chart.draw(data, options);
}