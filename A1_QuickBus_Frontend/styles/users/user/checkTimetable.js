$(document).ready(function () {
    $(".table-container").hide();

    $(".search-btn").click(function () {
        let departure = $("input[list='from-list-time']").val();
        let destination = $("input[list='to-list-time']").val();
        let busType = $("#bus-type").val();
        console.log("ooooo")
        if (!departure || !destination) {
            $(".table-container").hide();
            new Noty({
                type: "error",
                text: "Please select both departure and destination!",
                timeout: 2000
            }).show();
            return;
        }

        let apiUrl = `http://localhost:8080/api/v1/JourneyPass/schedule/search2?departure=${departure}&destination=${destination}`;
        if (busType) {
            apiUrl += `&busType=${busType}`;
        }
        $(".banner.bg-image").hide();
        $.ajax({
            url: apiUrl,
            type: "GET",
            success: function (data) {
                console.log("Received Data:", data);
                let tableBody = $("table tbody");
                tableBody.empty();

                if (data.length === 0) {
                    $(".table-container").hide();
                    new Noty({
                        type: "warning",
                        text: "No schedules found for the selected route.",
                        timeout: 2000
                    }).show();
                } else {
                    $(".table-container").show();
                    data.forEach((schedule) => {
                        let row = `<tr>
                        <td>${schedule.departure_time}</td>
                        <td>${schedule.arrival_time}</td>
                        <td>${schedule.route_number}</td>
                        <td>${schedule.bus_number}</td>
                        <td>${schedule.bus_type}</td>
                      </tr>`;
                        tableBody.append(row);
                    });
                }
            },
            error: function () {
                $(".table-container").hide();
                new Noty({
                    type: "error",
                    text: "Failed to load schedules. Please try again.",
                    timeout: 2000
                }).show();
            }
        });
    });
});