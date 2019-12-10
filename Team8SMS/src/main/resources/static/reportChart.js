var ctx = document.getElementById('barChart').getContext('2d');
var chart = new Chart(ctx, {
    // The type of chart we want to create
    type: 'bar',

    // The data for our dataset
    data: {
        labels: ['A+', 'A', 'A-', 'B+', 'B', 'B-', 'C+', 'C', 'D+', 'D'],
        datasets: [{
            label: 'Student Count of Grades',
            backgroundColor: 'rgb(150, 60, 90)',
            borderColor: 'rgb(0, 0, 0)',
            data: [12, 10, 15, 12, 13, 14, 15, 8, 14, 15],
            borderWidth: 1,

        }],
    },

    // Configuration options go here
    options: {
        layout: {
            padding: {
                left: 50,
                right: 50,
                top: 0,
                bottom: 0
            }
        },
        scales: {
            yAxes: [{
                ticks: {
                    suggestedMin: 0,
                    suggestedMax: 30
                }
            }]
        }
    }
});