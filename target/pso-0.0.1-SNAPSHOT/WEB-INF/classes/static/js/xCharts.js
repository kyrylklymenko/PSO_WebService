google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

var x;
function getX(array) {
    x = new Array(array.length);
    array.forEach(function(item, i) {
        x[i]=item;
    });
}
var speed=1000;
var maxX;
function setMAXX(x) {
    maxX=x;
}
var o=0;
var start = 0;

function drawChart () {

    function genData () {
        if(o>=x.length)
            o=x.length-1;
        o+=start;
        return x[o-start];
    }

    var data = new google.visualization.DataTable();
    data.addColumn('number', 'X');
    data.addColumn('number', 'Y');

    data.addRows(genData());

    var options = {
        width: 600,
        height: 400,
        title:"k = "+o,
        colors: ['#1E90FF'],
        pointSize: 5,
        animation: {
            duration: speed,
            easing: 'linear',
            startup: true
        },
        series: {
            1: {curveType: 'function'}
        },
        vAxis: {
            viewWindow: {min:-maxX, max:maxX},
            viewWindowMode:'explicit',
            title: 'x2',
            gridlines: {
                count: 10
            }
        },
        hAxis: {
            viewWindow: {min:-maxX, max:maxX},
            viewWindowMode:'explicit',
            title: 'x1',
            gridlines: {
                count: 10
            }
        },
        enableInteractivity: false
    };
    var retryButton = document.getElementById('retry');
    var fastButton = document.getElementById('faster');
    var slowButton = document.getElementById('slowly');
    var stopButton = document.getElementById('stop');
    var startButton = document.getElementById('start');
    var plusButton = document.getElementById('plus');
    var minusButton = document.getElementById('minus');
    stopButton.disabled = true;
    retryButton.onclick = function() {
        o=0;
    }
    plusButton.onclick = function() {
        maxX/=2;
    }
    minusButton.onclick = function() {
        maxX*=2;
    }
    startButton.onclick = function() {
        startButton.disabled = true;
        stopButton.disabled = false;
        start = 1;
    }
    stopButton.onclick = function() {
        startButton.disabled = false;
        stopButton.disabled = true;
        start = 0;
    }
    fastButton.onclick = function() {
        if(speed>250){
            slowButton.disabled = false;
            speed-=250;
        }
        else {
            fastButton.disabled = true;
        }
    }
    slowButton.onclick = function() {
        if(speed<=2500){
            fastButton.disabled=false;
            speed+=250;
        }else{
            slowButton.disabled = true;
        }

    }
    var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));

    google.visualization.events.addListener(chart, 'animationfinish', animate);

    chart.draw(data, options);

    function animate() {
        data = new google.visualization.DataTable();
        data.addColumn('number', 'X');
        data.addColumn('number', 'Y');
        data.addRows(genData());
        options.title="k = "+o;
        options.animation.duration = speed;
        options.vAxis.viewWindow.max=maxX;
        options.vAxis.viewWindow.min=-maxX;
        options.hAxis.viewWindow.max=maxX;
        options.hAxis.viewWindow.min=-maxX;
        chart.draw(data, options);

    }
}