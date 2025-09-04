$(document).ready(function () {
    let isEditMode = false;
    let editBusNumber = "";

    $("#bus-form").on("input", function () {
        let busNumber = $("#busNumber").val().trim();
        let busModel = $("#busModel").val().trim();
        let seatCount = $("#seatCount").val().trim();
        let busType = $("#busType").val().trim();

        $("#addBusButton").prop("disabled", !(busNumber && busModel && seatCount && busType));
    });

    $("#bus-form").on("submit", function (event) {
        event.preventDefault();

        let busData = {
            bus_number: $("#busNumber").val(),
            bus_name: $("#busModel").val(),
            total_seats: $("#seatCount").val(),
            bus_type: $("#busType").val()
        };

        if (isEditMode) {
            // Update existing bus
            $.ajax({
                url: "http://localhost:8080/api/v1/journeyPass/bus/update",
                method: "PUT",
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("authToken"),
                    "Content-Type": "application/json"
                },
                data: JSON.stringify(busData),
                success: function (response) {
                    new Noty({
                        type: "success",
                        layout: "topRight",
                        text: "Bus updated successfully!",
                        timeout: 2000
                    }).show();

                    resetForm();
                    loadAllBuses();
                },
                error: function (xhr) {
                    new Noty({
                        type: "error",
                        layout: "topRight",
                        text: "Failed to update bus: " + xhr.responseText,
                        timeout: 2000
                    }).show();
                }
            });
        } else {
            // Add new bus
            $.ajax({
                url: "http://localhost:8080/api/v1/journeyPass/bus/post",
                method: "POST",
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("authToken"),
                    "Content-Type": "application/json"
                },
                data: JSON.stringify(busData),
                success: function (response) {
                    new Noty({
                        type: "success",
                        layout: "topRight",
                        text: "Bus added successfully!",
                        timeout: 2000
                    }).show();

                    resetForm();
                    loadAllBuses();
                },
                error: function (xhr) {
                    new Noty({
                        type: "error",
                        layout: "topRight",
                        text: "Failed to add bus: " + xhr.responseText,
                        timeout: 2000
                    }).show();
                }
            });
        }
    });

    function loadAllBuses() {
        $.ajax({
            url: "http://localhost:8080/api/v1/journeyPass/bus/getAll",
            type: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("authToken"),
                "Content-Type": "application/json"
            },
            success: function (buses) {
                let tbody = $("#bus-tbody");
                tbody.empty();

                if (buses.length > 0) {
                    buses.forEach((bus) => {
                        let row = `<tr>
                            <td>${bus.bus_number}</td>
                            <td>${bus.bus_name}</td>
                            <td>${bus.total_seats}</td>
                            <td>${bus.bus_type}</td>
                            <td>
                                <button class="btn btn-sm btn-outline-dark edit-bus" data-bus='${JSON.stringify(bus)}'><i class="fas fa-pen"></i></button>
                                <button class="btn btn-sm btn-outline-danger delete-bus" data-bus-number="${bus.bus_number}"><i class="fas fa-trash"></i></button>
                            </td>
                        </tr>`;
                        tbody.append(row);
                    });
                } else {
                    tbody.append("<tr><td colspan='5'>No buses available</td></tr>");
                }
            },
            error: function () {
                new Noty({
                    type: "error",
                    layout: "topRight",
                    text: "Failed to load bus details!",
                    timeout: 2000
                }).show();
            }
        });
    }

    function resetForm() {
        $("#bus-form")[0].reset();
        $("#addBusButton").prop("disabled", true);
        $("#busNumber").prop("disabled", false);
        isEditMode = false;
        editBusNumber = "";
        $("#addBusModal").modal("hide");
        $("#addBusButton").text("Add Bus");
        $("#addBusModalLabel").text("Add New Bus");
    }

    // Handle Edit Bus button click
    $(document).on("click", ".edit-bus", function () {
        let busData = $(this).data("bus");

        $("#busNumber").val(busData.bus_number).prop("disabled", true);
        $("#busModel").val(busData.bus_name);
        $("#seatCount").val(busData.total_seats);
        $("#busType").val(busData.bus_type);

        isEditMode = true;
        editBusNumber = busData.bus_number;

        $("#addBusButton").prop("disabled", false).text("Update Bus");
        $("#addBusModalLabel").text("Update Bus Details");
        $("#addBusModal").modal("show");
    });

    loadAllBuses();
});

$(document).ready(function () {
    // DELETE BUS
    $(document).on("click", ".delete-bus", function () {
        let busNumber = $(this).data("bus-number");
        let row = $(this).closest("tr");

        // SweetAlert2 Confirmation
        Swal.fire({
            title: "Are you sure?",
            text: `Do you really want to delete bus number ${busNumber}?`,
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#d33",
            cancelButtonColor: "#3085d6",
            confirmButtonText: "Yes, delete it!",
            cancelButtonText: "No, cancel"
        }).then((result) => {
            if (result.isConfirmed) {
                // Send AJAX DELETE request
                $.ajax({
                    url: "http://localhost:8080/api/v1/journeyPass/bus/delete",
                    type: "DELETE",
                    headers: {
                        "Authorization": "Bearer " + localStorage.getItem("authToken"),
                        "Content-Type": "application/json"
                    },
                    data: JSON.stringify({ bus_number: busNumber }),
                    success: function (response) {
                        if (response.code === 201) {
                            new Noty({
                                type: "success",
                                layout: "topRight",
                                text: "Bus deleted successfully!",
                                timeout: 2000
                            }).show();
                            row.remove(); // Remove row from table
                        } else {
                            new Noty({
                                type: "error",
                                layout: "topRight",
                                text: "Failed to delete bus!",
                                timeout: 2000
                            }).show();
                        }
                    },
                    error: function () {
                        new Noty({
                            type: "error",
                            layout: "topRight",
                            text: "Error deleting bus!",
                            timeout: 2000
                        }).show();
                    }
                });
            }
        });
    });
});

// Search Bus
$(document).ready(function () {
    $("#searchBus").on("keypress", function (event) {
        if (event.which === 13) { // Enter key pressed
            let busNumber = $(this).val().trim();

            if (busNumber === "") {
                return;
            }

            $.ajax({
                url: `http://localhost:8080/api/v1/journeyPass/bus/search?bus_number=${busNumber}`,
                type: "GET",
                headers: {
                    "Authorization": "Bearer " + (localStorage.getItem("authToken") || ""),
                    "Content-Type": "application/json"
                },
                success: function (response) {
                    console.log("Response received:", response); // Debugging log

                    if (response && response.data) { // Ensure response has data
                        let bus = response.data;

                        new Noty({
                            type: "success",
                            layout: "center",
                            text: `Bus Found! <br>
                               Number: ${bus.bus_number} <br>
                               Model: ${bus.bus_name} <br>
                               Seats: ${bus.total_seats} <br>
                               Type: ${bus.bus_type}`,
                            timeout: 4000
                        }).show();
                    } else {
                        new Noty({
                            type: "error",
                            layout: "topRight",
                            text: `Bus number ${busNumber} not found!`,
                            timeout: 2000
                        }).show();
                    }
                },
                error: function (xhr) {
                    console.error("Search error:", xhr.responseText); // Debugging log

                    let errorMessage = "Error searching for bus!";
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
});