// load bus numbers
$(document).ready(function () {
    function loadBusNumbers() {
        $.ajax({
            url: "http://localhost:8080/api/v1/journeyPass/bus/numbers", // Ensure the correct API endpoint
            type: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("authToken"),
                "Content-Type": "application/json"
            },
            success: function (buses) {
                let busDropdown = $("#scheduleBusNumber");
                busDropdown.empty().append('<option value="" disabled selected>Select Bus Number</option>');

                buses.forEach(busNumber => { // Directly append busNumber
                    busDropdown.append(`<option value="${busNumber}">${busNumber}</option>`);
                });
            },
            error: function () {
                console.error("Failed to load bus numbers!");
            }
        });
    }

    loadBusNumbers();
});

// load route numbers
$(document).ready(function () {
    function loadRouteNumbers() {
        $.ajax({
            url: "http://localhost:8080/api/v1/JourneyPass/route/numbers", // Ensure the correct API endpoint
            type: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("authToken"),
                "Content-Type": "application/json"
            },
            success: function (routes) {
                let routeDropdown = $("#scheduleRouteNumber");
                routeDropdown.empty().append('<option value="" disabled selected>Select Bus Number</option>');

                routes.forEach(route_number => { // Directly append busNumber
                    routeDropdown.append(`<option value="${route_number}">${route_number}</option>`);
                });
            },
            error: function () {
                console.error("Failed to load route numbers!");
            }
        });
    }

    loadRouteNumbers();
});

// load all details of schedule
function loadAllSchedules() {
    $.ajax({
        url: "http://localhost:8080/api/v1/JourneyPass/schedule/getAll",
        type: "GET",
        headers: {
            "Authorization": "Bearer " + (localStorage.getItem("authToken") || ""),
            "Content-Type": "application/json"
        },
        success: function (schedules) {
            let tbody = $("#schedule-tbody"); // Correct selector
            tbody.empty(); // Clear existing data

            if (schedules && schedules.length > 0) {
                schedules.forEach((schedule) => {
                    let row = `<tr>
                        <td>${schedule.schedule_id}</td>
                        <td>${schedule.arrival_time}</td>
                        <td>${schedule.departure_time}</td>
                        <td>${schedule.available_seats}</td>
                        <td>Rs. ${schedule.price}</td>
                        <td>${schedule.bus_number}</td>
                        <td>${schedule.route_number}</td>
                        <td>
                            <button class="btn btn-sm btn-outline-dark edit-schedule" data-schedule='${JSON.stringify(schedule)}'><i class="fas fa-pen"></i></button>
                            <button class="btn btn-sm btn-outline-danger delete-schedule" data-schedule-id="${schedule.scheduleId}"><i class="fas fa-trash"></i></button>
                        </td>
                    </tr>`;
                    tbody.append(row);
                });
            } else {
                tbody.append("<tr><td colspan='8' class='text-center'>No schedules available</td></tr>");
            }
        },
        error: function (xhr, status, error) {
            console.error("Error loading schedules:", xhr.responseText);
            new Noty({
                type: "error",
                layout: "topRight",
                text: "Failed to load schedules! Please check the API response.",
                timeout: 2000
            }).show();
        }
    });
}

// Call the function when the document is ready
$(document).ready(function () {
    loadAllSchedules();
});

$(document).ready(function () {
    let isEditMode = false;
    let editScheduleId = "";

    $("#schedule-form input, #schedule-form select").on("input change", function () {
        $("#createScheduleButton").prop("disabled", false);
    });

    $("#schedule-form").on("submit", function (event) {
        event.preventDefault();

        let sArrivalTime = $("#scheduleArrivalTime").val().trim();
        let sDepartureTime = $("#scheduleDepartureTime").val().trim();

        if (sArrivalTime && !sArrivalTime.match(/^\d{2}:\d{2}:\d{2}$/)) {
            sArrivalTime += ":00";
        }
        if (sDepartureTime && !sDepartureTime.match(/^\d{2}:\d{2}:\d{2}$/)) {
            sDepartureTime += ":00";
        }

        let scheduleData = {
            schedule_id: $("#scheduleId").val().trim(),
            arrival_time: sArrivalTime,
            departure_time: sDepartureTime,
            available_seats: parseInt($("#scheduleAvailableSeats").val().trim(), 10),
            price: parseFloat($("#scheduleTicketPrice").val().trim()),
            bus_number: $("#scheduleBusNumber").val().trim(),
            route_number: $("#scheduleRouteNumber").val().trim(),
        };

        console.log("Sending Data:", scheduleData);

        $.ajax({
            url: isEditMode ? "http://localhost:8080/api/v1/JourneyPass/schedule/update" : "http://localhost:8080/api/v1/JourneyPass/schedule/post",
            type: isEditMode ? "PUT" : "POST",
            headers: {
                Authorization: "Bearer " + localStorage.getItem("authToken"),
                "Content-Type": "application/json",
            },
            data: JSON.stringify(scheduleData),
            success: function () {
                new Noty({ type: "success", layout: "topRight", text: isEditMode ? "Schedule updated successfully!" : "Schedule created successfully!", timeout: 2000 }).show();
                resetForm();
                loadAllSchedules();
            },
            error: function (xhr) {
                console.error("Error Response:", xhr);
                new Noty({ type: "error", layout: "topRight", text: "Failed to process schedule: " + xhr.responseText, timeout: 2000 }).show();
            },
        });
    });

    function resetForm() {
        $("#schedule-form")[0].reset();
        $("#createScheduleButton").prop("disabled", true).text("Create Schedule");
        $("#scheduleId").prop("disabled", false);
        isEditMode = false;
        editScheduleId = "";
        $("#addScheduleModal").modal("hide");
    }

    $(document).on("click", ".edit-schedule", function () {
        let scheduleData = $(this).data("schedule");

        $("#scheduleId").val(scheduleData.schedule_id).prop("disabled", true);
        $("#scheduleArrivalTime").val(scheduleData.arrival_time);
        $("#scheduleDepartureTime").val(scheduleData.departure_time);
        $("#scheduleAvailableSeats").val(scheduleData.available_seats);
        $("#scheduleTicketPrice").val(scheduleData.price);
        $("#scheduleBusNumber").val(scheduleData.bus_number);
        $("#scheduleRouteNumber").val(scheduleData.route_number);

        isEditMode = true;
        editScheduleId = scheduleData.schedule_id;

        $("#createScheduleButton").prop("disabled", false).text("Update Schedule");
        $("#addScheduleModalLabel").text("Update Schedule Details");
        $("#addScheduleModal").modal("show");
    });

    $(document).on("click", ".delete-schedule", function () {
        let scheduleId = $(this).data("schedule-id");

        Swal.fire({
            title: "Are you sure?",
            text: "You won't be able to revert this!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#d33",
            cancelButtonColor: "#3085d6",
            confirmButtonText: "Yes, delete it!",
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: `http://localhost:8080/api/v1/JourneyPass/schedule/delete`,
                    type: "DELETE",
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("authToken"),
                        "Content-Type": "application/json",
                    },
                    data: JSON.stringify({ schedule_id: scheduleId }),
                    success: function () {
                        new Noty({ type: "success", layout: "topRight", text: "Schedule deleted successfully!", timeout: 2000 }).show();
                        loadAllSchedules();
                    },
                    error: function (xhr) {
                        console.error("Delete Error:", xhr);
                        new Noty({ type: "error", layout: "topRight", text: "Failed to delete schedule: " + xhr.responseText, timeout: 2000 }).show();
                    },
                });
            }
        });
    });

    function loadAllSchedules() {
        $.ajax({
            url: "http://localhost:8080/api/v1/JourneyPass/schedule/getAll",
            type: "GET",
            headers: {
                Authorization: "Bearer " + (localStorage.getItem("authToken") || ""),
                "Content-Type": "application/json",
            },
            success: function (schedules) {
                let tbody = $("#schedule-tbody");
                tbody.empty();

                if (schedules && schedules.length > 0) {
                    schedules.forEach((schedule) => {
                        let row = `<tr>
                            <td>${schedule.schedule_id}</td>
                            <td>${schedule.arrival_time}</td>
                            <td>${schedule.departure_time}</td>
                            <td>${schedule.available_seats}</td>
                            <td>Rs. ${schedule.price}</td>
                            <td>${schedule.bus_number}</td>
                            <td>${schedule.route_number}</td>
                            <td>
                                <button class="btn btn-sm btn-outline-dark edit-schedule" data-schedule='${JSON.stringify(schedule)}'><i class="fas fa-pen"></i></button>
                                <button class="btn btn-sm btn-outline-danger delete-schedule" data-schedule-id="${schedule.schedule_id}"><i class="fas fa-trash"></i></button>
                            </td>
                        </tr>`;
                        tbody.append(row);
                    });
                } else {
                    tbody.append("<tr><td colspan='8' class='text-center'>No schedules available</td></tr>");
                }
            },
            error: function (xhr) {
                console.error("Load Error:", xhr);
                new Noty({ type: "error", layout: "topRight", text: "Failed to load schedules!", timeout: 2000 }).show();
            },
        });
    }

    loadAllSchedules();
});