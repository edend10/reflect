      function drawChart() {

         $.get("/bathroom/stats/last-week", function(data) {
             var container = document.getElementById('chart');
             var chart = new google.visualization.Timeline(container);
             var dataTable = new google.visualization.DataTable();
             dataTable.addColumn({ type: 'string', id: 'Day Of The Week' });
             dataTable.addColumn({ type: 'string', id: 'Date' });
             dataTable.addColumn({type: 'date', id: 'Start'});
             dataTable.addColumn({ type: 'date', id: 'End' });
             var rows = [];

             rows.push(['DAY', 'DATE', new Date(0,0,0,0,0,0), new Date(0,0,0,23,59,59)])

             var sittings = data.sittings;
             for (var i = 0; i < sittings.length; i++) {
                var sitting = sittings[i];
                var startTime = sitting.startTime;
                var endTime = sitting.endTime;
                if (startTime.dayOfWeek !== endTime.dayOfWeek) {
                    rows.push([ startTime.dayOfWeek,
                                       formatDate(startTime),
                                       new Date(0,0,0, startTime.hour, startTime.minute,0),
                                       new Date(0,0,0, 23,59,0) ]);
                    rows.push([ endTime.dayOfWeek,
                                       formatDate(endTime),
                                       new Date(0,0,0, 0,0,0),
                                       new Date(0,0,0, endTime.hour, endTime.minute,0) ]);
                } else {
                    rows.push([ startTime.dayOfWeek,
                       formatDate(startTime),
                       new Date(0,0,0, startTime.hour, startTime.minute,0),
                       new Date(0,0,0, endTime.hour, endTime.minute,0) ]);
                }
             }

             rows.sort(compareSittings);


             dataTable.addRows(rows);

             var options = {
               timeline: {
                            colorByRowLabel: true,
                            showBarLabels: false
                         },
               backgroundColor: '#ffd',
               height:1800

             };

             chart.draw(dataTable, options);
         });
      }

      function formatDate(dateTime) {
           return dateTime.monthValue + '/' + dateTime.dayOfMonth+ '/' + dateTime.year;
      }

      function compareSittings(a,b) {
          var date1 = Date.parse(a[1])
          var date2 = Date.parse(b[1])

          if (isNaN(date1)) {
            return -1;
          }

          if (isNaN(date2)) {
            return 1;
          }

          if (date1 < date2) {
            return -1;
          }

          if (date1 > date2) {
            return 1;
          }

          return 0;
      }


$(function () {
    google.charts.load("current", {packages:["timeline"]});
    google.charts.setOnLoadCallback(drawChart);
});