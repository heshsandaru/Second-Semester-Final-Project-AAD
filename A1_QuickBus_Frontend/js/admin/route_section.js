$(document).ready(function () {
    let isEditMode = false;
    let editRouteNumber = "";

    // Enable button only if all fields are filled
    $("#route-form").on("input", function () {
        let routeNumber = $("#routeNumber").val().trim();
        let routeDeparture = $("#routeDepature").val().trim();
        let routeDestination = $("#routeDestination").val().trim();
        let routeBusStops = $("#routeBusStops").val().trim();
        let routeDistance = $("#routeDistance").val().trim();
        let routeEstimatedTime = $("#routeEstimatedTime").val().trim();

        $("#addRouteButton").prop("disabled", !(routeNumber && routeDeparture && routeDestination && routeBusStops && routeDistance && routeEstimatedTime));
    });

    // Submit Route Form (Add or Update)
    $("#route-form").on("submit", function (event) {
        event.preventDefault();

        let routeEstimatedTime = $("#routeEstimatedTime").val().trim();

        // Ensure the time format is correct (HH:mm:ss)
        if (routeEstimatedTime && !routeEstimatedTime.includes(":")) {
            routeEstimatedTime += ":00";  // Add seconds if not already present
        }

        let routeData = {
            route_number: $("#routeNumber").val().trim(),
            departure: $("#routeDepature").val().trim(),
            destination: $("#routeDestination").val().trim(),
            stops: $("#routeBusStops").val().trim().split(","), // FIXED: Convert stops to array
            distance: $("#routeDistance").val().trim(),
            estimated_time: routeEstimatedTime // Send the time in the correct format
        };

        console.log(routeData)
        let ajaxOptions = {
            url: isEditMode
                ? "http://localhost:8080/api/v1/JourneyPass/route/update"
                : "http://localhost:8080/api/v1/JourneyPass/route/post",
            type: isEditMode ? "PUT" : "POST", // FIXED: Corrected "method" to "type"
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("authToken"),
                "Content-Type": "application/json"
            },
            data: JSON.stringify(routeData),
            success: function (response) {
                new Noty({
                    type: "success",
                    layout: "topRight",
                    text: isEditMode ? "Route updated successfully!" : "Route added successfully!",
                    timeout: 2000
                }).show();

                resetForm();
                loadAllRoutes();
            },
            error: function (xhr) {
                new Noty({
                    type: "error",
                    layout: "topRight",
                    text: "Failed to process route: " + xhr.responseText,
                    timeout: 2000
                }).show();
            }
        };

        $.ajax(ajaxOptions);
    });

    function resetForm() {
        $("#route-form")[0].reset();
        $("#addRouteButton").prop("disabled", true);
        $("#routeNumber").prop("disabled", false);
        isEditMode = false;
        editRouteNumber = "";
        $("#addRouteModal").modal("hide");
        $("#addRouteButton").text("Add Route");
        $("#addRouteModalLabel").text("Add New Route");
    }


    function loadAllRoutes() {
        $.ajax({
            url: "http://localhost:8080/api/v1/JourneyPass/route/getRoutesWithStops",
            type: "GET",
            headers: {
                "Authorization": "Bearer " + (localStorage.getItem("authToken") || ""),
                "Content-Type": "application/json"
            },
            success: function (routes) {
                let tbody = $("#routeTbody"); // Correct selector
                tbody.empty(); // Clear existing data

                if (routes && routes.length > 0) {
                    routes.forEach((route) => {
                        let busStops = (route.stops && route.stops.length > 0) ? route.stops.join(", ") : "No stops available";

                        let row = `<tr>
                        <td>${route.route_number}</td>
                        <td>${route.departure}</td>
                        <td>${route.destination}</td>
                        <td>${busStops}</td>
                        <td>${route.distance} Km</td>
                        <td>${route.estimated_time}</td>
                        <td>
                            <button class="btn btn-sm btn-outline-dark edit-route" data-route='${JSON.stringify(route)}' style="margin-bottom: 5px;"><i class="fas fa-pen"></i></button>
                            <button class="btn btn-sm btn-outline-danger delete-route" data-route-number="${route.route_number}"><i class="fas fa-trash"></i></button>
                        </td>
                    </tr>`;
                        tbody.append(row);
                    });
                } else {
                    tbody.append("<tr><td colspan='7' class='text-center'>No routes available</td></tr>");
                }
            },
            error: function (xhr, status, error) {
                console.error("Error loading routes:", xhr.responseText);
                new Noty({
                    type: "error",
                    layout: "topRight",
                    text: "Failed to load routes! Please check the API response.",
                    timeout: 2000
                }).show();
            }
        });
    }

// Load routes when the page is ready
    $(document).ready(function () {
        loadAllRoutes();
    });

    function resetForm() {
        $("#route-form")[0].reset();
        $("#addRouteButton").prop("disabled", true);
        $("#routeNumber").prop("disabled", false);
        isEditMode = false;
        editRouteNumber = "";
        $("#addRouteModal").modal("hide");
        $("#addRouteButton").text("Add Route");
        $("#addRouteModalLabel").text("Add New Route");
    }

    // Handle Edit Route button click
    $(document).on("click", ".edit-route", function () {
        let routeData = $(this).data("route");

        // Set the form fields with the current route data
        $("#routeNumber").val(routeData.route_number).prop("disabled", true);
        $("#routeDepature").val(routeData.departure);
        $("#routeDestination").val(routeData.destination);
        $("#routeDistance").val(routeData.distance);
        $("#routeEstimatedTime").val(routeData.estimated_time);

        // Set the bus stops - join the array into a comma-separated string
        $("#routeBusStops").val(routeData.stops.join(", "));

        // Enable editing mode
        isEditMode = true;
        editRouteNumber = routeData.route_number;

        // Update button text for update mode
        $("#addRouteButton").prop("disabled", false).text("Update Route");
        $("#addRouteModalLabel").text("Update Route Details");

        // Show the modal
        $("#addRouteModal").modal("show");
    });

    // Delete Route
    $(document).on("click", ".delete-route", function () {
        let routeNumber = $(this).data("route-number");
        let row = $(this).closest("tr");

        Swal.fire({
            title: "Are you sure?",
            text: `Do you really want to delete route number ${routeNumber}?`,
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#d33",
            cancelButtonColor: "#3085d6",
            confirmButtonText: "Yes, delete it!",
            cancelButtonText: "No, cancel"
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: "http://localhost:8080/api/v1/JourneyPass/route/delete",
                    type: "DELETE",
                    headers: {
                        "Authorization": "Bearer " + localStorage.getItem("authToken"),
                        "Content-Type": "application/json"
                    },
                    data: JSON.stringify({ route_number: routeNumber }),
                    success: function (response) {
                        if (response.code === 200) {
                            new Noty({
                                type: "success",
                                layout: "topRight",
                                text: "Route deleted successfully!",
                                timeout: 2000
                            }).show();
                            row.remove();
                        } else {
                            new Noty({
                                type: "error",
                                layout: "topRight",
                                text: "Failed to delete route!",
                                timeout: 2000
                            }).show();
                        }
                    },
                    error: function () {
                        new Noty({
                            type: "error",
                            layout: "topRight",
                            text: "Error deleting route!",
                            timeout: 2000
                        }).show();
                    }
                });
            }
        });
    });

    // Search Route
    $("#searchRoute").on("keypress", function (event) {
        if (event.which === 13) { // Enter key pressed
            let routeNumber = $(this).val().trim();

            if (routeNumber === "") {
                return;
            }

            $.ajax({
                url: `http://localhost:8080/api/v1/JourneyPass/route/search?route_number=${routeNumber}`,
                type: "GET",
                headers: {
                    "Authorization": "Bearer " + (localStorage.getItem("authToken") || ""),
                    "Content-Type": "application/json"
                },
                success: function (response) {
                    if (response.message === "Route Found") {
                        new Noty({
                            type: "success",
                            layout: "center",
                            text: `Route Found! <br> Number: ${routeNumber}`,
                            timeout: 4000
                        }).show();
                    } else {
                        new Noty({
                            type: "error",
                            layout: "topRight",
                            text: `Route number ${routeNumber} not found!`,
                            timeout: 2000
                        }).show();
                    }
                },
                error: function (xhr) {
                    let errorMessage = "Error searching for route!";
                    if (xhr.responseText) {
                        errorMessage = `Server Error: ${xhr.responseText}`;
                    }

                    new Noty({
                        type: "error",
                        layout: "topRight",
                        text: errorMessage,
                        timeout: 2000
                    }).show();
                }
            });
        }
    });

    loadAllRoutes();

});